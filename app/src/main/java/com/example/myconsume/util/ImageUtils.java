package com.example.myconsume.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myconsume.R;

public class ImageUtils {

    /**
     * 根据图片url设置与之相对应的本地图片
     * @param imgUrl
     * @return
     */
    public static Drawable getDrawable(Context context,String imgUrl){
        Drawable drawable = null;
        if(imgUrl.equals("sort_shouxufei.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_shouxufei);
        else if(imgUrl.equals("sort_huankuan.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_huankuan);
        else if(imgUrl.equals("sort_yongjin.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_yongjin);
        else if(imgUrl.equals("sort_lingqian.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_lingqian);
        else if(imgUrl.equals("sort_yiban.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_yiban);
        else if(imgUrl.equals("sort_lixi.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_lixi);
        else if(imgUrl.equals("sort_gouwu.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_gouwu);
        else if(imgUrl.equals("sort_weiyuejin.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_weiyuejin);
        else if(imgUrl.equals("sort_zhufang.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_zhufang);
        else if(imgUrl.equals("sort_bangong.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_bangong);
        else if(imgUrl.equals("sort_canyin.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_canyin);
        else if(imgUrl.equals("sort_yiliao.png"))
            drawable =context.getResources().getDrawable(R.mipmap.sort_yiliao);
        else if(imgUrl.equals("sort_tongxun.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_tongxun);
        else if(imgUrl.equals("sort_yundong.png"))
            drawable =context.getResources().getDrawable(R.mipmap.sort_yundong);
        else if(imgUrl.equals("sort_yule.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_yule);
        else if(imgUrl.equals("sort_jujia.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_jujia);
        else if(imgUrl.equals("sort_chongwu.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_chongwu);
        else if(imgUrl.equals("sort_shuma.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_shuma);
        else if(imgUrl.equals("sort_juanzeng.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_juanzeng);
        else if(imgUrl.equals("sort_lingshi.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_lingshi);
        else if(imgUrl.equals("sort_jiangjin.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_jiangjin);
        else if(imgUrl.equals("sort_haizi.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_haizi);
        else if(imgUrl.equals("sort_zhangbei.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_zhangbei);
        else if(imgUrl.equals("sort_liwu.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_liwu);
        else if(imgUrl.equals("sort_xuexi.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_xuexi);
        else if(imgUrl.equals("sort_shuiguo.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_shuiguo);
        else if(imgUrl.equals("sort_meirong.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_meirong);
        else if(imgUrl.equals("sort_weixiu.png"))
            drawable =context.getResources().getDrawable(R.mipmap.sort_weixiu);
        else if(imgUrl.equals("sort_lvxing.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_lvxing);
        else if(imgUrl.equals("sort_jiaotong.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_jiaotong);
        else if(imgUrl.equals("sort_jiushui.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_jiushui);
        else if(imgUrl.equals("sort_lijin.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_lijin);
        else if(imgUrl.equals("sort_fanxian.png"))
            drawable =context.getResources().getDrawable(R.mipmap.sort_fanxian);
        else if(imgUrl.equals("sort_jianzhi.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_jianzhi);
        else if(imgUrl.equals("sort_tianjiade.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_tianjiade);
        else if(imgUrl.equals("sort_tianjia.png"))
            drawable = context.getResources().getDrawable(R.mipmap.sort_tianjia);
        else if(imgUrl.equals("card_cash.png"))
            drawable = context.getResources().getDrawable(R.mipmap.card_cash);
        else if(imgUrl.equals("card_account.png"))
            drawable = context.getResources().getDrawable(R.mipmap.card_account);
        else if(imgUrl.equals("card_account.png"))
            drawable = context.getResources().getDrawable(R.mipmap.card_account);
        else
            drawable=null;

        return drawable;
    }


    public static Bitmap toRoundCorner(Bitmap bitmap, float ratio) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap ClipSquareBitmap(Bitmap bmp, int width, int radius) {
        if (bmp == null || width <= 0)
            return null;
        //如果图片比较小就没必要进行缩放了

        /**
         * 把图片进行缩放，以宽高最小的一边为准，缩放图片比例
         * */
        if (bmp.getWidth() > width && bmp.getHeight() > width) {
            if (bmp.getWidth() > bmp.getHeight()) {
                bmp = Bitmap.createScaledBitmap(bmp, (int) (((float) width) * bmp.getWidth() / bmp.getHeight()), width, false);
            } else {
                bmp = Bitmap.createScaledBitmap(bmp, width, (int) (((float) width) * bmp.getHeight() / bmp.getWidth()), false);
            }

        } else {
            width = bmp.getWidth() > bmp.getHeight() ? bmp.getHeight() : bmp.getWidth();
            Log.d("zeyu","宽" + width + ",w" + bmp.getWidth() + ",h" + bmp.getHeight());
            if (radius > width) {
                radius = width;
            }
        }
        Bitmap output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        //设置画笔全透明
        canvas.drawARGB(0, 0, 0, 0);
        Paint paints = new Paint();
        paints.setColor(Color.WHITE);
        paints.setAntiAlias(true);//去锯齿
        paints.setFilterBitmap(true);
        //防抖动
        paints.setDither(true);

        //把图片圆形绘制矩形
        if (radius <= 0)
            canvas.drawRect(new Rect(0, 0, width, width), paints);
        else //绘制圆角
            canvas.drawRoundRect(new RectF(0, 0, width, width), radius, radius, paints);
        // 取两层绘制交集。显示前景色。
        paints.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect();
        if (bmp.getWidth() >= bmp.getHeight()) {
            rect.set((bmp.getWidth() - width) / 2, 0, (bmp.getWidth() + width) / 2, width);
        } else {
            rect.set(0, (bmp.getHeight() - width) / 2, width, (bmp.getHeight() + width) / 2);
        }
        Rect rect2 = new Rect(0, 0, width, width);
        //第一个rect 针对bmp的绘制区域，rect2表示绘制到上面位置
        canvas.drawBitmap(bmp, rect, rect2, paints);
        bmp.recycle();
        return output;
    }
}
