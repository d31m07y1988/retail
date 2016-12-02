package ru.shop.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Ramil on 30.11.2016.
 */
public class Client {

    private int serverPort;
    private Socket socket;
    private Connection connection;

    public Client(int port) {
        serverPort = port;
        try {
            socket = new Socket("localhost", serverPort);
            connection = new Connection(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client(13667);
        ServerMessageGetter messageGetter = client.new ServerMessageGetter();
        messageGetter.setDaemon(true);
        messageGetter.start();

        try {
            client.connection.send(new Date().toString());
        } catch (IOException e) {
            System.err.println("идентификатор не отправлен");
        }

        try (BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in))) {
            String message = "";
            while (!"exit".equalsIgnoreCase(message)) {
                message = keyboardReader.readLine();
                client.connection.send(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ServerMessageGetter extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    String serverMessage = connection.receive();
                    if (serverMessage != null)
                        System.out.println(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
