package com.mdb.example.administrator.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.MainActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.fuwu.ReleaseProjectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提交身份验证 审核页面
 */
public class YanzhengShenheActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @BindView(R.id.shenhe)
    ImageView imageView;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanzheng_ok);
        ButterKnife.bind(this);
        setTtitle();
        sharehelper = new SharedPreferencesHelper(YanzhengShenheActivity.this,"xicheshop");
        status = getIntent().getStringExtra("status");
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
                YanzhengShenheActivity.this.finish();
            }
        });
    }

    /**
     *提交信息
     */
    @BindView(R.id.sfyz_ok)
    Button btn_next;
    @BindView(R.id.shenheinfo1)
    TextView tvinfo1;
    @BindView(R.id.shenheinfo2)
    TextView tvinfo2;
    @BindView(R.id.shenheinfo3)
    TextView tvinfo3;
    @BindView(R.id.shenheinfo4)
    TextView tvinfo4;
    @BindView(R.id.shibaixinxi)
    LinearLayout l_shibai;
    private void setinfo(){
        //成功
        if (status.equals("1")){
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.shenheok));
            tvinfo1.setText("审核中，请耐心等待");
            tvinfo2.setText("");
            btn_next.setText("进入");
            l_shibai.setVisibility(View.INVISIBLE);
        }else if (status.equals("2")){
            //失败
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.shibai));
            tvinfo1.setText("您好，您的审核未通过");
            tvinfo2.setText("如有问题请咨询客服");
            tvinfo3.setText("请修改您的信息");
            btn_next.setText("重新上传资料");
            l_shibai.setVisibility(View.VISIBLE);
            tvinfo4.setText(sharehelper.getSharedPreference("reason","")+"");
        }else if (status.equals("3")){
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.shenheok));
            tvinfo1.setText("审核中，请耐心等待");
            tvinfo2.setText("");
            btn_next.setText("进入");
            l_shibai.setVisibility(View.INVISIBLE);
        }else if (status.equals("5")){
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.shibai));
            tvinfo1.setText("您好，您已经被平台拉黑");
            tvinfo2.setText("如有问题请咨询客服");
            tvinfo3.setText("");
            btn_next.setText("完成");
            l_shibai.setVisibility(View.INVISIBLE);
        }



        //成功和失败的跳转
        if (btn_next.getText().toString().equals("发布服务信息")){
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YanzhengShenheActivity.this.finish();
                    //0代表没有进入manactivity页面的时候,1代表已进入
                    sharehelper.put("fabuinfo","0");
                    Intent intent = new Intent(YanzhengShenheActivity.this, ReleaseProjectActivity.class);
                    startActivity(intent);
                }
            });
        }else if (btn_next.getText().toString().equals("重新上传资料")){
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(YanzhengShenheActivity.this,ShenfenyanzhengActivity.class);
                    intent.putExtra("status","3");
                    startActivity(intent);
                    YanzhengShenheActivity.this.finish();
                }
            });
        }else if (btn_next.getText().toString().equals("完成")){
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YanzhengShenheActivity.this.finish();
                }
            });

        }else if (btn_next.getText().toString().equals("进入")){
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharehelper.put("shopstatus","1");
                    Intent intent = new Intent(YanzhengShenheActivity.this,MainActivity.class);
                    startActivity(intent);
                    YanzhengShenheActivity.this.finish();
                }
            });

        }


    }
}
