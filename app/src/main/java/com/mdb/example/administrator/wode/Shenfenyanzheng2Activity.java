package com.mdb.example.administrator.wode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.PhotoUtils;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.TimeUtil;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.Utils.UiUtils;
import com.mdb.example.administrator.bean.TongyongBean;
import com.mdb.example.administrator.login.LoginActivity;
import com.mdb.example.administrator.login.ShopActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 资质上传验证起始页
 */
public class Shenfenyanzheng2Activity extends BaseActivity {

    SharedPreferencesHelper sharehelper;
    //正本副本营业执照本地照片是否显示了，0代表未添加，1代表已添加了
    int zhengzhi1 = 0,fuzhi2 = 0;
    //网络照片是否上传了，0代表未添加，1代表已添加了
    int wangluo1=0,wangluo2=0;
    //上传商家的审核状态
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenfenyanzheng);
        ButterKnife.bind(this);
        status = getIntent().getStringExtra("status");
        sharehelper = new SharedPreferencesHelper(Shenfenyanzheng2Activity.this,"xicheshop");
        setTtitle();
        fanhui();
        setinfo();
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("资质上传");
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
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Shenfenyanzheng2Activity.this,LoginActivity.class);
                startActivity(intent);
                Shenfenyanzheng2Activity.this.finish();
            }
        });
    }
    /**
     * 填写信息上传
     */
    @BindView(R.id.sfyz_next)
    Button btn_next;
    @BindView(R.id.sfyz_shangjianame)
    EditText edt_shangjianame;
    @BindView(R.id.sfyz_shangjiadizhi)
    EditText edt_dizhi;
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

    private void setinfo(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_shangjianame.getText().toString())){
                    ToastUtils.shortToast("请输入商家名称");
                }else{
                    if (TextUtils.isEmpty(edt_dizhi.getText().toString())){
                        ToastUtils.shortToast("请输入商家地址");
                    }else{
//                        if (TextUtils.isEmpty(edt_name.getText().toString())){
//                            ToastUtils.shortToast("请输入法人姓名");
//                        }else{
//                        }

                        if (TextUtils.isEmpty(edt_phone.getText().toString())){
                            ToastUtils.shortToast("请输入联系电话");
                        }else{
                            if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                                ToastUtils.shortToast("输入的手机号不正确");
                            }else {
                                if (TextUtils.isEmpty(edt_lianxiren.getText().toString())){
                                    ToastUtils.shortToast("请输入业务联系人姓名");
                                }else{
                                    if (TextUtils.isEmpty(edt_youxiang.getText().toString())){
                                        ToastUtils.shortToast("请输入邮箱");
                                    }else {
                                        if (UiUtils.isEmail(edt_youxiang.getText().toString())==false){
                                            ToastUtils.shortToast("输入的邮箱格式不正确");
                                        }else{
//                                            if (zhengzhi1==0){
//                                                ToastUtils.shortToast("请上传营业执照正本");
//                                            }else{
//                                                if (fuzhi2==0){
//                                                    ToastUtils.shortToast("请上传营业执照副本");
//                                                }else{
//                                                }
//                                            }
                                            sharehelper.put("shopname",edt_shangjianame.getText().toString());
                                            OkGo.post(Api.zizhiup)
                                                    .tag(this)
                                                    .params("shopId",sharehelper.getSharedPreference("shopid","").toString())
                                                    .params("shopName",edt_shangjianame.getText().toString())
                                                    .params("shopAddr",edt_dizhi.getText().toString())
                                                    .params("ownerName",edt_name.getText().toString())
                                                    .params("ownerPhone",edt_phone.getText().toString())
                                                    .params("managerName",edt_lianxiren.getText().toString())
                                                    .params("mail",edt_youxiang.getText().toString())
                                                    .params("companyImg",zhengmian+","+fanmian+",")
                                                    .params("status",status)
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(String s, Call call, Response response) {
                                                            Log.e("资质上传info","id"+sharehelper.getSharedPreference("shopid","").toString()+"shopName"+edt_shangjianame.getText().toString()
                                                                    + "shopAddr"+edt_dizhi.getText().toString()+"ownerName"+edt_name.getText().toString()
                                                                    +"ownerName"+edt_name.getText().toString()+"ownerPhone"+edt_phone.getText().toString()
                                                                    +"managerName"+edt_lianxiren.getText().toString()+"mail"+edt_youxiang.getText().toString()
                                                                    +"companyImg"+zhengmian+","+fanmian+",");
                                                            Log.e("资质上传",s);
                                                            Gson gson = new Gson();
                                                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                            if (tongyongBean.getState()==1){
                                                                Handler handler = new Handler();
                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Intent intent = new Intent(Shenfenyanzheng2Activity.this,ShopActivity.class);
                                                                        intent.putExtra("status",status);
                                                                        startActivity(intent);
                                                                        Shenfenyanzheng2Activity.this.finish();
                                                                    }
                                                                },500);
                                                            }else{
                                                                ToastUtils.shortToast("信息上传失败，请重试！");
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });

        //资质上传正本
        img_zhengmian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Shenfenyanzheng2Activity.this);
                LayoutInflater inflater = LayoutInflater.from(Shenfenyanzheng2Activity.this);
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
                        autoObtainCameraPermission();
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=1;
                        autoObtainStoragePermission();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //资质上传副本
        img_fanmian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Shenfenyanzheng2Activity.this);
                LayoutInflater inflater = LayoutInflater.from(Shenfenyanzheng2Activity.this);
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
                        autoObtainCameraPermission();
                        dialog.dismiss();
                    }
                });
                //图库
                tuku.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        a=2;
                        autoObtainStoragePermission();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    //禁用返回键
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri,img1,img2;
    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.shortToast( "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(Shenfenyanzheng2Activity.this, "com.zz.fileprovider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.shortToast(  "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(Shenfenyanzheng2Activity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.shortToast( "设备没有SD卡！");
                    }
                } else {
                    ToastUtils.shortToast( "请允许打开相机！！");
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.shortToast( "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 1000;
    private static final int OUTPUT_Y = 1000;
    private int a=0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Log.e("显示",cropImageUri+"");
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1000, 1000, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.shortToast( "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        if (a==1){
                            beginupload(cropImageUri,a);
                            img1 = cropImageUri;
                            showImages(bitmap,a);
                        }else if (a==2){
                            beginupload(cropImageUri,a);
                            img2 = cropImageUri;
                            showImages(bitmap,a);
                        }

                    }
                    break;
                default:
            }
        }
    }


    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    private void showImages(Bitmap bitmap,int b) {
        if (b==1){
            img_zhengmian.setImageBitmap(bitmap);
            zhengzhi1=1;
        }else{
            img_fanmian.setImageBitmap(bitmap);
            fuzhi2 =1;
        }


    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private String zhengmian,fanmian,zhiye;
    public void beginupload(Uri bitmap, final int c) {

        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String startpoint = "washcarimg";
        //     明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI8ygujYgDvLJ9", "nLrO1bpn9IOpEu0tt0zyAaChc22j0c");
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        //通过填写文件名形成objectname,通过这个名字指定上传和下载的文件
        // 构造上传请求
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        final String objectname =df.format(new Date())+ ".png";

        PutObjectRequest put = new PutObjectRequest(startpoint, objectname, UiUtils.getImageAbsolutePath(Shenfenyanzheng2Activity.this,bitmap) );
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if(c==1){
                    Log.e("测试图片1","https://jiaoyuvideo.oss-cn-beijing.aliyuncs.com/"+objectname);
                    zhengmian=objectname;
                    wangluo1 = 1;
                }else{
                    Log.e("测试图片2","https://jiaoyuvideo.oss-cn-beijing.aliyuncs.com/"+objectname);
                    fanmian=objectname;
                    wangluo2 = 1;
                }
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
