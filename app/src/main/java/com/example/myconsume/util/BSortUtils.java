package com.example.myconsume.util;

import com.example.myconsume.entiy.BSort;

import org.litepal.crud.DataSupport;

import java.util.List;

public class BSortUtils {
    //按顺序返回支出分类
    public static List<BSort> getOutBSort(){
        return  DataSupport.where("income=?","0").order("priority").find(BSort.class);
    }
    //按顺序返回收入分类
    public static List<BSort> getInBSort(){
        return DataSupport.where("income=?","1").order("priority").find(BSort.class);
    }
    //获得所有的分类
    public static List<BSort> getAll(){
        return DataSupport.order("priority").find(BSort.class);
    }
    //初始化所有分类
    public static void init(){
        BSort bSort=new BSort("办公","sort_bangong.png",1,1,false);
        bSort.save();
        bSort=new BSort("餐饮","sort_canyin.png",2,1,false);
        bSort.save();
        bSort=new BSort("宠物","sort_chongwu.png",3,1,false);
        bSort.save();
        bSort=new BSort("返现","sort_fanxian.png",1,1,true);
        bSort.save();
        bSort=new BSort("购物","sort_gouwu.png",4,1,false);
        bSort.save();
        bSort=new BSort("孩子","sort_haizi.png",20,1,false);
        bSort.save();
        bSort=new BSort("还款","sort_huankuan.png",21,1,false);
        bSort.save();
        bSort=new BSort("奖金","sort_jiangjin.png",2,1,true);
        bSort.save();
        bSort=new BSort("兼职","sort_jianzhi.png",3,1,true);
        bSort.save();
        bSort=new BSort("交通","sort_jiaotong.png",7,1,false);
        bSort.save();
        bSort=new BSort("酒水","sort_jiushui.png",8,1,false);
        bSort.save();
        bSort=new BSort("捐赠","sort_juanzeng.png",9,1,false);
        bSort.save();
        bSort=new BSort("居家","sort_jujia.png",10,1,false);
        bSort.save();
        bSort=new BSort("礼金","sort_lijin.png",4,1,true);
        bSort.save();
        bSort=new BSort("零钱","sort_lingqian.png",5,1,true);
        bSort.save();
        bSort=new BSort("零食","sort_lingshi.png",11,1,false);
        bSort.save();
        bSort=new BSort("礼物","sort_liwu.png",12,1,false);
        bSort.save();
        bSort=new BSort("利息","sort_lixi.png",6,1,true);
        bSort.save();
        bSort=new BSort("旅行","sort_lvxing.png",13,1,false);
        bSort.save();
        bSort=new BSort("美容","sort_meirong.png",14,1,false);
        bSort.save();
        bSort=new BSort("手续费","sort_shouxufei.png",15,1,false);
        bSort.save();
        bSort=new BSort("数码","sort_shuma.png",16,1,false);
        bSort.save();
        bSort=new BSort("通讯","sort_tongxun.png",17,1,false);
        bSort.save();
        bSort=new BSort("学习","sort_xuexi.png",18,1,false);
        bSort.save();
        bSort=new BSort("医疗","sort_yiliao.png",19,1,false);
        bSort.save();
        bSort=new BSort("佣金","sort_yongjin.png",7,1,true);
        bSort.save();
        bSort=new BSort("娱乐","sort_yule.png",5,1,false);
        bSort.save();
        bSort=new BSort("运动","sort_yundong.png",6,1,false);
        bSort.save();
        bSort=new BSort("住房","sort_zhufang.png",22,1,false);
        bSort.save();
    }
}
