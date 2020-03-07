package com.test2.demo;
import com.DBTool.DBUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * <p>
 * 基于socket通讯-服务端
 */
public class Server {

    private static ServerSocket SERVER_SOCKET =null;;

    static{
        try {
            SERVER_SOCKET = new ServerSocket(8000);
        } catch (IOException e) {
            System.out.println("\n端口已被占用");
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            System.out.println("-----服务器已启动，等待客户端连接----");
            Socket socket = null;
            while(true){
                //循环监听客户端的连接
                socket = SERVER_SOCKET.accept();
                //新建一个线程ServerSocket，并开启
                new ServerSocketThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("-------服务器已启动，等待客户端连接----");
            Socket socket = null;

            while(true){
                //循环监听客户端的连接
                socket = SERVER_SOCKET.accept();
                //新建一个线程ServerSocket，并开启
                new ServerSocketThread(socket).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}