package com.mdb.example.administrator.fuwu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.adapter.YuyueAdapter;
import com.mdb.example.administrator.bean.ServersStatusBean;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.bean.YuyueBean;
import com.mdb.example.administrator.jiaohao.CallNumberStopActivity;
import com.mdb.example.administrator.jiaohao.SaomiaoActivity;
import com.mdb.example.administrator.login.ShopActivity;
import com.mdb.example.administrator.view.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 预约界面
 * A simple {@link Fragment} subclass.
 */
public class FuwuFragment extends Fragment  implements XListView.IXListViewListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fuwu, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    SharedPreferencesHelper sharehelper;
    ArrayList<YuyueBean> arrayList = new ArrayList<>();
    YuyueAdapter adapter ;
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    @BindView(R.id.weishenhe)
    TextView tv_weishenhe;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharehelper = new SharedPreferencesHelper(context,"xicheshop");
        if (sharehelper.getSharedPreference("shopstatus","").toString().equals("1")){
            tv_weishenhe.setVisibility(View.VISIBLE);
        }else {
            tv_weishenhe.setVisibility(View.GONE);
            SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
            Date curDate =  new Date(System.currentTimeMillis());
            tv_time.setText(formatter.format(curDate));
            setTtitle();
            fanhui();
            setIofo();
            setinfo();
            //实例化一次后直接使用
            adapter = new YuyueAdapter(context,arrayList);
            setzanting();
        }

    }


    @BindView(R.id.fuwu_zanting)
    Button btn_zanting;
    @BindView(R.id.yuyue_zanting)
    Button btn_showzanting;
    private void setzanting() {
        btn_zanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_zanting.getText().equals("暂停网上预约")){
                    btn_zanting.setText("开启网络预约");
                    setzantingOrkaiqi(0);

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
    int zhis=0;
    private void setzantingOrkaiqi(final int ss){
        OkGo.post(Api.chaxunstatus)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("商家的全部服务状态",s);
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
                                OkGo.post(Api.updatestatus)
                                        .tag(this)
                                        .params("servicesId",servicesId)
                                        .params("switch",ss)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                Gson gson1 = new Gson();
                                                TongyongBean tongyongBean = gson1.fromJson(s,TongyongBean.class);
                                                if (tongyongBean.getState()==1){
                                                    if (ss==0){
                                                        btn_showzanting.setVisibility(View.VISIBLE);
                                                    }else {
                                                        btn_showzanting.setVisibility(View.GONE);
                                                    }
                                                }else {
                                                    ToastUtils.shortToast(tongyongBean.getMessage());
                                                    btn_zanting.setText("暂停网上预约");
                                                }
                                            }
                                        });
                            }


                        }else{
                            ToastUtils.shortToast(serversStatusBean.getMessage());
                        }

                    }
                });
    }


    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_xiaoxi)
    ImageView img_xiaoxi;
    private void setTtitle(){
        ttitle.setText("预约详情");
        img_xiaoxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MessagesBianjiActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 扫描二维码
     */
    @BindView(R.id.xiche_saomiao)
    ImageView lsaomiao;
    private void fanhui(){
        lsaomiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SaomiaoActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     *写入信息
     */
    @BindView(R.id.touzi_quxiao)
    LinearLayout linearLayout_xiaoxi;
    @BindView(R.id.yuyue_time)
    TextView tv_time;
    private void setIofo(){
        //跳转消息界面
        linearLayout_xiaoxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MessagesBianjiActivity.class);
                startActivity(intent);
            }
        });

        //服务日期
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_time);
            }
        });


    }

    /**
     * 查询预约详情界面
     */
    @BindView(R.id.yuyue_page)
    XListView mListView;
    private Handler mHandler;
    private void setinfo(){
        mHandler = new Handler();
        //展示信息
        OkGo.post(Api.chaxunorderyuyue)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("order_time",tv_time.getText().toString())
                .params("page",page)
                .params("pageSize",pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("预约详情","shopCode"+sharehelper.getSharedPreference("shopcode","").toString()+
//                                "order_time"+tv_time.getText().toString()+"page"+page+"pageSize"+pageSize);
//                        Log.e("预约详情2",s);
                        //先清空信息，然后在追加，要不然会叠加报错
                        arrayList.clear();
                        Gson gson = new Gson();
                        YuyueBean yuyueBean = gson.fromJson(s,YuyueBean.class);
                        if (yuyueBean.getState()==1){
                            for (int i = 0; i <yuyueBean.getData().getPageInfo().getList().size(); i++) {
                                arrayList.add(yuyueBean);
                            }
                            mListView.setAdapter(adapter);
                            if (pageSize>yuyueBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }
                        }else{
                            ToastUtils.shortToast(yuyueBean.getMessage());
                        }
                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

    }
    /**
     * 上拉加载使用
     */
    private void setinfo2(final int page2,final int pageSize2) {
        OkGo.post(Api.chaxunorderyuyue)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("order_time",tv_time.getText().toString().trim())
                .params("page",page2)
                .params("pageSize",pageSize2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("预约详情",s);
                        //先清空信息，然后在追加，要不然会叠加报错
                        arrayList.clear();
                        Gson gson = new Gson();
                        YuyueBean yuyueBean = gson.fromJson(s,YuyueBean.class);
                        if (yuyueBean.getState()==1){
                            for (int i = 0; i <yuyueBean.getData().getPageInfo().getList().size(); i++) {
                                arrayList.add(yuyueBean);
                            }
                            adapter.notifyDataSetChanged();
                            if (pageSize2>yuyueBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }
                        }else{
                            ToastUtils.shortToast(yuyueBean.getMessage());
                        }
                    }
                });

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                pageSize = 10;
                setinfo();
                onLoad();
            }
        }, 2000);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setinfo2(page ++,(pageSize+10));
                adapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        //格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        String time = formatter.format(curDate);
        mListView.setRefreshTime(time);
    }


    //时间选择器
    private void showTimer(final TextView editText){
        //     TimePickerView 同样有上面设置样式的方法
        TimePickerView mTimePickerView = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);// 四种选择模式，年月日时分，年月日，时分，月日时分
        // 设置是否循环
        mTimePickerView.setCyclic(true);

        // 设置滚轮文字大小
        mTimePickerView.setTextSize(TimePickerView.TextSize.BIG);
        // 设置时间可选范围(结合 setTime 方法使用,必须在)
//        Calendar calendar = Calendar.getInstance();
//        mTimePickerView.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));
        // 设置选中时间
        mTimePickerView.setTime(new Date());//设置选中的时间  new date（）是今天的时间
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//                Toast.makeText(context, format.format(date), Toast.LENGTH_SHORT).show();
                editText.setText(format.format(date));
                setinfo();
            }
        });
        mTimePickerView.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("预约详情111","11");
        setinfo();
    }
}
