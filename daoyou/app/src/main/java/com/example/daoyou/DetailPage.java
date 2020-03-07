package com.example.daoyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class DetailPage extends AppCompatActivity {
    private TextView captain_name,captain_sex,team_detail;
    private ImageView back;
    private Button detailSureButton;
    MyApplication application = (MyApplication) this.getApplication();
    private String c_name,c_sex,detail;
    private int cap_id,team_id;
    //查询该队伍是否已被加入或已满
    private boolean isJoined = false;
    private boolean isFull = false;
    public static String IP_ADDRESS = "10.128.239.185";
    //public static String IP_ADDRESS = "192.168.1.197";
    public static int PORT = 8000;
    StringBuffer stringBuffer = new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_detail_page);
        captain_name = findViewById(R.id.captain_name);
        captain_sex = findViewById(R.id.captain_sex);
        team_detail = findViewById(R.id.team_detail);
        back = findViewById(R.id.back_main_Button);
        detailSureButton = findViewById(R.id.detailSureButton);
        Intent intent = getIntent();
        detail = ((Intent) intent).getStringExtra("teamdetail");
        System.out.println(((Intent) intent).getStringExtra("teamdetail"));
        System.out.println(((Intent) intent).getStringExtra("team_cap_id"));
        System.out.println(((Intent) intent).getStringExtra("team_id"));
        cap_id = Integer.parseInt(((Intent) intent).getStringExtra("team_cap_id"));
        team_id = Integer.parseInt(((Intent) intent).getStringExtra("team_id"));
        Send();
        //退回按钮逻辑
        back.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(DetailPage.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //加入按钮逻辑
        detailSureButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {

                if (detailSureButton.getText().toString().equals("确认加入"))
                {
                    Send2();
                }
                else if (detailSureButton.getText().toString().equals("队伍已满"))
                {
                    Toast.makeText(DetailPage.this,"队伍已满!无法再加入！",Toast.LENGTH_SHORT).show();
                }
                else if (detailSureButton.getText().toString().equals("您已在队伍里！"))
                {
                    Toast.makeText(DetailPage.this,"您已在队伍里！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Send() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket socket = new Socket(IP_ADDRESS, PORT);
                    if (socket.isConnected()) {
                        System.out.println("连接主机成功");
                    } else {
                        System.out.println("连接主机失败");
                    }
                    OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

                    /* 客户端向服务器发送数据  */
                    PrintWriter pw
                            = new PrintWriter(osw, true);
                    String sendMessage = "get captain info:";
                    sendMessage += cap_id + "|";
                    sendMessage += application.getCurrent_user().getId() + "|";
                    sendMessage += team_id + "|";
                    sendMessage += "not add";
                    pw.println(sendMessage);

                    /* 客户端接收服务器数据  */
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String msgReceive = null;

                    while (true) {
                        if ((msgReceive = br.readLine()) != null) {
                            stringBuffer = new StringBuffer();
                            stringBuffer.append(msgReceive);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if ((stringBuffer.toString()).startsWith("cap find")) {
                                        String getMsg = stringBuffer.toString();
                                        String cap_name = getMsg.substring(8,getMsg.indexOf("|"));
                                        String cap_sex = getMsg.substring(getMsg.indexOf("|")+1,getMsg.indexOf("|",getMsg.indexOf("|")+1));
                                        captain_name.setText(cap_name);
                                        captain_sex.setText(cap_sex);
                                        team_detail.setText(detail);
                                        System.out.println(cap_name);
                                        System.out.println(cap_sex);
                                        System.out.println(detail);
                                        if (getMsg.indexOf("isFull")!=-1)
                                        {
                                            isFull = true;
                                        }
                                        if (getMsg.indexOf("joined")!=-1)
                                        {
                                            isJoined = true;
                                        }
                                        if (isFull)
                                        {
                                            detailSureButton.setText("队伍已满");
                                            detailSureButton.setBackgroundColor(Color.parseColor("#708090"));
                                        }
                                        if (isJoined)
                                        {
                                            detailSureButton.setText("您已在队伍里！");
                                            detailSureButton.setBackgroundColor(Color.parseColor("#708090"));
                                        }

                                    }
                                }
                            });
                            System.out.println("服务器说：" + msgReceive);
                            //setContentView(R.layout.activity_detail_page);
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

    public void Send2() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket socket = new Socket(IP_ADDRESS, PORT);
                    if (socket.isConnected()) {
                        System.out.println("连接主机成功");
                    } else {
                        System.out.println("连接主机失败");
                    }
                    OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");

                    /* 客户端向服务器发送数据  */
                    PrintWriter pw
                            = new PrintWriter(osw, true);
                    String sendMessage = "get captain info:";
                    sendMessage += cap_id + "|";
                    sendMessage += application.getCurrent_user().getId() + "|";
                    sendMessage += team_id + "|";
                    sendMessage += "wanna add";
                    pw.println(sendMessage);

                    /* 客户端接收服务器数据  */
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String msgReceive = null;

                    while (true) {
                        if ((msgReceive = br.readLine()) != null) {
                            stringBuffer = new StringBuffer();
                            stringBuffer.append(msgReceive);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if ((stringBuffer.toString()).startsWith("cap find")) {
                                        String getMsg = stringBuffer.toString();
                                        if (!isJoined && !isFull)
                                        {
                                            Toast.makeText(DetailPage.this,"加入成功！",Toast.LENGTH_SHORT).show();
                                        }
                                        isJoined = true;
                                        if (isFull)
                                        {
                                            detailSureButton.setText("队伍已满");
                                            detailSureButton.setBackgroundColor(Color.parseColor("#708090"));
                                        }
                                        if (isJoined)
                                        {
                                            detailSureButton.setText("您已在队伍里！");
                                            detailSureButton.setBackgroundColor(Color.parseColor("#708090"));
                                        }

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
