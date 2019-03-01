package com.mdb.example.administrator.fuwu;

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
import com.mdb.example.administrator.login.YanzhengShenheActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发任务完成页面
 */
public class ReleaseProjectOkActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_project_ok);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(ReleaseProjectOkActivity.this,"xicheshop");
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
        ttitle.setText("发布服务信息");
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
                Intent intent = new Intent(ReleaseProjectOkActivity.this,ReleaseProjectActivity.class);
                startActivity(intent);
                ReleaseProjectOkActivity.this.finish();
            }
        });
    }

    /**
     *提交信息
     */
    @BindView(R.id.project_jixu)
    Button btn_jixu;
    @BindView(R.id.project_youhui)
    Button btn_youhui;
    @BindView(R.id.project_ok)
    Button btn_ok;
    private void setinfo(){
        //继续
        btn_jixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReleaseProjectOkActivity.this.finish();
                Intent intent = new Intent(ReleaseProjectOkActivity.this,ReleaseProjectActivity.class);
                startActivity(intent);
            }
        });
        //添加优惠券
        btn_youhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReleaseProjectOkActivity.this,YouhuiManagerActivity.class);
                startActivity(intent);
            }
        });
        //完成
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果是没有进入mainactivity为0，进入了为1
                if (sharehelper.getSharedPreference("fabuinfo","").toString().equals("0")){
                    Intent intent = new Intent(ReleaseProjectOkActivity.this, MainActivity.class);
                    startActivity(intent);
                    ReleaseProjectOkActivity.this.finish();
                }else{
                    ReleaseProjectOkActivity.this.finish();
                }

            }
        });

    }
}
