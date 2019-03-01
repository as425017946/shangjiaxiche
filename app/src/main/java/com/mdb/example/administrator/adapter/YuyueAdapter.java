package com.mdb.example.administrator.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.bean.YuyueBean;
import com.mdb.example.administrator.fuwu.YuyueMoreActivity;

import java.util.ArrayList;

import static com.mdb.example.administrator.login.LoginActivity.bodadianhua;

public class YuyueAdapter extends BaseAdapter {
    Context context;
    ArrayList<YuyueBean> arrayList;
    public YuyueAdapter(Context context,ArrayList<YuyueBean> arrayList){
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
        ViewHolder v;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.yuyue_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        v =(ViewHolder)view.getTag();
        final YuyueBean yuyueBean = arrayList.get(i);
        v.tv_time.setText(yuyueBean.getData().getPageInfo().getList().get(i).getOrder_time());
        v.tv_info.setText(yuyueBean.getData().getPageInfo().getList().get(i).getService_name());
        if (yuyueBean.getData().getPageInfo().getList().get(i).getStatus()==0){
            v.tv_status.setText("取消");
        }else if (yuyueBean.getData().getPageInfo().getList().get(i).getStatus()==1){
            v.tv_status.setText("待支付");
        }else if (yuyueBean.getData().getPageInfo().getList().get(i).getStatus()==2){
            v.tv_status.setText("待服务");
        }else if (yuyueBean.getData().getPageInfo().getList().get(i).getStatus()==3){
            v.tv_status.setText("待评价");
        }else if (yuyueBean.getData().getPageInfo().getList().get(i).getStatus()==4){
            v.tv_status.setText("已评价");
        }else if (yuyueBean.getData().getPageInfo().getList().get(i).getStatus()==4){
            v.tv_status.setText("售后");
        }
        //点击手机号
        v.img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bodadianhua==2){
                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_CALL);

                    intent.setData(Uri.parse("tel:"+ yuyueBean.getData().getPageInfo().getList().get(i).getShop_tel()));

                    context.startActivity(intent);
                }else {
                    ToastUtils.shortToast("您拨打电话权限尚未授权");
                }

            }
        });
        //点击更多
        v.img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,YuyueMoreActivity.class);
                intent.putExtra("orderid",yuyueBean.getData().getPageInfo().getList().get(i).getUuid());
                intent.putExtra("status","3");
                context.startActivity(intent);
            }
        });


        return view;
    }
    public static class ViewHolder{
        TextView tv_time;
        TextView tv_info;
        TextView tv_status;
        ImageView img_phone;
        ImageView img_more;
        public ViewHolder(View view){
            tv_time = (TextView)view.findViewById(R.id.yuyue_item_time);
            tv_info = (TextView)view.findViewById(R.id.yuyue_item_info);
            tv_status = (TextView)view.findViewById(R.id.yuyue_item_status);
            img_phone = (ImageView)view.findViewById(R.id.yuyue_item_phoneanniu);
            img_more = (ImageView)view.findViewById(R.id.yuyue_item_moreanniu);
        }
    }
}
