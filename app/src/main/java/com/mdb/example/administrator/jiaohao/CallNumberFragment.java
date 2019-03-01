package com.mdb.example.administrator.jiaohao;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.bean.JiaohaoShowBean;
import com.mdb.example.administrator.bean.LoginBean;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.fuwu.MessagesActivity;
import com.mdb.example.administrator.fuwu.MessagesBianjiActivity;
import com.mdb.example.administrator.fuwu.ReleaseProjectActivity;
import com.mdb.example.administrator.fuwu.ReleaseProjectOkActivity;
import com.mdb.example.administrator.login.ShopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.mdb.example.administrator.MyApplication.registrationID;

/**
 * 叫号页面
 */
public class CallNumberFragment extends Fragment {
    SharedPreferencesHelper sharehelper;
    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_number, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @BindView(R.id.weishenhe)
    TextView tv_weishenhe;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharehelper = new SharedPreferencesHelper(context,"xicheshop");
        if (sharehelper.getSharedPreference("shopstatus","").toString().equals("1")){
            tv_weishenhe.setVisibility(View.VISIBLE);
        }else {
            //进来后先判断用户的状态，如果是4直接使用，其他不能使用功能
            OkGo.post(Api.yzmSms)
                    .tag(this)
                    .params("mobile",sharehelper.getSharedPreference("shopphone","").toString())
                    .params("registrationId",registrationID)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            final Gson gson = new Gson();
                            final LoginBean loginBean = gson.fromJson(s,LoginBean.class);
                            if (loginBean.getState()==1){
                                sharehelper.put("shopstatus",loginBean.getData().getStatus());
                                if (sharehelper.getSharedPreference("shopstatus","").toString().equals("1")){
                                    tv_weishenhe.setVisibility(View.VISIBLE);
                                }else {

                                    tv_weishenhe.setVisibility(View.GONE);
                                    //判断是否开启了叫号功能
                                    if (TextUtils.isEmpty(sharehelper.getSharedPreference("waitnum_flag","").toString())){

                                        btn_next.setText("恢复叫号");
                                        l_zanting.setVisibility(View.VISIBLE);
                                        l_zhengchang.setVisibility(View.GONE);
                                    }else {

                                        setTtitle();
                                        fanhui();
                                        setinfo();
                                        setshow();
                                    }



                                }
                            }
                        }
                    });

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("叫号暂停","1");
        setshow();
    }

    /**
     * 展示叫号信息
     */
    @BindView(R.id.callnumber_1)
    TextView tv_1;
    @BindView(R.id.callnumber_2)
    TextView tv_2;
    @BindView(R.id.callnumber_3)
    TextView tv_3;
    private void setshow() {


        OkGo.post(Api.selectjiaohao)
                .tag(this)
                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("叫号",s);
                        Gson gson = new Gson();
                        final JiaohaoShowBean showBean = gson.fromJson(s,JiaohaoShowBean.class);
                        if (showBean.getState()==1){
                            if (showBean.getData().getNum()==0){
                                tv_1.setVisibility(View.GONE);
                                tv_3.setVisibility(View.INVISIBLE);
                                tv_2.setText("无人等候");
                                btn_next.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.huibuttons));
                            }else {
                                tv_1.setVisibility(View.VISIBLE);
                                tv_2.setVisibility(View.VISIBLE);
                                tv_2.setText(showBean.getData().getCurrentNo()+"号");
                                tv_3.setText("排队车辆数："+showBean.getData().getNum()+"辆");
                                btn_next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        OkGo.post(Api.xiayiliang)
                                                .tag(this)
                                                .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                                                .params("id",showBean.getData().getId())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(String s, Call call, Response response) {
                                                        Gson gson1 = new Gson();
                                                        TongyongBean tongyongBean = gson1.fromJson(s,TongyongBean.class);
                                                        if (tongyongBean.getState()==1){
                                                            setshow();
                                                        }else {
                                                            ToastUtils.shortToast(tongyongBean.getMessage());
                                                        }
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    }
                });
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_xiaoxi)
    ImageView img_xiaoxi;
    private void setTtitle(){
        ttitle.setText("叫号");
        img_xiaoxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MessagesBianjiActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 扫描二维码
     */
    @BindView(R.id.xiche_saomiao)
    ImageView lsaomiao;
    private void fanhui(){
        lsaomiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SaomiaoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 写入信息
     */
    @BindView(R.id.callnumber_stop)
    Button btn_stop;
    @BindView(R.id.callnumber_zhengchang)
    LinearLayout l_zhengchang;
    @BindView(R.id.callnumber_zanting)
    LinearLayout l_zanting;
    @BindView(R.id.callnumber_next)
    Button btn_next;
    private void setinfo(){
        //暂停叫号
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkGo.post(Api.kaiOguanbi)
                        .tag(this)
                        .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                        .params("waitnum_flag",0)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Gson gson = new Gson();
                                TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                if (tongyongBean.getState()==1){
                                    btn_next.setText("恢复叫号");
                                    l_zanting.setVisibility(View.VISIBLE);
                                    l_zhengchang.setVisibility(View.GONE);
                                }else
                                {
                                    ToastUtils.shortToast("关闭失败，请稍后重试");
                                }
                            }
                        });
            }
        });

        //下一辆或者恢复叫号
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_next.getText().equals("下一辆")){

                }else{
                    OkGo.post(Api.kaiOguanbi)
                            .tag(this)
                            .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                            .params("waitnum_flag",0)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                    if (tongyongBean.getState()==1){
                                        btn_next.setText("下一辆");
                                        l_zanting.setVisibility(View.GONE);
                                        l_zhengchang.setVisibility(View.VISIBLE);
                                    }else
                                    {
                                        ToastUtils.shortToast("开启失败，请稍后重试");
                                    }
                                }
                            });

                }

            }
        });


    }
}
