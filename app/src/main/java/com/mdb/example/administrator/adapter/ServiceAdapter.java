package com.mdb.example.administrator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.bean.ServersStatusBean;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.wode.ServiceBianjiActivity;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class ServiceAdapter extends BaseAdapter {
    Context context;
    ArrayList<ServersStatusBean> arrayList;
    public ServiceAdapter(Context context,ArrayList<ServersStatusBean> arrayList){
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
        final ViewHolder v;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.service_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        v = (ViewHolder)view.getTag();
        final ServersStatusBean service = arrayList.get(i);
        v.tv_name.setText(service.getData().get(i).getService_name());
        if (service.getData().get(i).getStatus()==1){
             v.btn_status.setText("暂停服务");
             v.btn_status.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     setdange(service.getData().get(i).getService_id(),3);
                     v.btn_status.setText("开启服务");
    //                    ToastUtils.shortToast(service.getData().get(i).getService_id()+"");
                 }
             });

        }else if (service.getData().get(i).getStatus()==2){
             v.btn_status.setText("已被禁用");
        }else if (service.getData().get(i).getStatus()==3){
             v.btn_status.setText("开启服务");
             v.btn_status.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     setdange(service.getData().get(i).getService_id(),1);
                     v.btn_status.setText("暂停服务");
        //                    ToastUtils.shortToast(service.getData().get(i).getService_id()+"");
                 }
             });
        }




        v.btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (v.btn_status.getText().toString().equals("开启服务")){
                    setdange(service.getData().get(i).getService_id(),1);
                    v.btn_status.setText("暂停服务");
                }else if (v.btn_status.getText().toString().equals("暂停服务")){
                    setdange(service.getData().get(i).getService_id(),3);
                    v.btn_status.setText("开启服务");
                }else {
                    ToastUtils.shortToast("已被平台禁用，请联系平台后启用");
                }
            }
        });




        v.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ServiceBianjiActivity.class);
                intent.putExtra("serviceid",service.getData().get(i).getService_id()+"");
                intent.putExtra("isonlineorder",service.getData().get(i).getIsonlineorder()+"");
                context.startActivity(intent);
            }
        });

        return view;
    }
    /**
     * 开启关闭单个服务
     */
    private void setdange(int servicesId,int switchs){
        OkGo.post(Api.upfuwuguanli)
                .tag(this)
                .params("servicesId",servicesId)
                .params("status",switchs)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson1 = new Gson();
                        TongyongBean tongyongBean = gson1.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==1){

                        }else {
                            ToastUtils.shortToast(tongyongBean.getMessage());
                        }
                    }
                });
    }
    public static class ViewHolder{
        TextView tv_name;
        Button btn_status;
        LinearLayout linearLayout;
        public ViewHolder(View view){
            tv_name =(TextView) view.findViewById(R.id.service_name1);
            btn_status = (Button) view.findViewById(R.id.service_button1);
            linearLayout  = (LinearLayout)view.findViewById(R.id.service_show1);
        }
    }
}
