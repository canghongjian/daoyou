package com.example.daoyou;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class PersonalFragment extends Fragment {

    private PersonalViewModel mViewModel;
    private ImageView myTeam,myParti;
    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_fragment2, container, false);
        myTeam = (ImageView) view.findViewById(R.id.myTeam);
        myParti = (ImageView) view.findViewById(R.id.myParticipant);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PersonalViewModel.class);
        // TODO: Use the ViewModel
        myTeam.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent();
                String type = "我发布的";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });

        myParti.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                Intent intent = new Intent();
                String type = "我参加的";
                intent.putExtra("teamtype",type);
                intent.setClass(getActivity(), TeamTypePage.class);
                startActivity(intent);
            }
        });
    }

}
