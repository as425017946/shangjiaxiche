package com.mdb.example.administrator.wode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 账户安全
 */
public class AccountsecurityActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsecurity);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(AccountsecurityActivity.this,"xicheshop");
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 写入信息
     */
    @BindView(R.id.account_phone)
    LinearLayout ll_phone;
    @BindView(R.id.account_password)
    LinearLayout ll_password;
    @BindView(R.id.account_card)
    LinearLayout ll_card;
    @BindView(R.id.account_bing_phone)
    TextView tv_bing_phone;
    private void setinfo() {
        tv_bing_phone.setText(sharehelper.getSharedPreference("shopphone","").toString().substring(0,3)+"***"+sharehelper.getSharedPreference("shopphone","").toString().substring(sharehelper.getSharedPreference("shopphone","").toString().length()-4,sharehelper.getSharedPreference("shopphone","").toString().length()));

        ll_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountsecurityActivity.this,JieBangPhoneActivity.class);
                intent.putExtra("bank2zhi","0");
                startActivity(intent);
            }
        });
        ll_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountsecurityActivity.this,WodePasswordActivity.class);
                startActivity(intent);
            }
        });
        ll_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountsecurityActivity.this,WodeBankActivity.class);
                intent.putExtra("bankzhi","1");
                startActivity(intent);
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
        ttitle.setText("账户与安全");
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
                AccountsecurityActivity.this.finish();
            }
        });
    }
}
