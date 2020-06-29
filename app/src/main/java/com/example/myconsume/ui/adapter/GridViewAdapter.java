package com.example.myconsume.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.myconsume.R;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.ui.gallery.GalleryFragment;
import com.example.myconsume.util.DateUtil;
import com.example.myconsume.util.Util;

import org.w3c.dom.Text;

import java.util.List;

public class GridViewAdapter extends BaseSwipeAdapter{

    private Context mContext;
    private List<Record> records;
    private GalleryFragment listener;
    private int idTag=0;
    public GridViewAdapter(Context mContext,List<Record> records,GalleryFragment listener) {
        this.records=records;
        this.mContext = mContext;
        this.listener=listener;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_record_grid, null);
    }

    @Override
    public void fillValues(int position, View convertView) {
        Record record=records.get(position);
        TextView money=convertView.findViewById(R.id.item_money);
        TextView type=convertView.findViewById(R.id.consume_type);
        TextView method=convertView.findViewById(R.id.consume_method);
        TextView data=convertView.findViewById(R.id.consume_data);
        TextView remark=convertView.findViewById(R.id.consume_remark);
        money.setText(String.valueOf(record.getMoney()));
        type.setText(record.getType());
        method.setText(record.getPay_method());
        remark.setText(record.getRemarks());
        ImageView imageView=convertView.findViewById(R.id.trash);
        imageView.setOnClickListener(listener);
        imageView.setTag(record.getId());
        data.setText(DateUtil.getTime(DateUtil.getCalendar(record.getTime()),"y/M/d H:m:s"));
    }

    @Override
    public int getCount() {
        return records!=null?records.size():0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
