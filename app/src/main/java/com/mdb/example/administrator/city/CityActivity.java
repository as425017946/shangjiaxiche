package com.mdb.example.administrator.city;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.mdb.example.administrator.MainActivity;
import com.mdb.example.administrator.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {
    private TextView mTvAddress;
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectHomeDialog();
            }
        });
    }

    private CityPicker mCityPicker;

    //地址选择
    private void showSelectHomeDialog() {
        if (mCityPicker == null) {
            //如果城市对象为空则进行添加
            mCityPicker = new CityPicker.Builder(this)
                    .title("选择地址")            //标题
                    .textSize(16)                       //文本大小
                    .titleBackgroundColor("#0C63D7")    //标题背景颜色
                    .onlyShowProvinceAndCity(true)      //显示省市县
                    .cancelTextColor("#ffffff")         //取消按钮字体颜色
                    .confirTextColor("#ffffff")         //确认按钮字体颜色
                    .province("天津市")                 //默认显示的省
                    .city("天津市")                     //默认显示的市
                    .district("塘沽区")                     //默认显示的区
                    .textColor(Color.parseColor("#333333"))//滚轮文字的颜色
                    .provinceCyclic(false)              //滚轮是否循环显示
                    .cityCyclic(false)
                    .districtCyclic(false)
                    .itemPadding(10)    //条目间距，默认5
                    .visibleItemsCount(7)   //滚轮条目显示个数
                    .onlyShowProvinceAndCity(false)
                    .build();

            //给地址设置条目点击的监听
            mCityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                @Override
                public void onSelected(String... citySelected) {
                    String province = citySelected[0];//省
                    String city = citySelected[1];//市   //区县（如果设定了两级联动，那么该项返回空）
                    String district = citySelected[2];
                    //邮编
                    String code = citySelected[3];
                    mTvAddress.setText(province + "-" + city + "-" + district);
                }

                @Override
                public void onCancel() {

                }
            });
        }
        mCityPicker.show();
    }


}
