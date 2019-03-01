package com.mdb.example.administrator.wode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.login.BankActivity;
import com.mdb.example.administrator.login.InvitationCodeActivity;
import com.mdb.example.administrator.login.YanzhengOkActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class WodeBank2Activity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(WodeBank2Activity.this,"xicheshop");
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
        ttitle.setText("绑定银行卡");
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
                Intent intent = new Intent(WodeBank2Activity.this,JieBangPhoneActivity.class);
                intent.putExtra("bank2zhi","2");
                startActivity(intent);
                WodeBank2Activity.this.finish();
            }
        });
    }

    /**
     *提交信息
     */
    @BindView(R.id.sfyz_tijiaoshenhe)
    Button btn_next;
    @BindView(R.id.sfyz_yaoqingma)
    Button btn_yaoqingma;
    @BindView(R.id.sfyz_yinhangcard)
    EditText edt_yinhangcard;
    @BindView(R.id.sfyz_kaihuiname)
    EditText edt_name;
    @BindView(R.id.sfyz_shenfenzheng)
    EditText edt_shenfencard;
    @BindView(R.id.sfyz_yuliuphone)
    EditText edt_phone;
    @BindView(R.id.sfyz_kaihuhang)
    TextView tv_kaihuhang;
    String zhi="";
    private void setinfo(){
        btn_yaoqingma.setVisibility(View.GONE);
        //选择银行
        tv_kaihuhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(WodeBank2Activity.this);
                LayoutInflater inflater = LayoutInflater.from(WodeBank2Activity.this);
                final View DialogView = inflater .inflate ( R.layout.bankinfo, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.bank_ok);//自定义控件
                final RadioGroup radioGroup = (RadioGroup)DialogView.findViewById(R.id.radiogrop1);
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        View checkView = radioGroup.findViewById(i);

                        if (!checkView.isPressed()){
                            return;
                        }
                        switch (i){
                            case R.id.radiobutton1:
                                zhi= "中国工商银行";
                                break;
                            case R.id.radiobutton2:
                                zhi= "中国农业银行";
                                break;
                            case R.id.radiobutton3:
                                zhi= "中国银行";
                                break;
                            case R.id.radiobutton4:
                                zhi= "中国建设银行";
                                break;
                            case R.id.radiobutton5:
                                zhi= "中信银行";
                                break;
                            case R.id.radiobutton6:
                                zhi= "中国光大银行";
                                break;
                            case R.id.radiobutton7:
                                zhi= "中国民生银行";
                                break;
                            case R.id.radiobutton8:
                                zhi= "中国平安银行";
                                break;
                            case R.id.radiobutton9:
                                zhi= "中信银行";
                                break;
                            case R.id.radiobutton10:
                                zhi= "华夏银行";
                                break;
                            case R.id.radiobutton11:
                                zhi= "招商银行";
                                break;
                            case R.id.radiobutton12:
                                zhi= "浦发银行";
                                break;
                            case R.id.radiobutton13:
                                zhi= "海口联合农商银行";
                                break;
                        }
//                        Log.e("值",zhi);
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        tv_kaihuhang.setText(zhi);
//                        Log.e("值2",zhi+"");
                    }
                });
                dialog.show();
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_yinhangcard.getText().toString())){
                    ToastUtils.shortToast("请输入银行卡号");
                }else {
                    if (tv_kaihuhang.getText().toString().equals("请选择开户银行")){
                        ToastUtils.shortToast("请选择开户银行");
                    }else {
                        if (TextUtils.isEmpty(edt_name.getText().toString())){
                            ToastUtils.shortToast("请输入开户姓名");
                        }else {
                            if (TextUtils.isEmpty(edt_shenfencard.getText().toString())){
                                ToastUtils.shortToast("请输入身份证号");
                            }else {
                                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                                    ToastUtils.shortToast("请输入预留手机号");
                                }else {
                                    OkGo.post(Api.BingBank)
                                            .tag(this)
                                            .params("shopId",sharehelper.getSharedPreference("shopid","").toString())
                                            .params("cardNo",edt_yinhangcard.getText().toString())
                                            .params("cardProduce",tv_kaihuhang.getText().toString())
                                            .params("cardOwner",edt_name.getText().toString())
                                            .params("cardMobile",edt_phone.getText().toString())
                                            .params("idCard",edt_shenfencard.getText().toString())
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
                                                                Intent intent = new Intent(WodeBank2Activity.this,WodeBankOkActivity.class);
                                                                startActivity(intent);
                                                                WodeBank2Activity.this.finish();
                                                            }
                                                        },200);
                                                    }else {
                                                        ToastUtils.shortToast(tongyongBean.getMessage());
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                }

            }
        });
        btn_yaoqingma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WodeBank2Activity.this,InvitationCodeActivity.class);
                startActivity(intent);
                WodeBank2Activity.this.finish();
            }
        });
    }

}
