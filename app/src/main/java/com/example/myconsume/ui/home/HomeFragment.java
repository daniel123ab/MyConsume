package com.example.myconsume.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myconsume.R;
import com.example.myconsume.activity.AccountActivity;
import com.example.myconsume.activity.MainActivity;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.ui.chart.DemoBase;
import com.example.myconsume.util.Util;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class HomeFragment extends DemoBase implements OnChartValueSelectedListener,View.OnClickListener {

    private HomeViewModel homeViewModel;
    private PieChart chart;
    private LinearLayout parent;
    private TextView balance;
    private View view;
    private TextView mTvDayOut;
    private TextView mTvDayIn;
    private TextView mTvMonthOut;
    private TextView mTvMonthIn;


//    private RecyclerView recycle;

    private int idTag=0;
    private List<Account> accounts;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        chart = root.findViewById(R.id.chart1);
        parent=root.findViewById(R.id.card_parent);
        balance=root.findViewById(R.id.all_balance);
        view=root;
//        recycle=root.findViewById(R.id.recycler_accounts);

        initAccount();
        initView(view);
        initChart();

        return root;
    }

    private void initView(View view) {
        mTvDayIn=view.findViewById(R.id.tv_this_day_in);
        mTvDayOut=view.findViewById(R.id.tv_this_day_out);
        mTvMonthIn=view.findViewById(R.id.tv_this_month_in);
        mTvMonthOut=view.findViewById(R.id.tv_this_month_out);

        List<Record> dayRecords=Util.getCDRecord(getActivity());
        List<Record> monthRecords=Util.getCRecords(getActivity());
        float dayOut=0;
        float dayIn=0;
        float monthOut=0;
        float monthIn=0;
        for (Record dayRecord : dayRecords) {
            float money=dayRecord.getMoney();
            if (money>=0){
                dayIn+=money;
            }else{
                dayOut+=-money;
            }
        }
        for (Record record : monthRecords) {
            float money=record.getMoney();
            if (money>=0){
                monthIn+=money;
            }else{
                monthOut+=-money;
            }
        }
        mTvDayOut.setText(Util.formatMoney(dayOut)+" 元");
        mTvDayIn.setText(Util.formatMoney(dayIn)+" 元");
        mTvMonthOut.setText(Util.formatMoney(monthOut)+" 元");
        mTvMonthIn.setText(Util.formatMoney(monthIn)+" 元");

    }

    private void initAccount() {
        float all_balance=0;
        accounts=Util.getAccount(getActivity());
        for (int i = 0; i < accounts.size(); i++) {
//            addCard(parent,accounts.get(i));                  一般样式的卡
            all_balance+=accounts.get(i).getBalance();
        }
        balance.setText(Util.formatMoney(all_balance)+" 元");

//        MyAdapter adapter=new MyAdapter(accounts,this);
//        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recycle.setAdapter(adapter);
    }

    //初始化图表数据
    private void initChart() {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);


        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);
        setData(Util.getOutRecords(getActivity()));
    }

    //设置处图表数据
    private void setData(List<Record> records) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Map<String ,Float> recordsTypeCount=Util.analyzeRecords(records);
        for (Map.Entry<String,Float> typeCount:recordsTypeCount.entrySet()){
            entries.add(new PieEntry(typeCount.getValue(),typeCount.getKey(),getResources().getDrawable(R.drawable.star)));
        }

        if (records.size()==0){
            entries.add(new PieEntry(0,"",getResources().getDrawable(R.drawable.star)));
        }
        PieDataSet dataSet = new PieDataSet(entries, "本月消费统计");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }
    //设置图表中间的文字
    private SpannableString generateCenterSpannableText() {
        float money= -Util.getCurrentMonthConsumeMoney(getActivity());
        GregorianCalendar calendar=new GregorianCalendar();
        SimpleDateFormat dateFormat=new SimpleDateFormat("y/M/d");
        String source="总消费\n  "+ money +"元  "+dateFormat.format(calendar.getTime());
        SpannableString s = new SpannableString(source);
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 3, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 3, s.length() - 10, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 3, s.length() - 10, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 3, s.length() - 10, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 10, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 10, s.length(), 0);
        return s;
    }
    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "PieChartActivity");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    public void addCard(LinearLayout parent, Account account){
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150);
        params.setMargins(50,25,50,25);
        LinearLayout card=new LinearLayout(getActivity());
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setPadding(0,10,0,0);
        card.setBackground(getActivity().getDrawable(R.drawable.card_view_normal));
        card.setLayoutParams(params);
        LinearLayout.LayoutParams gravity=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        gravity.gravity= Gravity.CENTER_VERTICAL;
        TextView textView=new TextView(getActivity());
//        textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        textView.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setGravity(Gravity.CENTER_VERTICAL);

        textView.setText(account.getType()+":");

        textView.setPadding(70,0,0,0);
        textView.setTextColor(Color.rgb(255,255,255));
        textView.setLayoutParams(gravity);
        TextView money=new TextView(getActivity());
//        money.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams weight=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);
        weight.weight=1.0f;
        weight.gravity= Gravity.CENTER_VERTICAL;
        money.setLayoutParams(weight);
        money.setGravity(Gravity.RIGHT);
        Log.i("TAG", "addCard: "+Util.formatMoney(account.getBalance())+"元");
        money.setPadding(0,0,50,0);
        money.setText(Util.formatMoney(account.getBalance())+"元");
        idTag++;
        money.setId(idTag);
        card.addView(textView);
        card.addView(money);
        card.setTag(account.getId());
        card.setOnClickListener(this);
        parent.addView(card);
    }

    @Override
    public void onResume() {
        super.onResume();
//        updateBalance();
    }

    private void updateBalance(){
        float allBalance=0;
        List<Account> accounts= Util.getAccount(getActivity());
        for (int i = 1; i <=idTag; i++) {
            TextView textView=view.findViewById(i);
            textView.setText(Util.formatMoney(accounts.get(i-1).getBalance())+"元");
            allBalance+=accounts.get(i-1).getBalance();
        }
        balance.setText(Util.formatMoney(allBalance)+"元");
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), AccountActivity.class);
        intent.putExtra("accountId",Integer.parseInt(v.getTag().toString()));
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startActivity(new Intent(getActivity(),MainActivity.class));
        getActivity().finish();
    }

//    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
//        private List<Account> accounts;
//        private HomeFragment fragment;
//        public MyAdapter(List<Account> accounts,HomeFragment fragment) {
//            this.accounts = accounts;
//            this.fragment=fragment;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account,parent,false);
//            view.setOnClickListener(fragment);
//            ViewHolder holder=new ViewHolder(view);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            Account account=accounts.get(position);
//            holder.type.setText(account.getType());
//            holder.balance.setText("余额:"+String.valueOf(account.getBalance())+" 元");
//            holder.view.setTag(account.getId());
//        }
//
//        @Override
//        public int getItemCount() {
//            return accounts.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder{
//            public TextView type;
//            public TextView balance;
//            public View view;
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                type=itemView.findViewById(R.id.tv_account_type);
//                balance=itemView.findViewById(R.id.tv_account_balance);
//                view=itemView;
//            }
//        }
//    }
}
