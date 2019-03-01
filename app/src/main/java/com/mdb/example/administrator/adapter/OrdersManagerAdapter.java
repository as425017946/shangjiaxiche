package com.mdb.example.administrator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.R;
import com.mdb.example.administrator.bean.OrderManagerBean;
import com.mdb.example.administrator.fuwu.YuyueMoreActivity;

import java.util.ArrayList;

public class OrdersManagerAdapter extends BaseAdapter {
    Context context ;
    ArrayList<OrderManagerBean> arrayList;
    public OrdersManagerAdapter(Context context,ArrayList<OrderManagerBean> arrayList){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.order_items,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        viewHolder = (ViewHolder) view.getTag();
        final OrderManagerBean orderManagerBean = arrayList.get(i);
        if (orderManagerBean.getData().getPageInfo().getList().get(i).getType().equals("3")){
            viewHolder.tv_name.setText("预约洗车");
            viewHolder.tv_fuwushijian.setText("预定时间");
            viewHolder.tv_fuwushijian.setVisibility(View.VISIBLE);
            if (orderManagerBean.getData().getPageInfo().getList().get(i).getOrder_time()==null || orderManagerBean.getData().getPageInfo().getList().get(i).getOrder_time().equals("")){

                viewHolder.tv_yudingtime.setVisibility(View.GONE);
            }else {
                viewHolder.tv_yudingtime.setVisibility(View.VISIBLE);
                viewHolder.tv_yudingtime.setText(orderManagerBean.getData().getPageInfo().getList().get(i).getOrder_time());
            }

        }
        if (orderManagerBean.getData().getPageInfo().getList().get(i).getType().equals("2")){
            viewHolder.tv_name.setText("到店洗车");
            viewHolder.tv_fuwushijian.setText("服务时间");
            viewHolder.tv_fuwushijian.setVisibility(View.GONE);
            viewHolder.tv_yudingtime.setVisibility(View.GONE);
        }
        if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==0){
            viewHolder.tv_status.setText("已取消");
        }else if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==1){
            viewHolder.tv_status.setText("待支付");
        }else if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==2){
            viewHolder.tv_status.setText("待服务");
        }else if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==3){
            viewHolder.tv_status.setText("待评价");
        }else if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==4){
            viewHolder.tv_status.setText("已评价");
        }else if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==5){
            viewHolder.tv_status.setText("售后");
        }else if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==6){
            viewHolder.tv_status.setText("退款中");
        }else if (orderManagerBean.getData().getPageInfo().getList().get(i).getStatus()==7){
            viewHolder.tv_status.setText("退款完成");
        }
        viewHolder.tv_time.setText(orderManagerBean.getData().getPageInfo().getList().get(i).getCreateTime().substring(0,10));
        viewHolder.tv_fuwuname.setText(orderManagerBean.getData().getPageInfo().getList().get(i).getService_name());

        viewHolder.tv_youhuijiage.setText("优惠："+orderManagerBean.getData().getPageInfo().getList().get(i).getCoupon_money()+"");
        viewHolder.tv_zonge.setText(orderManagerBean.getData().getPageInfo().getList().get(i).getMoney()+"");

        viewHolder.l_zhengti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,YuyueMoreActivity.class);
                intent.putExtra("orderid",orderManagerBean.getData().getPageInfo().getList().get(i).getUuid());
                intent.putExtra("status",orderManagerBean.getData().getPageInfo().getList().get(i).getType()+"");
                context.startActivity(intent);
            }
        });


        return view;
    }
    public static class ViewHolder{
        TextView tv_name;
        TextView tv_time;
        TextView tv_status;
        TextView tv_fuwuname;
        TextView tv_yudingtime;
        TextView tv_youhuijiage;
        TextView tv_zonge;
        TextView tv_fuwushijian;
        LinearLayout l_zhengti;
        public ViewHolder(View v){
            tv_name = (TextView) v.findViewById(R.id.order_item_name);
            tv_time = (TextView) v.findViewById(R.id.order_item_time);
            tv_status = (TextView) v.findViewById(R.id.order_item_status);
            tv_fuwuname = (TextView) v.findViewById(R.id.order_item_fuwuname);
            tv_yudingtime = (TextView) v.findViewById(R.id.order_item_yudingtime);
            tv_youhuijiage = (TextView) v.findViewById(R.id.order_item_youhuijiage);
            tv_zonge = (TextView) v.findViewById(R.id.order_item_zongji);
            l_zhengti = (LinearLayout)v.findViewById(R.id.order_item_zhengti);
            tv_fuwushijian = (TextView) v.findViewById(R.id.yuyueshijianshow);
        }
    }
}
