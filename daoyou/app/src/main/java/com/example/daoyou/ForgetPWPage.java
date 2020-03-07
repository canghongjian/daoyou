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

public class ForgetPWPage extends AppCompatActivity {
    String findPW_un,findPW_pw,findPW_pw_again,findPW_info;
    Button reset_button;
    EditText editText1,editText2,editText3,editText4;
    //IP地址和端口号
    public static String IP_ADDRESS = "10.128.239.185";
    public static int PORT = 8000;
    StringBuffer stringBuffer=new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_pwpage);

        editText1 = (EditText) findViewById(R.id.username_find);
        editText2 = (EditText) findViewById(R.id.password_find);
        editText3 = (EditText) findViewById(R.id.password_find_again);
        editText4 = (EditText) findViewById(R.id.info_findPW_inForget);
        reset_button = (Button) findViewById(R.id.reset_pw);

        //处理重置按钮事件：
        reset_button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                findPW_un = editText1.getText().toString();
                findPW_pw = editText2.getText().toString();
                findPW_pw_again = editText3.getText().toString();
                findPW_info = editText4.getText().toString();



                /***以下处理重置逻辑，判断是否存在该用户，是否输入为空，两次密码输入是否相同，密保信息是否相同，符合条件则弹出消息提示，并返回登陆主界面，并销毁该activity**/
                if (findPW_un.equals("") || findPW_pw.equals("") || findPW_pw_again.equals("") || findPW_info.equals(""))
                {
                    Toast.makeText(ForgetPWPage.this,"输入为空，请重新输入！",Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                }
                else if (!findPW_pw.equals(findPW_pw_again))
                {
                    Toast.makeText(ForgetPWPage.this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                }
                /*
                Intent intent = new Intent();
                intent.setClass(RegisterPage.this,MainActivity.class);
                startActivity(intent);
                */
                else
                    ConnectServer(view);
                /*
                Intent intent = new Intent();
                intent.setClass(RegisterPage.this,MainActivity.class);
                startActivity(intent);
                */
            }
        });
        //返回后要结束activity
        //finish();
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
                    String sendMessage = "reset send Message:";
                    sendMessage += findPW_un + "|";
                    sendMessage += findPW_pw + "|";
                    sendMessage += findPW_info;
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
                                    if ((stringBuffer.toString()).startsWith("reset success"))
                                    {
                                        Toast.makeText(ForgetPWPage.this,"修改成功！",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.setClass(ForgetPWPage.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if ((stringBuffer.toString()).startsWith("user not exists"))
                                    {
                                        Toast.makeText(ForgetPWPage.this,"用户名不存在！",Toast.LENGTH_SHORT).show();
                                        editText1.setText("");
                                        editText2.setText("");
                                        editText3.setText("");
                                        editText4.setText("");
                                    }
                                    else if ((stringBuffer.toString()).startsWith("info wrong"))
                                    {
                                        Toast.makeText(ForgetPWPage.this,"密保信息错误！",Toast.LENGTH_SHORT).show();
                                        editText1.setText("");
                                        editText2.setText("");
                                        editText3.setText("");
                                        editText4.setText("");
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
