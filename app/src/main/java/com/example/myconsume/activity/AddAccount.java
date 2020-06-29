package com.example.myconsume.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myconsume.R;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.util.Util;

public class AddAccount extends AppCompatActivity {
    private Spinner mSpAccountType;
    private EditText mEdAccountId;
    private EditText mEdAccountBalance;
    private Button mBtCommit;
    private int markAccountType=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        initView();
    }

    private void initView() {
        mSpAccountType=findViewById(R.id.account_type);
        mEdAccountId=findViewById(R.id.account_id);
        mEdAccountBalance=findViewById(R.id.account_balance);
        mBtCommit=findViewById(R.id.bt_account_add);
        //设置卡类型的Spinner的下拉列表
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Account.TYPE);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpAccountType.setAdapter(adapter);
        mSpAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                markAccountType=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBtCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountType=Account.TYPE[markAccountType];
                String accountId=mEdAccountId.getText().toString();
                float accountBalance=TextUtils.isEmpty(mEdAccountBalance.getText().toString())?0:Float.parseFloat(mEdAccountBalance.getText().toString());
                if (!TextUtils.isEmpty(accountId)){
                    int userId= Util.getUserId(AddAccount.this);
                    Account account=new Account(userId,accountType,accountBalance,accountId);
                    account.save();
                    Toast.makeText(AddAccount.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AddAccount.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
