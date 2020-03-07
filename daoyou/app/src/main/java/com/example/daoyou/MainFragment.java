package com.example.daoyou;

import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    Guideline gl1,gl2,gl3,gl4,gl5;
    private ImageView addButton;
    private ImageView button_yanchu,button_chuxing,button_waimai,button_zhanlan,button_xiaonei,button_xuexi,button_saishi,button_zhiyuan;
    private Button see_button;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Team>teams=new ArrayList<>();
    MyApplication application;
    //IP地址和端口号
    public static String IP_ADDRESS = "10.128.239.185";
    public static int PORT = 8000;
    StringBuffer stringBuffer=new StringBuffer();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        gl1 = view.findViewById(R.id.gl1);
        gl2 = view.findViewById(R.id.gl2);
        gl3 = view.findViewById(R.id.gl3);
        gl4 = view.findViewById(R.id.gl4);
        gl5 = view.findViewById(R.id.gl5);
        addButton = (ImageView) view.findViewById(R.id.back_main_Button);
        button_yanchu = (ImageView) view.findViewById(R.id.button_yanchu);
        button_chuxing = (ImageView) view.findViewById(R.id.button_chuxing);
        button_waimai = (ImageView) view.findViewById(R.id.button_waimai);
        button_zhanlan = (ImageView) view.findViewById(R.id.button_zhanlan);
        button_xiaonei = (ImageView) view.findViewById(R.id.button_xiaonei);
        button_xuexi = (ImageView) view.findViewById(R.id.button_study);
        button_saishi = (ImageView) view.findViewById(R.id.button_saishi);
        button_zhiyuan = (ImageView) view.findViewById(R.id.button_zhiyuan);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyleview_inTT);
        see_button = (Button)view.findViewById(R.id.see_button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        application = (MyApplication)getActivity().getApplication();
        // TODO: Use the ViewModel
        gl1.setVisibility(View.INVISIBLE);
        gl2.setVisibility(View.INVISIBLE);
        gl3.setVisibility(View.INVISIBLE);
        gl4.setVisibility(View.INVISIBLE);
        gl5.setVisibility(View.INVISIBLE);
        //用application获取服务器传来的东西
        //teams = application.getTeams();
       // System.out.println(teams.get(0).getTeam_info());
        //System.out.println("yishangshi debug");
        //处理新建组队按钮逻辑
        addButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent();
                intent.setClass(getActivity(), AddTeamPage.class);
                startActivity(intent);
            }
        });
        see_button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                //展示最新组队信息
                teams = application.getTeams();
                myAdapter = new MyAdapter();
                myAdapter.setAllWords(teams);
                myAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //线性列表用Linear 有行列的用GridLayoutManager
                recyclerView.setAdapter(myAdapter);
            }
        });

        //以下是点击各组队类型按钮触发事件
        button_yanchu.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "演出";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        button_chuxing.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "出行";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        button_waimai.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "外卖";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        button_zhanlan.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "展览";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        button_xiaonei.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "校内";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        button_xuexi.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "学习";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        button_saishi.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "赛事";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        button_zhiyuan.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                String type = "志愿";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
        /*
        //展示最新组队信息
        myAdapter = new MyAdapter();
        myAdapter.setAllWords(teams);
        myAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //线性列表用Linear 有行列的用GridLayoutManager
        recyclerView.setAdapter(myAdapter);
*/
    }



}
