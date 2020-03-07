package com.example.daoyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //IP地址和端口号
    //  学校网络ip地址
    public static String IP_ADDRESS = "10.128.239.185";
    //public static String IP_ADDRESS ="192.168.1.197";
    public static int PORT = 8000;
    StringBuffer stringBuffer=new StringBuffer();
    MyApplication application = (MyApplication)this.getApplication();
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        /*
        Team team1=new Team("外卖",3,1,"2020-1-9 20","美赛组队");
        List<Team> teams=new ArrayList<>();
        teams.add(team1);
        application.setTeams(teams);
        */
        /*
        List<Team> teams1 = application.getTeams();
        System.out.println(teams1.get(0).getTeam_info());
*/
        //Send();
        /*以下为主界面导航栏设置*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.fragment);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        //获取服务器信息
        Send();
    }

    public List<Team> parseStr(String getMsg2_1)
    {
        int pos = 0;
        List<Team> teams1 = new ArrayList<>();
        while (true)
        {
            pos = getMsg2_1.indexOf('|',pos) + 1;
            if (pos == 0)
                break;
            int team_id = Integer.parseInt(getMsg2_1.substring(pos+3,getMsg2_1.indexOf("type:",pos)));
            String type = getMsg2_1.substring(getMsg2_1.indexOf("type:",pos)+5,getMsg2_1.indexOf("info:",pos));
            String info = getMsg2_1.substring(getMsg2_1.indexOf("info:",pos)+5,getMsg2_1.indexOf("year:",pos));
            int year = Integer.parseInt(getMsg2_1.substring(getMsg2_1.indexOf("year:",pos)+5,getMsg2_1.indexOf("month:",pos)));
            int month = Integer.parseInt(getMsg2_1.substring(getMsg2_1.indexOf("month:",pos)+6,getMsg2_1.indexOf("day:",pos)));
            int day = Integer.parseInt(getMsg2_1.substring(getMsg2_1.indexOf("day:",pos)+4,getMsg2_1.indexOf("hour:",pos)));
            int hour = Integer.parseInt(getMsg2_1.substring(getMsg2_1.indexOf("hour:",pos)+5,getMsg2_1.indexOf("curNum:",pos)));
            int curNum = Integer.parseInt(getMsg2_1.substring(getMsg2_1.indexOf("curNum:",pos)+7,getMsg2_1.indexOf("maxNum:",pos)));
            int maxNum = Integer.parseInt(getMsg2_1.substring(getMsg2_1.indexOf("maxNum:",pos)+7,getMsg2_1.indexOf("captain_id:",pos)));
            int captain_id = Integer.parseInt(getMsg2_1.substring(getMsg2_1.indexOf("captain_id:",pos)+11,getMsg2_1.indexOf("details:",pos)));
            String details;
            if (getMsg2_1.indexOf('|',pos)!=-1)
                details = getMsg2_1.substring(getMsg2_1.indexOf("details:",pos)+8,getMsg2_1.indexOf('|',pos));
            else
                details = getMsg2_1.substring(getMsg2_1.indexOf("details:",pos)+8);
            String team_startTime = String.valueOf(year) +  "-" + String.valueOf(month) +  "-" + String.valueOf(day) +  " " + String.valueOf(hour) +  "时";
            teams1.add(new Team(team_id,type,maxNum,curNum,team_startTime,info,details,captain_id));
        }
        return teams1;
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
                    String currentTime  = GetCurrentTime.getCurrentDate();
                    System.out.println(currentTime);
                    String sendMessage = "mainPage get Message:" + currentTime + "current user id is "+ application.getCurrent_user().getId();
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
                                    if ((stringBuffer.toString()).startsWith("teams info follows:"))
                                    {
                                        //获取传过来的队伍信息
                                        String getMsg = stringBuffer.toString();
                                        System.out.println(getMsg);
                                        String getMsg1 = getMsg.substring(0,getMsg.indexOf("mainPage get Message for 8:"));
                                        String getMsg2 = getMsg.substring(getMsg.indexOf("mainPage get Message for 8:"),getMsg.indexOf("mainPage get Message for current_user:"));
                                        String getMsg3 = getMsg.substring(getMsg.indexOf("mainPage get Message for current_user:"));
                                        System.out.println(getMsg1);
                                        System.out.println(getMsg2);
                                        System.out.println(getMsg3);
                                        //下面处理最近组队信息的存储
                                        List<String> recv_teams = new ArrayList<>();
                                        List<Team> teams = new ArrayList<>();
                                        int intervalPosition = getMsg1.indexOf('|')+1;
                                        String recv_team="";
                                        if (getMsg1.indexOf('|',intervalPosition)!=-1) {
                                            recv_team = getMsg1.substring(20, getMsg1.indexOf('|', intervalPosition));
                                            recv_teams.add(recv_team);
                                            while (true)
                                            {
                                                intervalPosition = getMsg1.indexOf('|',intervalPosition)+1;
                                                if (getMsg1.indexOf('|',intervalPosition)!=-1)
                                                {
                                                    recv_team = getMsg1.substring(intervalPosition,getMsg1.indexOf('|',intervalPosition));

                                                }
                                                else
                                                    recv_team = "";
                                                if (recv_team == "")
                                                    break;
                                                else
                                                    recv_teams.add(recv_team);
                                            }
                                        }
                                        else                                                    //若只有一个队伍信息
                                        {
                                            recv_team = getMsg.substring(20);
                                            if (recv_team!="")
                                                recv_teams.add(recv_team);
                                        }
                                            //从接收消息中获取队伍的各样信息
                                        for (int i = 0;i<recv_teams.size();i++)
                                        {
                                            recv_team = recv_teams.get(i);
                                            int id = Integer.parseInt(recv_team.substring(recv_team.indexOf("id:")+3,recv_team.indexOf("type:")));
                                            String type = recv_team.substring(recv_team.indexOf("type:")+5,recv_team.indexOf("info:"));
                                            String info = recv_team.substring(recv_team.indexOf("info:")+5,recv_team.indexOf("year:"));
                                            int year = Integer.parseInt(recv_team.substring(recv_team.indexOf("year:")+5,recv_team.indexOf("month:")));
                                            int month = Integer.parseInt(recv_team.substring(recv_team.indexOf("month:")+6,recv_team.indexOf("day:")));
                                            int day = Integer.parseInt(recv_team.substring(recv_team.indexOf("day:")+4,recv_team.indexOf("hour:")));
                                            int hour = Integer.parseInt(recv_team.substring(recv_team.indexOf("hour:")+5,recv_team.indexOf("curNum:")));
                                            int curNum = Integer.parseInt(recv_team.substring(recv_team.indexOf("curNum:")+7,recv_team.indexOf("maxNum:")));
                                            int maxNum = Integer.parseInt(recv_team.substring(recv_team.indexOf("maxNum:")+7,recv_team.indexOf("captain_id:")));
                                            int cap_id = Integer.parseInt(recv_team.substring(recv_team.indexOf("captain_id:")+11,recv_team.indexOf("details:")));
                                            String details = recv_team.substring(recv_team.indexOf("details:")+8);

                                            String team_startTime = String.valueOf(year) +  "-" + String.valueOf(month) +  "-" + String.valueOf(day) +  " " + String.valueOf(hour) +  "时";
                                            Team team = new Team(id,type,maxNum,curNum,team_startTime,info,details,cap_id);
                                            teams.add(team);
                                            System.out.println(type);
                                            System.out.println(info);
                                            System.out.println(year);
                                            System.out.println(month);
                                            System.out.println(day);
                                            System.out.println(hour);
                                            System.out.println(curNum);
                                            System.out.println(maxNum);
                                            System.out.println(cap_id);
                                            System.out.println(details);
                                        }
                                        application = (MyApplication) getApplication();
                                        application.setTeams(teams);
                                        System.out.println(application.getTeams().size());
                                        //下面处理所有种类队伍信息的存储
                                        String getMsg2_1 = getMsg2.substring(getMsg2.indexOf("type1 team Message:")+19,getMsg2.indexOf("type2 team Message:"));
                                        String getMsg2_2 = getMsg2.substring(getMsg2.indexOf("type2 team Message:")+19,getMsg2.indexOf("type3 team Message:"));
                                        String getMsg2_3 = getMsg2.substring(getMsg2.indexOf("type3 team Message:")+19,getMsg2.indexOf("type4 team Message:"));
                                        String getMsg2_4 = getMsg2.substring(getMsg2.indexOf("type4 team Message:")+19,getMsg2.indexOf("type5 team Message:"));
                                        String getMsg2_5 = getMsg2.substring(getMsg2.indexOf("type5 team Message:")+19,getMsg2.indexOf("type6 team Message:"));
                                        String getMsg2_6 = getMsg2.substring(getMsg2.indexOf("type6 team Message:")+19,getMsg2.indexOf("type7 team Message:"));
                                        String getMsg2_7 = getMsg2.substring(getMsg2.indexOf("type7 team Message:")+19,getMsg2.indexOf("type8 team Message:"));
                                        String getMsg2_8 = getMsg2.substring(getMsg2.indexOf("type8 team Message:")+19);
                                        System.out.println(getMsg2_1);
                                        System.out.println(getMsg2_2);
                                        System.out.println(getMsg2_3);
                                        System.out.println(getMsg2_4);
                                        System.out.println(getMsg2_5);
                                        System.out.println(getMsg2_6);
                                        System.out.println(getMsg2_7);
                                        System.out.println(getMsg2_7);
                                        System.out.println(getMsg2_8);
                                        //int intervalPosition = getMsg1.indexOf('|')+1;
                                        //String recv_team="";
                                        application.setTeams1(parseStr(getMsg2_1));
                                        application.setTeams2(parseStr(getMsg2_2));
                                        application.setTeams3(parseStr(getMsg2_3));
                                        application.setTeams4(parseStr(getMsg2_4));
                                        application.setTeams5(parseStr(getMsg2_5));
                                        application.setTeams6(parseStr(getMsg2_6));
                                        application.setTeams7(parseStr(getMsg2_7));
                                        application.setTeams8(parseStr(getMsg2_8));

                                        //下面处理当前登录用户拥有及加入队伍的存储
                                        String getMsg3_1 = getMsg3.substring(getMsg3.indexOf("teams belong to current_user:")+29,getMsg3.indexOf("teams current_user join:"));
                                        String getMsg3_2 = getMsg3.substring(getMsg3.indexOf("teams current_user join:")+24);
                                        System.out.println(getMsg3_1);
                                        System.out.println(getMsg3_2);
                                        application.setTeams_belong(parseStr(getMsg3_1));
                                        application.setTeams_join(parseStr(getMsg3_2));
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
