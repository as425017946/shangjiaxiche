package com.mdb.example.administrator.wode;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.MainActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.adapter.OrderFragmentAdapter;
import com.mdb.example.administrator.login.ShopActivity;
import com.mdb.example.administrator.wode.order.OrdersActivity;
import com.mdb.example.administrator.wode.shopinfo.ShopInfoFragment;
import com.mdb.example.administrator.wode.shopinfo.ShopZizhiFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺信息
 */
public class ShopInfoActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        initData();
        setTtitle();
        fanhui();
    }

    /**
     * 展示信息
     */
    private void initData() {
        ShopInfoFragment shopInfoFragment = new ShopInfoFragment();
        ShopZizhiFragment shopZizhiFragment = new ShopZizhiFragment();
        mFragments.add(shopInfoFragment);
        mFragments.add(shopZizhiFragment);
        list.add("店铺信息");
        list.add("店铺资质");
        mTabLayout.setupWithViewPager(mViewpager);
        mViewpager.setAdapter(new OrderFragmentAdapter(getSupportFragmentManager(),mFragments,list));
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout, 10, 10);
            }
        });

    }
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.touzi_quxiao)
    LinearLayout l_bianji;
    private void setTtitle(){
        ttitle.setText("店铺信息");

        //一期先注释不用
        l_bianji.setVisibility(View.INVISIBLE);
        l_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShopInfoActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShopInfoActivity.this);
                final View DialogView = inflater .inflate ( R.layout.bianjiinfo, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //编辑店铺信息
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ShopInfoActivity.this,Shenfenyanzheng2Activity.class);
                        startActivity(intent);
//                        Intent intent2 = new Intent(ShopInfoActivity.this,MainActivity.class);
//                        //传递退出所有Activity的Tag对应的布尔值为true
//                        intent2.putExtra(MainActivity.EXIST, true);
//                        //启动BaseActivity
//                        startActivity(intent2);
                    }
                });
                //编辑店家资质信息
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ShopInfoActivity.this,Shop2Activity.class);
                        startActivity(intent);
//                        Intent intent2 = new Intent(ShopInfoActivity.this,MainActivity.class);
//                        //传递退出所有Activity的Tag对应的布尔值为true
//                        intent2.putExtra(MainActivity.EXIST, true);
//                        //启动BaseActivity
//                        startActivity(intent2);
                    }
                });
                dialog.show();
            }
        });
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
                this.finish();
            }
        }
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
                ShopInfoActivity.this.finish();
            }
        });
    }
}
