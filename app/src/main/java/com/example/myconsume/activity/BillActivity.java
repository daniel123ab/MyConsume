package com.example.myconsume.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.myconsume.R;
import com.example.myconsume.activity.adapter.BookNoteAdapter;
import com.example.myconsume.activity.adapter.MonthAccountAdapter;
import com.example.myconsume.entiy.Account;
import com.example.myconsume.entiy.BSort;
import com.example.myconsume.entiy.Record;
import com.example.myconsume.util.BSortUtils;
import com.example.myconsume.util.DateUtil;
import com.example.myconsume.util.Util;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    //时间选择
    private int mYear;
    private int mMonth;
    private int mDay;
    private String days;


    private ViewPager mVpType;
    private TextView mTvDate;
    private TextView mTvSort;
    private TextView mTvPayType;
    private TextView mTvIn;
    private TextView mTvOut;
    private TextView mTvMoney;
    private LinearLayout layoutIcon;



    private List<View> viewList;
    private List<BSort> mDatas;

    protected int page;
    protected boolean isTotalPage;
    protected int sortPage = -1;
    protected List<BSort> tempList;
    protected ImageView[] icons;
    //记录上一次点击后的分类
    public BSort lastBean;

    //选择器
    protected List<String> cardItems;
    protected List<Account> accounts;
    protected int selectedPayinfoIndex = 0;      //选择的支付方式序号


    public boolean isOutcome = true;
    public boolean isEdit = false;

    //设置金额
    protected boolean isDot;
    protected String num = "0";               //整数部分
    protected String dotNum = ".00";          //小数部分
    protected final int MAX_NUM = 9999999;    //最大整数
    protected final int DOT_NUM = 2;          //小数部分最大位数
    protected int count = 0;

    //备注
    protected String remarkInput = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }

    private void initView() {
        mTvDate=findViewById(R.id.tb_note_date);
        mTvIn=findViewById(R.id.tb_note_income);
        mTvOut=findViewById(R.id.tb_note_outcome);
        mVpType=findViewById(R.id.viewpager_item);
        mTvSort=findViewById(R.id.item_tb_type_tv);
        mTvPayType=findViewById(R.id.tb_note_cash);
        mTvIn = findViewById(R.id.tb_note_income);
        mTvOut = findViewById(R.id.tb_note_outcome);
        mTvMoney = findViewById(R.id.tb_note_money);
        layoutIcon = findViewById(R.id.layout_icon);
        initData();
        initViewPager();
    }

    private void initData() {
        mDatas= BSortUtils.getOutBSort();
        if (mDatas.size()==0||mDatas==null){
            BSortUtils.init();
            mDatas=BSortUtils.getOutBSort();
        }
        //加载支付方式信息
        accounts=Util.getAccount(this);
        cardItems= Util.getAccountType(accounts);
        //设置当前时间
        GregorianCalendar calendar=DateUtil.getCalendar(System.currentTimeMillis());
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        days=DateUtil.getTime(calendar,"y-MM-dd");
        mTvDate.setText(days);

        mTvOut.setSelected(true);

        mTvPayType.setText(cardItems.get(0));
        setTitle();
        setTitleStatus();
    }

    private void initViewPager(){
        LayoutInflater inflater = this.getLayoutInflater();// 获得一个视图管理器LayoutInflater
        viewList = new ArrayList<>();// 创建一个View的集合对象
        //声明一个局部变量来存储分类集合
        //否则在收入支出类型切换时末尾一直添加选项
        List<BSort> tempData = new ArrayList<>();
        tempData.addAll(mDatas);
//        //末尾加上添加选项
//        tempData.add(new BSort( "添加", "sort_tianjia.png", 0, 0, null));
        if (tempData.size() % 15 == 0)
            isTotalPage = true;
            page = (int) Math.ceil(tempData.size() * 1.0 / 15);
            for (int i = 0; i < page; i++) {
                tempList = new ArrayList<>();
                View view = inflater.inflate(R.layout.item_tb_type_page, null);
                RecyclerView recycle = view.findViewById(R.id.pager_type_recycle);
                if (i != page - 1 || (i == page - 1 && isTotalPage)) {
                    for (int j = 0; j < 15; j++) {
                        tempList.add(tempData.get(i * 15 + j));
                    }
                } else {
                    for (int j = 0; j < tempData.size() % 15; j++) {
                        tempList.add(tempData.get(i * 15 + j));
                    }
                }

            BookNoteAdapter mAdapter = new BookNoteAdapter(this, tempList);
            mAdapter.setOnBookNoteClickListener(new BookNoteAdapter.OnBookNoteClickListener() {
                @Override
                public void OnClick(int index) {
                    //获取真实index
                    index = index + mVpType.getCurrentItem() * 15;
                    if (index == mDatas.size()) {
                        //修改分类

                    } else {
                        //选择分类
                        lastBean = mDatas.get(index);
                        mTvSort.setText(lastBean.getSortName());
                    }
                }

                @Override
                public void OnLongClick(int index) {

                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            recycle.setLayoutManager(layoutManager);
            recycle.setAdapter(mAdapter);
            viewList.add(view);
            mVpType.setAdapter(new MonthAccountAdapter(viewList));
            mVpType.setOverScrollMode(View.OVER_SCROLL_NEVER);
            mVpType.setOffscreenPageLimit(1);//预加载数据页
            mVpType.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    try {
                        for (int i = 0; i < viewList.size(); i++) {
                            icons[i].setImageResource(R.drawable.icon_banner_point2);
                        }
                        icons[position].setImageResource(R.drawable.icon_banner_point1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            initIcon();
        }
    }
    /**
     * 添加账单分类指示器
     */
    protected void initIcon() {
        icons = new ImageView[viewList.size()];
        layoutIcon.removeAllViews();
        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageView(this);
            icons[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            icons[i].setImageResource(R.drawable.icon_banner_point2);
            if (mVpType.getCurrentItem() == i) {
                icons[i].setImageResource(R.drawable.icon_banner_point1);
            }
            icons[i].setPadding(5, 0, 5, 0);
            icons[i].setAdjustViewBounds(true);
            layoutIcon.addView(icons[i]);
        }
        if (sortPage != -1)
            mVpType.setCurrentItem(sortPage);
    }

    /**
     * 显示日期选择器
     */

    public void showTimeSelector(){
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                mYear = i;
                mMonth = i1;
                mDay = i2;
                if (mMonth + 1 < 10) {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("-").append("0").
                                append(mMonth + 1).append("-").append("0").append(mDay).toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("-").append("0").
                                append(mMonth + 1).append("-").append(mDay).toString();
                    }

                } else {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("-").
                                append(mMonth + 1).append("-").append("0").append(mDay).toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("-").
                                append(mMonth + 1).append("-").append(mDay).toString();
                    }

                }
                mTvDate.setText(days);
            }

        }, mYear, mMonth, mDay).show();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.tb_note_income://收入
                isOutcome = false;
                setTitleStatus();
                break;
            case R.id.tb_note_outcome://支出
                isOutcome = true;
                setTitleStatus();
                break;
            case R.id.tb_note_cash://现金
                showPayinfoSelector();
                break;
            case R.id.tb_note_date://日期
                showTimeSelector();
                break;
            case R.id.tb_note_remark://备注
                showContentDialog();
                break;
            case R.id.tb_calc_num_done://确定
                doCommit();
                break;
            case R.id.tb_calc_num_1:
                calcMoney(1);
                break;
            case R.id.tb_calc_num_2:
                calcMoney(2);
                break;
            case R.id.tb_calc_num_3:
                calcMoney(3);
                break;
            case R.id.tb_calc_num_4:
                calcMoney(4);
                break;
            case R.id.tb_calc_num_5:
                calcMoney(5);
                break;
            case R.id.tb_calc_num_6:
                calcMoney(6);
                break;
            case R.id.tb_calc_num_7:
                calcMoney(7);
                break;
            case R.id.tb_calc_num_8:
                calcMoney(8);
                break;
            case R.id.tb_calc_num_9:
                calcMoney(9);
                break;
            case R.id.tb_calc_num_0:
                calcMoney(0);
                break;
            case R.id.tb_calc_num_dot:
                if (dotNum.equals(".00")) {
                    isDot = true;
                    dotNum = ".";
                }
                mTvMoney.setText(num + dotNum);
                break;
            case R.id.tb_note_clear://清空
                doClear();
                break;
            case R.id.tb_calc_num_del://删除
                doDelete();
                break;
        }
    }

    protected void setTitle() {
        if (isOutcome) {
            //设置支付状态
            mTvOut.setSelected(true);
            mTvIn.setSelected(false);
            mDatas = BSortUtils.getOutBSort();
        } else {
            //设置收入状态
            mTvOut.setSelected(false);
            mTvIn.setSelected(true);
            mDatas = BSortUtils.getInBSort();
        }
    }
    protected void setTitleStatus() {

        setTitle();
        //默认选择第一个分类
        lastBean = mDatas.get(0);
        //设置选择的分类
        mTvSort.setText(lastBean.getSortName());
        initViewPager();
    }
    /**
     * 清空金额
     */
    public void doClear() {
        num = "0";
        count = 0;
        dotNum = ".00";
        isDot = false;
        mTvMoney.setText("0.00");
    }

    /**
     * 删除上次输入
     */
    public void doDelete() {
        if (isDot) {
            if (count > 0) {
                dotNum = dotNum.substring(0, dotNum.length() - 1);
                count--;
            }
            if (count == 0) {
                isDot = false;
                dotNum = ".00";
            }
            mTvMoney.setText(num + dotNum);
        } else {
            if (num.length() > 0)
                num = num.substring(0, num.length() - 1);
            if (num.length() == 0)
                num = "0";
            mTvMoney.setText(num + dotNum);
        }
    }

    /**
     * 计算金额
     *
     * @param money
     */
    protected void calcMoney(int money) {
        if (num.equals("0") && money == 0)
            return;
        if (isDot) {
            if (count < DOT_NUM) {
                count++;
                dotNum += money;
                mTvMoney.setText(num + dotNum);
            }
        } else if (Integer.parseInt(num) < MAX_NUM) {
            if (num.equals("0"))
                num = "";
            num += money;
            mTvMoney.setText(num + dotNum);
        }
    }
    /**
     * 显示支付方式选择器
     */
    public void showPayinfoSelector() {
        new MaterialDialog.Builder(this)
                .title("选择支付方式")
                .titleGravity(GravityEnum.CENTER)
                .items(cardItems)
                .positiveText("确定")
                .negativeText("取消")
                .itemsCallbackSingleChoice(selectedPayinfoIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        selectedPayinfoIndex = which;
                        mTvPayType.setText(cardItems.get(which));
                        dialog.dismiss();
                        return false;
                    }
                }).show();
    }

    /**
     * 显示备注内容输入框
     */
    public void showContentDialog() {

        new MaterialDialog.Builder(this)
                .title("备注")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 200, R.color.textRed)
                .input("写点什么", remarkInput, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "内容不能为空！" + input,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            remarkInput = input.toString();
                        }
                    }
                }).positiveText("确定")
                .show();
    }

    /**
     * 提交账单
     */
    public void doCommit() {
        final SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
        final String crDate = days + sdf.format(new Date());
        if ((num + dotNum).equals("0.00")||(num + dotNum).equals("0.0")) {
            Toast.makeText(this, "唔姆，你还没输入金额", Toast.LENGTH_SHORT).show();
            return;
        }

        float money=Float.valueOf(num+dotNum);
        Account a=accounts.get(selectedPayinfoIndex);
        if (mTvOut.isSelected()) {
            money=-money;
        }
        Record record=new Record(Util.getUserId(this),money,lastBean.getSortName(),cardItems.get(selectedPayinfoIndex), DateUtil.getMillis(crDate),remarkInput,a.getId());
        record.save();
        Account account=new Account();
        account.setBalance(a.getBalance()+money);
        account.updateAll("id=?",String.valueOf(a.getId()));
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        finish();
    }

}
