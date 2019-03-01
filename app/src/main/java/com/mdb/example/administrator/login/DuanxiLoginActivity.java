package com.mdb.example.administrator.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.MainActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.Utils.UiUtils;
import com.mdb.example.administrator.bean.BankBean;
import com.mdb.example.administrator.bean.LoginBean;
import com.mdb.example.administrator.bean.TongyongBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.mdb.example.administrator.MyApplication.registrationID;

/**
 * 注册页面
 */
public class DuanxiLoginActivity extends BaseActivity {
    SharedPreferencesHelper sharedPreferencesHelper;
    @BindView(R.id.zhucexieyi)
    TextView tv_zhucexieyi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(DuanxiLoginActivity.this,"xicheshop");
        setTtitle();
        fanhui();
        setnext();
        tv_zhucexieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(DuanxiLoginActivity.this);
                LayoutInflater inflater = LayoutInflater.from(DuanxiLoginActivity.this);
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
        if (getIntent().getStringExtra("zhi").equals("0")){
            ttitle.setText("验证码登录");
            btn_next.setText("登录");
        }else{
            ttitle.setText("重置密码");
            btn_next.setText("下一步");
        }
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
              Intent intent = new Intent(DuanxiLoginActivity.this,LoginActivity.class);
              startActivity(intent);
              DuanxiLoginActivity.this.finish();
            }
        });
    }
    /**
     * 下一步
     */
    @BindView(R.id.register_next)
    Button btn_next;
    @BindView(R.id.register_phone)
    EditText edt_phone;
    @BindView(R.id.register_huoqu)
    Button btn_huoqu;
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
        //下一步
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("zhi").equals("0")){
                    //短信验证码登录使用
                    if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                        ToastUtils.shortToast("输入的手机号不正确");
                    }else{
                        if (TextUtils.isEmpty(edt_sms.getText().toString())){
                            ToastUtils.shortToast("请输入验证码");
                        }else{
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
                                                //如果验证码登录通过，访问下面的接口跳转
                                                OkGo.post(Api.yzmSms)
                                                        .tag(this)
                                                        .params("mobile",edt_phone.getText().toString())
                                                        .params("registrationId",registrationID)
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(String s, Call call, Response response) {
                                                                Log.e("短信登录",s);
                                                                // 1未审核2驳回3重新提交4通过5拉黑
                                                                final Gson gson = new Gson();
                                                                final LoginBean loginBean = gson.fromJson(s,LoginBean.class);
                                                                if (loginBean.getState()==1){
                                                                    sharedPreferencesHelper.put("shopid",loginBean.getData().getShopId()+"");
                                                                    sharedPreferencesHelper.put("shopphone",loginBean.getData().getMobile()+"");
                                                                    sharedPreferencesHelper.put("shopcode",loginBean.getData().getShop_code()+"");
                                                                    sharedPreferencesHelper.put("shopstatus",loginBean.getData().getStatus()+"");
                                                                    sharedPreferencesHelper.put("youhuiquan","1");
                                                                    if (loginBean.getData().getReason()==null){
                                                                        sharedPreferencesHelper.put("reason","");
                                                                    }else{
                                                                        sharedPreferencesHelper.put("reason",loginBean.getData().getReason()+"");
                                                                    }
                                                                    if (loginBean.getData().getShop_name()==null){
                                                                        sharedPreferencesHelper.put("shopname","");
                                                                    }else {
                                                                        sharedPreferencesHelper.put("shopname",loginBean.getData().getShop_name());
                                                                    }
                                                                    if (loginBean.getData().getWaitnum_flag()==null){
                                                                        sharedPreferencesHelper.put("waitnum_flag","");
                                                                    }else {
                                                                        sharedPreferencesHelper.put("waitnum_flag",loginBean.getData().getWaitnum_flag());
                                                                    }


                                                                    Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            // 1未审核2驳回3重新提交4通过5拉黑
                                                                            if (loginBean.getData().getStatus().equals("4")){
                                                                                Intent intent = new Intent(DuanxiLoginActivity.this, MainActivity.class);
                                                                                startActivity(intent);
                                                                                DuanxiLoginActivity.this.finish();
                                                                            }else{
                                                                                //未审核
                                                                                if (loginBean.getData().getStatus().equals("1")){
                                                                                    //先判断是否上传了银行卡
                                                                                    OkGo.post(Api.chaxunBank)
                                                                                            .tag(this)
                                                                                            .params("shopId",loginBean.getData().getShopId())
                                                                                            .execute(new StringCallback() {
                                                                                                @Override
                                                                                                public void onSuccess(String s, Call call, Response response) {
                                                                                                    Log.e("银行卡",s);
                                                                                                    Gson gson1 = new Gson();
                                                                                                    BankBean bankBean = gson1.fromJson(s,BankBean.class);
                                                                                                    if (bankBean.getState()==1){
                                                                                                        if (bankBean.getData()==null){
                                                                                                            //没有上传银行卡，而且没有审核通过，重新上传
                                                                                                            Intent intent = new Intent(DuanxiLoginActivity.this,ShenfenyanzhengActivity.class);
                                                                                                            intent.putExtra("status","1");
                                                                                                            startActivity(intent);
                                                                                                            DuanxiLoginActivity.this.finish();
                                                                                                        }else{
                                                                                                            //已上传银行卡，但是没有提示通过
                                                                                                            Intent intent = new Intent(DuanxiLoginActivity.this,YanzhengShenheActivity.class);
                                                                                                            intent.putExtra("status",loginBean.getData().getStatus());
                                                                                                            startActivity(intent);
                                                                                                            DuanxiLoginActivity.this.finish();
                                                                                                        }
//

                                                                                                    }else {
                                                                                                        ToastUtils.shortToast("信息请求失败，请稍后重试");
                                                                                                    }
                                                                                                }
                                                                                            });

                                                                                }else if (loginBean.getData().getStatus().equals("2")){
                                                                                    //已上传银行卡，但是没有提示通过
                                                                                    Intent intent = new Intent(DuanxiLoginActivity.this,YanzhengShenheActivity.class);
                                                                                    intent.putExtra("status","2");
                                                                                    startActivity(intent);
                                                                                    DuanxiLoginActivity.this.finish();

                                                                                }else if (loginBean.getData().getStatus().equals("3")){
                                                                                    //已上传银行卡，但是没有提示通过
                                                                                    Intent intent = new Intent(DuanxiLoginActivity.this,YanzhengShenheActivity.class);
                                                                                    intent.putExtra("status","3");
                                                                                    startActivity(intent);
                                                                                    DuanxiLoginActivity.this.finish();

                                                                                }else if (loginBean.getData().getStatus().equals("5")){
                                                                                    //已上传银行卡，但是没有提示通过
                                                                                    Intent intent = new Intent(DuanxiLoginActivity.this,YanzhengShenheActivity.class);
                                                                                    intent.putExtra("status","5");
                                                                                    startActivity(intent);
                                                                                    DuanxiLoginActivity.this.finish();

                                                                                }

                                                                            }


                                                                        }
                                                                    },2000);
                                                                    LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(DuanxiLoginActivity.this)
                                                                            .setMessage("登录成功...")
                                                                            .setCancelable(false)
                                                                            .setCancelOutside(false);
                                                                    LoadingDailog dialog=loadBuilder.create();
                                                                    dialog.show();
                                                                }else {
                                                                    ToastUtils.shortToast(loginBean.getMessage());
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


                }else{
                    //重置密码使用
                    if (TextUtils.isEmpty(edt_phone.getText().toString())){
                        ToastUtils.shortToast("请输入手机号");
                    }else{
                        if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                            ToastUtils.shortToast("输入的手机号不正确");
                        }else{
                            if (TextUtils.isEmpty(edt_sms.getText().toString())){
                                ToastUtils.shortToast("请输入验证码");
                            }else{
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
                                                    Intent intent = new Intent(DuanxiLoginActivity.this,ResetPasswordActivity.class);
                                                    intent.putExtra("zhi","1");
                                                    intent.putExtra("phone",edt_phone.getText().toString());
                                                    startActivity(intent);
                                                    DuanxiLoginActivity.this.finish();
                                                }else{
                                                    ToastUtils.shortToast("验证码输入有误");
                                                }
                                            }
                                        });
                            }
                        }
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
