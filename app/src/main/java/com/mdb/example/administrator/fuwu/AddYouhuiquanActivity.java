package com.mdb.example.administrator.fuwu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.MyDialog;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.Utils.UiUtils;
import com.mdb.example.administrator.Utils.Utils2;
import com.mdb.example.administrator.bean.TongyongBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 *添加优惠券
 */
public class AddYouhuiquanActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_youhuiquan);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(AddYouhuiquanActivity.this,"xicheshop");
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 写入信息
     */
    @BindView(R.id.add_time_star)
    TextView tv_star;
    @BindView(R.id.add_time_end)
    TextView tv_end;
    @BindView(R.id.fabu_spinner)
    Spinner spinner;
    String zhi="1";
    int status = 1;
    private void setinfo() {
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
//        tv_star.setText(formatter.format(curDate));
//        tv_end.setText(formatter.format(curDate));
        tv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_star);
            }
        });
        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_end);
            }
        });
//        List<String> list = new ArrayList<String>();
//        list.add("满减");
//        list.add("赠品");
//        list.add("代金券");
//        list.add("折扣");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
//        spinner.setAdapter(adapter);
        //下拉框信息展示
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().toString().equals("满减")){
                    zhi = "1";
                    status = 1;
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                    l3.setVisibility(View.GONE);
                    l4.setVisibility(View.GONE);
                }else if (spinner.getSelectedItem().toString().equals("赠品")){
                    zhi = "4";
                    status = 2;
                    l2.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.GONE);
                    l3.setVisibility(View.GONE);
                    l4.setVisibility(View.GONE);
                }if (spinner.getSelectedItem().toString().equals("代金券")){
                    zhi = "3";
                    status = 3;
                    l3.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                    l4.setVisibility(View.GONE);
                }if (spinner.getSelectedItem().toString().equals("折扣")){
                    zhi = "2";
                    status = 4;
                    l4.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                    l3.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
    @BindView(R.id.add_manjian)
    TextView tv_manjian;
    @BindView(R.id.add_zengzhi)
    TextView tv_zeng;

    @BindView(R.id.add_manjian2)
    TextView tv_manjian2;
    @BindView(R.id.add_zengzhi2)
    TextView tv_zeng2;

    @BindView(R.id.add_manjian3)
    TextView tv_manjian3;

    @BindView(R.id.add_manjian4)
    TextView tv_manjian4;

    @BindView(R.id.youhuiquan_show1)
    LinearLayout l1;
    @BindView(R.id.youhuiquan_show2)
    LinearLayout l2;
    @BindView(R.id.youhuiquan_show3)
    LinearLayout l3;
    @BindView(R.id.youhuiquan_show4)
    LinearLayout l4;


    @BindView(R.id.add_zhangshu)
    TextView tv_shu;
    private void setTtitle(){
        ttitle.setText("优惠信息");
        ttitle2.setText("发布");
        ttitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils2.isFastClick()==true){
                    if (TextUtils.isEmpty(tv_shu.getText().toString())){
                        ToastUtils.shortToast("请输入发布的信息");
                    }else{
                        if (tv_star.getText().toString().equals("开始时间") || tv_end.getText().toString().equals("结束时间")){
                            ToastUtils.shortToast("请选择使用有效期");
                        }else {
                            String d1 = tv_star.getText().toString().substring(0,4)+
                                    tv_star.getText().toString().substring(5,7)+
                                    tv_star.getText().toString().substring(8,10);
                            String d2 = tv_end.getText().toString().substring(0,4)+
                                    tv_end.getText().toString().substring(5,7)+
                                    tv_end.getText().toString().substring(8,10);
//                        Log.e("d1",d1);
//                        Log.e("d2",d2);
                            if (Integer.parseInt(d1)>Integer.parseInt(d2)){
                                ToastUtils.shortToast("开始时间不能大于结束时间");
                            }else{
                                MyDialog.show(AddYouhuiquanActivity.this, "确认发布优惠信息？", new MyDialog.OnConfirmListener() {
                                    @Override
                                    public void onConfirmClick() {

                                        //输入的时间正常
                                        if (status==1){
                                            OkGo.post(Api.addyouhuiquan)
                                                    .tag(this)
                                                    .params("couponType",zhi)
                                                    .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                                                    .params("shopName",sharehelper.getSharedPreference("shopname","").toString())
                                                    .params("item",tv_manjian.getText().toString())
                                                    .params("term",tv_zeng.getText().toString())
                                                    .params("starttime",tv_star.getText().toString()+" 00:00:00")
                                                    .params("endtime",tv_end.getText().toString()+" 23:59:59")
                                                    .params("num",tv_shu.getText().toString())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
                                                            Gson gson = new Gson();
                                                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                            if (tongyongBean.getState()==1){
                                                                Intent intent = new Intent(AddYouhuiquanActivity.this,AddyouhuiquanOKActivity.class);
                                                                startActivity(intent);
                                                                AddYouhuiquanActivity.this.finish();
                                                            }else {
                                                                ToastUtils.shortToast(tongyongBean.getMessage());
                                                            }
                                                        }
                                                    });
                                        }else if(status==2){
                                            OkGo.post(Api.addyouhuiquan)
                                                    .tag(this)
                                                    .params("couponType",zhi)
                                                    .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                                                    .params("shopName",sharehelper.getSharedPreference("shopname","").toString())
                                                    .params("item",tv_manjian2.getText().toString())
                                                    .params("freegoods",tv_zeng2.getText().toString())
                                                    .params("starttime",tv_star.getText().toString()+" 00:00:00")
                                                    .params("endtime",tv_end.getText().toString()+" 23:59:59")
                                                    .params("num",tv_shu.getText().toString())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
                                                            Log.e("赠品",s);
                                                            Gson gson = new Gson();
                                                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                            if (tongyongBean.getState()==1){
                                                                Intent intent = new Intent(AddYouhuiquanActivity.this,AddyouhuiquanOKActivity.class);
                                                                startActivity(intent);
                                                                AddYouhuiquanActivity.this.finish();
                                                            }else {
                                                                ToastUtils.shortToast(tongyongBean.getMessage());
                                                            }
                                                        }
                                                    });
                                        }else if(status==3){
                                            OkGo.post(Api.addyouhuiquan)
                                                    .tag(this)
                                                    .params("couponType",zhi)
                                                    .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                                                    .params("shopName",sharehelper.getSharedPreference("shopname","").toString())
                                                    .params("item",tv_manjian3.getText().toString())
                                                    .params("starttime",tv_star.getText().toString()+" 00:00:00")
                                                    .params("endtime",tv_end.getText().toString()+" 23:59:59")
                                                    .params("num",tv_shu.getText().toString())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
                                                            Gson gson = new Gson();
                                                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                            if (tongyongBean.getState()==1){
                                                                Intent intent = new Intent(AddYouhuiquanActivity.this,AddyouhuiquanOKActivity.class);
                                                                startActivity(intent);
                                                                AddYouhuiquanActivity.this.finish();
                                                            }else {
                                                                ToastUtils.shortToast(tongyongBean.getMessage());
                                                            }
                                                        }
                                                    });
                                        }else if(status==4){
                                            OkGo.post(Api.addyouhuiquan)
                                                    .tag(this)
                                                    .params("couponType",zhi)
                                                    .params("shopCode",sharehelper.getSharedPreference("shopcode","").toString())
                                                    .params("shopName",sharehelper.getSharedPreference("shopname","").toString())
                                                    .params("item",tv_manjian4.getText().toString())
                                                    .params("starttime",tv_star.getText().toString()+" 00:00:00")
                                                    .params("endtime",tv_end.getText().toString()+" 23:59:59")
                                                    .params("num",tv_shu.getText().toString())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
                                                            Gson gson = new Gson();
                                                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                            if (tongyongBean.getState()==1){
                                                                Intent intent = new Intent(AddYouhuiquanActivity.this,AddyouhuiquanOKActivity.class);
                                                                startActivity(intent);
                                                                AddYouhuiquanActivity.this.finish();
                                                            }else {
                                                                ToastUtils.shortToast(tongyongBean.getMessage());
                                                            }
                                                        }
                                                    });
                                        }


                                    }
                                });
                            }
                        }



                    }
                }else {
                    ToastUtils.shortToast("4秒内请勿多次点击发布");
                }


            }
        });
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
               AddYouhuiquanActivity.this.finish();
            }
        });
    }

    //时间选择器
    private void showTimer(final TextView editText){
        //     TimePickerView 同样有上面设置样式的方法
        TimePickerView mTimePickerView = new TimePickerView(AddYouhuiquanActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);// 四种选择模式，年月日时分，年月日，时分，月日时分
        // 设置是否循环
        mTimePickerView.setCyclic(true);

        // 设置滚轮文字大小
        mTimePickerView.setTextSize(TimePickerView.TextSize.BIG);
        // 设置时间可选范围(结合 setTime 方法使用,必须在)
//        Calendar calendar = Calendar.getInstance();
//        mTimePickerView.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));
        // 设置选中时间
        mTimePickerView.setTime(new Date());//设置选中的时间  new date（）是今天的时间
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//                Toast.makeText(context, format.format(date), Toast.LENGTH_SHORT).show();
                editText.setText(format.format(date));
                setinfo();
            }
        });
        mTimePickerView.show();
    }
}
