package com.mdb.example.administrator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.fuwu.FuwuFragment;
import com.mdb.example.administrator.jiaohao.CallNumberFragment;
import com.mdb.example.administrator.wode.WodeFragment;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FuwuFragment fuwuFragment;
    private WodeFragment wodeFragment;
    private CallNumberFragment callNumberFragment;
    // 帧布局对象，用来存放Fragment对象
    private FrameLayout frameLayout;
    // 定义每个选项中的相关控件
    private LinearLayout secondLayout;
    private LinearLayout thirdLayout;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView middleImage;
    private TextView secondText;
    private TextView thirdText;
    // 定义几个颜色
    private int whirt = 0xFFFFFFFF;  //背景色颜色
    private int gray = 0xFFACACAC;  //未选中颜色
    private int dark = 0xFF0C63D7;  //选中后的颜色
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
    //获取登录进来的用户信息
    private SharedPreferencesHelper sharehelper;
    public static String Userphone ,Userid,back;

    private static boolean mBackKeyPressed = false;//记录是否有首次按键



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initView(); // 初始化界面控件
        setChioceItem(2); // 初始化页面加载时显示第中间的选项卡  0第一个1最后一个2中间的那个
    }

    /**
     * 初始化页面
     */
    private void initView() {
        // 初始化底部导航栏的控件
        secondImage = (ImageView) findViewById(R.id.second_image);
        thirdImage = (ImageView) findViewById(R.id.third_image);
        secondText = (TextView) findViewById(R.id.second_text);
        thirdText = (TextView) findViewById(R.id.third_text);
        secondLayout = (LinearLayout) findViewById(R.id.second_layout);
        thirdLayout = (LinearLayout) findViewById(R.id.third_layout);
        middleImage = (ImageView)findViewById(R.id.middle_img);
        secondLayout.setOnClickListener(MainActivity.this);
        thirdLayout.setOnClickListener(MainActivity.this);
        middleImage.setOnClickListener(MainActivity.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_layout:
                setChioceItem(0);
                break;
            case R.id.third_layout:
                setChioceItem(1);
                break;
            case R.id.middle_img:
                setChioceItem(2);
                break;
            default:
                break;
        }
    }

    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1, 2, 3
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
                secondText.setTextColor(dark);
                secondImage.setImageDrawable(MainActivity.this.getResources().getDrawable(R.mipmap.fuwu1));
                if (fuwuFragment == null) {
                    fuwuFragment = new FuwuFragment();
                    fragmentTransaction.add(R.id.content, fuwuFragment);
                } else {
                    fragmentTransaction.show(fuwuFragment);
                }
                break;
            case 1:
// thirdImage.setImageResource(R.drawable.XXXX);
                thirdText.setTextColor(dark);
                thirdImage.setImageDrawable(MainActivity.this.getResources().getDrawable(R.mipmap.wode1));
                if (wodeFragment == null) {
                    wodeFragment = new WodeFragment();
                    fragmentTransaction.add(R.id.content, wodeFragment);
                } else {
                    fragmentTransaction.show(wodeFragment);
                }
                break;
            case 2:
                middleImage.setImageDrawable(MainActivity.this.getResources().getDrawable(R.mipmap.jiaohao1));
                if (callNumberFragment == null) {
                    callNumberFragment = new CallNumberFragment();
                    fragmentTransaction.add(R.id.content, callNumberFragment);
                } else {
                    fragmentTransaction.show(callNumberFragment);
                }
                break;
        }
        fragmentTransaction.commit(); // 提交
    }
    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
// secondImage.setImageResource(R.drawable.XXX);
        secondText.setTextColor(gray);
        secondLayout.setBackgroundColor(whirt);
        secondImage.setImageDrawable(MainActivity.this.getResources().getDrawable(R.mipmap.fuwu2));
// thirdImage.setImageResource(R.drawable.XXX);
        thirdText.setTextColor(gray);
        thirdLayout.setBackgroundColor(whirt);
        thirdImage.setImageDrawable(MainActivity.this.getResources().getDrawable(R.mipmap.wode2));

        middleImage.setImageDrawable(MainActivity.this.getResources().getDrawable(R.mipmap.jiaohao2));

    }
    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fuwuFragment != null) {
            fragmentTransaction.hide(fuwuFragment);
        }
        if (wodeFragment != null) {
            fragmentTransaction.hide(wodeFragment);
        }
        if (callNumberFragment != null) {
            fragmentTransaction.hide(callNumberFragment);
        }
    }

    /**
     * 返回键触发的方法
     * */
    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtils.shortToast("再按一次退出程序");
            mBackKeyPressed = true;


            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);//延时两秒，如果超出则擦错第一次按键记录
        } else

        {
            this.finish();
            System.exit(0);
        }

    }

    //声明一个静态常量，用作退出BaseActivity的Tag
    public static final String EXIST = "exist";
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {//判断其他Activity启动本Activity时传递来的intent是否为空
            //获取intent中对应Tag的布尔值
            boolean isExist = intent.getBooleanExtra(EXIST, false);
            //如果为真则退出本Activity
            if (isExist) {
//                this.finish();
            }
        }
    }


}
