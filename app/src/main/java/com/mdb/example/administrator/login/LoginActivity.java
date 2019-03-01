package com.mdb.example.administrator.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mdb.example.administrator.BaseActivity;
import com.mdb.example.administrator.BuildConfig;
import com.mdb.example.administrator.MainActivity;
import com.mdb.example.administrator.R;
import com.mdb.example.administrator.Utils.Api;
import com.mdb.example.administrator.Utils.PermissionsChecker;
import com.mdb.example.administrator.Utils.SharedPreferencesHelper;
import com.mdb.example.administrator.Utils.ToastUtils;
import com.mdb.example.administrator.Utils.UiUtils;
import com.mdb.example.administrator.Utils.UrlCollect;
import com.mdb.example.administrator.bean.BankBean;
import com.mdb.example.administrator.bean.LoginBean;
import com.mdb.example.administrator.wode.BanBenActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

import static com.mdb.example.administrator.MyApplication.registrationID;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    public static int bodadianhua = 0;
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPermissionsChecker = new PermissionsChecker(this);
        isRequireCheck = true;
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        isNetworkConnected(LoginActivity.this);
        isWifiConnected(LoginActivity.this);
        setTtitle();
        setinfo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) LoginActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        5);
            }else {
                bodadianhua=5;
//                showToast("权限已申请");
            }
        }
        sharedPreferencesHelper = new SharedPreferencesHelper(LoginActivity.this,"xicheshop");

        if (TextUtils.isEmpty(sharedPreferencesHelper.getSharedPreference("zhanghao","").toString())){

        }else {
            checkBox.setChecked(true);
            edt_phone.setText(sharedPreferencesHelper.getSharedPreference("zhanghao","").toString());
            edt_phone.setSelection(edt_phone.getText().length());
            edt_password.setText(sharedPreferencesHelper.getSharedPreference("mima","").toString());
            edt_password.setSelection(edt_password.getText().length());
        }
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("登录");
        ttitle2.setText("注册");
        ttitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    private void fanhui(){
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }
    /**
     * 忘记密码
     * 短信登录
     * 登录
     */
    @BindView(R.id.login_forgetpassword)
    TextView tv_forgetpassword;
    @BindView(R.id.login_duanxin)
    TextView tv_duanxin;
    @BindView(R.id.login_btn)
    Button btn_login;
    @BindView(R.id.login_phone)
    EditText edt_phone;
    @BindView(R.id.login_password)
    EditText edt_password;
    @BindView(R.id.login_baocuninfo)
    CheckBox checkBox;

    private void setinfo(){
        //忘记密码
        tv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,DuanxiLoginActivity.class);
                intent.putExtra("zhi","1");
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        //短信验证码登录
        tv_duanxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,DuanxiLoginActivity.class);
                intent.putExtra("zhi","0");
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        //登录按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                    ToastUtils.shortToast("请输入手机号");
                }else{
                    if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                        ToastUtils.shortToast("输入的手机号不正确");
                    }else {
                        if (TextUtils.isEmpty(edt_password.getText().toString())){
                            ToastUtils.shortToast("请输入密码");
                        }else{
                            Log.e("测试id",registrationID);
                            OkGo.post(Api.login)
                                    .tag(this)
                                    .params("mobile",edt_phone.getText().toString())
                                    .params("password",edt_password.getText().toString())
                                    .params("registrationId",registrationID)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            Log.e("登录",s);
                                            // 1未审核2驳回3重新提交4通过5拉黑
                                            final Gson gson = new Gson();
                                            final LoginBean loginBean = gson.fromJson(s,LoginBean.class);
                                            if (loginBean.getState()==1){
                                                LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(LoginActivity.this)
                                                        .setMessage("登录成功...")
                                                        .setCancelable(false)
                                                        .setCancelOutside(false);
                                                final LoadingDailog dialog=loadBuilder.create();
                                                dialog.show();
                                                if (checkBox.isChecked()==true){
                                                    sharedPreferencesHelper.put("zhanghao",edt_phone.getText().toString());
                                                    sharedPreferencesHelper.put("mima",edt_password.getText().toString());
                                                }else {
                                                    sharedPreferencesHelper.remove("zhanghao");
                                                    sharedPreferencesHelper.remove("mima");
                                                }
                                                sharedPreferencesHelper.put("shopid",loginBean.getData().getShopId()+"");
                                                sharedPreferencesHelper.put("shopphone",loginBean.getData().getMobile()+"");
                                                sharedPreferencesHelper.put("shopcode",loginBean.getData().getShop_code()+"");
                                                sharedPreferencesHelper.put("shopstatus",loginBean.getData().getStatus()+"");
                                                sharedPreferencesHelper.put("youhuiquan","1");
                                                if (loginBean.getData().getReason()==null){
                                                    sharedPreferencesHelper.put("reason","");
                                                }else{
                                                    sharedPreferencesHelper.put("reason",loginBean.getData().getReason()+"");
                                                }
                                                if (loginBean.getData().getShop_name()==null){
                                                    sharedPreferencesHelper.put("shopname","");
                                                }else {
                                                    sharedPreferencesHelper.put("shopname",loginBean.getData().getShop_name());
                                                }
                                                if (loginBean.getData().getWaitnum_flag()==null){
                                                    sharedPreferencesHelper.put("waitnum_flag","");
                                                }else {
                                                    sharedPreferencesHelper.put("waitnum_flag",loginBean.getData().getWaitnum_flag());
                                                }


                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        // 1未审核2驳回3重新提交4通过5拉黑
                                                        if (loginBean.getData().getStatus().equals("4")){
                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            LoginActivity.this.finish();
                                                        }else{
                                                            //未审核
                                                            if (loginBean.getData().getStatus().equals("1")){
                                                                //先判断是否上传了银行卡
                                                                OkGo.post(Api.chaxunBank)
                                                                        .tag(this)
                                                                        .params("shopId",loginBean.getData().getShopId())
                                                                        .execute(new StringCallback() {
                                                                            @Override
                                                                            public void onSuccess(String s, Call call, Response response) {
                                                                               Log.e("银行卡",s);
                                                                                Gson gson1 = new Gson();
                                                                                BankBean bankBean = gson1.fromJson(s,BankBean.class);
                                                                                if (bankBean.getState()==1){
                                                                                    if (bankBean.getData()==null){
                                                                                        //没有上传银行卡，而且没有审核通过，重新上传
                                                                                        Intent intent = new Intent(LoginActivity.this,ShenfenyanzhengActivity.class);
                                                                                        intent.putExtra("status","1");
                                                                                        startActivity(intent);
                                                                                        LoginActivity.this.finish();
                                                                                    }else{
                                                                                        //已上传银行卡，但是没有提示通过
                                                                                        Intent intent = new Intent(LoginActivity.this,YanzhengShenheActivity.class);
                                                                                        intent.putExtra("status",loginBean.getData().getStatus());
                                                                                        startActivity(intent);
                                                                                        LoginActivity.this.finish();
                                                                                    }
//

                                                                                }else {
                                                                                    ToastUtils.shortToast("信息请求失败，请稍后重试");
                                                                                }
                                                                            }
                                                                        });

                                                            }else if (loginBean.getData().getStatus().equals("2")){
                                                                //已上传银行卡，但是没有提示通过
                                                                Intent intent = new Intent(LoginActivity.this,YanzhengShenheActivity.class);
                                                                intent.putExtra("status","2");
                                                                startActivity(intent);
                                                                LoginActivity.this.finish();

                                                            }else if (loginBean.getData().getStatus().equals("3")){
                                                                //已上传银行卡，但是没有提示通过
                                                                Intent intent = new Intent(LoginActivity.this,YanzhengShenheActivity.class);
                                                                intent.putExtra("status","3");
                                                                startActivity(intent);
                                                                LoginActivity.this.finish();

                                                            }else if (loginBean.getData().getStatus().equals("5")){
                                                                //已上传银行卡，但是没有提示通过
                                                                Intent intent = new Intent(LoginActivity.this,YanzhengShenheActivity.class);
                                                                intent.putExtra("status","5");
                                                                startActivity(intent);
                                                                LoginActivity.this.finish();

                                                            }

                                                        }


                                                    }
                                                },2000);

                                            }else {
                                                ToastUtils.shortToast(loginBean.getMessage());
                                            }
                                        }
                                    });
                        }
                    }
                }


            }
        });
    }

    /**
     * 调取摄像头权限
     * @param
     */
    private static final int REQUEST_CODE = 0; // 请求码
    private boolean isRequireCheck; // 是否需要系统权限检测
    //危险权限（运行时权限）
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private PermissionsChecker mPermissionsChecker;//检查权限
    private static final int PERMISSION_REQUEST_CODE = 0;        // 系统权限返回码
    private static final String PACKAGE_URL_SCHEME = "package:";

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            //权限没有授权，进入授权界面
            if(mPermissionsChecker.judgePermissions(PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
            }
        }else{
            isRequireCheck = true;
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
        }else if (requestCode==5){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ToastUtils.shortToast("定位权限已申请");
                bodadianhua=2;
            } else {
                bodadianhua=0;
                ToastUtils.shortToast("拨打电话权限已拒绝");
            }
        }else {
            isRequireCheck = false;
            showPermissionDialog();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted( int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 提示对话框
     */
    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
    //判断是否有网络连接
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }else{
                Toast.makeText(LoginActivity.this, "当前无可用的网络服务",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
    //判断WIFI网络是否可用
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }else{
                Toast.makeText(LoginActivity.this, "当前WIFI网络不可用",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
}
