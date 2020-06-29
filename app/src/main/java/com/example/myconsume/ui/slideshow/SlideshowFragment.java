package com.example.myconsume.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myconsume.R;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.util.DateUtil;
import com.example.myconsume.util.Util;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private TextView mTvMoney;
    private RecyclerView mRvRecords;
    private TextView mtVText;
    DropDownView dropDownView;
    private LinearLayout mLiAllMoney;

    private String[] FirstTitle = {"a1", "b1"};
    private int year;
    private int month;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        dropDownView = (DropDownView) root.findViewById(R.id.dropdownview);
        mTvMoney=root.findViewById(R.id.tv_all_consume_money);
        mRvRecords=root.findViewById(R.id.recycler_records);
        mLiAllMoney=root.findViewById(R.id.linear_all_money);
        mtVText=root.findViewById(R.id.tv_text);
        mRvRecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        init(root);
        setData();
        return root;
    }

    private void setData() {
        Log.i("TAG", "setData: "+year+" "+month);
        List<Record> records= Util.getRecordsByTime(getActivity(),DateUtil.getLongTime(year,month-1),DateUtil.getLongTime(year,month));
        if (records.size()==0||records==null){
            mLiAllMoney.setVisibility(View.GONE);
            mRvRecords.setVisibility(View.GONE);
            mtVText.setVisibility(View.VISIBLE);
            mTvMoney.setVisibility(View.GONE);
        }else{
            float allMoney=0;
            for (Record record : records) {
                allMoney+=record.getMoney();
            }
            mTvMoney.setText(String.valueOf(allMoney));
            mTvMoney.setVisibility(View.VISIBLE);
            mLiAllMoney.setVisibility(View.VISIBLE);
            mtVText.setVisibility(View.GONE);
            mRvRecords.setVisibility(View.VISIBLE);
            MyRecyclerRecords adapter=new MyRecyclerRecords(records);
            mRvRecords.setAdapter(adapter);
        }
    }

    private void init(View root) {
        dropDownView.setmContext(getActivity());
        dropDownView.setLastList(FirstTitle);
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dropDownView.setCurrentMonth(""+(++month));
        dropDownView.setCurrentYear(""+year);
        dropDownView.setOnDropDownItemClickListener(new DropDownView.OnDropDownItemClickListener() {
            @Override
            public void onDropDownItemClick(String selectedDropDownChildItem, int clickDropDownNum) {
                dropDownView.setTitle(selectedDropDownChildItem,clickDropDownNum);
                if (clickDropDownNum==0){
                    year=Integer.parseInt(selectedDropDownChildItem);
                }else {
                    month=Integer.parseInt(selectedDropDownChildItem);
                }
                Toast.makeText(getActivity(), "现在选择的时间为："+year+"年"+month+"月", Toast.LENGTH_SHORT).show();
                setData();
                Log.i("TAG",selectedDropDownChildItem);
            }
        });

        dropDownView.setOnDropDownClickListener(new DropDownView.OnDropDownClickListener() {
            @Override
            public void onDropDownClick(int clickDropDownNum) {
                switch (clickDropDownNum) {
                    case 0:
                        dropDownView.setStrs(Constant.A,clickDropDownNum);
                        break;
                    case 1:
                        dropDownView.setStrs(Constant.B,clickDropDownNum);
                        break;

                    default:
                        break;
                }
            }
        });

    }


    class MyRecyclerRecords extends RecyclerView.Adapter<MyRecyclerRecords.ViewHolder>{
        List<Record> records;

        public MyRecyclerRecords(List<Record> records) {
            this.records = records;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_records_1,parent,false);
            ViewHolder holder=new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Record record=records.get(position);
            holder.tvMoney.setText(String.valueOf(record.getMoney()));
            holder.tvMethod.setText("支付方式："+record.getPay_method());
            holder.tvType.setText(record.getType());
            holder.tvRemark.setText(record.getRemarks());
            GregorianCalendar time= DateUtil.getCalendar(record.getTime());
            holder.tvTime.setText(DateUtil.getTime(time,"y/M/d H:m:s"));
        }

        @Override
        public int getItemCount() {
            return records.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public TextView tvMoney;
            public TextView tvTime;
            public TextView tvType;
            public TextView tvRemark;
            public TextView tvMethod;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMoney=itemView.findViewById(R.id.item_money);
                tvMethod=itemView.findViewById(R.id.consume_method);
                tvRemark=itemView.findViewById(R.id.consume_remark);
                tvTime=itemView.findViewById(R.id.consume_data);
                tvType=itemView.findViewById(R.id.consume_type);
            }
        }
    }
}
