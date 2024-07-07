package com.example.lib;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MyClass {

    public static List<Socket> waitingPlayers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int port = 9999;

        ServerSocket serverSocket1 = new ServerSocket(port);

        System.out.println(InetAddress.getLocalHost());

        //启动线程
        Thread gameThread = new Thread(() -> {
            try {
                acceptConnections(serverSocket1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        gameThread.start();
    }

    private static void acceptConnections(ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("Waiting client connect");
            // 进行连接请求的接受
            Socket socket1 = serverSocket.accept();
            Socket socket2 = serverSocket.accept();
            waitingPlayers.add(socket1);
            waitingPlayers.add(socket2);
            System.out.println(waitingPlayers.size());

            // 处理Socket连接
            new Thread(new ServerSocketThread(socket1,socket2)).start();
            new Thread(new ServerSocketThread(socket2,socket1)).start();
        }
    }

}