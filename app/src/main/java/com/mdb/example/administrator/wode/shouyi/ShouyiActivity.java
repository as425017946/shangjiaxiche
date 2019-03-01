package com.mdb.example.administrator.wode.shouyi;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
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
import com.mdb.example.administrator.adapter.OrderFragmentAdapter;
import com.mdb.example.administrator.adapter.ShouyiAdapter;
import com.mdb.example.administrator.bean.ShouyiBean;
import com.mdb.example.administrator.bean.ZongshouyiBean;
import com.mdb.example.administrator.login.ShopActivity;
import com.mdb.example.administrator.view.XListView;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 店铺收益 主页
 */
public class ShouyiActivity extends BaseActivity implements XListView.IXListViewListener{
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    SharedPreferencesHelper sharehelper;
    @BindView(R.id.shouyi_xlistview)
    XListView mListView;
    ShouyiAdapter adapter;
    ArrayList<ShouyiBean> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouyi);
        ButterKnife.bind(this);
//        initData();
        setTtitle();
        fanhui();
        sharehelper = new SharedPreferencesHelper(ShouyiActivity.this,"xicheshop");
        adapter = new ShouyiAdapter(ShouyiActivity.this,arrayList);
        showtimemonthday();
        selectinfo();
    }
    private void showtimemonthday() {
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(curDate);
        tv_star.setText(time);
        tv_end.setText(time);

        selectData(tv_star.getText().toString(),tv_end.getText().toString());
    }
    /**
     * 查询信息
     */
    @BindView(R.id.shouyi_zongshouyi)
    TextView tv_zongshouyi;
    @BindView(R.id.shouyi_star)
    TextView tv_star;
    @BindView(R.id.shouyi_end)
    TextView tv_end;
    @BindView(R.id.shouyi_chaxun)
    Button btn_chaxun;
    private void selectinfo() {
        OkGo.post(Api.selectzongshouyi)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //查询店家总收益
                        Gson gson = new Gson();
                        ZongshouyiBean zongshouyiBean = gson.fromJson(s,ZongshouyiBean.class);
                        if (zongshouyiBean.getState()==1){
                            tv_zongshouyi.setText(zongshouyiBean.getData().getMoney()+"元");
                        }else {
                            ToastUtils.shortToast(zongshouyiBean.getMessage());
                        }

                    }
                });
        tv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_star);
            }
        });
        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_end);
            }
        });

        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_star.getText().equals("开始时间") || tv_end.getText().equals("结束时间")){
                    ToastUtils.shortToast("请选择要查询的日期后再来查询");
                }else {
                    selectData(tv_star.getText().toString()+" 00:00:00",tv_end.getText().toString()+" 23:59:59");
                }
            }
        });
    }
    //时间选择器
    private void showTimer(final TextView editText){
        //     TimePickerView 同样有上面设置样式的方法
        TimePickerView mTimePickerView = new TimePickerView(ShouyiActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);// 四种选择模式，年月日时分，年月日，时分，月日时分
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
            }
        });
        mTimePickerView.show();
    }
    /**
     * 按日期查询
     */
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    private Handler mHandler;
    private void selectData(String startime,String endtime){
        mHandler = new Handler();
        Log.e("商家",page+"**"+pageSize+"--");
        Log.e("商家编码",sharehelper.getSharedPreference("shopcode","").toString());
        OkGo.post(Api.selectshopshouyi)
                .tag(this)
                .params("page",page)
                .params("pageSize",pageSize)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("startTime",startime+" 00:00:00")
                .params("endTime",endtime+" 23:59:59")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("收益全部展示",s);
                        Gson gson = new Gson();
                        arrayList.clear();
                        ShouyiBean shouyiBean = gson.fromJson(s,ShouyiBean.class);
                        if (shouyiBean.getState()==1){
                            for (int i = 0; i < shouyiBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(shouyiBean);
                            }
                            mListView.setAdapter(adapter);
                            if (pageSize>shouyiBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else {
                                mListView.zhanshi(true);
//                                ToastUtils.shortToast("暂无查询信息！");
                            }
                        }else {
                            ToastUtils.shortToast(shouyiBean.getMessage());
                        }
                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
    }

    private void selectData2(int page2, final int pageSize2){
        mHandler = new Handler();
        Log.e("商家",page+"**"+pageSize+"--");
        Log.e("商家编码",sharehelper.getSharedPreference("shopcode","").toString());
        if (!tv_star.getText().equals("开始时间") && !tv_end.getText().equals("结束时间")){
            OkGo.post(Api.selectshopshouyi)
                    .tag(this)
                    .params("page",page2)
                    .params("pageSize",pageSize2)
                    .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                    .params("startTime",tv_star.getText().toString()+" 00:00:00")
                    .params("endTime",tv_end.getText().toString()+" 23:59:59")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e("收益全部展示",s);
                            arrayList.clear();
                            Gson gson = new Gson();
                            ShouyiBean shouyiBean = gson.fromJson(s,ShouyiBean.class);
                            if (shouyiBean.getState()==1){
                                for (int i = 0; i < shouyiBean.getData().getPageInfo().getList().size() ; i++) {
                                    arrayList.add(shouyiBean);
                                }
                                adapter.notifyDataSetChanged();
                                if (pageSize2>shouyiBean.getData().getPageInfo().getTotal()){
                                    mListView.zhanshi(false);
                                }else {
                                    mListView.zhanshi(true);
//                                ToastUtils.shortToast("暂无查询信息！");
                                }
                            }else {
                                ToastUtils.shortToast(shouyiBean.getMessage());
                            }
                        }
                    });
        }else{
            OkGo.post(Api.selectshopshouyi)
                    .tag(this)
                    .params("page",page2)
                    .params("pageSize",pageSize2)
                    .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                    .params("startTime","")
                    .params("endTime","")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e("收益全部展示",s);
                            arrayList.clear();
                            Gson gson = new Gson();
                            ShouyiBean shouyiBean = gson.fromJson(s,ShouyiBean.class);
                            if (shouyiBean.getState()==1){
                                for (int i = 0; i < shouyiBean.getData().getPageInfo().getList().size() ; i++) {
                                    arrayList.add(shouyiBean);
                                }
                                adapter.notifyDataSetChanged();
                                if (shouyiBean.getData().getPageInfo().getTotal()>0){
                                    mListView.zhanshi(false);
                                }else {
                                    mListView.zhanshi(true);
//                                ToastUtils.shortToast("暂无查询信息！");
                                }
                            }else {
                                ToastUtils.shortToast(shouyiBean.getMessage());
                            }
                        }
                    });
        }

    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("店铺收益");
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
                ShouyiActivity.this.finish();
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
                selectData("","");
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
                selectData2(page ++,(pageSize+10));
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


//    private void initData(){
//        //设置投资页面的里面的6个fragment页面
//        ShouyiDayFragment dayFragment = new ShouyiDayFragment();
//        ShouyiMonthFragment monthFragment = new ShouyiMonthFragment();
//        ShouyiallFragment allFragment = new ShouyiallFragment();
//        ShouyiMoreFragment moreFragment = new ShouyiMoreFragment();
//        mFragments.add(dayFragment);
//        mFragments.add(monthFragment);
//        mFragments.add(allFragment);
//        mFragments.add(moreFragment);
//        list.add("今日收益");
//        list.add("月度收益");
//        list.add("总收益");
//        list.add("收入明细");
//        mTabLayout.setupWithViewPager(mViewpager);
//        mViewpager.setAdapter(new OrderFragmentAdapter(getSupportFragmentManager(),mFragments,list));
//        mTabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                setIndicator(mTabLayout, 10, 10);
//            }
//        });
//
//
//    }
//    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout llTab = null;
//        try {
//            llTab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
//        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
//
//        for (int i = 0; i < llTab.getChildCount(); i++) {
//            View child = llTab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//    }
}
