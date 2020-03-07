package com.example.daoyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainPage extends AppCompatActivity {
    //IP地址和端口号
    public static String IP_ADDRESS = "10.128.239.185";
    public static int PORT = 8000;
    StringBuffer stringBuffer=new StringBuffer();
    //handler
    Handler handler = null;
    Socket soc = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    String messageRecv = null;

    EditText test_Text;
    Button test_Button,test_ConnectButton;
    TextView test_View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        test_Text = (EditText)findViewById(R.id.text_edit);
        test_Button = (Button)findViewById(R.id.send_text);
        test_View = (TextView)findViewById(R.id.test_recv);
        test_ConnectButton = (Button)findViewById(R.id.connect_test);
        test_ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ConnectionThread(test_Text.getText().toString()).start();
                SendMessage(v);
            }
        });
        /*
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();           //获取消息中的Bundle对象
                String str = b.getString("data");           //获取键为data的字符串的值
                test_View.setText(str);
            }
        };*/
    }


    public void SendMessage(View v){
        Send();

    }

    public void Send(){
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket socket=new Socket(IP_ADDRESS,PORT);
                    if(socket.isConnected()){
                        System.out.println("连接主机成功");
                    }else{
                        System.out.println("连接主机失败");
                    }
                    OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

                    /* 客户端向服务器发送数据  */
                    PrintWriter pw
                            = new PrintWriter(osw, true);
                    pw.println(test_Text.getText().toString());

                    /* 客户端接收服务器数据  */
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String msgReceive = null;

                    while(true) {
                        if((msgReceive = br.readLine()) != null) {
                            stringBuffer = new StringBuffer();
                            stringBuffer.append(msgReceive);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    test_View.setText("\n"+stringBuffer.toString());
                                }
                            });
                            System.out.println("服务器说：" + msgReceive);
                        }
                    }

                } catch (IOException e) {
                    System.out.println("\nSocket的连接失败");
                    e.printStackTrace();
                }
            }
        });

        th.start();



    }
}
