package com.mdb.example.administrator.wode.shopinfo;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.bean.ShopZizhiBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * 商家资质上传
 */
public class ShopZizhiFragment extends Fragment {
    Context context;
    SharedPreferencesHelper sharehelper;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_zizhi, container, false);
        ButterKnife.bind(this,view);
        return view;

    }

    @BindView(R.id.sfyz_shangjianame)
    EditText edt_shangjianame;
    @BindView(R.id.sfyz_shangjiadizhi)
    TextView edt_dizhi;
    @BindView(R.id.sfyz_name)
    EditText edt_name;
    @BindView(R.id.sfyz_phone)
    EditText edt_phone;
    @BindView(R.id.sfyz_yewulianxiren)
    EditText edt_lianxiren;
    @BindView(R.id.sfyz_weixinhao)
    EditText edt_youxiang;
    @BindView(R.id.zizhi_zhengmian)
    ImageView img_zhengmian;
    @BindView(R.id.zizhi_fanmian)
    ImageView img_fanmian;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharehelper = new SharedPreferencesHelper(context,"xicheshop");
        selectInfo();
    }

    /**
     * 查询消息
     */
    private void selectInfo() {
        OkGo.post(Api.selectshopzizhi)
                .tag(this)
                .params("shopId",sharehelper.getSharedPreference("shopid","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("商家资质",s);
                        Gson gson = new Gson();
                        ShopZizhiBean shopZizhiBean = gson.fromJson(s,ShopZizhiBean.class);
                        if (shopZizhiBean.getState()==1){
                            edt_shangjianame.setText(shopZizhiBean.getData().getShop_name()+"");
                            edt_dizhi.setText(shopZizhiBean.getData().getShop_addr()+"");
                            edt_name.setText(shopZizhiBean.getData().getOwner_name()+"");
                            edt_phone.setText(shopZizhiBean.getData().getOwner_phone()+"");
                            edt_lianxiren.setText(shopZizhiBean.getData().getManager_name()+"");
                            edt_youxiang.setText(shopZizhiBean.getData().getMail()+"");
                            //是否添加了营业执照
                            if (shopZizhiBean.getData().getCompany_img()==null || shopZizhiBean.getData().getCompany_img().contains(",")==false){

                            }else{
                                String shu[] = shopZizhiBean.getData().getCompany_img().split(",");
                                if (shu.length==0){
                                    img_zhengmian.setVisibility(View.GONE);
                                    img_fanmian.setVisibility(View.GONE);
                                }
                                if (shu.length>0){
                                    img_zhengmian.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shu[0]).into(img_zhengmian);
                                    img_fanmian.setVisibility(View.GONE);
                                }
                                if (shu.length>1){
                                    img_fanmian.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shu[1]).into(img_fanmian);
                                }
                            }

                        }else {
                            ToastUtils.shortToast(shopZizhiBean.getMessage());
                        }

                    }
                });
    }
}
