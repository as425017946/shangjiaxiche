package com.mdb.example.administrator.wode.order;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.adapter.OrdersManagerAdapter;
import com.mdb.example.administrator.bean.OrderManagerBean;
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
 * A simple {@link Fragment} subclass.
 * 我的订单 已完成
 */
public class OrderYiwanchengFragment extends Fragment  implements XListView.IXListViewListener{
    SharedPreferencesHelper sharehelper;
    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_all, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    private Handler mHandler;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharehelper = new SharedPreferencesHelper(context,"xicheshop");
        adapter = new OrdersManagerAdapter(context,arrayList);
        selectInfo();
    }

    /**
     * 查询订单信息
     */
    @BindView(R.id.xlistview_orders)
    XListView mListView;
    ArrayList<OrderManagerBean> arrayList  = new ArrayList<>();
    OrdersManagerAdapter adapter;
    private void selectInfo() {
        mHandler = new Handler();
        OkGo.post(Api.chaxunAllorders)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("page",page)
                .params("pageSize",pageSize)
                .params("status","3,4")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("查询订单已完成",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        OrderManagerBean orderManagerBean = gson.fromJson(s,OrderManagerBean.class);
                        if (orderManagerBean.getState()==1){
                            for (int i = 0; i <orderManagerBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(orderManagerBean);
                            }
                            mListView.setAdapter(adapter);
                            if (pageSize>orderManagerBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }

                        }else {
                            ToastUtils.shortToast(orderManagerBean.getMessage());
                        }
                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

    }
    private void setinfo2(int page2,int pageSize2) {
        OkGo.post(Api.chaxunAllorders)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("page",page2)
                .params("pageSize",pageSize2)
                .params("status","3,4")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("查询订单",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        OrderManagerBean orderManagerBean = gson.fromJson(s,OrderManagerBean.class);
                        if (orderManagerBean.getState()==1){
                            for (int i = 0; i <orderManagerBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(orderManagerBean);
                            }
                            adapter.notifyDataSetChanged();
                            if (pageSize>orderManagerBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }

                        }else {
                            ToastUtils.shortToast(orderManagerBean.getMessage());
                        }
                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                pageSize = 10;
                selectInfo();
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
                selectInfo();
            }
        });
        mTimePickerView.show();
    }
}
