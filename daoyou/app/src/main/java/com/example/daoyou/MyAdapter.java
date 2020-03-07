package com.example.daoyou;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Team> teams = new ArrayList<>();
    private Context context;
    private List<String> types = new ArrayList<>();
    private List<String> detail = new ArrayList<>();
    private List<Integer> cap_id = new ArrayList<>();
    private List<Integer> team_id = new ArrayList<>();
    public void setAllWords(List<Team> ateams) {
        this.teams = ateams;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_card,parent,false);
        context = parent.getContext();
        return  new MyViewHolder(itemView);
        //创建ViewHolder呼叫的
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //RecylerView和ViewHolder绑定时呼叫,做逻辑关联，把textView显示
        Team team = teams.get(position);
        types.add(team.getTeam_type());
        detail.add(team.getTeam_detail());
        cap_id.add(team.getCaptain_id());
        team_id.add(team.getId());
        holder.teamType.setText(team.getTeam_type());
        holder.teamInfo.setText(team.getTeam_info());
        holder.teamTime.setText(team.getTeam_startTime());
        holder.teamPeople.setText("人数"+String.valueOf(team.getTeam_currentNumber())+"/"+String.valueOf(team.getTeam_maxNumber()));
        holder.button_detail.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(context,DetailPage.class);
                intent.putExtra("teamdetail",detail.get(position));
                intent.putExtra("team_id",String.valueOf(team_id.get(position)));
                intent.putExtra("team_cap_id",String.valueOf(cap_id.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回列表数据总个数
        return teams.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView teamType,teamInfo,teamTime,teamPeople;
        Button button_detail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teamType = itemView.findViewById(R.id.teamType);
            teamInfo = itemView.findViewById(R.id.teamInfo);
            teamTime = itemView.findViewById(R.id.teamTime);
            teamPeople = itemView.findViewById(R.id.teamPeople);
            button_detail = itemView.findViewById(R.id.button_detail);
        }
    }
}

