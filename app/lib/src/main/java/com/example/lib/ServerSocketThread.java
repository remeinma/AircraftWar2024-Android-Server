package com.example.lib;


import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class ServerSocketThread extends Thread{
    private BufferedReader in;
    private PrintWriter pw;
    private PrintWriter ypw;
    private Socket msocket;
    private Socket ysocket;
    String content = "";
    public ServerSocketThread(Socket msocket,Socket ysocket) throws IOException {
        this.msocket = msocket;
        this.ysocket = ysocket;

        in = new BufferedReader(new InputStreamReader(msocket.getInputStream()));
        pw = new PrintWriter(new OutputStreamWriter(msocket.getOutputStream(), StandardCharsets.UTF_8),true);
        ypw = new PrintWriter(new OutputStreamWriter(ysocket.getOutputStream(), StandardCharsets.UTF_8),true);

    }

    @Override
    public void run(){
        try {
            //开始游戏
            if(MyClass.waitingPlayers.size()==2)
            {
                pw.println("start");
            }

            while ((content = in.readLine())!=null){
                if(content.equals("end")){
                    //结束游戏
                    MyClass.waitingPlayers.remove(msocket);
                    System.out.println(MyClass.waitingPlayers.size());
                    if(MyClass.waitingPlayers.size()==0){
                        break;
                    }
                }else {
                    ypw.println(content);
                }
            }

            if(MyClass.waitingPlayers.size()==0){
                pw.println("gameover");
                System.out.println("gameover");
                ypw.println("gameover");
                System.out.println("gameover");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
