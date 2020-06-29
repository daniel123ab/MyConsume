package com.example.myconsume.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myconsume.entiy.Record;
import com.example.myconsume.util.Util;


import org.litepal.crud.DataSupport;

import java.io.StringBufferInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class AddRecord extends AppCompatActivity {

    private EditText mEtMoney;
    private Spinner mDdvPayType;
    private Spinner mDdvConsumeType;
    private EditText mEtRemarks;
    private Button mBtCommit;

    List<Account> accounts=null;
    List<String> accountTypes=null;

    String[] consumeType=null;
    private int markAccountType=0;
    private int markConsumeType=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        mEtMoney=findViewById(R.id.consume_money);
        mDdvConsumeType=findViewById(R.id.consume_type);
        mDdvPayType=findViewById(R.id.pay_type);
        mEtRemarks=findViewById(R.id.edit_remarks);
        mBtCommit=findViewById(R.id.bt_record_add);
        initSpinner();
        initCommitBt();
    }
    //添加记录
    private void initCommitBt() {
        mBtCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float money=Float.parseFloat(mEtMoney.getText().toString());
                Account account=accounts.get(markAccountType);
                String consumeT=consumeType[markConsumeType];
                String accountT=accountTypes.get(markAccountType);
                String remarks= TextUtils.isEmpty(mEtRemarks.getText().toString())?"无":mEtRemarks.getText().toString();
                int userId=getUserId();
                long time=System.currentTimeMillis();
                Record record=new Record(userId,money,consumeT,accountT,time,remarks,account.getId());
                record.save();
                Account account1=new Account();
                account1.setBalance(account.getBalance()-money);
                account1.updateAll("id=?", String.valueOf(account.getId()));
                Toast.makeText(AddRecord.this, "添加成功！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initSpinner(){
        //设置消费类型的下拉列表
        consumeType=new String[]{Record.PAY_TYPE_1,Record.PAY_TYPE_2,Record.PAY_TYPE_3,Record.PAY_TYPE_4,Record.PAY_TYPE_5,Record.PAY_TYPE_6};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,consumeType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDdvConsumeType.setAdapter(adapter);
        mDdvConsumeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                markConsumeType=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //设置支付方式的下拉列表

        accountTypes=getAccountType();
        ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, accountTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDdvPayType.setAdapter(typeAdapter);
        mDdvPayType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                markAccountType=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private List<String> getAccountType(){
        int userId=getUserId();
        accounts= DataSupport.where("userid=?",String.valueOf(userId)).find(Account.class);
        List<String> accountTypes=new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            accountTypes.add(accounts.get(i).getType());
        }
        return accountTypes;
    }
    private int getUserId(){
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        int id=sharedPreferences.getInt("id",-1);



        return id;
    }


}
