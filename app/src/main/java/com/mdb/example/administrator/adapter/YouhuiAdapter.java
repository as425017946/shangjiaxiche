package com.mdb.example.administrator.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mdb.example.administrator.R;
import com.mdb.example.administrator.bean.YouhuiManagerBean;

import java.util.ArrayList;

public class YouhuiAdapter extends BaseAdapter {
    Context context;
    ArrayList<YouhuiManagerBean> arrayList;
    public YouhuiAdapter(Context context,ArrayList<YouhuiManagerBean> arrayList){
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
        ViewHolder v;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.youhui_manager_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        v = (ViewHolder) view.getTag();
        final YouhuiManagerBean youhuiManagerBean = arrayList.get(i);
        if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getPublish_type()==1){

            if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==1){
                v.tv_quan.setText("平台满减券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：满"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getItem()+"元可用，不得与其他优惠叠加");
                String a = youhuiManagerBean.getData().getPageInfo().getList().get(i).getTerm()+"";
                v.tv_zhi.setVisibility(View.VISIBLE);
                v.tv_zhi.setText("￥"+a.substring(0,(a.length()-2)));
                v.tv_zhi2.setVisibility(View.GONE);
                v.tv_zhi3.setVisibility(View.GONE);
            }else  if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==2){
                v.tv_quan.setText("平台打折券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：消费即使用，不得与其他优惠叠加");
                String a = youhuiManagerBean.getData().getPageInfo().getList().get(i).getTerm()+"";
                v.tv_zhi.setVisibility(View.VISIBLE);
                v.tv_zhi.setText(a.substring(0,(a.length()-2))+"折");
                v.tv_zhi2.setVisibility(View.GONE);
                v.tv_zhi3.setVisibility(View.GONE);

            }else  if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==3){
                v.tv_quan.setText("平台代金券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：消费即使用，不得与其他优惠叠加");
                String a = youhuiManagerBean.getData().getPageInfo().getList().get(i).getTerm()+"";
                v.tv_zhi.setVisibility(View.VISIBLE);
                v.tv_zhi.setText("￥"+a.substring(0,(a.length()-2)));
                v.tv_zhi2.setVisibility(View.GONE);
                v.tv_zhi3.setVisibility(View.GONE);
            }else  if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==4){
                v.tv_quan.setText("平台赠品券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：消费即使用，不得与其他优惠叠加");
                v.tv_zhi.setVisibility(View.GONE);
                v.tv_zhi2.setVisibility(View.VISIBLE);
                v.tv_zhi3.setVisibility(View.VISIBLE);
                v.tv_zhi3.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getFreegoods());
            }

        }else {

            if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==1){
                v.tv_quan.setText("满减券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：满"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getTerm()+"元可用，不得与其他优惠叠加");
                String a = youhuiManagerBean.getData().getPageInfo().getList().get(i).getItem()+"";
                v.tv_zhi.setVisibility(View.VISIBLE);
                v.tv_zhi.setText("￥"+a.substring(0,(a.length()-2)));
                v.tv_zhi2.setVisibility(View.GONE);
                v.tv_zhi3.setVisibility(View.GONE);
            }else  if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==2){
                v.tv_quan.setText("打折券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：消费即使用，不得与其他优惠叠加");
                String a = youhuiManagerBean.getData().getPageInfo().getList().get(i).getItem()+"";
                v.tv_zhi.setVisibility(View.VISIBLE);
                Log.e("值a",a);
                v.tv_zhi.setText(a.substring(0,(a.length()-2))+"折");
                v.tv_zhi2.setVisibility(View.GONE);
                v.tv_zhi3.setVisibility(View.GONE);

            }else  if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==3){
                v.tv_quan.setText("代金券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：消费即使用，不得与其他优惠叠加");
                String a = youhuiManagerBean.getData().getPageInfo().getList().get(i).getItem()+"";
                v.tv_zhi.setVisibility(View.VISIBLE);
                v.tv_zhi.setText("￥"+a.substring(0,(a.length()-2)));
                v.tv_zhi2.setVisibility(View.GONE);
                v.tv_zhi3.setVisibility(View.GONE);
            }else  if (youhuiManagerBean.getData().getPageInfo().getList().get(i).getCoupon_type()==4){
                v.tv_quan.setText("赠品券");
                v.tv_tiem.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getStarttime().substring(0,10)+"-"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getEndtime().substring(0,10));
                v.tv_shiyongguize.setText("使用规则：满"+youhuiManagerBean.getData().getPageInfo().getList().get(i).getTerm()+"元可用，不得与其他优惠叠加");
                v.tv_zhi.setVisibility(View.GONE);
                v.tv_zhi2.setVisibility(View.VISIBLE);
                v.tv_zhi3.setVisibility(View.VISIBLE);
                v.tv_zhi3.setText(youhuiManagerBean.getData().getPageInfo().getList().get(i).getFreegoods());

            }

        }




        return view;
    }
    public static class ViewHolder{
        TextView tv_quan;
        TextView tv_tiem;
        TextView tv_shiyongguize;
        TextView tv_zhi;
        TextView tv_zhi2;
        TextView tv_zhi3;

        public ViewHolder(View view){
            tv_quan = (TextView)view.findViewById(R.id.youhui_quan);
            tv_tiem = (TextView)view.findViewById(R.id.youhui_time);
            tv_shiyongguize = (TextView)view.findViewById(R.id.youhui_shiyongguize);
            tv_zhi = (TextView)view.findViewById(R.id.youhui_zhi);
            tv_zhi2 = (TextView)view.findViewById(R.id.youhui_zhi2);
            tv_zhi3 = (TextView)view.findViewById(R.id.youhui_zhi3);
        }
    }
}
