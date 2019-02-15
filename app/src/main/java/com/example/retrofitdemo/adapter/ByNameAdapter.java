package com.example.retrofitdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofitdemo.bean.ByName;
import com.facebook.drawee.view.SimpleDraweeView;
import com.example.retrofitdemo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class ByNameAdapter extends RecyclerView.Adapter<ByNameAdapter.ViewHolder> {
    private Context mContext;
    private List<ByName.ResultBean> mList;

    public ByNameAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
    }

    public void setData(List<ByName.ResultBean> datas) {
        mList.clear();
        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_recycle,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri=Uri.parse(mList.get(i).getMasterPic());
        viewHolder.imageView.setImageURI(uri);
        viewHolder.name.setText(mList.get(i).getCommodityName());
        viewHolder.price.setText("￥："+mList.get(i).getPrice()+"");
        viewHolder.sayMo.setText("已售"+mList.get(i).getSaleNum()+"件");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.setonclicklisener(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView imageView;
        private TextView name,price,sayMo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            sayMo=itemView.findViewById(R.id.sayMo);
        }
    }
    private Cicklistener listener;

    public void result(Cicklistener listener) {
        this.listener = listener;
    }
    public interface Cicklistener {
        void setonclicklisener(int index);
    }
}
