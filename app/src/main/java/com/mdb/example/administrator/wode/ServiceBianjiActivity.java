package com.mdb.example.administrator.wode;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.tu.loadingdialog.LoadingDailog;
import com.bumptech.glide.Glide;
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
import com.mdb.example.administrator.bean.ServiceMoreBean;
import com.mdb.example.administrator.bean.TongyongBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
/**
 * 编辑服务页面详情
 */
public class ServiceBianjiActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    String shopcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_bianji);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(ServiceBianjiActivity.this,"xicheshop");
        shopcode = sharehelper.getSharedPreference("shopcode","").toString();
        setTtitle();
        fanhui();
        setinfo();
        showinfo();
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(ServiceBianjiActivity.this)
                .setMessage("加载中...")
                .setCancelable(false)
                .setCancelOutside(false);
        final LoadingDailog dialog=loadBuilder.create();
        dialog.show();
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },1000);
    }



    @BindView(R.id.fabu_spinner)
    Spinner fabu_spinner;
    @BindView(R.id.fabu_suolvimg)
    ImageView img_suolv;
    @BindView(R.id.fabu_xiangqing)
    EditText edt_xiangqing;
    @BindView(R.id.shop_time_star)
    TextView tv_star;
    @BindView(R.id.shop_time_end)
    TextView tv_end;
    @BindView(R.id.fabu_chexing1)
    EditText edt_1;
    @BindView(R.id.fabu_chexing2)
    EditText edt_2;
    @BindView(R.id.fabu_chexing3)
    EditText edt_3;
    @BindView(R.id.fabu_shuoming1)
    EditText edt_shuoming1;
    @BindView(R.id.fabu_shuoming2)
    EditText edt_shuoming2;
    @BindView(R.id.fabu_shuoming3)
    EditText edt_shuoming3;
    @BindView(R.id.fabu_jiage1)
    EditText edt_jiage1;
    @BindView(R.id.fabu_jiage2)
    EditText edt_jiage2;
    @BindView(R.id.fabu_jiage3)
    EditText edt_jiage3;
    @BindView(R.id.fabu_yuyue1)
    ImageView img_yuyue1;
    @BindView(R.id.fabu_yuyue2)
    ImageView img_yuyue2;
    @BindView(R.id.fabu_yuyuegongneng)
    LinearLayout l_yuyuegongneng;
    @BindView(R.id.fabu_jiange)
    EditText edt_jiange;
    @BindView(R.id.fabu_liang)
    EditText edt_cheliang;
    String spinnerzhi="普洗",isyuyue="0",cartype="",typename="",typedetail="",typeprice="",servicesDetailId="",timejiange="",cheliang="";
    int showzhi = 0 ,status = 0;
    int showimgs = 0; //0代表没有修改照片，1代表修改了照片
    /**
     * 获取需要展示的信息
     */
    private void showinfo() {
        Log.e("编辑",getIntent().getStringExtra("serviceid"));
        OkGo.post(Api.servicemore)
                .tag(this)
                .params("serviceId",getIntent().getStringExtra("serviceid")+"")
                .params("isonlineorder",getIntent().getStringExtra("isonlineorder"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("编辑页面",s);
                        Gson gson = new Gson();
                        ServiceMoreBean serviceMoreBean = gson.fromJson(s,ServiceMoreBean.class);
                        if (serviceMoreBean.getState()==1){
                            if (serviceMoreBean.getData().getService_name().equals("普洗")) {
                                spinnerzhi = "普洗";
                                fabu_spinner.setSelection(0);
                            }else if (serviceMoreBean.getData().getService_name().equals("精洗")) {
                                spinnerzhi = "精洗";
                                fabu_spinner.setSelection(1);
                            }else if (serviceMoreBean.getData().getService_name().equals("打蜡")) {
                                spinnerzhi = "打蜡";
                                fabu_spinner.setSelection(2);
                            } else if (serviceMoreBean.getData().getService_name().equals("喷漆")) {
                                spinnerzhi = "喷漆";
                                fabu_spinner.setSelection(3);
                            }else if (serviceMoreBean.getData().getService_name().equals("钣金")) {
                                spinnerzhi = "钣金";
                                fabu_spinner.setSelection(4);
                            }

//                            Log.e("详情测试","11");
                            if (!serviceMoreBean.getData().getService_img().contains(",")){

                            }else {
//                                Log.e("详情测试","11");
                                String[] img = serviceMoreBean.getData().getService_img().split(",");
                                String url = Api.ossUrl+img[0];
                                zhengmian = serviceMoreBean.getData().getService_img();
                                Log.e("图片展示",url);
                                Glide.with(ServiceBianjiActivity.this).load(url).into(img_suolv);
                            }

                            edt_xiangqing.setText(serviceMoreBean.getData().getService_descript());
                            edt_xiangqing.setSelection(edt_xiangqing.getText().length());
                            if (serviceMoreBean.getData().getStarttime()!=null){
                                tv_star.setText(serviceMoreBean.getData().getStarttime().substring(serviceMoreBean.getData().getStarttime().length()-9,serviceMoreBean.getData().getStarttime().length()-3));
                            }
                            if (serviceMoreBean.getData().getEndtime()!=null){

                                tv_end.setText(serviceMoreBean.getData().getEndtime().substring(serviceMoreBean.getData().getEndtime().length()-9,serviceMoreBean.getData().getEndtime().length()-3));
                            }

                            //保存分类信息
                            showzhi = serviceMoreBean.getData().getServicesDetail().size();

                            for (int i = 0; i <serviceMoreBean.getData().getServicesDetail().size() ; i++) {
                               //判断是否有3个值了，如果有直接存

                                servicesDetailId+=serviceMoreBean.getData().getServicesDetail().get(i).getId()+",";
                                if (serviceMoreBean.getData().getServicesDetail().get(i).getCar_type() == 1) {
                                    edt_1.setText(serviceMoreBean.getData().getServicesDetail().get(i).getType_name());
                                    edt_shuoming1.setText(serviceMoreBean.getData().getServicesDetail().get(i).getDetail());
                                    edt_jiage1.setText(serviceMoreBean.getData().getServicesDetail().get(i).getPrice() + "");
                                } else if (serviceMoreBean.getData().getServicesDetail().get(i).getCar_type() == 2) {
                                    edt_2.setText(serviceMoreBean.getData().getServicesDetail().get(i).getType_name());
                                    edt_shuoming2.setText(serviceMoreBean.getData().getServicesDetail().get(i).getDetail());
                                    edt_jiage2.setText(serviceMoreBean.getData().getServicesDetail().get(i).getPrice() + "");
                                } else if (serviceMoreBean.getData().getServicesDetail().get(i).getCar_type() == 3) {
                                    edt_3.setText(serviceMoreBean.getData().getServicesDetail().get(i).getType_name());
                                    edt_shuoming3.setText(serviceMoreBean.getData().getServicesDetail().get(i).getDetail());
                                    edt_jiage3.setText(serviceMoreBean.getData().getServicesDetail().get(i).getPrice() + "");
                                }
                            }
                            if (serviceMoreBean.getData().getIsonlineorder()==0){
                                timejiange = "";
                                cheliang = "";
                                isyuyue="0";
                                img_yuyue1.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio2));
                                img_yuyue2.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio1));
                                l_yuyuegongneng.setVisibility(View.GONE);
                            }else {
                                timejiange = serviceMoreBean.getData().getInterval_time();
                                cheliang  =serviceMoreBean.getData().getService_num();
                                isyuyue="1";
                                img_yuyue1.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio1));
                                img_yuyue2.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio2));
                                l_yuyuegongneng.setVisibility(View.VISIBLE);
                                edt_jiange.setText(serviceMoreBean.getData().getInterval_time());
                                edt_cheliang.setText(serviceMoreBean.getData().getService_num());
                            }


                        }else {
                            ToastUtils.shortToast("服务器请求失败");
                        }



                    }
                });


    }

    //获取页面信息
    private void setinfo() {
        //下拉框信息展示
        fabu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerzhi = fabu_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        //第一个图
        img_suolv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ServiceBianjiActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ServiceBianjiActivity.this);
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
//                        a=1;
//                        //这个手机的版本是多少  嗯
//                        autoObtainCameraPermission();

                        onClickTakePhoto(view);//拍照
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        a=1;
//                        autoObtainStoragePermission();

                        onClickOpenGallery(view);//图库
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        img_yuyue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isyuyue="1";
                img_yuyue1.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio1));
                img_yuyue2.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio2));
                l_yuyuegongneng.setVisibility(View.VISIBLE);
            }
        });
        img_yuyue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isyuyue="0";
                img_yuyue1.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio2));
                img_yuyue2.setImageDrawable(ServiceBianjiActivity.this.getResources().getDrawable(R.mipmap.radio1));
                l_yuyuegongneng.setVisibility(View.GONE);
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
        ttitle.setText("发布信息");
        ttitle2.setText("修改");
        ttitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils2.isFastClick()==true){

                    if (showzhi==1 && !TextUtils.isEmpty(edt_2.getText().toString())){
                        servicesDetailId+="@,";
                    }
                    if (showzhi==2 && !TextUtils.isEmpty(edt_3.getText().toString())){
                        servicesDetailId+="@,";
                    }
                    //获取添加的车型类型,车型名字
                    if (!TextUtils.isEmpty(edt_1.getText().toString())){
                        cartype="1,";
                        typename = edt_1.getText().toString()+",";
                        if (!TextUtils.isEmpty(edt_2.getText().toString())){
                            cartype="1,2,";
                            typename +=edt_2.getText().toString()+",";
                            if (!TextUtils.isEmpty(edt_3.getText().toString())){
                                cartype="1,2,3,";
                                typename +=edt_3.getText().toString()+",";
                            }
                        }
                    }
                    //添加车型说明
                    if (!TextUtils.isEmpty(edt_shuoming1.getText().toString())){
                        typedetail = edt_shuoming1.getText().toString()+",";
                        if (!TextUtils.isEmpty(edt_shuoming2.getText().toString())){
                            typedetail += edt_shuoming2.getText().toString()+",";
                            if (!TextUtils.isEmpty(edt_shuoming3.getText().toString())){
                                typedetail += edt_shuoming3.getText().toString()+",";
                            }
                        }
                    }else{
                        if (!TextUtils.isEmpty(edt_shuoming2.getText().toString())){
                            typedetail =","+ edt_shuoming2.getText().toString()+",";
                            if (!TextUtils.isEmpty(edt_shuoming3.getText().toString())){
                                typedetail += edt_shuoming3.getText().toString()+",";
                            }
                        }else {
                            if (!TextUtils.isEmpty(edt_shuoming3.getText().toString())){
                                typedetail += ","+","+edt_shuoming3.getText().toString()+",";
                            }
                        }
                    }
                    //添加车型价格
                    if (!TextUtils.isEmpty(edt_jiage1.getText().toString())){
                        typeprice = edt_jiage1.getText().toString()+",";
                        if (!TextUtils.isEmpty(edt_jiage2.getText().toString())){
                            typeprice += edt_jiage2.getText().toString()+",";
                            if (!TextUtils.isEmpty(edt_jiage3.getText().toString())){
                                typeprice += edt_jiage3.getText().toString()+",";
                            }
                        }
                    }


//                if (zhengmian==null){
//                    ToastUtils.shortToast("请上传服务缩略图");
//                }else {
//                }

                    if (TextUtils.isEmpty(edt_xiangqing.getText().toString())){
                        ToastUtils.shortToast("请输入详情介绍");
                    }else{
                        if (TextUtils.isEmpty(edt_1.getText().toString())){
                            ToastUtils.shortToast("请至少输入一个车型");
                        }else {
                            if (TextUtils.isEmpty(edt_jiage1.getText().toString())){
                                ToastUtils.shortToast("请至少输入一个车型的价格");
                            }else{
//                                Log.e("发布任务","serviceName"+spinnerzhi+"serviceImg"+zhengmian+"serviceDescript"+edt_xiangqing.getText().toString()
//                                +"starttime"+tv_star.getText().toString()+"endtime"+tv_end.getText().toString()+"isonlineorder"+isyuyue
//                                +"shopCode"+shopcode+"carType"+cartype+"typeName"+typename+"detail"+typedetail+"price"+typeprice
//                                        +"serviceNum"+edt_jiange.getText().toString()+"intervalTime"+edt_cheliang.getText().toString());
                                //照片有没有修改
                                if (showimgs==0){
                                    if (edt_jiange.getText().toString().equals(timejiange) && edt_cheliang.getText().toString().equals(cheliang)){
                                        status=0;
                                        setup(zhengmian);
                                    }else {
                                        status=1;
                                        setup(zhengmian);
                                    }

                                }else {
                                    Log.e("修改了照片","1");
                                    //修改了照片
                                    if (edt_jiange.getText().toString().equals(timejiange) && edt_cheliang.getText().toString().equals(cheliang)){
                                        status=0;
                                        if (shangchaunImg!=null){
                                            Log.e("修改了照片","2");
                                            setup(zhengmian);
//                                        beginupload(shangchaunImg);
                                        }else {
                                            ToastUtils.shortToast("请上传服务缩略图");
                                        }
                                    }else {
                                        status=1;
                                        if (shangchaunImg!=null){
                                            Log.e("修改了照片","3"+zhengmian);
                                            setup(zhengmian);
//                                        beginupload(shangchaunImg);
                                        }else {
                                            ToastUtils.shortToast("请上传服务缩略图");
                                        }

                                    }


                                }




                            }
                        }
                    }



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
                ServiceBianjiActivity.this.finish();
            }
        });
    }
    //时间选择器
    private void showTimer(final TextView editText){
        //     TimePickerView 同样有上面设置样式的方法
        TimePickerView mTimePickerView = new TimePickerView(ServiceBianjiActivity.this, TimePickerView.Type.HOURS_MINS);// 四种选择模式，年月日时分，年月日，时分，月日时分
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
//    /**
//     * 适配api19以上,根据uri获取图片的绝对路径
//     */
//    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
//        String filePath = null;
//        String wholeID = DocumentsContract.getDocumentId(uri);
//
//        // 使用':'分割
//        String id = wholeID.split(":")[1];
//
//        String[] projection = { MediaStore.Images.Media.DATA };
//        String selection = MediaStore.Images.Media._ID + "=?";
//        String[] selectionArgs = { id };
//
//        Cursor cursor = context.getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
//                selection, selectionArgs, null);
//        int columnIndex = cursor.getColumnIndex(projection[0]);
//
//        if (cursor.moveToFirst()) {
//            filePath = cursor.getString(columnIndex);
//        }
//        cursor.close();
//        return filePath;
//    }


    private String zhengmian,fanmian,zhiye;
    Uri shangchaunImg;
    private static final String TAG = "ServiceBianjiActivity";
    public void beginupload(Uri bitmap) {
        Log.e("传递值",bitmap+"");
        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String startpoint = "washcarimg";
        //     明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI8ygujYgDvLJ9", "nLrO1bpn9IOpEu0tt0zyAaChc22j0c");
        ClientConfiguration conf=new ClientConfiguration();
        conf.setConnectionTimeout(15*1000);
        conf.setSocketTimeout(15*1000);
        conf.setMaxConcurrentRequest(5);
        conf.setMaxErrorRetry(2);
        OSSLog.enableLog();
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider,conf);
        //通过填写文件名形成objectname,通过这个名字指定上传和下载的文件
        // 构造上传请求
        final String objectname = TimeUtil.getTimeMis() + "";

//        final String url = startpoint +"."+ endpoint+"/"+ objectname;
        ///storage/emulated/0/Android/data/com.mdb.example.administrator/files/2018_1110_055129_crop.jpg
        ///storage/emulated/0/Android/data/com.mdb.example.administrator/files/2018_1110_055413_crop.jpg
        Log.e(TAG, "beginupload: 1======"+UiUtils.getImageAbsolutePath(ServiceBianjiActivity.this,bitmap) );
        PutObjectRequest put = new PutObjectRequest(startpoint, objectname, UiUtils.getImageAbsolutePath(ServiceBianjiActivity.this,bitmap));
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.e(TAG, "onProgress: "+currentSize +",totalSize="+totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

//                Log.e(TAG, "onSuccess: "+result.toString() );
                Log.e("测试图片1","https://washcarimg.oss-cn-beijing.aliyuncs.com/"+objectname);
                zhengmian=objectname+",";

//                setup(zhengmian);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常

                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    ToastUtils.shortToast("图片上传失败导致信息无法发布");
                }
                if (serviceException != null) {
                    ToastUtils.shortToast(serviceException+"");
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }

        });

    }
    //成功的回调接口
    public interface picResultCallback {
        void getPicData(PutObjectResult data, String oldPath);
    }
    /**
     * 上传
     */
    private void setup(String zhiye){
        Log.e("发布任务","serviceName"+spinnerzhi+"serviceImg"+zhengmian+"serviceDescript"+edt_xiangqing.getText().toString()
        +"starttime"+tv_star.getText().toString()+"endtime"+tv_end.getText().toString()+"isonlineorder"+isyuyue
        +"shopCode"+shopcode+"carType"+cartype+"typeName"+typename+"detail"+typedetail+"price"+typeprice
                +"serviceNum"+edt_cheliang.getText().toString()+"intervalTime"+edt_jiange.getText().toString()+"servicesDetailId"+servicesDetailId+
        "++"+status+"servicesId"+getIntent().getStringExtra("serviceid"));

        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(ServiceBianjiActivity.this)
                .setMessage("信息提交中...")
                .setCancelable(false)
                .setCancelOutside(false);
        final LoadingDailog dialog=loadBuilder.create();
        dialog.show();


        //图片上传成功
        if (isyuyue.equals("0")){
            OkGo.post(Api.upfabuxinxi)
                    .tag(this)
                    .params("servicesId",getIntent().getStringExtra("serviceid"))
                    .params("serviceName",spinnerzhi)
                    .params("serviceImg",zhiye)
                    .params("serviceDescript",edt_xiangqing.getText().toString())
                    .params("starttime","2018-10-30 "+tv_star.getText().toString()+":00")
                    .params("endtime","2018-10-30 "+tv_end.getText().toString()+":00")
                    .params("isonlineorder",isyuyue)
                    .params("carType",cartype)
                    .params("typeName",typename)
                    .params("detail",typedetail)
                    .params("price",typeprice)
                    .params("status",status)
                    .params("servicesDetailId",servicesDetailId)
                    .params("intervalTime","")
                    .params("serviceNum","")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e("发布信息1",s);
                            Gson gson = new Gson();
                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);

                            if (tongyongBean.getState()==1){
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        dialog.dismiss();
//                                        ToastUtils.shortToast("发布新信息成功");
                                        ServiceBianjiActivity.this.finish();
                                    }
                                },2000);

                            }else {
                                dialog.dismiss();
                                ToastUtils.shortToast(tongyongBean.getMessage());
                            }
                        }
                    });
        }else {
            if (Integer.parseInt(edt_jiange.getText().toString())>20){
                OkGo.post(Api.upfabuxinxi)
                        .tag(this)
                        .params("servicesId",getIntent().getStringExtra("serviceid"))
                        .params("serviceName",spinnerzhi)
                        .params("serviceImg",zhiye)
                        .params("serviceDescript",edt_xiangqing.getText().toString())
                        .params("starttime","2018-10-30 "+tv_star.getText().toString()+":00")
                        .params("endtime","2018-10-30 "+tv_end.getText().toString()+":00")
                        .params("isonlineorder",isyuyue)
                        .params("carType",cartype)
                        .params("typeName",typename)
                        .params("detail",typedetail)
                        .params("price",typeprice)
                        .params("status",status)
                        .params("servicesDetailId",servicesDetailId)
                        .params("serviceNum",edt_cheliang.getText().toString())
                        .params("intervalTime",edt_jiange.getText().toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.e("发布信息2",s);
                                Gson gson = new Gson();
                                TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                if (tongyongBean.getState()==1){
//                                    ToastUtils.shortToast("发布新信息成功");
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ServiceBianjiActivity.this.finish();
                                            dialog.dismiss();
                                        }
                                    },2000);

                                }else {
                                    dialog.dismiss();
                                    ToastUtils.shortToast(tongyongBean.getMessage());
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Log.e("错误信息",e+"");
                            }
                        });
            }else {
                ToastUtils.shortToast("时间间隔不能小于20分钟");
            }
        }
    }



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
//                    decodeImage(imageUri);
                    break;

                case IMAGE_REQUEST_CODE://选择图片成功返回
                    if (data != null && data.getData() != null) {
                        imageUri = data.getData();

//                        cropPhoto(imageUri,true);
                        decodeImage(imageUri);
                    }
                    break;
                case CROP_REREQUEST_CODE:
                    Log.e("zmm", "裁剪以后的地址是------------>" + cropUri);
                    shangchaunImg  = cropUri;
                    decodeImage(cropUri);
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
//        intent.putExtra("aspectY", 3);

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
    private void decodeImage(Uri imageUri) {
        try {
            Bitmap bitmapFormUri = getBitmapFormUri(this, imageUri);
            img_suolv.setImageBitmap(bitmapFormUri);
            showimgs = 1;
            beginupload(imageUri);
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


}
