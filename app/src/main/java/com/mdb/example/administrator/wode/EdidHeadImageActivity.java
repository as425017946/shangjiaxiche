package com.mdb.example.administrator.wode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更换头像
 */
public class EdidHeadImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edid_head_image);
        ButterKnife.bind(this);
        setinfo();
    }

    /**
     * 信息提交
     */
    @BindView(R.id.bianji_imgfanhui)
    ImageView ima_fanhui;
    private void setinfo() {
        ima_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdidHeadImageActivity.this.finish();
            }
        });
    }

}
