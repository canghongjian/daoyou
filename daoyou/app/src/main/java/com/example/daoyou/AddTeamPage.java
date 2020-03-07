package com.example.daoyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class AddTeamPage extends AppCompatActivity {
    private ImageView back_main_Button, button_yanchu, button_chuxing, button_waimai, button_zhanlan, button_xiaonei, button_xuexi, button_saishi, button_zhiyuan;
    private EditText addmemberNumber, addendTimeYear, addendTimeMonth, addendTimeDay, addendTimeHour, addteam_info, addteam_detail;
    private Button addSureButton;
    private String type = "";
    private int maxNumber, endYear, endMonth, endDay, endHour;
    private String detail, info;
    MyApplication application = (MyApplication) this.getApplication();
    //IP地址和端口号

    public static String IP_ADDRESS = "10.128.239.185";
    //public static String IP_ADDRESS = "192.168.1.197";
    public static int PORT = 8000;
    StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_team_page);
        back_main_Button = findViewById(R.id.back_main_Button);
        button_yanchu = findViewById(R.id.button_yanchu);
        button_chuxing = findViewById(R.id.button_chuxing);
        button_waimai = findViewById(R.id.button_waimai);
        button_zhanlan = findViewById(R.id.button_zhanlan);
        button_xiaonei = findViewById(R.id.button_xiaonei);
        button_xuexi = findViewById(R.id.button_study);
        button_saishi = findViewById(R.id.button_saishi);
        button_zhiyuan = findViewById(R.id.button_zhiyuan);
        addmemberNumber = findViewById(R.id.addmemberNumber);
        addendTimeYear = findViewById(R.id.addendTimeYear);
        addendTimeMonth = findViewById(R.id.addendTimeMonth);
        addendTimeHour = findViewById(R.id.addendTimeHour);
        addendTimeDay = findViewById(R.id.addendTimeDay);
        addteam_info = findViewById(R.id.addteam_info);
        addteam_detail = findViewById(R.id.addteam_detail);
        addSureButton = findViewById(R.id.addSureButton);
        //退回按钮逻辑
        back_main_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(AddTeamPage.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        //以下是点击各组队类型按钮触发事件
        button_yanchu.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "演出";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_yanchu.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        button_chuxing.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "出行";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        button_waimai.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "外卖";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        button_zhanlan.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "展览";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        button_xiaonei.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "校内";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        button_xuexi.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "学习";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        button_saishi.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "赛事";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        button_zhiyuan.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                type = "志愿";
                button_yanchu.setBackgroundColor(Color.parseColor("#ffffff"));
                button_chuxing.setBackgroundColor(Color.parseColor("#ffffff"));
                button_waimai.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhanlan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xiaonei.setBackgroundColor(Color.parseColor("#ffffff"));
                button_xuexi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_saishi.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#ffffff"));
                button_zhiyuan.setBackgroundColor(Color.parseColor("#6495ED"));
            }
        });
        addSureButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                maxNumber = Integer.parseInt(addmemberNumber.getText().toString());
                endYear = Integer.parseInt(addendTimeYear.getText().toString());
                endMonth = Integer.parseInt(addendTimeMonth.getText().toString());
                endDay = Integer.parseInt(addendTimeDay.getText().toString());
                endHour = Integer.parseInt(addendTimeHour.getText().toString());
                detail = addteam_detail.getText().toString();
                info = addteam_info.getText().toString();
                if (type.equals("")) {
                    Toast.makeText(AddTeamPage.this, "请选择队伍类型！", Toast.LENGTH_SHORT).show();
                    addmemberNumber.setText("");
                    addendTimeYear.setText("");
                    addendTimeMonth.setText("");
                    addendTimeDay.setText("");
                    addendTimeHour.setText("");
                    addteam_info.setText("");
                } else if (addmemberNumber.getText().toString().equals("") || addendTimeYear.getText().toString().equals("") || addendTimeMonth.getText().toString().equals("")
                        || addendTimeDay.getText().toString().equals("") || addendTimeHour.getText().toString().equals("") || addteam_detail.getText().toString().equals("")) {
                    Toast.makeText(AddTeamPage.this, "数据不能为空！", Toast.LENGTH_SHORT).show();
                    addmemberNumber.setText("");
                    addendTimeYear.setText("");
                    addendTimeMonth.setText("");
                    addendTimeDay.setText("");
                    addendTimeHour.setText("");
                    addteam_info.setText("");
                }
                Send();
                Toast.makeText(AddTeamPage.this, "添加成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(AddTeamPage.this, MainActivity.class);
                finish();
                startActivity(intent);
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
                    String sendMessage = "add team send Message:";
                    sendMessage += application.getCurrent_user().getId() + "|";
                    sendMessage += type + "|";
                    sendMessage += maxNumber + "|";
                    sendMessage += endYear + "|";
                    sendMessage += endMonth + "|";
                    sendMessage += endDay + "|";
                    sendMessage += endHour + "|";
                    sendMessage += info + "|";
                    sendMessage += detail;
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
                                    if ((stringBuffer.toString()).startsWith("add team success")) {
                                        Toast.makeText(AddTeamPage.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.setClass(AddTeamPage.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
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

