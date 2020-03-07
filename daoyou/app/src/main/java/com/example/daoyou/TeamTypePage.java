package com.example.daoyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TeamTypePage extends AppCompatActivity {
    TextView page_title;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ImageView back_main_Button;
    private List<Team> teams=new ArrayList<>();
    MyApplication application;
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
        setContentView(R.layout.activity_team_type_page);
        Intent intent = getIntent();
        String team_type = ((Intent) intent).getStringExtra("teamtype");
        page_title = (TextView)findViewById(R.id.typePage_title_text);
        recyclerView = (RecyclerView)findViewById(R.id.recyleview_inTT);
        back_main_Button = findViewById(R.id.back_main_Button);
        page_title.setText(team_type);
        application = (MyApplication)getApplication();
        myAdapter = new MyAdapter();
        if (team_type.equals("我发布的"))
        {
            teams = application.getTeams_belong();
        }
        else if (team_type.equals("我参加的"))
        {
            teams = application.getTeams_join();
        }
        else if (team_type.equals("演出"))
        {
            teams = application.getTeams1();
        }
        else if (team_type.equals("出行"))
        {
            teams = application.getTeams2();
        }
        else if (team_type.equals("外卖"))
        {
            teams = application.getTeams3();
        }
        else if (team_type.equals("展览"))
        {
            teams = application.getTeams4();
        }
        else if (team_type.equals("校内"))
        {
            teams = application.getTeams5();
        }
        else if (team_type.equals("学习"))
        {
            teams = application.getTeams6();
        }
        else if (team_type.equals("赛事"))
        {
            teams = application.getTeams7();
        }
        else if (team_type.equals("志愿"))
        {
            teams = application.getTeams8();
        }
        myAdapter.setAllWords(teams);
        myAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //线性列表用Linear 有行列的用GridLayoutManager
        recyclerView.setAdapter(myAdapter);
        back_main_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(TeamTypePage.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
