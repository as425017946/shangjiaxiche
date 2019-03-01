package com.mdb.example.administrator.wode;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.adapter.DianpuPingjiaAdapter;
import com.mdb.example.administrator.bean.DianpupingfenBean;
import com.mdb.example.administrator.bean.DianpupingjiaBean;
import com.mdb.example.administrator.view.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 店铺评价
 */
public class DianpupingfenActivity extends BaseActivity implements XListView.IXListViewListener {
    SharedPreferencesHelper sharehelper;
    DianpuPingjiaAdapter adapter;
    ArrayList<DianpupingjiaBean> arrayList = new ArrayList<>();
    @BindView(R.id.pingjia_xlistview)
    XListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpupingfen);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(DianpupingfenActivity.this,"xicheshop");
        adapter = new DianpuPingjiaAdapter(DianpupingfenActivity.this,arrayList);
        setTtitle();
        fanhui();
        selectinfo();
        selectPingjia();
    }

    /**
     * 展示信息
     */
    @BindView(R.id.pingfen_fenshu)
    TextView tv_fenshu;
    private void selectinfo() {
        OkGo.post(Api.selectpingfen)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("店铺评分数",s);
                        Gson gson = new Gson();
                        DianpupingfenBean dianpupingfenBean = gson.fromJson(s,DianpupingfenBean.class);
                        if (dianpupingfenBean.getState()==1){
                            if (dianpupingfenBean.getData().getGrade()==null){
                                    tv_fenshu.setText("暂无评分");
                            }else {
                                tv_fenshu.setText(dianpupingfenBean.getData().getGrade());
                            }
                        }else {
                            ToastUtils.shortToast(dianpupingfenBean.getMessage());
                        }
                    }
                });
    }
    /**
     * 查询店铺评价
     */
//    @BindView(R.id.yuyue_page)
//    XListView mListView;
    private Handler mHandler;
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    @BindView(R.id.pingjia_quanbu)
    TextView tv_quanbupingjia;
    private void selectPingjia(){
        mHandler = new Handler();
        OkGo.post(Api.selectpingjia)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("page",page)
                .params("pageSize",pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("店铺评价信息",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        DianpupingjiaBean dianpupingjiaBean = gson.fromJson(s,DianpupingjiaBean.class);
                        if (dianpupingjiaBean.getState()==1){
                            tv_quanbupingjia.setText("全部评论（共"+dianpupingjiaBean.getData().getPageInfo().getTotal()+"条）");
                            for (int i = 0; i <dianpupingjiaBean.getData().getPageInfo().getList().size(); i++) {
                                arrayList.add(dianpupingjiaBean);
                            }
                            mListView.setAdapter(adapter);
                            if (pageSize>dianpupingjiaBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }

                        }else{

                        }

                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
    }
    private void selectPingjia2(int page2,int pageSize2){
        mHandler = new Handler();
        OkGo.post(Api.selectpingjia)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .params("page",page2)
                .params("pageSize",pageSize2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("店铺评价",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        DianpupingjiaBean dianpupingjiaBean = gson.fromJson(s,DianpupingjiaBean.class);
                        if (dianpupingjiaBean.getState()==1){
                            for (int i = 0; i <dianpupingjiaBean.getData().getPageInfo().getList().size(); i++) {
                                arrayList.add(dianpupingjiaBean);
                            }
                            adapter.notifyDataSetChanged();
                            if (pageSize>dianpupingjiaBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }

                        }else{

                        }

                    }
                });
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("店铺评价");
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
                DianpupingfenActivity.this.finish();
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
                selectPingjia();
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
                selectPingjia2(page ++,(pageSize+10));
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
}
