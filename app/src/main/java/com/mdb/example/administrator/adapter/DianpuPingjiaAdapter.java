package com.mdb.example.administrator.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.bean.DianpupingjiaBean;

import java.util.ArrayList;

public class DianpuPingjiaAdapter extends BaseAdapter {
    Context context;
    ArrayList<DianpupingjiaBean> arrayList;
    private static final String TAG = "DianpuPingjiaAdapter";
    public DianpuPingjiaAdapter(Context context,ArrayList<DianpupingjiaBean> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.dianpupingfen_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        viewHolder = (ViewHolder) view.getTag();
        final DianpupingjiaBean dianpupingjiaBean = arrayList.get(i);
        viewHolder.tv_id.setText(dianpupingjiaBean.getData().getPageInfo().getList().get(i).getUser_id()+"");
        viewHolder.tv_time.setText(dianpupingjiaBean.getData().getPageInfo().getList().get(i).getCreateTime()+"");
        if (dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_des()!=null){
            viewHolder.ll_info.setVisibility(View.VISIBLE);
            viewHolder.tv_info.setText(dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_des()+"");
        }else {
            viewHolder.ll_info.setVisibility(View.GONE);
        }
        Log.e(TAG, "getView: "+ dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_img());
        if (dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_img()==null || dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_img().equals("")){
            viewHolder.img1.setVisibility(View.GONE);
            viewHolder.img2.setVisibility(View.GONE);
            viewHolder.img3.setVisibility(View.GONE);
        }else{
            if (dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_img().contains(",")){
                String[] shuzu = dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_img().split(",");
                if (shuzu.length>0){
                    viewHolder.img1.setVisibility(View.VISIBLE);
                    String url1 = Api.ossUrl+shuzu[0];
                    Log.e(TAG, "getView: "+ url1);
                    Glide.with(context)
                            .load(url1)
                            .placeholder(R.mipmap.projectshow)
                            .error(R.mipmap.projectshow)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(viewHolder.img1);
                }
                if (shuzu.length>1){
                    viewHolder.img2.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(Api.ossUrl+shuzu[1])
                            .placeholder(R.mipmap.projectshow)
                            .error(R.mipmap.projectshow)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(viewHolder.img2);
                }
                if (shuzu.length>2){
                    viewHolder.img3.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(Api.ossUrl+shuzu[2])
                            .placeholder(R.mipmap.projectshow)
                            .error(R.mipmap.projectshow)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(viewHolder.img3);
                }
            }else {
                viewHolder.img1.setVisibility(View.VISIBLE);
                viewHolder.img2.setVisibility(View.GONE);
                viewHolder.img3.setVisibility(View.GONE);
                Glide.with(context)
                        .load(Api.ossUrl+dianpupingjiaBean.getData().getPageInfo().getList().get(i).getComment_img())
                        .placeholder(R.mipmap.projectshow)
                        .error(R.mipmap.projectshow)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(viewHolder.img1);
            }

        }


        return view;
    }
    public static class ViewHolder{
        TextView tv_id;
        TextView tv_time;
        TextView tv_info;
        ImageView img1;
        ImageView img2;
        ImageView img3;
        LinearLayout ll_info;
        public ViewHolder(View view){
            tv_id = (TextView)view.findViewById(R.id.pingjia_id);
            tv_time = (TextView) view.findViewById(R.id.pingjia_time);
            tv_info = (TextView) view.findViewById(R.id.pingjia_fuwu);
            img1 = (ImageView) view.findViewById(R.id.pingjia_img1);
            img2 = (ImageView) view.findViewById(R.id.pingjia_img2);
            img3 = (ImageView) view.findViewById(R.id.pingjia_img3);
            ll_info = (LinearLayout)view.findViewById(R.id.yonghupingjia);
        }

    }
}
