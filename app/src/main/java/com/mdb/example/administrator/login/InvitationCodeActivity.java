package com.mdb.example.administrator.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.Utils.VerifyCodeView;
import com.mdb.example.administrator.bean.TongyongBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 邀请码
 */
public class InvitationCodeActivity extends BaseActivity {
    VerifyCodeView verifyCodeView;
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_code);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setinfo();
        sharehelper = new SharedPreferencesHelper(InvitationCodeActivity.this,"xicheshop");
    }

    @BindView(R.id.code_next)
    Button btn_next;
    String zhi;//保存邀请码的值
    @BindView(R.id.yqm_yaoqingma)
    TextView tv_yaoqingma;
    private int red = 0xFFf05151;  //选中后的颜色
    private void setinfo() {
        //输入的邀请码信息
        verifyCodeView = (VerifyCodeView) findViewById(R.id.verify_code_view);
        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                zhi = verifyCodeView.getEditContent();
//                Toast.makeText(InvitationCodeActivity.this, "inputComplete: " + verifyCodeView.getEditContent(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void invalidContent() {

            }
        });


        //完成按钮
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(zhi)){
                    tv_yaoqingma.setText("请输入正确长度的邀请码");
                    tv_yaoqingma.setTextColor(red);
                }else if (verifyCodeView.getEditContent().length()==6){
                    OkGo.post(Api.zizhiup)
                            .tag(this)
                            .params("shopId",sharehelper.getSharedPreference("shopid","").toString())
                            .params("invitationCode",zhi)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Log.e("邀请码",s);
                                    Gson gson = new Gson();
                                    TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                    if (tongyongBean.getState()==1){
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(InvitationCodeActivity.this,CodeOkActivity.class);
                                                startActivity(intent);
                                                InvitationCodeActivity.this.finish();
                                            }
                                        },500);
                                    }else{
                                        tv_yaoqingma.setText("邀请码输入错误请重新输入");
                                        tv_yaoqingma.setTextColor(red);
//                                        ToastUtils.shortToast("信息上传失败，请重试！");
                                    }
                                }
                            });
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
        ttitle.setText("邀请码");
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
                Intent intent = new Intent(InvitationCodeActivity.this,BankActivity.class);
                startActivity(intent);
                InvitationCodeActivity.this.finish();
            }
        });
    }
}
