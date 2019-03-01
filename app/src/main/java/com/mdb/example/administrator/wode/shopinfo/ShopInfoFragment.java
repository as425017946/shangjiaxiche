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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.CircleImageView;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.bean.ShopInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * 商家信息
 */
public class ShopInfoFragment extends Fragment {
    Context context;
    SharedPreferencesHelper sharehelper;
    @BindView(R.id.shop_time_star)
    TextView tv_star;
    @BindView(R.id.shop_time_end)
    TextView tv_end;
    @BindView(R.id.shop_fanwei)
    TextView tv_fanwei;
    @BindView(R.id.shop_phone)
    EditText edt_phone;
    @BindView(R.id.shop_img1)
    ImageView img1;
    @BindView(R.id.shop_img2)
    ImageView img2;
    @BindView(R.id.shop_img3)
    ImageView img3;
    @BindView(R.id.shop_img4)
    ImageView img4;
    @BindView(R.id.shop_img5)
    ImageView img5;
    @BindView(R.id.shop_img6)
    ImageView img6;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_info, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharehelper = new SharedPreferencesHelper(context,"xicheshop");
        showinfo();
    }

    /**
     * 展示信息
     */
    private void showinfo() {
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        img6.setVisibility(View.GONE);
        OkGo.post(Api.selectshopinfo)
                .tag(this)
                .params("shopId",sharehelper.getSharedPreference("shopid","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("查询店铺信息",s);
                        Gson gson = new Gson();
                        ShopInfoBean shopInfoBean = gson.fromJson(s,ShopInfoBean.class);
                        if (shopInfoBean.getState()==1){
                            tv_star.setText(shopInfoBean.getData().getStart_time());
                            tv_end.setText(shopInfoBean.getData().getEnd_time());
                            String[]  shuzu = null;
                            String[]  shuzu2 = null;
                            String a = "" , b = "";
                            //店铺经营范围是否添加
                            if (shopInfoBean.getData().getBusinessline()==null || shopInfoBean.getData().getBusinessline().contains(",")==false){

                            }else{
                                shuzu = shopInfoBean.getData().getBusinessline().split(",");
                                for (int i = 0; i <shuzu.length ; i++) {
                                    if ((i+1)==shuzu.length){
                                        a +=shuzu[i];
                                    }else {
                                        a +=shuzu[i]+"、";
                                    }

//                                    if (shuzu[i].equals("1")){
//                                        a +="洗车  ";
//                                    }
//                                    if (shuzu[i].equals("2")){
//                                        a +="抛光  ";
//                                    }
//                                    if (shuzu[i].equals("3")){
//                                        a +="打蜡  ";
//                                    }
//                                    if (shuzu[i].equals("4")){
//                                        a +="喷漆  ";
//                                    }
//                                    if (shuzu[i].equals("5")){
//                                        a +="钣金  ";
//                                    }
//                                    if (shuzu[i].equals("6")){
//                                        a +="汽修  ";
//                                    }
                                    tv_fanwei.setText(a);
                                }

                            }

                            //店铺照片是否存在
                            if (shopInfoBean.getData().getShop_logo().contains(",")==false){

                            }else{
                                //店铺信息
                                shuzu2 = shopInfoBean.getData().getShop_logo().split(",");
                                if (shuzu2.length>0){
                                    img1.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shuzu2[0]).into(img1);
                                }
                                if (shuzu2.length>1){
                                    img2.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shuzu2[1]).into(img2);
                                }
                                if (shuzu2.length>2){
                                    img3.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shuzu2[2]).into(img3);

                                }
                                if (shuzu2.length>3){
                                    img4.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shuzu2[3]).into(img4);

                                }
                                if (shuzu2.length>4){
                                    img5.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shuzu2[4]).into(img5);
                                }
                                if (shuzu2.length>5){
                                    img6.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(Api.ossUrl+shuzu2[5]).into(img6);
                                }

                            }

                            edt_phone.setText(shopInfoBean.getData().getTel());

                        }else {

                        }
                    }
                });
    }
}
