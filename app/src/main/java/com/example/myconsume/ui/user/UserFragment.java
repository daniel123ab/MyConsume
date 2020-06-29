package com.example.myconsume.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.myconsume.R;
import com.example.myconsume.activity.login.MainActivity;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.entiy.User;
import com.example.myconsume.util.ImageUtils;
import com.example.myconsume.util.Util;

import org.litepal.crud.DataSupport;

public class UserFragment extends Fragment implements View.OnClickListener {
    private ImageView mIgUserHead;
    private TextView mTvUserId;
    private TextView mTvUserName;
    private TextView mTvUserSex;
    private TextView mTvUserPhone;

    protected String inputPaw = "";

    private Button mBtExit;
    private Button mBtDel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mIgUserHead=view.findViewById(R.id.user_head);
        mTvUserId=view.findViewById(R.id.tv_user_id);
        mTvUserName=view.findViewById(R.id.tv_user_name);
        mTvUserSex=view.findViewById(R.id.tv_user_sex);
        mTvUserPhone=view.findViewById(R.id.tv_user_phone);
        mBtDel=view.findViewById(R.id.bt_user_del);
        mBtExit=view.findViewById(R.id.bt_user_exit);
        mBtExit.setOnClickListener(this);
        mBtDel.setOnClickListener(this);

        //设置头像图片
        Bitmap head= BitmapFactory.decodeResource(getResources(),R.mipmap.head_1);
        Bitmap roundHead= ImageUtils.ClipSquareBitmap(head,800,400);
        mIgUserHead.setImageBitmap(roundHead);
        //设置用户信息
        User user= Util.getUser(getActivity());
        mTvUserId.setText(""+user.getId());
        mTvUserPhone.setText(user.getPhone()+"");
        mTvUserName.setText(user.getUserName());
        mTvUserSex.setText(user.getSex());
    }
    //注销用户
    public void cancellation(){
        deleteUserInfo();
        startActivity(new Intent(getActivity(), com.example.myconsume.activity.login.MainActivity.class));
        getActivity().finish();
    }
    //清除本地用户信息
    private void deleteUserInfo(){
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("user",getActivity().MODE_PRIVATE).edit();
        editor.putInt("id",-1);
        editor.putString("username","");
        editor.putString("password","");
        editor.putString("sex","");
        editor.putLong("phone",-1);
        editor.putBoolean("auto",false);
        editor.apply();
    }
    //删除用户对话框
    public void userDelete(){

        MaterialDialog dialog=new MaterialDialog.Builder(getActivity())
                .title("确认删除：(该操作会删除该用户的所有本地信息，且不可恢复)")
                .titleGravity(GravityEnum.CENTER)
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input("请输入用户密码", inputPaw, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        inputPaw=input.toString();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (isPwd(inputPaw)){
                            deleteUser();
                        }else{
                            Toast.makeText(getActivity(), "密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .negativeText("取消")
                .show();
    }
    //删除用户
    private void deleteUser() {
        int userId=Util.getUserId(getActivity());
        DataSupport.deleteAll(User.class,"id=?",String.valueOf(userId));
        DataSupport.deleteAll(Account.class,"userid=?",String.valueOf(userId));
        DataSupport.deleteAll(Record.class,"userid=?",String.valueOf(userId));
        deleteUserInfo();
        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), com.example.myconsume.activity.login.MainActivity.class));
        getActivity().finish();
    }

    //检查密码是否正确
    private boolean isPwd(String password){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user",getActivity().MODE_PRIVATE);
        String truePwd = sharedPreferences.getString("password", null);
        if (truePwd==null){
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
        if (truePwd.equals(password.trim()))
            return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_user_exit:
                cancellation();     //注销用户
                break;
            case R.id.bt_user_del:
                userDelete();
                break;
        }

    }
}
