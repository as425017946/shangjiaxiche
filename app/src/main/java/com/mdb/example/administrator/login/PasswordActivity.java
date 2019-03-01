package com.mdb.example.administrator.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.mdb.example.administrator.bean.TongyongBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 写入密码页面
 */
public class PasswordActivity extends BaseActivity {

    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        setnext();
        setTtitle();
        fanhui();
        phone = getIntent().getStringExtra("phone");
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
                Intent intent = new Intent(PasswordActivity.this,RegisterActivity.class);
                startActivity(intent);
                PasswordActivity.this.finish();
            }
        });
    }
    /**
     * 下一步
     */
    @BindView(R.id.password_next)
    Button btn_next;
    @BindView(R.id.password_one)
    EditText edt_one;
    @BindView(R.id.password_two)
    EditText edt_two;
    private void setnext(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断密码和确认密码是否为空，是否一致
                if (TextUtils.isEmpty(edt_one.getText().toString())){
                    ToastUtils.shortToast("请输入密码");
                }else{
                    if (TextUtils.isEmpty(edt_two.getText().toString())){
                        ToastUtils.shortToast("请输入确认密码");
                    }else{
                        if (edt_one.getText().toString().length()<6 || edt_two.getText().toString().length()<6){
                            ToastUtils.shortToast("请输入密码长度过短");
                        }else {
                            if (!edt_one.getText().toString().equals(edt_two.getText().toString())){
                                ToastUtils.shortToast("两次输入的密码不一致");
                            }else{
                                OkGo.post(Api.zhuce)
                                        .tag(this)
                                        .params("password",edt_one.getText().toString())
                                        .params("mobile",phone)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                Gson gson = new Gson();
                                                TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                if (tongyongBean.getState()==1){
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Intent intent = new Intent(PasswordActivity.this,RegisterOkActivity.class);
                                                            startActivity(intent);
                                                            PasswordActivity.this.finish();
                                                        }
                                                    },1000);

                                                }else{
                                                    ToastUtils.shortToast(tongyongBean.getMessage());
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
}
