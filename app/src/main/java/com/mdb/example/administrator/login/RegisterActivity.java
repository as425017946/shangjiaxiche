package com.mdb.example.administrator.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.Utils.UiUtils;
import com.mdb.example.administrator.bean.TongyongBean;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.zhucexieyi)
    TextView tv_zhucexieyi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setnext();
        tv_zhucexieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RegisterActivity.this);
                LayoutInflater inflater = LayoutInflater.from(RegisterActivity.this);
                final View DialogView = inflater .inflate ( R.layout.zhucetishi, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.fxts_show);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
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
        ttitle.setText("注册");
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    private void fanhui(){
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
              startActivity(intent);
              RegisterActivity.this.finish();
            }
        });
    }
    /**
     * 下一步
     */
    @BindView(R.id.register_next)
    Button btn_next;
    @BindView(R.id.register_huoqu)
    Button btn_huoqu;
    @BindView(R.id.register_phone)
    EditText edt_phone;
    @BindView(R.id.register_sms)
    EditText edt_sms;
    private void setnext(){


        //获取验证码
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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断手机号和验证码是否为空
                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                    ToastUtils.shortToast("请输入手机号");
                }else{
                    if (TextUtils.isEmpty(edt_phone.getText().toString())){
                        ToastUtils.shortToast("请输入验证码");
                    }else {
                        OkGo.post(Api.yanzhengsms)
                                .tag(this)
                                .params("mobile",edt_phone.getText().toString())
                                .params("smsCode",edt_sms.getText().toString())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
//                                        Log.e("测试",s);
                                        Gson gson = new Gson();
                                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                        if (tongyongBean.getState()==1){
                                            Intent intent = new Intent(RegisterActivity.this,PasswordActivity.class);
                                            intent.putExtra("phone",edt_phone.getText().toString());
                                            startActivity(intent);
                                            RegisterActivity.this.finish();
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
