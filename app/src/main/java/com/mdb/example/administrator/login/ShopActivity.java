package com.mdb.example.administrator.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.BuildConfig;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.PhotoUtils;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.TimeUtil;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.Utils.UiUtils;
import com.mdb.example.administrator.Utils.Utils2;
import com.mdb.example.administrator.bean.TongyongBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * 店铺信息上传
 */
public class ShopActivity extends BaseActivity {
    String status;
    @BindView(R.id.checkbox1)
    CheckBox cb1;
    @BindView(R.id.checkbox2)
    CheckBox cb2;
    @BindView(R.id.checkbox3)
    CheckBox cb3;
    @BindView(R.id.checkbox4)
    CheckBox cb4;
    @BindView(R.id.checkbox5)
    CheckBox cb5;
    @BindView(R.id.checkbox6)
    CheckBox cb6;
    SharedPreferencesHelper sharehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(ShopActivity.this,"xicheshop");
//        status = "1";
        setTtitle();
        fanhui();
        setinfo();
        setImagesckick();
    }

    @BindView(R.id.shop_time_star)
    TextView tv_star;
    @BindView(R.id.shop_time_end)
    TextView tv_end;
    @BindView(R.id.shopup_next)
    Button btn_next;
    @BindView(R.id.shop_phone)
    EditText edt_phone;
    @BindView(R.id.shop_img1)
    ImageView img1s;
    @BindView(R.id.shop_img2)
    ImageView img2s;
    @BindView(R.id.shop_img3)
    ImageView img3s;
    @BindView(R.id.shop_img4)
    ImageView img4s;
    @BindView(R.id.shop_img5)
    ImageView img5s;
    @BindView(R.id.shop_img6)
    ImageView img6s;
    int zhi=0; //上传照片使用
    private void setinfo() {
        //开始时间
        tv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_star);
            }
        });
        //结束时间
        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_end);
            }
        });
        //下一步
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils2.isFastClick()==true){
                    //保存店铺经营范围
                    String fanwei="";
                    //6个复选框是否选中
                    if (cb1.isChecked()){
                        fanwei+="洗车,";
                    }
                    if (cb2.isChecked()){
                        fanwei+="抛光,";
                    }
                    if (cb3.isChecked()){
                        fanwei+="打蜡,";
                    }
                    if (cb4.isChecked()){
                        fanwei+="喷漆,";
                    }
                    if (cb5.isChecked()){
                        fanwei+="钣金,";
                    }
                    if (cb6.isChecked()){
                        fanwei+="汽修,";
                    }


                    if (fanwei==null || "".equals(fanwei)){
                        Toast.makeText(ShopActivity.this, "请至少选择一项经营范围", Toast.LENGTH_SHORT).show();
                    }else{
                        if (TextUtils.isEmpty(edt_phone.getText().toString())){
                            ToastUtils.shortToast("请输入店铺联系方式");
                        }else{
                            if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                                ToastUtils.shortToast("输入的联系方式不正确");
                            }else{
                                if (zhi==0){
                                    setupinfo(fanwei);
                                }else{
                                    final String fanwei1 = fanwei;
                                    setupinfo(fanwei1);

                                }

                            }
                        }

                    }
                }

            }
        });

    }

    /***
     * 提交服务器新
     *
     *  */
    private void setupinfo(final String fanwei){
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(ShopActivity.this)
                .setMessage("信息上传中...")
                .setCancelable(false)
                .setCancelOutside(false);
        final LoadingDailog dialog=loadBuilder.create();
        dialog.show();

//        Log.e("上传信息","ID值"+sharehelper.getSharedPreference("shopid","").toString()+
//        "开始时间"+formatter.format(curDate)+" "+tv_star.getText().toString()+":00"+
//        "结束时间"+formatter.format(curDate)+" "+tv_end.getText().toString()+":00"+
//                "手机号"+edt_phone.getText().toString()+
//                "范围"+fanwei+
//                "店铺图片"+zhengmian+"状态"+sharehelper.getSharedPreference("shopstatus","").toString());

        Handler handler = new Handler();
        if (zhi<4){

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
                    Date curDate =  new Date(System.currentTimeMillis());
                    //上传信息
                    OkGo.post(Api.zizhiup)
                            .tag(this)
                            .params("shopId",sharehelper.getSharedPreference("shopid","").toString())
                            .params("startTime",formatter.format(curDate)+" "+tv_star.getText().toString()+":00")
                            .params("endTime",formatter.format(curDate)+" "+tv_end.getText().toString()+":00")
                            .params("tel",edt_phone.getText().toString())
                            .params("businessline",fanwei)
                            .params("shopLogo",zhengmian)
                            .params("status",getIntent().getStringExtra("status"))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                    if (tongyongBean.getState()==1){

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                Intent intent = new Intent(ShopActivity.this,BankActivity.class);
                                                startActivity(intent);
                                                ShopActivity.this.finish();
                                            }
                                        },2000);

                                    }else {
                                        ToastUtils.shortToast("信息上传失败");
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    dialog.dismiss();
                                    ToastUtils.longToast("服务器相应失败，请稍后重试");

                                }
                            });
                }
            },3000);
        }else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
                    Date curDate =  new Date(System.currentTimeMillis());
                    //上传信息
                    OkGo.post(Api.zizhiup)
                            .tag(this)
                            .params("shopId",sharehelper.getSharedPreference("shopid","").toString())
                            .params("startTime",formatter.format(curDate)+" "+tv_star.getText().toString()+":00")
                            .params("endTime",formatter.format(curDate)+" "+tv_end.getText().toString()+":00")
                            .params("tel",edt_phone.getText().toString())
                            .params("businessline",fanwei)
                            .params("shopLogo",zhengmian)
                            .params("status",getIntent().getStringExtra("status"))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                    if (tongyongBean.getState()==1){

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                Intent intent = new Intent(ShopActivity.this,BankActivity.class);
                                                startActivity(intent);
                                                ShopActivity.this.finish();
                                            }
                                        },2000);

                                    }else {
                                        ToastUtils.shortToast("信息上传失败");
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    dialog.dismiss();
                                    ToastUtils.longToast("服务器相应失败，请稍后重试");

                                }
                            });
                }
            },5000);
        }

    }




    //时间选择器
    private void showTimer(final TextView editText){
        //     TimePickerView 同样有上面设置样式的方法
        TimePickerView mTimePickerView = new TimePickerView(ShopActivity.this, TimePickerView.Type.HOURS_MINS);// 四种选择模式，年月日时分，年月日，时分，月日时分
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
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINA);
//                Toast.makeText(context, format.format(date), Toast.LENGTH_SHORT).show();
                editText.setText(format.format(date));
            }
        });
        mTimePickerView.show();
    }

    /**
     * 上传图片的点击事件
     */
    int a=0;
    private void setImagesckick(){
        //第一个图
        img1s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShopActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShopActivity.this);
                final View DialogView = inflater .inflate ( R.layout.headimg, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //拍照
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=1;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=1;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //第2个图
        img2s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShopActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShopActivity.this);
                final View DialogView = inflater .inflate ( R.layout.headimg, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //拍照
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=2;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=2;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //第3个图
        img3s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShopActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShopActivity.this);
                final View DialogView = inflater .inflate ( R.layout.headimg, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //拍照
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=3;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=3;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //第4个图
        img4s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShopActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShopActivity.this);
                final View DialogView = inflater .inflate ( R.layout.headimg, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //拍照
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=4;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=4;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //第5个图
        img5s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShopActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShopActivity.this);
                final View DialogView = inflater .inflate ( R.layout.headimg, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //拍照
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=5;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=5;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //第6个图
        img6s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ShopActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ShopActivity.this);
                final View DialogView = inflater .inflate ( R.layout.headimg, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.headimg_quxiao);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.headimg_paizhao);//自定义控件
                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                //点击取消
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //拍照
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=6;
                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=6;
                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
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
        ttitle.setText("店铺信息上传");
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
                Intent intent = new Intent(ShopActivity.this,ShenfenyanzhengActivity.class);
                intent.putExtra("status",status);
                startActivity(intent);
                ShopActivity.this.finish();
            }
        });
    }


//    private static final int CODE_GALLERY_REQUEST = 0xa0;
//    private static final int CODE_CAMERA_REQUEST = 0xa1;
//    private static final int CODE_RESULT_REQUEST = 0xa2;
//    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
//    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
//    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
//    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
//    private Uri imageUri;
//    private Uri cropImageUri,show1,show2,show3,show4,show5,show6;
//    Uri[] shuzu = new Uri[6];
//    /**
//     * 自动获取相机权限
//     */
//    private void autoObtainCameraPermission() {
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                ToastUtils.shortToast( "您已经拒绝过一次");
//            }
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
//        } else {//有权限直接调用系统相机拍照
//            if (hasSdcard()) {
//                imageUri = Uri.fromFile(fileUri);
//                //通过FileProvider创建一个content类型的Uri
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    imageUri = FileProvider.getUriForFile(ShopActivity.this, "com.zz.fileprovider", fileUri);
//                }
//                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
//            } else {
//                ToastUtils.shortToast(  "设备没有SD卡！");
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            //调用系统相机申请拍照权限回调
//            case CAMERA_PERMISSIONS_REQUEST_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (hasSdcard()) {
//                        imageUri = Uri.fromFile(fileUri);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                            imageUri = FileProvider.getUriForFile(ShopActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
//                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
//                    } else {
//                        ToastUtils.shortToast( "设备没有SD卡！");
//                    }
//                } else {
//                    ToastUtils.shortToast( "请允许打开相机！！");
//                }
//                break;
//
//
//            }
//            //调用系统相册申请Sdcard权限回调
//            case STORAGE_PERMISSIONS_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
//                } else {
//                    ToastUtils.shortToast( "请允许打操作SDCard！！");
//                }
//                break;
//            default:
//        }
//    }
//
//    private static final int OUTPUT_X = 1000;
//    private static final int OUTPUT_Y = 1000;
//    private int a=0;
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                //拍照完成回调
//                case CODE_CAMERA_REQUEST:
//                    cropImageUri = Uri.fromFile(fileCropUri);
//                    Log.e("显示",cropImageUri+"");
//                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1000, 1000, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
//                    break;
//                //访问相册完成回调
//                case CODE_GALLERY_REQUEST:
//                    if (hasSdcard()) {
//                        cropImageUri = Uri.fromFile(fileCropUri);
//                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
//                        }
//                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
//                    } else {
//                        ToastUtils.shortToast( "设备没有SD卡！");
//                    }
//                    break;
//                case CODE_RESULT_REQUEST:
//                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
//                    if (bitmap != null) {
//                        if (a==1){
//                            beginupload(cropImageUri,a);
//                            show1 = cropImageUri;
//                            showImages(bitmap,a);
//                        }else if (a==2){
//                            beginupload(cropImageUri,a);
//                            show2 = cropImageUri;
//                            showImages(bitmap,a);
//                        }else if (a==3){
//                            beginupload(cropImageUri,a);
//                            show3 = cropImageUri;
//                            showImages(bitmap,a);
//                        }else if (a==4){
//                            beginupload(cropImageUri,a);
//                            show4 = cropImageUri;
//                            showImages(bitmap,a);
//                        }else if (a==5){
//                            beginupload(cropImageUri,a);
//                            show5 = cropImageUri;
//                            showImages(bitmap,a);
//                        }else if (a==6){
//                            beginupload(cropImageUri,a);
//                            show6 = cropImageUri;
//                            showImages(bitmap,a);
//                        }
//
//                    }
//                    break;
//                default:
//            }
//        }
//    }
//
//
//    /**
//     * 自动获取sdk权限
//     */
//
//    private void autoObtainStoragePermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
//        } else {
//            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
//        }
//
//    }
//
//    private void showImages(Bitmap bitmap,int b) {
//        if (b==1){
//            zhi = 1;
//            img1s.setImageBitmap(bitmap);
//            img2s.setVisibility(View.VISIBLE);
//        }else if(b==2){
//            zhi = 2;
//            img2s.setImageBitmap(bitmap);
//            img3s.setVisibility(View.VISIBLE);
//        }else if(b==3){
//            zhi = 3;
//            img3s.setImageBitmap(bitmap);
//            img4s.setVisibility(View.VISIBLE);
//        }else if(b==4){
//            zhi = 4;
//            img4s.setImageBitmap(bitmap);
//            img5s.setVisibility(View.VISIBLE);
//        }else if(b==5){
//            zhi = 5;
//            img5s.setImageBitmap(bitmap);
//            img6s.setVisibility(View.VISIBLE);
//        }else if(b==6){
//            zhi = 6;
//            img6s.setImageBitmap(bitmap);
//        }
//
//
//    }
//
//    /**
//     * 检查设备是否存在SDCard的工具方法
//     */
//    public static boolean hasSdcard() {
//        String state = Environment.getExternalStorageState();
//        return state.equals(Environment.MEDIA_MOUNTED);
//    }

    //打开相机的返回码
    private static final int CAMERA_REQUEST_CODE = 1;
    //选择图片的返回码
    private static final int IMAGE_REQUEST_CODE = 2;
    //剪切图片的返回码
    public static final int CROP_REREQUEST_CODE = 3;
//    private ImageView iv;

    //相机
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 100;

    public static final int REQUEST_CODE_PERMISSION_GALLERY = 101;

    //照片图片名
    private String photo_image;
    //截图图片名
    private String crop_image;

    //拍摄的图片的真实路径
    private String takePath;
    //拍摄的图片的虚拟路径
    private Uri imageUri;
    private Uri cropUri;
    //    private File tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    /**
     * 拍照
     *
     * @param view
     */
    public void onClickTakePhoto(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(REQUEST_CODE_PERMISSION_CAMERA);
            return;
        }
        openCamera();
    }

    private void openCamera() {
        if (isSdCardExist()) {
            Intent cameraIntent = new Intent(
                    "android.media.action.IMAGE_CAPTURE");

            photo_image = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date()) + ".jpg";
            imageUri = getImageUri(photo_image);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT
                    , imageUri);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 打开图库
     * 不需要用FileProvider
     *
     * @param view
     */
    public void onClickOpenGallery(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(REQUEST_CODE_PERMISSION_GALLERY);
            return;
        }
        openGallery();
    }

    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    /**
     * @param path 原始图片的路径
     */
    public void cropPhoto(String path) {
        crop_image = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date()) + "_crop" +
                ".jpg";
        File cropFile = createFile(crop_image);
        File file = new File(path);


        Intent intent = new Intent("com.android.camera.action.CROP");
        //TODO:访问相册需要被限制，需要通过FileProvider创建一个content类型的Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //TODO:访问相册需要被限制，需要通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider", file);
            cropUri = Uri.fromFile(cropFile);
            //TODO:cropUri 是裁剪以后的图片保存的地方。也就是我们要写入此Uri.故不需要用FileProvider
            //cropUri = FileProvider.getUriForFile(getApplicationContext(),
            //    BuildConfig.APPLICATION_ID + ".provider", cropFile);
        } else {
            imageUri = Uri.fromFile(file);
            cropUri = Uri.fromFile(cropFile);
        }

        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
//        //设置宽高比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        //设置裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("scale", true);
        //裁剪成功以后保存的位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_REREQUEST_CODE);


    }


    /**
     * 获得一个uri。该uri就是将要拍摄的照片的uri
     *
     * @return
     */
    private Uri getImageUri(String name) {
        if (isSdCardExist()) {
            File file = createFile(name);
            if (file != null) {
                takePath = file.getAbsolutePath();
                Log.e("zmm", "图片的路径---》" + takePath);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return FileProvider.getUriForFile(getApplicationContext(),
                            BuildConfig.APPLICATION_ID + ".provider", file);
                } else {
                    return Uri.fromFile(file);
                }

            }
        }
        return Uri.EMPTY;
    }

    public File createFile(String name) {
        if (isSdCardExist()) {
            File[] dirs = ContextCompat.getExternalFilesDirs(this, null);
            if (dirs != null && dirs.length > 0) {
                File dir = dirs[0];
                return new File(dir, name);
            }
        }

        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE://拍照成功并且返回
                    Log.e("zmm", "选择的图片的虚拟地址是------------>" +takePath);
                    cropPhoto(takePath);
//                    cropPhoto(getMediaUriFromPath(this,takePath),false);
                    break;

                case IMAGE_REQUEST_CODE://选择图片成功返回
                    if (data != null && data.getData() != null) {
                        imageUri = data.getData();

                        cropPhoto(imageUri,true);
                    }
                    break;
                case CROP_REREQUEST_CODE:
                    Log.e("zmm", "裁剪以后的地址是------------>" + cropUri);
//                    shangchaunImg  = cropUri;
                    decodeImage(cropUri,a);
                    break;
            }
        }
    }
    // 图片裁剪
    private void cropPhoto(Uri uri, boolean fromCapture) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);

//        // 设置裁剪区域的宽高比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);

        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);

        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 指定裁剪完成以后的图片所保存的位置,pic info显示有延时
        if (fromCapture) {
            // 如果是使用拍照，那么原先的uri和最终目标的uri一致
            cropUri = uri;
        } else { // 从相册中选择，那么裁剪的图片保存在take_photo中
            String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
            String fileName = "photo_" + time;
            File mCutFile = new File(Environment.getExternalStorageDirectory() + "/take_photo", fileName + ".jpeg");
            if (!mCutFile.getParentFile().exists()) {
                mCutFile.getParentFile().mkdirs();
            }
            cropUri = getUriForFile(this, mCutFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        Toast.makeText(this, "剪裁图片", Toast.LENGTH_SHORT).show();
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(uri);
        this.sendBroadcast(intentBc);

        startActivityForResult(intent, CROP_REREQUEST_CODE); //设置裁剪参数显示图片至ImageVie
    }
    // 从file中获取uri
    // 7.0及以上使用的uri是contentProvider content://com.rain.takephotodemo.FileProvider/images/photo_20180824173621.jpg
    // 6.0使用的uri为file:///storage/emulated/0/take_photo/photo_20180824171132.jpg
    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(),  BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
    /**
     * 根据uri拿到bitmap
     *
     * @param imageUri 这个Uri是
     */
    private void decodeImage(Uri imageUri,int b) {
        try {
            Bitmap bitmapFormUri = getBitmapFormUri(this, imageUri);
        if (b==1){
            zhi = 1;
            img1s.setImageBitmap(bitmapFormUri);
            beginupload(imageUri);
            img2s.setVisibility(View.VISIBLE);
        }else if(b==2){
            zhi = 2;
            img2s.setImageBitmap(bitmapFormUri);
            beginupload(imageUri);
            img3s.setVisibility(View.VISIBLE);
        }else if(b==3){
            zhi = 3;
            img3s.setImageBitmap(bitmapFormUri);
            beginupload(imageUri);
            img4s.setVisibility(View.VISIBLE);
        }else if(b==4){
            zhi = 4;
            img4s.setImageBitmap(bitmapFormUri);
            beginupload(imageUri);
            img5s.setVisibility(View.VISIBLE);
        }else if(b==5){
            zhi = 5;
            img5s.setImageBitmap(bitmapFormUri);
            beginupload(imageUri);
            img6s.setVisibility(View.VISIBLE);
        }else if(b==6){
            zhi = 6;
            beginupload(imageUri);
            img6s.setImageBitmap(bitmapFormUri);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    /**
     * 检查权限
     *
     * @param requestCode
     */
    private void checkPermission(int requestCode) {

        boolean granted = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission_group.CAMERA);
        if (granted) {//有权限
            if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
                openCamera();//打开相机
            } else {
                openGallery();//打开图库
            }
            return;
        }
        //没有权限的要去申请权限
        //注意：如果是在Fragment中申请权限，不要使用ActivityCompat.requestPermissions,
        // 直接使用Fragment的requestPermissions方法，否则会回调到Activity的onRequestPermissionsResult
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest
                        .permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean flag = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PERMISSION_GRANTED) {
                    flag = false;
                    break;
                }
            }
            //权限通过以后。自动回调拍照
            if (flag) {
                if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
                    openCamera();//打开相机
                } else {
                    openGallery();//打开图库
                }
            } else {
                Toast.makeText(this, "请开启权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 检查SD卡是否存在
     */
    public boolean isSdCardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }



    private String zhengmian="",fanmian,zhiye;
    public void beginupload( Uri bitmap) {

        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String startpoint = "washcarimg";
        //     明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI8ygujYgDvLJ9", "nLrO1bpn9IOpEu0tt0zyAaChc22j0c");
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        //通过填写文件名形成objectname,通过这个名字指定上传和下载的文件
        // 构造上传请求
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        final String objectname =df.format(new Date())+ ".png";

        PutObjectRequest put = new PutObjectRequest(startpoint, objectname, UiUtils.getImageAbsolutePath(ShopActivity.this,bitmap) );
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                Log.e("测试图片1","https://jiaoyuvideo.oss-cn-beijing.aliyuncs.com/"+objectname);
                zhengmian=zhengmian+objectname+",";
//                setinfo();
                zhi--;
                Log.e("测试",zhengmian);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                }
            }
        });


    }


}
