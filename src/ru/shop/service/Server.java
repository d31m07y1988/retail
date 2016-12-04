package ru.shop.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.shop.model.Product;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by Ramil on 30.11.2016.
 */
public class Server {
    public final int serverPort;
    private static Logger logger = Logger.getLogger(Server.class.getSimpleName());
    private Map<String, Connection> connectedClients = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    private ProductService productService = (ProductService) applicationContext.getBean("ProductService");

    public Server(int port) {
        this.serverPort = port;
    }

    public static void main(String[] args) {
        Server server = new Server(13667);
        try (ServerSocket clientListener = new ServerSocket(server.serverPort)) {
            logger.info("Сервер стартовал");
            while (true) {
                Socket socket = clientListener.accept();
                logger.info("Соединение с клиентом установлено");
                incomeClients newClient = server.new incomeClients(socket);
                newClient.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class incomeClients extends Thread {
        private Socket socket;

        public incomeClients(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String sellerId = null;
            try (Connection connection = new Connection(socket)) {
                sellerId = serverHandshake(connection);

                while (true) {
                    String sellerRequest = connection.receive();

                    if (sellerRequest.equalsIgnoreCase("exit")) {
                        connectedClients.remove(sellerId);
                        break;
                    } else if (sellerRequest.equalsIgnoreCase("show all")) {
                        List<Product> products = productService.fetchAll();
                        connection.send(outputFormatting(products));
                    } else if (sellerRequest.startsWith("search ")) {
                        String lookingFor = sellerRequest.substring(7);
                        List<Product> allFinded = productService.findByName(lookingFor);
                        connection.send(outputFormatting(allFinded));
                    } else if (sellerRequest.startsWith("help")) {
                        connection.send("Список доступных команд: \n\t" +
                                "show all - показать все товары, \n\t" +
                                "search <name> - поиск товара по имени, \n\t" +
                                "sell <id> <count> - реализовать товар, \n\t" +
                                "exit - выход");
                    } else if (sellerRequest.startsWith("sell ")) {
                        sellProduct(sellerRequest, connection);
                    } else {
                        connection.send("команда введена не верно. Для просмотра доступных команд введите: help");
                    }
                }

                logger.info("Клиент отключился");
            } catch (SocketException e) {
                System.err.println("Связь с клиентом утеряна");
                if(sellerId!=null) {
                    connectedClients.remove(sellerId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private String serverHandshake(Connection connection) throws IOException {
            logger.info("соединение с пользователем");
            String sellerId = connection.receive();
            connectedClients.put(sellerId, connection);
            connection.send("...произведено подключение. Для просмотра доступных команд введите: help");
            return sellerId;
        }

        private String outputFormatting(List<Product> products) {
            StringBuilder totalProductList = new StringBuilder();
            products.forEach(p -> totalProductList.append(p).append("\n"));
            return totalProductList.toString();
        }

        private void sellProduct(String sellerRequest, Connection connection) throws IOException {
            String[] request = sellerRequest.split(" ");
            try {
                if (request.length != 3) throw new NumberFormatException();
                long id = Long.parseLong(request[1]);
                int count = Math.abs(Integer.parseInt(request[2]));
                if (!productService.sell(id, count)) throw new IllegalArgumentException();
                Product soldProduct = productService.fetch(id);
                connection.send("арт." + soldProduct.getId() + " осталось " + soldProduct.getCount() + "шт.");
            } catch (NumberFormatException e) {
                connection.send("не верный формат запроса");
            } catch (IllegalArgumentException e) {
                connection.send("количество товара превышает допустимые количество.");
            }
        }
    }
}