package com.example.myconsume.activity.login;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myconsume.R;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.entiy.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, TextWatcher {
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    private ImageView mIvLoginUsernameDel;
    private ImageView mIvLoginPwdDel;
    private Button mBtRegisterSubmit;
    private LinearLayout mLayBackBar;
    private ImageButton mIbNavigationBack;
    private TextView male;
    private TextView female;

    private EditText mEtRegisterPhone;
    private ImageView mIvRegisterPhoneDel;
    private int sex=0;      //性别 0 男 1 女
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_two);
        init(); //绑定视图
    }

    private void init(){
        //导航栏+返回按钮
        mLayBackBar = findViewById(R.id.ly_retrieve_bar);
        mIbNavigationBack = findViewById(R.id.ib_navigation_back);


        //username
        mEtLoginUsername = findViewById(R.id.et_register_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_register_username_del);

        //passwd
        mEtLoginPwd = findViewById(R.id.et_register_pwd_input);
        mIvLoginPwdDel = findViewById(R.id.iv_register_pwd_del);

        //提交
        mBtRegisterSubmit = findViewById(R.id.bt_register_submit);

        male=findViewById(R.id.tv_register_man);
        female=findViewById(R.id.tv_register_female);
        //手机号
        mEtRegisterPhone=findViewById(R.id.et_register_phone_input);
        mIvRegisterPhoneDel=findViewById(R.id.iv_register_phone_del);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                //返回
                finish();
                break;
            case R.id.et_register_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_register_pwd_input:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.iv_register_username_del:
                //清空用户名
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_register_pwd_del:
                //清空密码
                mEtLoginPwd.setText(null);
                break;
            case R.id.iv_register_phone_del:
                //清空手机号
                mEtRegisterPhone.setText(null);
            case R.id.bt_register_submit:
                //注册
                registerRequest();
            default:
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    public void setSex(View view){
        switch (view.getId()){
            case R.id.tv_register_man:
                male.setTextColor(getResources().getColor(R.color.login_input_active));
                female.setTextColor(getResources().getColor(R.color.login_line_color));
                sex=0;
                break;
            case R.id.tv_register_female:
                female.setTextColor(getResources().getColor(R.color.login_input_active));
                male.setTextColor(getResources().getColor(R.color.login_line_color));
                sex=1;
                break;
            default:
                break;
        }
    }
    private void registerRequest() {
        //注册
        String userName=mEtLoginUsername.getText().toString();
        String password=mEtLoginPwd.getText().toString();
        long phone=Long.parseLong(mEtRegisterPhone.getText().toString());
        if(isExist(userName)){
            Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
            mEtLoginUsername.setText("");
            mEtLoginUsername.setFocusable(true);
        }else if (phone<99999){
            Toast.makeText(this, "手机号输入有误！", Toast.LENGTH_SHORT).show();
            mEtRegisterPhone.setFocusable(true);
        }else{
            String sex1 = sex==0?"男":"女";
            if (!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(password)&&phone>0){
                User user=new User(userName,password,sex1,phone);
                user.save();
                setAccount(userName);
                Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "所有项都不能为空！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void setAccount(String username) {
        List<User> users=DataSupport.where("username=?",username).find(User.class);
        Account account=new Account(users.get(0).getId(),"默认",0,String.valueOf(users.get(0).getId()));
        account.save();
    }
    private boolean isExist(String userName){
        List<User> users= DataSupport.where("username=?",userName).find(User.class);
        if (users.size()>0) return true;
        return false;
    }


    //用户名密码焦点改变
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    /**
     * menu glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     */
    private void glide(int height, float progress, int time) {

    }

    /**
     * menu up glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     */
    private void upGlide(int height, float progress, int time) {

    }

    //显示或隐藏logo
    @Override
    public void onGlobalLayout() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



}