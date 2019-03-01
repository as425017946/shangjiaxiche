package com.mdb.example.administrator.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.MainActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.fuwu.ReleaseProjectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 验证码输入正确
 */
public class CodeOkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeok);
        ButterKnife.bind(this);
        setnext();
        setTtitle();
        fanhui();
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
    private void fanhui(){
        lfanhui.setVisibility(View.INVISIBLE);
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CodeOkActivity.this,PasswordActivity.class);
                startActivity(intent);
                CodeOkActivity.this.finish();
            }
        });
    }
    /**
     * 下一步
     */
    @BindView(R.id.codeok_next)
    Button btn_next;
    private void setnext(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CodeOkActivity.this,ReleaseProjectActivity.class);
                startActivity(intent);
                CodeOkActivity.this.finish();
            }
        });
    }
}
