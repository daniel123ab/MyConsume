package com.example.myconsume.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myconsume.R;
import com.example.myconsume.entiy.User;
import com.example.myconsume.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView mIgUserHead;
    private TextView mTvUserName;
    private TextView mTvUserSex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,BillActivity.class),1);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mTvUserSex=navigationView.findViewById(R.id.user_tv_sex);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //设置右上角菜单项的点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.action_exit:
                        SharedPreferences.Editor editor=getSharedPreferences("user",MODE_PRIVATE).edit();
                        editor.putBoolean("auto",false);
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, com.example.myconsume.activity.login.MainActivity.class));
                        finish();
                        break;
                    case R.id.action_addAccount:
                        startActivityForResult(new Intent(MainActivity.this,AddAccount.class),2);
                    default:
                        break;
                }
            return true; }
        });

        //设置图像和用户名
        //获得NavigationView里的控件
        mIgUserHead=navigationView.getHeaderView(0).findViewById(R.id.user_image_head);
        mTvUserName=navigationView.getHeaderView(0).findViewById(R.id.user_tv_name);
        mTvUserSex=navigationView.getHeaderView(0).findViewById(R.id.user_tv_sex);
        User user= Util.getUser(this);
        if(user==null) {
            startActivity(new Intent(this, com.example.myconsume.activity.login.MainActivity.class));
            finish();
        }
        mTvUserName.setText(user.getUserName());
        mTvUserSex.setText(user.getSex());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        super.onOptionsItemSelected(item);
//        switch (item.getItemId()){
//            case  R.id.action_exit:
//                SharedPreferences.Editor editor=getSharedPreferences("user",MODE_PRIVATE).edit();
//                editor.putBoolean("auto",false);
//                editor.apply();
//                startActivity(new Intent(this, com.example.myconsume.activity.login.MainActivity.class));
//                finish();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
