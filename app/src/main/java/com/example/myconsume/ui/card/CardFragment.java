package com.example.myconsume.ui.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myconsume.R;
import com.example.myconsume.activity.AccountActivity;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.util.Util;

import java.util.List;

public class CardFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRvCards;


    private List<Account> accounts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_card,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvCards=view.findViewById(R.id.recycler_accounts);


        initRecycler(); //初始化Recycler
    }

    private void initRecycler() {
        accounts= Util.getAccount(getActivity());
        MyAdapter adapter=new MyAdapter(accounts,this);
        mRvCards.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvCards.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), AccountActivity.class);
        intent.putExtra("accountId",Integer.parseInt(v.getTag().toString()));
        startActivityForResult(intent,1);
    }

    class MyAdapter extends RecyclerView.Adapter<CardFragment.MyAdapter.ViewHolder>{
        private List<Account> accounts;
        private CardFragment fragment;
        public MyAdapter(List<Account> accounts, CardFragment fragment) {
            this.accounts = accounts;
            this.fragment=fragment;
        }

        @NonNull
        @Override
        public CardFragment.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account,parent,false);
            view.setOnClickListener(fragment);
            CardFragment.MyAdapter.ViewHolder holder=new CardFragment.MyAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CardFragment.MyAdapter.ViewHolder holder, int position) {
            Account account=accounts.get(position);
            holder.type.setText(account.getType());
            holder.balance.setText("余额:"+String.valueOf(account.getBalance())+" 元");
            holder.view.setTag(account.getId());
        }

        @Override
        public int getItemCount() {
            return accounts.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public TextView type;
            public TextView balance;
            public View view;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                type=itemView.findViewById(R.id.tv_account_type);
                balance=itemView.findViewById(R.id.tv_account_balance);
                view=itemView;
            }
        }
    }
}
