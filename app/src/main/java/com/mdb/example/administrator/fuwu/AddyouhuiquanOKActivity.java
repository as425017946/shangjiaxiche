package com.mdb.example.administrator.fuwu;

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
import com.mdb.example.administrator.login.ShenfenyanzhengActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券发布成功
 */
public class AddyouhuiquanOKActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addyouhuiquan_ok);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(AddyouhuiquanOKActivity.this,"xicheshop");
        setTtitle();
        fanhui();
        setinfo();
    }

    @BindView(R.id.addyouhuiquan_jixu)
    Button btn_jixu;
    @BindView(R.id.addyouhuiquan_ok)
    Button btn_ok;
    private void setinfo() {
        btn_jixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddyouhuiquanOKActivity.this,AddYouhuiquanActivity.class);
                startActivity(intent);
                AddyouhuiquanOKActivity.this.finish();
            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("优惠完成",sharehelper.getSharedPreference("youhuiquan","").toString());
                //如果是没有进入mainactivity为0，进入了为1
                if (sharehelper.getSharedPreference("youhuiquan","").toString().equals("0")){
                    Intent intent = new Intent(AddyouhuiquanOKActivity.this,MainActivity.class);
                    startActivity(intent);
                    AddyouhuiquanOKActivity.this.finish();
                }else{
                    AddyouhuiquanOKActivity.this.finish();
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
        ttitle.setText("优惠信息");
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
                AddyouhuiquanOKActivity.this.finish();
            }
        });
    }


}
