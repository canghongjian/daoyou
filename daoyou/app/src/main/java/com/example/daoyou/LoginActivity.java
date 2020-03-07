package com.example.daoyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {
    Button button_login, button_register, button_forgetPW;
    EditText input_name,input_password;
    String username1,password1;
    //IP地址和端口号

    public static String IP_ADDRESS = "10.128.239.185";
    //public static String IP_ADDRESS ="192.168.1.197";
    public static int PORT = 8000;
    StringBuffer stringBuffer=new StringBuffer();
    MyApplication application = (MyApplication)this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        button_login = (Button)findViewById(R.id.login);
        button_register = (Button)findViewById(R.id.registerNow);
        button_forgetPW = (Button)findViewById(R.id.forget_pw);
        input_name = (EditText)findViewById(R.id.username);
        input_password = (EditText)findViewById(R.id.password);

        //处理登录按钮事件：
        button_login.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                username1 = input_name.getText().toString();
                password1 = input_password.getText().toString();
                //以下处理登陆逻辑，包括从数据库读取用户数据判断，成功则进入主页面，失败则弹出提醒
                /*************待填写***********************/
                if (username1.equals("") || password1.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"输入为空，请重新输入！",Toast.LENGTH_SHORT).show();
                    input_name.setText("");
                    input_password.setText("");
                }
                else
                    ConnectServer(view);

            }
        });
        //处理注册按钮事件
        button_register.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterPage.class);
                startActivity(intent);
            }
        });
        //处理忘记密码按钮事件
        button_forgetPW.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,ForgetPWPage.class);
                startActivity(intent);
            }
        });
    }
    public void ConnectServer(View v){
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
                    String sendMessage = "login send Message:";
                    sendMessage += username1 + "|";
                    sendMessage += password1;
                    pw.println(sendMessage);

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
                                    if ((stringBuffer.toString()).startsWith("permitted login"))
                                    {
                                        Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
                                        //保存登陆用户状态（名字,性别,id）
                                        String getMsg = stringBuffer.toString();
                                        String userSex = getMsg.substring(15);
                                        int id = Integer.parseInt(getMsg.substring(getMsg.indexOf('|')+1));
                                        application = (MyApplication) getApplication();
                                        application.setCurrent_user(new User(username1,userSex,id));
                                        Intent intent = new Intent();
                                        intent.setClass(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if ((stringBuffer.toString()).startsWith("password error"))
                                    {
                                        Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                                        input_name.setText("");
                                        input_password.setText("");
                                    }
                                    else if ((stringBuffer.toString()).startsWith("user not existed"))
                                    {
                                        Toast.makeText(LoginActivity.this,"用户不存在！",Toast.LENGTH_SHORT).show();
                                        input_name.setText("");
                                        input_password.setText("");
                                    }
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
