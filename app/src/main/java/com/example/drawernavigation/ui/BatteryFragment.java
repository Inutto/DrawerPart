package com.example.drawernavigation.ui;

import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drawernavigation.MainActivity;
import com.example.drawernavigation.R;

public class BatteryFragment extends Fragment {

    /*



    public static interface BatteryListener{

        public int getBatteryLeft();

        public void simulateNotification(View view);

        public void pushNotification();

        public void saveBattery(View view);

    }

     */
    public MainActivity mListener;


    public static BatteryFragment newInstance() {
        return new BatteryFragment();
    }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        //transfer interface
        if(activity instanceof MainActivity){
            mListener = ((MainActivity) activity);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.battery_fragment, container, false);

        //Battery left
        TextView batteryLeft = root.findViewById(R.id.batteryTextView);
        batteryLeft.setText(Integer.toString(mListener.getBatteryLeft()) + "%");

        return root;
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BatteryViewModel.class);
        // TODO: Use the ViewModel
    }

     */
}


