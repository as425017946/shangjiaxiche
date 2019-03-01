package com.mdb.example.administrator.wode;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.mdb.example.administrator.Utils.UiUtils;
import com.mdb.example.administrator.bean.TongyongBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 绑定手机
 */
public class BangdingPhoneActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangding_phone);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(BangdingPhoneActivity.this,"xicheshop");
        setTtitle();
        fanhui();
        setinfo();
    }


    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("绑定新手机");
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
                Intent intent = new Intent(BangdingPhoneActivity.this,JieBangPhoneActivity.class);
                startActivity(intent);
                BangdingPhoneActivity.this.finish();
            }
        });
    }

    /**
     * 提交信息
     */
    @BindView(R.id.bangding_tijiao)
    Button btn_tijiao;
    @BindView(R.id.bind_phone)
    EditText edt_phone;
    @BindView(R.id.bind_huoqu)
    Button btn_huoqu;
    @BindView(R.id.bind_yzm)
    EditText edt_yzm;
    private void setinfo(){
        btn_huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                    ToastUtils.shortToast("手机号不能为空！");
                }else{
                    if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                        ToastUtils.shortToast("手机号输入不正确！");
                    }else{
                        timer.start();
                        getsms(edt_phone.getText().toString());
                    }
                }
            }
        });
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                    ToastUtils.shortToast("请输入手机号");
                }else {
                    if (TextUtils.isEmpty(edt_yzm.getText().toString())){
                        ToastUtils.shortToast("请输入验证码");
                    }else {
                        //解绑手机
                        OkGo.post(Api.yanzhengsms)
                                .tag(this)
                                .params("mobile",edt_phone.getText().toString())
                                .params("smsCode",edt_yzm.getText().toString())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
//                                        Log.e("测试",s);
                                        Gson gson = new Gson();
                                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                        if (tongyongBean.getState()==1){
                                            OkGo.post(Api.genghuanphone)
                                                    .tag(this)
                                                    .params("mobileN",edt_phone.getText().toString())
                                                    .params("mobileO",sharehelper.getSharedPreference("shopphone","").toString())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
//                                                            Log.e("手机号",s);
                                                            Gson gson1 = new Gson();
                                                            TongyongBean tongyongBean1 = gson1.fromJson(s,TongyongBean.class);
                                                            if (tongyongBean1.getState()==1){
                                                                ToastUtils.shortToast("更换成功");
                                                                Handler handler = new Handler();
                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        BangdingPhoneActivity.this.finish();
                                                                    }
                                                                },2000);

                                                            }
                                                        }
                                                    });
                                        }else{
                                            ToastUtils.shortToast("验证码输入有误");
                                        }
                                    }
                                });
                    }
                }
                BangdingPhoneActivity.this.finish();
            }
        });
    }
    /**
     * 获取验证码
     */
    private void getsms(String phone){
        OkGo.post(Api.huoqusms)
                .tag(this)
                .params("mobile",phone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("测试短信",s);
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }



    /**
     * 线程读秒
     */
    private CountDownTimer timer = new CountDownTimer(120000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btn_huoqu.setText("倒计时("+(millisUntilFinished / 1000)+")");
            btn_huoqu.setEnabled(false);
        }

        @Override
        public void onFinish() {
            btn_huoqu.setEnabled(true);
            btn_huoqu.setText("再次获取");
        }
    };
}
