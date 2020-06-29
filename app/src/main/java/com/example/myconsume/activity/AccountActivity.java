package com.example.myconsume.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myconsume.R;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.util.DateUtil;
import com.example.myconsume.util.Util;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";

    private EditText mEtMoney;
    private EditText mEtTurnMoney;
    private Spinner mSpAccount;
    private RecyclerView mLvRecords;

    private int accountId;
    private List<Account> accounts;

    private int markAccount;
    private Account account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        init();
    }

    private void init() {
        Intent intent=getIntent();
        accountId=intent.getIntExtra("accountId",0);
        Log.i(TAG, "init: accountId="+accountId);
        account=Util.getAccountById(accountId);
        mEtMoney=findViewById(R.id.et_add_money);
        mSpAccount=findViewById(R.id.sp_account);
        mLvRecords=findViewById(R.id.recycler_view_record);
        mEtTurnMoney=findViewById(R.id.et_turn_money);

        //初始化Spinner
        accounts=Util.getAccount(this);
        List<String> accountTypes=Util.getAccountType(accounts);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,accountTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSpAccount.setAdapter(adapter);
        mSpAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                markAccount=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //初始化该卡记录
        initRecords();
    }

    private void initRecords() {
        List<Record> recordList=Util.getRecordsByAccountId(this,accountId);
        Collections.sort(recordList, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getId()>o2.getId()?-1:1;
            }
        });
        Log.i(TAG, "initRecords: records.size="+recordList.size());
        MyRecordsAdapter adapter=new MyRecordsAdapter(recordList);
        mLvRecords.setLayoutManager(new LinearLayoutManager(this));
        mLvRecords.setAdapter(adapter);
    }
    //添加余额的按钮的点击事件
    public void addMoney(View view){
        float money=Float.parseFloat(mEtMoney.getText().toString());
        float balance=money+account.getBalance();
        Account account1=new Account();
        account1.setBalance(balance);
        account1.updateAll("id=?",String.valueOf(accountId));
        Toast.makeText(this, "添加成功："+money+"元", Toast.LENGTH_SHORT).show();
        finish();
    }
    //转账的按钮点击事件
    public void turnMoney(View view){
        Account toAccount=accounts.get(markAccount);
        if (toAccount.getId()==accountId){
            Toast.makeText(this, "转账卡为同一卡", Toast.LENGTH_SHORT).show();
            return;
        }
        float turnMoney=Float.parseFloat(mEtTurnMoney.getText().toString());
        Account account_1=new Account();
        account_1.setBalance(account.getBalance()-turnMoney);
        account_1.updateAll("id=?",String.valueOf(account.getId()));
        account_1.setBalance(toAccount.getBalance()+turnMoney);
        account_1.updateAll("id=?",String.valueOf(toAccount.getId()));
        Toast.makeText(this, "转账成功", Toast.LENGTH_SHORT).show();
        finish();
    }



    class MyRecordsAdapter extends RecyclerView.Adapter<MyRecordsAdapter.ViewHolder>{
        private List<Record> records;
        public MyRecordsAdapter(List<Record> records){
            this.records=records;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records,parent,false);
            ViewHolder holder=new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Record record=records.get(position);
            holder.recordMoney.setText(String.valueOf(record.getMoney())+"元");
            holder.recordTime.setText(DateUtil.getTime(DateUtil.getCalendar(record.getTime()),"y/M/d H:m:s"));
        }

        @Override
        public int getItemCount() {
            return records.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView recordMoney;
            TextView recordTime;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                recordMoney=itemView.findViewById(R.id.item_record_money);
                recordTime=itemView.findViewById(R.id.item_record_time);
            }
        }
    }
}
