package com.example.myconsume.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.myconsume.R;
import com.example.myconsume.activity.BillActivity;
import com.example.myconsume.entiy.BSort;
import com.example.myconsume.util.ImageUtils;

import java.util.List;

/**
 * 账单分类Adapter（AddBillAdapter)
 */
public class BookNoteAdapter extends RecyclerView.Adapter<BookNoteAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BSort> mDatas;

    private OnBookNoteClickListener onBookNoteClickListener;

    public void setmDatas(List<BSort> mDatas) {
        this.mDatas = mDatas;
    }

    public BookNoteAdapter(BillActivity context, List<BSort> datas) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;

    }

    public void setOnBookNoteClickListener(OnBookNoteClickListener listener) {
        if (onBookNoteClickListener == null)
            this.onBookNoteClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return (mDatas == null) ? 0 : mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tb_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(mDatas.get(position).getSortName());
        holder.img.setImageDrawable(ImageUtils.getDrawable(mContext,mDatas.get(position).getSortImg()));
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView title;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.item_tb_type_tv);
            img = (ImageView) view.findViewById(R.id.item_tb_type_img);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBookNoteClickListener.OnClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onBookNoteClickListener.OnLongClick(getAdapterPosition());
            return false;
        }
    }

    /**
     * 自定义分类选择接口
     */
    public interface OnBookNoteClickListener {
        void OnClick(int index);

        void OnLongClick(int index);
    }

}
