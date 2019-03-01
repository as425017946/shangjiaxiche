package com.mdb.example.administrator.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.MainActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提交身份验证完成页面
 */
public class YanzhengOkActivity extends BaseActivity {

    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanzheng_ok);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(YanzhengOkActivity.this,"xicheshop");
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
        ttitle.setText("提交审核");
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
        img.setVisibility(View.INVISIBLE);
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YanzhengOkActivity.this,BankActivity.class);
                startActivity(intent);
                YanzhengOkActivity.this.finish();
            }
        });
    }

    /**
     *提交信息
     */
    @BindView(R.id.sfyz_ok)
    Button btn_next;
    private void setinfo(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                YanzhengOkActivity.this.finish();
//                Intent intent = new Intent(YanzhengOkActivity.this,YanzhengShenheActivity.class);
//                intent.putExtra("status","1");
//                startActivity(intent);
                sharehelper.put("shopstatus","1");
                Intent intent = new Intent(YanzhengOkActivity.this,MainActivity.class);
                startActivity(intent);
                YanzhengOkActivity.this.finish();
            }
        });
    }
}
