package com.mdb.example.administrator.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.R;
import com.mdb.example.administrator.bean.ShouyiBean;

import java.util.ArrayList;

public class ShouyiAdapter extends BaseAdapter {
    Context context;
    ArrayList<ShouyiBean> arrayList;
    int red = 0xf05151;
    public ShouyiAdapter(Context context,ArrayList<ShouyiBean> arrayList){
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
            view = LayoutInflater.from(context).inflate(R.layout.shouyi_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        viewHolder = (ViewHolder) view.getTag();
        ShouyiBean shouyiBean = arrayList.get(i);

        if (shouyiBean.getData().getPageInfo().getList().get(i).getTime().equals("合计")){
            viewHolder.tv_time.setText(shouyiBean.getData().getPageInfo().getList().get(i).getTime());
            viewHolder.tv_daonum.setText(shouyiBean.getData().getPageInfo().getList().get(i).getDNum()+"");
            viewHolder.tv_yunum.setText(shouyiBean.getData().getPageInfo().getList().get(i).getYNum()+"");
            viewHolder.tv_zong.setText(shouyiBean.getData().getPageInfo().getList().get(i).getMoney()+"");

        }else {
            viewHolder.tv_time.setText(shouyiBean.getData().getPageInfo().getList().get(i).getTime());
            viewHolder.tv_daonum.setText(shouyiBean.getData().getPageInfo().getList().get(i).getDNum()+"");
            viewHolder.tv_yunum.setText(shouyiBean.getData().getPageInfo().getList().get(i).getYNum()+"");
            viewHolder.tv_zong.setText(shouyiBean.getData().getPageInfo().getList().get(i).getMoney()+"");
        }

        return view;
    }
    public static class ViewHolder{
        TextView tv_time;
        TextView tv_daonum;
        TextView tv_yunum;
        TextView tv_zong;
        public ViewHolder(View view){
            tv_time = (TextView) view.findViewById(R.id.shouyi_item_time);
            tv_daonum = (TextView) view.findViewById(R.id.shouyi_item_daodiannum);
            tv_yunum = (TextView) view.findViewById(R.id.shouyi_item_yuyuenum);
            tv_zong = (TextView) view.findViewById(R.id.shouyi_item_zongjine);

        }
    }
}
