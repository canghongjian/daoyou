package com.example.daoyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterPage extends AppCompatActivity {
    String reg_un,reg_pw,reg_pw_again,reg_nn,reg_sex,reg_info;
    Button register_button;
    EditText editText1,editText2,editText3,editText4,editText5;
    //IP地址和端口号
    public static String IP_ADDRESS = "10.128.239.185";
    //public static String IP_ADDRESS ="192.168.1.197";
    public static int PORT = 8000;
    StringBuffer stringBuffer=new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_page);

        editText1 = (EditText) findViewById(R.id.reg_username);
        editText2 = (EditText) findViewById(R.id.reg_password);
        editText3 = (EditText) findViewById(R.id.reg_nickname);
        editText4 = (EditText) findViewById(R.id.info_findPW);
        editText5 = (EditText) findViewById(R.id.reg_password_again);
        register_button = (Button) findViewById(R.id.register);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        //以下处理性别选择
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reg_sex = RegisterPage.this.getResources().getStringArray(R.array.sexes)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //处理注册按钮事件：
        register_button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                reg_un = editText1.getText().toString();
                reg_pw = editText2.getText().toString();
                reg_nn = editText3.getText().toString();
                reg_info = editText4.getText().toString();
                reg_pw_again = editText5.getText().toString();


                /***以下处理注册逻辑，判断是否重名，是否输入为空，两次密码输入是否相同，符合条件则返回登陆主界面，并销毁该activity**/
                if (reg_un.equals("") || reg_pw.equals("") || reg_nn.equals("") || reg_info.equals("") || reg_pw_again.equals(""))
                {
                    Toast.makeText(RegisterPage.this,"输入为空，请重新输入！",Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                }
                else if (!reg_pw.equals(reg_pw_again))
                {
                    Toast.makeText(RegisterPage.this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                }
                /*
                Intent intent = new Intent();
                intent.setClass(RegisterPage.this,MainActivity.class);
                startActivity(intent);
                */
                else
                    ConnectServer(view);
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
                    String sendMessage = "register send Message:";
                    sendMessage += reg_un + "|";
                    sendMessage += reg_pw + "|";
                    sendMessage += reg_nn + "|";
                    sendMessage += reg_info + "|";
                    sendMessage += reg_sex;
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
                                    if ((stringBuffer.toString()).startsWith("register success"))
                                    {
                                        Toast.makeText(RegisterPage.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.setClass(RegisterPage.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if ((stringBuffer.toString()).startsWith("user already exists"))
                                    {
                                        Toast.makeText(RegisterPage.this,"用户名已存在！",Toast.LENGTH_SHORT).show();
                                        editText1.setText("");
                                        editText2.setText("");
                                        editText3.setText("");
                                        editText4.setText("");
                                        editText5.setText("");
                                    }
                                    else if ((stringBuffer.toString()).startsWith("nickname already exists"))
                                    {
                                        Toast.makeText(RegisterPage.this,"用户昵称已被取用！",Toast.LENGTH_SHORT).show();
                                        editText1.setText("");
                                        editText2.setText("");
                                        editText3.setText("");
                                        editText4.setText("");
                                        editText5.setText("");
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
