package com.test2.demo;

import com.DBTool.DBUtil;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * Socket线程服务类
 * <p>
 *
 */
public class ServerSocketThread extends Thread {

    private Socket socket;

    public ServerSocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            // socket获取字节输入流
            is = socket.getInputStream();
            // 将字节输入流转字符输入流
            isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
            // 将字符输入流转行输入流
            br = new BufferedReader(isr);
            String message = null;
            // 从客户端接受数据
            if ((message = br.readLine()) != null) {
                // 处理客户端的登陆请求
                if (message.startsWith("login send Message:"))
                {
                    socket.shutdownInput();
                    String client_username;
                    String client_password;
                    int startPosition = 19;
                    boolean isFind = false;
                    System.out.println("服务器收到：" + message);
                    //下面从客户端输入中取出用户名和密码
                    client_username = message.substring(startPosition,message.indexOf('|'));
                    client_password = message.substring(message.indexOf('|')+1);
                    //System.out.println(client_username);
                    //System.out.println(client_password);
                    // 以下为数据库查询操作
                    try
                    {
                        Connection con= DBUtil.getConnection();
                        Statement stmt=con.createStatement();
                        //数据库中的数据表，表名叫：user ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
                        //String sql="select * from test.user where login_name="+ID+" and login_password="+PW;
                        String sql="select * from user";
                        ResultSet rs=stmt.executeQuery(sql);
                        String msgSend = "\n";
                        while(rs.next())
                        {
                            int id = rs.getInt(1);
                            String login_name = rs.getString(2);
                            String login_password = rs.getString(3);
                            String username = rs.getString(4);
                            String sex = rs.getString(5);
                            if (login_name.equals(client_username) && login_password.equals(client_password))
                            {
                                //找到用户并且密码正确
                                isFind = true;
                                msgSend = "permitted login" + sex + "|" + String.valueOf(id);
                                // 服务器向客户端发送数据
                                OutputStreamWriter osw = null;
                                osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                                pw = new PrintWriter(osw, true);
                                pw.println(msgSend);
                                pw.flush();
                                break;
                            }
                            else if (login_name.equals(client_username) && (!login_password.equals(client_password)))
                            {
                                //找到用户但密码错误
                                isFind = true;
                                msgSend = "password error";
                                // 服务器向客户端发送数据
                                OutputStreamWriter osw = null;
                                osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                                pw = new PrintWriter(osw, true);
                                pw.println(msgSend);
                                pw.flush();
                                break;
                            }
                        }
                        // 没有找到用户
                        if (!isFind)
                        {
                            msgSend = "user not existed";
                            // 服务器向客户端发送数据
                            OutputStreamWriter osw = null;
                            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                            pw = new PrintWriter(osw, true);
                            pw.println(msgSend);
                            pw.flush();
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
                // 处理注册逻辑
                else if (message.startsWith("register send Message:"))
                {
                    socket.shutdownInput();
                    String client_username;
                    String client_password;
                    String client_nickname;
                    String client_info;
                    String client_sex;
                    int startPosition = 22;
                    int intervalPosition;
                    boolean isFind = false;
                    System.out.println("服务器收到：" + message);
                    //下面从客户端输入中取出用户信息
                    client_username = message.substring(startPosition,message.indexOf('|'));
                    intervalPosition = message.indexOf('|')+1;
                    client_password = message.substring(intervalPosition,message.indexOf('|',intervalPosition));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    client_nickname = message.substring(intervalPosition,message.indexOf('|',intervalPosition));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    client_info = message.substring(intervalPosition,message.indexOf('|',intervalPosition));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    client_sex = message.substring(intervalPosition);
                    System.out.println(client_username);
                    System.out.println(client_password);
                    System.out.println(client_nickname);
                    System.out.println(client_info);
                    System.out.println(client_sex);
                    // 以下为数据库查询操作
                    try
                    {
                        Connection con= DBUtil.getConnection();
                        Statement stmt=con.createStatement();
                        //数据库中的数据表，表名叫：user ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
                        //String sql="select * from test.user where login_name="+ID+" and login_password="+PW;
                        String sql="select * from user";
                        ResultSet rs=stmt.executeQuery(sql);
                        String msgSend = "\n";
                        while(rs.next())
                        {
                            int id = rs.getInt(1);
                            String login_name = rs.getString(2);
                            String login_password = rs.getString(3);
                            String username = rs.getString(4);
                            String sex = rs.getString(5);
                            if (login_name.equals(client_username))
                            {
                                //用户已存在
                                msgSend = "user already exists";
                                isFind = true;
                                // 服务器向客户端发送数据
                                OutputStreamWriter osw = null;
                                osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                                pw = new PrintWriter(osw, true);
                                pw.println(msgSend);
                                pw.flush();
                                break;
                            }
                            else if (username.equals(client_nickname))
                            {
                                //用户昵称重复
                                msgSend = "nickname already exists";
                                isFind = true;
                                // 服务器向客户端发送数据
                                OutputStreamWriter osw = null;
                                osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                                pw = new PrintWriter(osw, true);
                                pw.println(msgSend);
                                pw.flush();
                                break;
                            }
                        }
                        // 没有找到用户
                        if (!isFind)
                        {
                            //执行数据库的插入工作
                            //VALUES ('百度','https://www.baidu.com/','4','CN')"
                            String sql2="insert into user(login_name, login_password, uname, sex, info_findPW)" +
                                    " values("+"'"+client_username+"'"+","+"'"+client_password+"'"+","+"'"+client_nickname+"'"+
                                    ","+"'"+client_sex+"'"+","+"'"+client_info+"'"+")";
                            //System.out.println(sql2);
                            stmt.execute(sql2);
                            msgSend = "register success";
                            // 服务器向客户端发送数据
                            OutputStreamWriter osw = null;
                            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                            pw = new PrintWriter(osw, true);
                            pw.println(msgSend);
                            pw.flush();
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
                // 处理找回密码逻辑
                else if (message.startsWith("reset send Message:"))
                {
                    socket.shutdownInput();
                    String client_username;
                    String client_password;
                    String client_info;
                    int startPosition = 19;
                    int intervalPosition;
                    boolean isFind = false;
                    System.out.println("服务器收到：" + message);
                    //下面从客户端输入中取出用户名和密码和密保信息
                    client_username = message.substring(startPosition,message.indexOf('|'));
                    intervalPosition = message.indexOf('|')+1;
                    client_password = message.substring(intervalPosition,message.indexOf('|',intervalPosition));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    client_info = message.substring(intervalPosition);
                    System.out.println(client_username);
                    System.out.println(client_password);
                    System.out.println(client_info);
                    // 以下为数据库查询操作
                    try
                    {
                        Connection con= DBUtil.getConnection();
                        Statement stmt=con.createStatement();
                        //数据库中的数据表，表名叫：user ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
                        //String sql="select * from test.user where login_name="+ID+" and login_password="+PW;
                        String sql="select * from user";
                        ResultSet rs=stmt.executeQuery(sql);
                        String msgSend = "\n";
                        while(rs.next())
                        {
                            int id = rs.getInt(1);
                            String login_name = rs.getString(2);
                            String login_password = rs.getString(3);
                            String username = rs.getString(4);
                            String PW_info = rs.getString(6);
                            if (login_name.equals(client_username))
                            {
                                //用户存在
                                // msgSend = "user already exists";
                                isFind = true;
                                if (PW_info.equals(client_info))
                                {
                                    //密保输入正确 修改用户信息
                                    msgSend = "reset success";
                                    String sql2="update user set login_password = " +
                                            "'"+client_password + "'"+" where login_name = "+"'"+client_username+"'";
                                    //System.out.println(sql2);
                                    stmt.execute(sql2);
                                }
                                else
                                {
                                    //密保输入错误
                                    msgSend = "info wrong";
                                }
                                // 服务器向客户端发送数据
                                OutputStreamWriter osw = null;
                                osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                                pw = new PrintWriter(osw, true);
                                pw.println(msgSend);
                                pw.flush();
                                break;
                            }
                        }
                        // 没有找到用户
                        if (!isFind)
                        {

                            msgSend = "user not exists";
                            // 服务器向客户端发送数据
                            OutputStreamWriter osw = null;
                            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                            pw = new PrintWriter(osw, true);
                            pw.println(msgSend);
                            pw.flush();
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
                // 处理主页获取组队信息逻辑
                else if (message.startsWith("mainPage get Message:"))
                {
                    //最新组队只获取当天发布队伍信息
                    socket.shutdownInput();
                    System.out.println("服务器收到：" + message);
                    int CurrentYear;
                    int CurrentMonth;
                    int CurrentDay;
                    int CurrentHour;
                    int CurrentUser_id;
                    int startPosition = 21;
                    int intervalPosition;

                        //下面从客户端输入中取出当前时间
                    /*
                    intervalPosition = message.indexOf('|')+1;
                    client_password = message.substring(intervalPosition,message.indexOf('|',intervalPosition));
                    */
                    CurrentYear = Integer.parseInt(message.substring(startPosition, message.indexOf('|')));
                    intervalPosition = message.indexOf('|')+1;
                    CurrentMonth = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    CurrentDay = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    CurrentHour = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    CurrentUser_id = Integer.parseInt(message.substring(message.indexOf("current user id is ")+19));
                    System.out.println(CurrentYear);
                    System.out.println(CurrentMonth);
                    System.out.println(CurrentDay);
                    System.out.println(CurrentHour);
                    System.out.println(CurrentUser_id);
                    // 以下为数据库查询操作
                    try
                    {
                        Connection con= DBUtil.getConnection();
                        Statement stmt=con.createStatement();
                        //数据库中的数据表，表名叫：team ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
                        //String sql="select * from test.user where login_name="+ID+" and login_password="+PW;
                        String sql="select * from team";
                        ResultSet rs=stmt.executeQuery(sql);
                        //第一段信息，发送最新组队
                        String msgSend = "teams info follows:";
                        //第二段信息，发送所有队伍
                        String msgSend2 = "mainPage get Message for 8:";
                        String msgSend2_1 = "type1 team Message:";
                        String msgSend2_2 = "type2 team Message:";
                        String msgSend2_3 = "type3 team Message:";
                        String msgSend2_4 = "type4 team Message:";
                        String msgSend2_5 = "type5 team Message:";
                        String msgSend2_6 = "type6 team Message:";
                        String msgSend2_7 = "type7 team Message:";
                        String msgSend2_8 = "type8 team Message:";
                        //第三段信息，发送当前用户发布及参加的队伍信息
                        String msgSend3 = "mainPage get Message for current_user:";
                        String msgSend3_1 = "teams belong to current_user:";
                        String msgSend3_2 = "teams current_user join:";
                        while(rs.next())
                        {
                            //获取队伍信息
                            int id = rs.getInt(1);
                            String team_type = rs.getString(2);
                            String team_info = rs.getString(7);
                            int startYear = rs.getInt(9);
                            int startMonth = rs.getInt(10);
                            int startDay = rs.getInt(11);
                            int startHour = rs.getInt(12);
                            int endYear = rs.getInt(13);
                            int endMonth = rs.getInt(14);
                            int endDay = rs.getInt(15);
                            int endHour = rs.getInt(16);
                            int MaxNumber = rs.getInt(4);
                            int Captain_id = rs.getInt(3);
                            int CurrentNumber = rs.getInt(5);
                            String team_member = rs.getString(6);
                            String team_details = rs.getString(8);
                            int team_isOut = rs.getInt(17);

                            if (endHour == 24)
                                endHour = 0;
                            List<Integer>members = new ArrayList<>();
                            if ((CurrentYear > endYear || CurrentMonth > endMonth || CurrentDay > endDay || (CurrentDay == endDay&&CurrentHour > endHour)) && team_isOut == 0)
                            {
                                //该队伍刚刚过期,更新过期消息
                                team_isOut = 1;
                                String sql2="update team set team_isOutDate = " +
                                        "'"+team_isOut + "'"+" where id = "+"'"+id+"'";
                                //System.out.println(sql2);
                                stmt.execute(sql2);
                            }
                            if (team_isOut == 0 && startDay == CurrentDay)
                            {
                                //保存该队伍信息
                                msgSend = msgSend + "|id:"+id+"type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            // 下面存储所有类型队伍信息
                            if (team_type.equals("演出"))
                            {
                                msgSend2_1 = msgSend2_1 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            else if (team_type.equals("出行"))
                            {
                                msgSend2_2 = msgSend2_2 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            else if (team_type.equals("外卖"))
                            {
                                msgSend2_3 = msgSend2_3 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            else if (team_type.equals("展览"))
                            {
                                msgSend2_4 = msgSend2_4 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            else if (team_type.equals("校内"))
                            {
                                msgSend2_5 = msgSend2_5 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            else if (team_type.equals("学习"))
                            {
                                msgSend2_6 = msgSend2_6 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            else if (team_type.equals("赛事"))
                            {
                                msgSend2_7 = msgSend2_7 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            else if (team_type.equals("志愿"))
                            {
                                msgSend2_8 = msgSend2_8 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                        + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                        + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                        +  "details:" + team_details;
                            }
                            //下面存储属于该用户的信息
                            int gap_parse = 0;
                            while(true)
                            {
                                int member_id = Integer.parseInt(team_member.substring(gap_parse,team_member.indexOf(',',gap_parse)));
                                gap_parse = team_member.indexOf(',',gap_parse) + 1;
                                System.out.println("获取队员id："+member_id);
                                members.add(member_id);
                                System.out.println(team_member.indexOf(',',gap_parse));
                                if (team_member.indexOf(',',gap_parse) == -1)
                                    break;
                            }
                            for (int i = 0;i<members.size();i++)
                            {
                                int id_get = members.get(i);
                                if (id_get==CurrentUser_id)
                                {
                                    if (CurrentUser_id == Captain_id)
                                    {
                                        //若该队队长就是当前登录用户
                                        msgSend3_1 = msgSend3_1 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                                + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                                + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                                +  "details:" + team_details;
                                    }
                                    else
                                    {
                                        //若当前登录用户是队员之一
                                        msgSend3_2 = msgSend3_2 + "|id:"+id+ "type:" + team_type + "info:" + team_info + "year:" + String.valueOf(startYear)
                                                + "month:" + String.valueOf(startMonth) + "day:" + String.valueOf(startDay) + "hour:" + String.valueOf(startHour)
                                                + "curNum:" + String.valueOf(CurrentNumber) + "maxNum:" + String.valueOf(MaxNumber) + "captain_id:" + String.valueOf(Captain_id)
                                                +  "details:" + team_details;
                                    }
                                }
                            }



                        }
                        // 发送合并的队伍消息
                        OutputStreamWriter osw = null;
                        msgSend = msgSend + "|";
                        msgSend2 = msgSend2 + msgSend2_1 + msgSend2_2 + msgSend2_3 + msgSend2_4 + msgSend2_5 + msgSend2_6 + msgSend2_7 + msgSend2_8;
                        msgSend3 = msgSend3 + msgSend3_1 + msgSend3_2;
                        msgSend = msgSend + msgSend2 + msgSend3;
                        System.out.println(msgSend);
                        osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                        pw = new PrintWriter(osw, true);
                        pw.println(msgSend);
                        pw.flush();
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
                // 处理队伍添加逻辑
                else if (message.startsWith("add team send Message:"))
                {
                    socket.shutdownInput();
                    int cap_id;
                    String team_Type;
                    int Max_number;
                    int endYear;
                    int endMonth;
                    int endDay;
                    int endHour;
                    String team_info;
                    String team_detail;
                    String team_members;
                    int current_number = 1;
                    int startPosition = 22;
                    int intervalPosition;
                    System.out.println("服务器收到：" + message);
                    //下面从客户端输入中取出用户信息
                    cap_id = Integer.parseInt(message.substring(startPosition,message.indexOf('|')));
                    intervalPosition = message.indexOf('|')+1;
                    team_Type = message.substring(intervalPosition,message.indexOf('|',intervalPosition));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    Max_number = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    endYear = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    endMonth = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    endDay = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    endHour = Integer.parseInt(message.substring(intervalPosition,message.indexOf('|',intervalPosition)));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    team_info = message.substring(intervalPosition,message.indexOf('|',intervalPosition));
                    intervalPosition = message.indexOf('|',intervalPosition)+1;
                    team_detail = message.substring(intervalPosition);
                    System.out.println(cap_id);
                    System.out.println(team_Type);
                    System.out.println(Max_number);
                    System.out.println(endYear);
                    System.out.println(endMonth);
                    System.out.println(endDay);
                    System.out.println(endHour);
                    System.out.println(team_info);
                    System.out.println(team_detail);
                    team_members = cap_id + ",";
                    Calendar date = Calendar.getInstance();
                    int year = date.get(Calendar.YEAR);
                    int month = date.get(Calendar.MONTH)+1;
                    int day = date.get(Calendar.DATE);
                    int hour = date.get(Calendar.HOUR_OF_DAY);
                    // 以下为数据库添加操作
                    try
                    {
                        Connection con= DBUtil.getConnection();
                        Statement stmt=con.createStatement();
                        //数据库中的数据表，表名叫：team ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
                        //String sql="select * from test.user where login_name="+ID+" and login_password="+PW;
                        String sql="select * from team";
                        String msgSend = "";
                        //执行数据库的插入工作
                        //VALUES ('百度','https://www.baidu.com/','4','CN')"
                        String sql2="insert into team(team_type, captain, team_maxNumber, team_currentNumber, team_member, " +
                                "team_describe, team_details, team_start_timeYear, team_start_timeMonth, team_start_timeDay, " +
                                "team_start_timeHour, team_end_timeYear, team_end_timeMonth, team_end_timeDay, team_end_timeHour)" +
                                    " values("+"'"+team_Type+"'"+","+"'"+cap_id+"'"+","+"'"+Max_number+"'"+
                                    ","+"'"+current_number+"'"+","+"'"+team_members+"'"+","+"'"+team_info+"'"+","+"'"+team_detail+"'"
                                +","+"'"+year+"'"+","+"'"+month+"'"+","+"'"+day+"'"+","+"'"+hour+"'"
                                +","+"'"+endYear+"'"+","+"'"+endMonth+"'"+","+"'"+endDay+"'"+","+"'"+endHour+"'"+")";
                        //System.out.println(sql2);
                        stmt.execute(sql2);
                        msgSend = "add team success";
                        // 服务器向客户端发送数据
                        OutputStreamWriter osw = null;
                        osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                        pw = new PrintWriter(osw, true);
                        pw.println(msgSend);
                        pw.flush();

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
                // 处理队伍详情逻辑（查找某用户的信息）
                else if (message.startsWith("get captain info:"))
                {
                    socket.shutdownInput();
                    String cap_name;
                    String cap_sex;
                    int cap_id;
                    int cur_id;
                    int team_id;
                    int startPosition = 17;
                    int intervalPosition;
                    boolean isAdd = false;
                    System.out.println("服务器收到：" + message);
                    //下面从客户端输入中取出用户信息
                    cap_id = Integer.parseInt(message.substring(startPosition,message.indexOf('|')));
                    cur_id = Integer.parseInt(message.substring(message.indexOf('|')+1,message.indexOf('|',message.indexOf('|')+1)));
                    int interval = message.indexOf('|',message.indexOf('|')+1);
                    team_id = Integer.parseInt(message.substring(interval+1,message.indexOf('|',interval+1)));
                    if (message.indexOf("wanna add")!=-1)
                        isAdd = true;
                    System.out.println(cap_id);
                    System.out.println(team_id);
                    System.out.println(cur_id);
                    // 以下为数据库查询操作
                    try
                    {
                        Connection con= DBUtil.getConnection();
                        Statement stmt=con.createStatement();
                        //数据库中的数据表，表名叫：user ，需要自己预先在数据库中进行创建，包含相应的字段和记录。
                        //String sql="select * from test.user where login_name="+ID+" and login_password="+PW;
                        String sql="select * from user";
                        ResultSet rs=stmt.executeQuery(sql);
                        String msgSend = "";
                        while(rs.next())
                        {
                            int id = rs.getInt(1);
                            String login_name = rs.getString(2);
                            String login_password = rs.getString(3);
                            String username = rs.getString(4);
                            String sex = rs.getString(5);
                            if (id == cap_id)
                            {
                                msgSend = "cap find";
                                // 服务器向客户端发送数据
                                msgSend = msgSend + username + "|" + sex + "|";
                                break;
                            }
                        }
                        rs.last();
                        sql="select * from team";
                        rs=stmt.executeQuery(sql);
                        while(rs.next())
                        {
                            //获取队伍信息
                            int id = rs.getInt(1);
                            String team_type = rs.getString(2);
                            String team_info = rs.getString(7);
                            int startYear = rs.getInt(9);
                            int startMonth = rs.getInt(10);
                            int startDay = rs.getInt(11);
                            int startHour = rs.getInt(12);
                            int endYear = rs.getInt(13);
                            int endMonth = rs.getInt(14);
                            int endDay = rs.getInt(15);
                            int endHour = rs.getInt(16);
                            int MaxNumber = rs.getInt(4);
                            int Captain_id = rs.getInt(3);
                            int CurrentNumber = rs.getInt(5);
                            String team_member = rs.getString(6);
                            String team_details = rs.getString(8);
                            int team_isOut = rs.getInt(17);

                            boolean canJoin = true;
                            if (team_id == id)
                            {
                                if (MaxNumber == CurrentNumber)
                                {
                                    msgSend = msgSend + "isFull" + "|";
                                    canJoin = false;
                                }
                                else
                                    msgSend = msgSend + "notFull" + "|";
                                int gap_parse = 0;
                                List<Integer>members = new ArrayList<>();
                                while(true)
                                {
                                    int member_id = Integer.parseInt(team_member.substring(gap_parse,team_member.indexOf(',',gap_parse)));
                                    gap_parse = team_member.indexOf(',',gap_parse) + 1;
                                    System.out.println("获取队员id："+member_id);
                                    members.add(member_id);
                                    System.out.println(team_member.indexOf(',',gap_parse));
                                    if (team_member.indexOf(',',gap_parse) == -1)
                                        break;
                                }
                                String msg_to_add = "not join";
                                for (int i = 0;i<members.size();i++)
                                {
                                    int id_get = members.get(i);
                                    if (id_get==cur_id)
                                    {
                                        msg_to_add= "joined";
                                        canJoin = false;
                                       break;
                                    }
                                }
                                msgSend = msgSend + msg_to_add;
                                if (canJoin == true && isAdd == true)
                                {
                                    team_member = team_member + cur_id + ",";
                                    String sql2="update team set team_member = " +
                                            "'"+team_member + "'"+" where id = "+"'"+team_id+"'";
                                    //System.out.println(sql2);
                                    stmt.execute(sql2);
                                    CurrentNumber = CurrentNumber + 1;
                                    sql2="update team set team_currentNumber = " +
                                            "'"+CurrentNumber + "'"+" where id = "+"'"+team_id+"'";
                                    //System.out.println(sql2);
                                    stmt.execute(sql2);
                                }
                            }

                        }
                        System.out.println(msgSend);
                        OutputStreamWriter osw = null;
                        osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                        pw = new PrintWriter(osw, true);
                        pw.println(msgSend);
                        pw.flush();
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
            /*
            // 必须先关闭输入流才能获取下面的输出流
            socket.shutdownInput();
            String msgSend    = "\nhello I am server, I have receive your message: "+message;
            // 服务器向客户端发送数据
            OutputStreamWriter osw = null;
            osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            pw = new PrintWriter(osw, true);
            pw.println(msgSend);
            pw.flush();
            System.out.println("向客户端发送"+msgSend);
            */

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // 关闭输入流
            try {
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 收工前最后一步：关闭资源
            try {
                if (pw != null) {
                    pw.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}