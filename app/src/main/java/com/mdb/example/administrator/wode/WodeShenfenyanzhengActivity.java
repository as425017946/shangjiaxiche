package com.mdb.example.administrator.wode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.login.BankActivity;
import com.mdb.example.administrator.login.LoginActivity;
import com.mdb.example.administrator.login.ShenfenyanzhengActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 身份验证起始页
 */
public class WodeShenfenyanzhengActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenfenyanzheng);
        ButterKnife.bind(this);
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
        ttitle.setText("资质上传");
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
                Intent intent = new Intent(WodeShenfenyanzhengActivity.this,LoginActivity.class);
                startActivity(intent);
                WodeShenfenyanzhengActivity.this.finish();
            }
        });
    }
    /**
     * 填写信息上传
     */
    @BindView(R.id.sfyz_next)
    Button btn_next;
    private void setinfo(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WodeShenfenyanzhengActivity.this,BankActivity.class);
                startActivity(intent);
                WodeShenfenyanzhengActivity.this.finish();
            }
        });
    }

    //禁用返回键
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }
}
