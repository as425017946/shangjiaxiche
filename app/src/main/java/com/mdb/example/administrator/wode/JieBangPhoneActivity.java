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
import com.mdb.example.administrator.bean.BankBean;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.login.PasswordActivity;
import com.mdb.example.administrator.login.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 解绑手机
 */
public class JieBangPhoneActivity extends BaseActivity {
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_bang_phone);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(JieBangPhoneActivity.this,"xicheshop");
        setTtitle();
        fanhui();
        setinfo();
        selectbank();
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        if (getIntent().getStringExtra("bank2zhi").equals("1")){
            ttitle.setText("更换银行卡");
        }else{
            ttitle.setText("更换手机");
        }

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
                if(getIntent().getStringExtra("bank2zhi").equals("1")){
                    Log.e("111","11");
                    Intent intent = new Intent(JieBangPhoneActivity.this,WodeBankActivity.class);
                    startActivity(intent);
                    JieBangPhoneActivity.this.finish();
                }else{
                    JieBangPhoneActivity.this.finish();
                    Log.e("22","22");
                }

            }
        });
    }
    /**
     * 提交信息
     */
    @BindView(R.id.bangding_tijiao)
    Button btn_tijiao;
    @BindView(R.id.jiebang_huoqu)
    Button btn_huoqu;
    @BindView(R.id.jiebang_yzm)
    EditText edt_yzm;
    @BindView(R.id.jiebang_phone)
    TextView tv_phone;
    private void setinfo(){
        tv_phone.setText(sharedPreferencesHelper.getSharedPreference("shopphone","").toString().substring(0,3)+"***"+sharedPreferencesHelper.getSharedPreference("shopphone","").toString().substring(sharedPreferencesHelper.getSharedPreference("shopphone","").toString().length()-4,sharedPreferencesHelper.getSharedPreference("shopphone","").toString().length()));
        //获取验证码
        btn_huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
                getsms(sharedPreferencesHelper.getSharedPreference("shopphone","").toString());
            }
        });
        //确认解绑
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_yzm.getText().toString())){
                    ToastUtils.shortToast("请输入验证码");
                }else{
                    if(getIntent().getStringExtra("bank2zhi").equals("1")){
                        //解绑银行卡，先清空之前绑定的银行卡，然后跳转
//                        OkGo.post(Api.BingBank)
//                                .tag(this)
//                                .params("shopId",sharedPreferencesHelper.getSharedPreference("shopid","").toString())
//                                .params("cardNo","")
//                                .params("cardProduce","")
//                                .params("cardOwner","")
//                                .params("cardMobile","")
//                                .params("idCard","")
//                                .execute(new StringCallback() {
//                                    @Override
//                                    public void onSuccess(String s, Call call, Response response) {
//                                        Gson gson = new Gson();
//                                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
//                                        if (tongyongBean.getState()==1){
//
//                                        }else {
////                                            ToastUtils.shortToast(tongyongBean.getMessage());
//                                        }
//                                    }
//                                });
                        Intent intent = new Intent(JieBangPhoneActivity.this,WodeBank2Activity.class);
                        startActivity(intent);
                        JieBangPhoneActivity.this.finish();
                    }else{
                        //解绑手机
                        OkGo.post(Api.yanzhengsms)
                                .tag(this)
                                .params("mobile",sharedPreferencesHelper.getSharedPreference("shopphone","").toString())
                                .params("smsCode",edt_yzm.getText().toString())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
//                                        Log.e("测试",s);
                                        Gson gson = new Gson();
                                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                        if (tongyongBean.getState()==1){
                                            Intent intent = new Intent(JieBangPhoneActivity.this,BangdingPhoneActivity.class);
                                            startActivity(intent);
                                            JieBangPhoneActivity.this.finish();
                                        }else{
                                            ToastUtils.shortToast("验证码输入有误");
                                        }
                                    }
                                });

                    }
                }


            }
        });
    }


    /**
     * 查询银行卡
     */
    private void selectbank(){
        OkGo.post(Api.chaxunBank)
                .tag(this)
                .params("shopId",sharedPreferencesHelper.getSharedPreference("shopid","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        BankBean bankBean = gson.fromJson(s,BankBean.class);
                        if (bankBean.getState()==1){
                            if (TextUtils.isEmpty(bankBean.getData().getCard_no())){
                                ttitle.setText("绑定银行卡");
                            }
                        }
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
