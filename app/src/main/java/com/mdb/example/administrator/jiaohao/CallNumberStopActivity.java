package com.mdb.example.administrator.jiaohao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.bean.OrderMoreBean;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.fuwu.MessagesActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 叫号扫描结果页面
 */
public class CallNumberStopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_number_stop);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setInfo();
//        Log.e("回调",getIntent().getStringExtra("danhao"));
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_xiaoxi)
    ImageView img_xiaoxi;
    private void setTtitle(){
        ttitle.setText("叫号");
        img_xiaoxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CallNumberStopActivity.this,MessagesActivity.class);
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
                Intent intent = new Intent(CallNumberStopActivity.this,SaomiaoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 写入信息
     */
    @BindView(R.id.callnumber_querenxiche)
    Button btn_queren;
    @BindView(R.id.callnumber_quxiao)
    Button btn_quxiao;
    @BindView(R.id.callnumber_jieguo_name)
    TextView tv_name;
    @BindView(R.id.callnumber_jieguo_time)
    TextView tv_time;
    @BindView(R.id.callnumber_jieguo_status)
    TextView tv_status;
    @BindView(R.id.callnumber_chaoshi)
    TextView tv_chaoshi;
    private void setInfo(){
        if (getIntent().getStringExtra("danhao").contains(",")==false){
            ToastUtils.shortToast("扫描的二维码不存在");
        }else{
            String[] shuzu = getIntent().getStringExtra("danhao").split(",");
            OkGo.post(Api.chaxunordermore)
                    .tag(this)
                    .params("orderUUID",shuzu[0])
                    .params("detailId",shuzu[1])
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Gson gson = new Gson();
                            OrderMoreBean moreBean = gson.fromJson(s,OrderMoreBean.class);
                            if (moreBean.getState()==1){
                                if (moreBean.getData().size()==0){
                                    ToastUtils.shortToast("二维码已失效或不存在！");
                                }else {
                                    tv_name.setText(moreBean.getData().get(0).getService_name()+"");
                                    tv_time.setText(moreBean.getData().get(0).getOrderTime()+"");
                                    if (moreBean.getData().get(0).getType().equals("1")){
                                        tv_status.setText("上门洗车");
                                        //获取当前时间
                                        Date curDate = new Date(System.currentTimeMillis());
                                        Date curDate2 ;
                                        //格式化
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
                                        try {
                                            curDate2 = formatter.parse(moreBean.getData().get(0).getOrderTime());
                                            long L1 = curDate2.getTime();
                                            String time = formatter.format(curDate);
                                            long L2 =  curDate.getTime();
                                            if (L1<L2){
                                                tv_chaoshi.setVisibility(View.VISIBLE);
                                            }else {
                                                tv_chaoshi.setVisibility(View.INVISIBLE);
                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }else  if (moreBean.getData().get(0).getType().equals("2")){
                                        tv_status.setText("到店洗车");
                                    }else  if (moreBean.getData().get(0).getType().equals("3")){
                                        tv_status.setText("预约洗车");
                                        //获取当前时间
                                        Date curDate = new Date(System.currentTimeMillis());
                                        Date curDate2 ;
                                        //格式化
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
                                        try {
                                            curDate2 = formatter.parse(moreBean.getData().get(0).getOrderTime());
                                            long L1 = curDate2.getTime();
                                            String time = formatter.format(curDate);
                                            long L2 =  curDate.getTime();
                                            if (L1<L2){
                                                tv_chaoshi.setVisibility(View.VISIBLE);
                                            }else {
                                                tv_chaoshi.setVisibility(View.INVISIBLE);
                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }

                            }else {
                                ToastUtils.shortToast(moreBean.getMessage());
                            }
                        }
                    });
        }

        btn_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("danhao").contains(",")==false){
                    ToastUtils.shortToast("扫描的二维码无效");
                }else{
                    String[] shuzu = getIntent().getStringExtra("danhao").split(",");
                    OkGo.post(Api.xiugaishopstatus)
                            .tag(this)
                            .params("orderUUID",shuzu[0])
                            .params("detailId",shuzu[1])
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                    if (tongyongBean.getState()==1){
                                        CallNumberStopActivity.this.finish();
                                    }else {
                                        ToastUtils.shortToast(tongyongBean.getMessage());
                                    }
                                }
                            });
                }

            }
        });
        btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallNumberStopActivity.this.finish();
            }
        });
    }
}
