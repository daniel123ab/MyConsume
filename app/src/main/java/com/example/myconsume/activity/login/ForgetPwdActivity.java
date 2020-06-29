package com.example.myconsume.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myconsume.R;
import com.example.myconsume.entiy.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEtResetPhone;
    private EditText mEtResetPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_retrieve_pwd);
        mEtResetPhone=findViewById(R.id.et_reset_phone);
        mEtResetPwd=findViewById(R.id.et_reset_pwd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.iv_reset_phone_del:
                //清空手机号
                mEtResetPhone.setText(null);
                break;
            case R.id.iv_reset_pwd_del:
                //清空密码
                mEtResetPwd.setText(null);
                break;
            case R.id.bt_reset_submit:
                //更改密码
                reset();
                break;
        }
    }

    private void reset() {
        String phone=mEtResetPhone.getText().toString();
        String password=mEtResetPwd.getText().toString();
        List<User> users= DataSupport.where("phone=?",phone).find(User.class);
        if (users.size()>0){
            User user=users.get(0);
            user.setPassword(password);
            user.update(user.getId());
            Toast.makeText(this, "密码修改成功！", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "手机号不存在！", Toast.LENGTH_SHORT).show();
        }
    }
}
