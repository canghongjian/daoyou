package com.test.demo;

import com.DBTool.DBUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Server {
    ServerSocket serverSocket = null;
    public final int port = 8888;

    public Server(){

        //输出服务器的IP地址
        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("local host:"+addr);
            serverSocket = new ServerSocket(port);
            System.out.println("0k");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startService(){

        try {
            Socket socket = null;
            System.out.println("waiting...");
            //等待连接，每建立一个连接，就新建一个线程
            while(true){
                socket = serverSocket.accept();//等待一个客户端的连接，在连接之前，此方法是阻塞的
                System.out.println("connect to"+socket.getInetAddress()+":"+socket.getLocalPort());
                new ConnectThread(socket).start();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    //向客户端发送信息
    class ConnectThread extends Thread{
        Socket socket = null;

        public ConnectThread(Socket socket){
            super();
            this.socket = socket;
        }

        @Override
        public void run(){
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                while(true){
                    String msgRecv = dis.readUTF();
                    System.out.println("msg from client:"+msgRecv);
                    dos.writeUTF("received:"+msgRecv);
                    dos.flush();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //new Server().startService();
        try
        {
            Connection con= DBUtil.getConnection();
            Statement stmt=con.createStatement();
            String ID = "admin";
            String PW = "admin";
            //mysql数据库中的数据表，表名叫：demotable ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
            //String sql="select * from test.user where login_name="+ID+" and login_password="+PW;
            String sql="select * from user";
            ResultSet rs=stmt.executeQuery(sql);


            while(rs.next())
            {

                int id = rs.getInt(1);
                String login_name = rs.getString(2);
                String login_password = rs.getString(3);
                String username = rs.getString(4);
                String sex = rs.getString(5);
                System.out.println(sex);

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DBUtil.Close();

        }
    }
}
