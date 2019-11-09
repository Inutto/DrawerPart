package com.example.drawernavigation;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.example.drawernavigation.ui.BatteryFragment;
import com.example.drawernavigation.ui.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("main","set view of content");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //gittest

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        // Settings Part
        //navigationView.setNavigationItemSelectedListener(this);

    }

    //remove overflow button from toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





    // Battery Part

    private Notification notify1;
    private NotificationManager nManager;
    //public static int CHANNEL_ID = 1;
    public String CHANNEL_ID;

    {
        CHANNEL_ID = "battery_notification";
    }

    public int getBatteryLeft(){
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, filter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryP = level / (float)scale;
        return level;
    }

    public void simulateNotification(View view){
        Switch switchStatus = (Switch) findViewById(R.id.switchReceive);
        if(switchStatus.isChecked()){
            pushNotification();
        }

    }

    //function of send notification to self
    public void pushNotification(){
        Log.d("battery","pushNotification()");
        //nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder nBuilder = new Notification.Builder(this);

        //value
        nBuilder.setContentTitle("Battery SOS !")
                .setContentText("Someone's phone is dying! Can you help him?")
                .setSmallIcon(R.drawable.pic_batterysos)
                .setTicker(",,, --- ...")
                .setPriority(Notification.PRIORITY_MAX);
        NotificationManagerCompat nManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notify1 = nBuilder.build();
        nManager.notify(1,notify1);

    }

    public void saveBattery(View view){
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        boolean powerSaveMode = powerManager.isPowerSaveMode();
        if (powerSaveMode){
            Log.d("battery","Power save mode is on");
        } else {
            Log.d("battery","Power save mode is off, trying to open it...");

            //requrest permission


            Intent battSaverIntent = new Intent();

            //currently for opening the savermode's page:
            //battSaverIntent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$BatterySaverSettingsActivity"));
            //startActivityForResult(battSaverIntent, 0);

            //only works on google/sansung phone I guess;
            Intent batterySaverIntent=new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
            startActivity(batterySaverIntent);
            Log.d("battery","Finish opening power save mode");
        }
    }

    // Settings Part

    /*
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d("main","on navigation item selected...");

        switch(menuItem.getItemId()){
            case R.id.nav_battery:{
                Log.d("main","switch to battery fragment");
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new BatteryFragment())
                        .commit();
                break;
            }
        }


        return false;
    }
    */
}

