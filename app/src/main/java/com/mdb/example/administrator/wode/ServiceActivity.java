package com.mdb.example.administrator.wode;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.adapter.ServiceAdapter;
import com.mdb.example.administrator.bean.ServersStatusBean;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.view.XListView;
import com.mdb.example.administrator.wode.shouyi.ShouyiActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 服务管理
 */
public class ServiceActivity extends BaseActivity implements XListView.IXListViewListener{

    SharedPreferencesHelper sharehelper;
    ServiceAdapter adapter;
    ArrayList<ServersStatusBean> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(ServiceActivity.this,"xicheshop");
        adapter = new ServiceAdapter(ServiceActivity.this,arrayList);
        setTtitle();
        fanhui();
        setzanting();
    }

    /**
     * 编辑服务信息
     */
    @BindView(R.id.fuwu_zanting)
    Button btn_zanting;
    private void setzanting() {
        btn_zanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_zanting.getText().equals("暂停网上预约")){
                    btn_zanting.setText("开启网络预约");
                    setzantingOrkaiqi(2);

                }else {
                    btn_zanting.setText("暂停网上预约");
                    setzantingOrkaiqi(1);
                }
            }
        });

    }

    /**
     * 暂停开启方法
     */
    @BindView(R.id.service_xlistview)
    XListView xListView;
    int zhis=0;
    private Handler mHandler;
    private void setzantingOrkaiqi(final int ss){
        OkGo.post(Api.chaxunstatus)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("商家的全部服务状态",s);
                        final Gson gson = new Gson();
                        ServersStatusBean serversStatusBean = gson.fromJson(s,ServersStatusBean.class);
                        if (serversStatusBean.getState()==1){
                            //成功获取服务id后全部进行请求关闭
                            String servicesId = "";
                            for (int i = 0; i <serversStatusBean.getData().size() ; i++) {
                                servicesId= servicesId + serversStatusBean.getData().get(i).getService_id()+",";
                                zhis=i;
                                Log.e("服务id和状态aaaa",servicesId+"**"+ss+"---"+zhis);
                            }
                            if((zhis+1)==serversStatusBean.getData().size()){
                                Log.e("服务id和状态",servicesId+"**"+ss);
                                setzantingall(servicesId,ss);
                            }


                        }else{
                            ToastUtils.shortToast(serversStatusBean.getMessage());
                        }

                    }
                });
    }

    /**
     * 暂停全部服务
     */
    private void setzantingall(String servicesId,int ss){
        OkGo.post(Api.upfuwuguanli)
                .tag(this)
                .params("servicesId",servicesId)
                .params("status",ss)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson1 = new Gson();
                        TongyongBean tongyongBean = gson1.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==1){
                            //暂停或开启成功,重新请求信息
                            setshow();
                        }else {
                            ToastUtils.shortToast(tongyongBean.getMessage());
                            btn_zanting.setText("暂停网上预约");
                        }
                    }
                });
    }
    /**
     * 展示信息
     */
    private void setshow(){
        mHandler = new Handler();
        OkGo.post(Api.chaxunstatus)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("商家的全部服务状态",s);
                        arrayList.clear();
                        final Gson gson = new Gson();
                        ServersStatusBean serversStatusBean = gson.fromJson(s,ServersStatusBean.class);
                        if (serversStatusBean.getState()==1){
                            for (int i = 0; i <serversStatusBean.getData().size() ; i++) {
                                arrayList.add(serversStatusBean);
                            }
                            xListView.setAdapter(adapter);
                            if (20>=serversStatusBean.getData().size()){
                                xListView.zhanshi(false);
                            }else{
                                xListView.zhanshi(true);
                            }

                        }else{
                            ToastUtils.shortToast(serversStatusBean.getMessage());
                        }

                    }
                });
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
    }

    /**
     * 返回后重新展示
     */
    @Override
    protected void onResume() {
        super.onResume();
        setshow();
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("编辑服务");
        ttitle2.setText("新服务");
        ttitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this,ReleaseProject2Activity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceActivity.this.finish();
            }
        });
    }


    //上拉加载
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setshow();
                onLoad();
            }
        }, 2000);
    }

    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        //格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        String time = formatter.format(curDate);
        xListView.setRefreshTime(time);
    }


}
