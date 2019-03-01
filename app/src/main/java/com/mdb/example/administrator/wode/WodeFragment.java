package com.mdb.example.administrator.wode;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.wode.order.OrdersActivity;
import com.mdb.example.administrator.wode.shouyi.ShouyiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * A simple {@link Fragment} subclass.
 * 我的页面
 */
public class WodeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wode2, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    SharedPreferencesHelper share;
    @BindView(R.id.weishenhe)
    TextView tv_weishenhe;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        share = new SharedPreferencesHelper(context,"xicheshop");
        if (share.getSharedPreference("shopstatus","").toString().equals("1")){
            tv_weishenhe.setVisibility(View.VISIBLE);
            fanhui();
        }else {
            tv_weishenhe.setVisibility(View.GONE);
            setTtitle();
            fanhui();
            setinfo();
        }

    }
    @BindView(R.id.my_shangjiainfo)
    LinearLayout l_info;
    @BindView(R.id.my_orders)
    LinearLayout l_orders;
    @BindView(R.id.my_dianpushouyi)
    LinearLayout l_shouyi;
    @BindView(R.id.my_dianpupingjia)
    LinearLayout l_pingjia;
    @BindView(R.id.my_fuwuguanli)
    LinearLayout l_fuwuguanli;
    @BindView(R.id.my_youhuiguanli)
    LinearLayout l_youhuiguanli;
    @BindView(R.id.my_erweima)
    LinearLayout l_erweima;
    @BindView(R.id.my_shezhi)
    LinearLayout l_shezhi;
    private void setinfo() {
        l_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShopInfoActivity.class);
                startActivity(intent);
            }
        });
        l_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrdersActivity.class);
                intent.putExtra("orderzhi","1");
                startActivity(intent);
            }
        });
        l_shouyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShouyiActivity.class);
                startActivity(intent);
            }
        });
        l_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DianpupingfenActivity.class);
                startActivity(intent);
            }
        });
        l_fuwuguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ServiceActivity.class);
                startActivity(intent);
            }
        });
        l_youhuiguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,YouhuiManager2Activity.class);
                startActivity(intent);
            }
        });
        l_erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShopErweimaActivity.class);
                startActivity(intent);
            }
        });
        l_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SettingActivity.class);
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
        ttitle.setText("我的");
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
    }

//    /**
//     *提交信息
//     */
//    @BindView(R.id.mine_bianji)
//    ImageView img_bianji;
//    @BindView(R.id.mine_order)
//    LinearLayout ll_order;
//    @BindView(R.id.mine_quanbu)
//    LinearLayout ll_quanbu;
//    @BindView(R.id.mine_daifuwu)
//    LinearLayout ll_daifuwu;
//    @BindView(R.id.mine_yiwancheng)
//    LinearLayout ll_yiwancheng;
//    @BindView(R.id.mine_yiquxiao)
//    LinearLayout ll_yiquxiao;
//    @BindView(R.id.mine_dianpuquanyi)
//    LinearLayout ll_quanyi;
//    @BindView(R.id.mine_dianpupingfen)
//    LinearLayout ll_pingfen;
//    @BindView(R.id.mine_shezhi)
//    LinearLayout ll_shehzi;
//    @BindView(R.id.mine_fuwuguanli)
//    LinearLayout l_fuwuguanli;
//    private void setinfo(){
//        img_bianji.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, EditActivity.class);
//                startActivity(intent);
//            }
//        });
//        ll_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, OrdersActivity.class);
//                intent.putExtra("orderzhi","1");
//                startActivity(intent);
//            }
//        });
//        ll_quanbu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, OrdersActivity.class);
//                intent.putExtra("orderzhi","1");
//                startActivity(intent);
//            }
//        });
//        ll_daifuwu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, OrdersActivity.class);
//                intent.putExtra("orderzhi","2");
//                startActivity(intent);
//            }
//        });
//        ll_yiwancheng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, OrdersActivity.class);
//                intent.putExtra("orderzhi","3");
//                startActivity(intent);
//            }
//        });
//        ll_yiquxiao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, OrdersActivity.class);
//                intent.putExtra("orderzhi","4");
//                startActivity(intent);
//            }
//        });
//        ll_quanyi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ShouyiActivity.class);
//                startActivity(intent);
//            }
//        });
//        //评分
//        ll_pingfen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent  = new Intent(context,DianpupingfenActivity.class);
//                startActivity(intent);
//            }
//        });
//        //设置
//        ll_shehzi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,SettingActivity.class);
////                startActivity(intent);
//            }
//        });
//        //服务管理
//        l_fuwuguanli.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,ServiceActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}
