package com.mdb.example.administrator.fuwu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.bean.OrderMoreBean;
import com.mdb.example.administrator.login.ShopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.mdb.example.administrator.login.LoginActivity.bodadianhua;

/**
 * 预约详情more页面
 */
public class YuyueMoreActivity extends BaseActivity {

    //获取订单的id,手机号
    String orderid,phone,statuss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuyue_more);
        ButterKnife.bind(this);
        orderid = getIntent().getStringExtra("orderid");
        statuss = getIntent().getStringExtra("status");
        Log.e("orderid",orderid);
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 写入信息
     */
    @BindView(R.id.ordermores_status)
    TextView tv_status;
    @BindView(R.id.ordermores_phone)
    ImageView img_phone;
    @BindView(R.id.ordermores_time)
    TextView tv_xiadantime;
    @BindView(R.id.ordermores_youhuijine)
    TextView tv_youhuijine;
    @BindView(R.id.ordermores_zhifujine)
    TextView tv_zhifujine;
    @BindView(R.id.ordermores_yudingtime)
    TextView tv_yudingtime;
    @BindView(R.id.ordermores_dingdanhao)
    TextView tv_dingdanhao;
    @BindView(R.id.ordermores_zongjine)
    TextView tv_zongjine;
    @BindView(R.id.ordermores_name)
    TextView tv_name;
    @BindView(R.id.yuyue_item_timessss)
    LinearLayout ll;
    private void setinfo() {
        OkGo.post(Api.chaxunordermore)
                .tag(this)
                .params("orderUUID",getIntent().getStringExtra("orderid"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("订单详情",s);
                        Gson gson = new Gson();
                        OrderMoreBean moreBean = gson.fromJson(s,OrderMoreBean.class);
                        if (moreBean.getState()==1){
                            tv_name.setText(moreBean.getData().get(0).getService_name());
                            tv_zongjine.setText(moreBean.getData().get(0).getMoney()+"元");
                            if (moreBean.getData().get(0).getStatus()==0){
                               tv_status.setText("取消");
                            }else if (moreBean.getData().get(0).getStatus()==1){
                                tv_status.setText("待支付");
                            }else if (moreBean.getData().get(0).getStatus()==2){
                                tv_status.setText("待服务");
                            }else if (moreBean.getData().get(0).getStatus()==3){
                                tv_status.setText("待评价");
                            }else if (moreBean.getData().get(0).getStatus()==4){
                                tv_status.setText("已评价");
                            }else if (moreBean.getData().get(0).getStatus()==4){
                                tv_status.setText("售后");
                            }
                            tv_xiadantime.setText(moreBean.getData().get(0).getCreateTime());
                            tv_youhuijine.setText(moreBean.getData().get(0).getCoupon_money()+"元");
                            tv_zhifujine.setText((moreBean.getData().get(0).getMoney()-moreBean.getData().get(0).getCoupon_money())+"元");
                            if (statuss.equals("2")){
                                ll.setVisibility(View.GONE);
                            }else {
                                ll.setVisibility(View.VISIBLE);
                                tv_yudingtime.setText(moreBean.getData().get(0).getOrderTime());
                            }
                            tv_dingdanhao.setText(moreBean.getData().get(0).getUuid());
                            phone = moreBean.getData().get(0).getTel();
                        }else {
                            ToastUtils.shortToast(moreBean.getMessage());
                        }
                    }
                });
        img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bodadianhua==2){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+ phone));
                    startActivity(intent);
                }else {
                    ToastUtils.shortToast("您拨打电话权限尚未授权");
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
        ttitle.setText("订单详情");
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
                YuyueMoreActivity.this.finish();
            }
        });
    }

}
