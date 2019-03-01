package com.mdb.example.administrator.fuwu;

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
import com.mdb.example.administrator.adapter.YouhuiAdapter;
import com.mdb.example.administrator.bean.YouhuiManagerBean;
import com.mdb.example.administrator.bean.YuyueBean;
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
 * 管理优惠券界面
 */
public class YouhuiManagerActivity extends BaseActivity implements XListView.IXListViewListener{
    SharedPreferencesHelper sharehelper;
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    ArrayList<YouhuiManagerBean> arrayList = new ArrayList<>();
    YouhuiAdapter adapter;
    @BindView(R.id.xlistview_youhui)
    XListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhui_manager);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(YouhuiManagerActivity.this,"xicheshop");
        adapter = new YouhuiAdapter(YouhuiManagerActivity.this,arrayList);
        setTtitle();
        fanhui();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setinfo();
    }

    /**
     * 写入信息
     * 展示信息
     */
    @BindView(R.id.youhuiquan_add)
    Button btn_add;
    private Handler mHandler;
    private void setinfo() {
        mHandler = new Handler();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharehelper.put("youhuiquan","0");
                Intent intent = new Intent(YouhuiManagerActivity.this,AddYouhuiquanActivity.class);
                startActivity(intent);
            }
        });
        Log.e("cehsi",sharehelper.getSharedPreference("shopcode","").toString()+"***");
        //展示信息
        OkGo.post(Api.chaxunyouhuiquan)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("page",page)
                .params("pageSize",pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("优惠券",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        YouhuiManagerBean youhuiquanBean = gson.fromJson(s,YouhuiManagerBean.class);
                        if (youhuiquanBean.getState()==1){
                            for (int i = 0; i < youhuiquanBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(youhuiquanBean);
                            }
                            mListView.setAdapter(adapter);
                            if (pageSize>youhuiquanBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else {
                                mListView.zhanshi(true);
                            }
                        }else{
                            ToastUtils.shortToast(youhuiquanBean.getMessage());
                        }
                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

    }


    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("优惠信息");
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
//                Intent intent = new Intent(YouhuiManagerActivity.this,ReleaseProjectActivity.class);
//                startActivity(intent);
                YouhuiManagerActivity.this.finish();
            }
        });
    }

    /**
     * 上拉加载使用
     */
    private void setinfo2(final int page2,final int pageSize2) {
        OkGo.post(Api.chaxunyouhuiquan)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("page",page2)
                .params("pageSize",pageSize2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("优惠券",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        YouhuiManagerBean youhuiquanBean = gson.fromJson(s,YouhuiManagerBean.class);
                        if (youhuiquanBean.getState()==1){
                            for (int i = 0; i < youhuiquanBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(youhuiquanBean);
                            }
                            adapter.notifyDataSetChanged();
                            if (pageSize>youhuiquanBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else {
                                mListView.zhanshi(true);
                            }
                        }else{
                            ToastUtils.shortToast(youhuiquanBean.getMessage());
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
        TimePickerView mTimePickerView = new TimePickerView(YouhuiManagerActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);// 四种选择模式，年月日时分，年月日，时分，月日时分
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
}
