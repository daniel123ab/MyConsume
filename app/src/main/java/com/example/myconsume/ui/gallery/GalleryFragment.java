package com.example.myconsume.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.daimajia.swipe.util.Attributes;
import com.example.myconsume.R;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.ui.adapter.GridViewAdapter;
import com.example.myconsume.util.Util;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public class GalleryFragment extends Fragment implements View.OnClickListener{

    private GalleryViewModel galleryViewModel;
    private GridView recordList;

    private TextView outMoney;
    private TextView inMoney;
    private TextView total;

    List<Record> records=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recordList=root.findViewById(R.id.gv_list_record);
        setGridViewAdapter();
        initData(root);     //对上部分析初始化
        return root;
    }

    private void initData(View root) {
        outMoney = root.findViewById(R.id.t_outcome);
        inMoney = root.findViewById(R.id.t_income);
        total = root.findViewById(R.id.t_total);
        float out = Util.getCOut(getActivity());
        float in = Util.getCIn(getActivity());
        float t = in - out;
        outMoney.setText(String.valueOf(out));
        inMoney.setText(String.valueOf(in));
        total.setText(String.valueOf(t));
    }

    private void setGridViewAdapter() {
        records = Util.getRecords(getActivity());
        GridViewAdapter adapter=new GridViewAdapter(getActivity(),records,this);
        adapter.setMode(Attributes.Mode.Single);
        recordList.setAdapter(adapter);
        recordList.setSelected(false);
        recordList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemLongClick","onItemLongClick:" + position);
                return false;
            }
        });
        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemClick","onItemClick:" + position);
            }
        });


        recordList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemSelected","onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int recordId=Integer.parseInt(v.getTag().toString());
        Record record= Util.getRecordById(recordId);
        record.delete();
        int option=0;
        for (int i=0;i<records.size();i++){
            if (record.getId()==records.get(i).getId()){
                option=i;
                records.remove(records.get(i));
            }
        }
        GridViewAdapter adapter=new GridViewAdapter(getActivity(),records,this);
        recordList.setAdapter(adapter);
    }
}
