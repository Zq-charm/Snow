package com.jon.snow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.message.TIMMessageLocator;
import com.tencent.imsdk.ext.message.TIMMessageRevokedListener;
import com.tencent.imsdk.session.SessionWrapper;

import java.io.IOException;
import java.util.List;

import Entity.User;
import Utils.GenerateTestUserSig;
import Utils.MD5Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static Utils.HttpUtil.OkHttpRequestGet;
import static Utils.HttpUtil.OkHttpRequestPost;

public class LoginActivity extends AppCompatActivity{
    private TextView tv_main_title;//标题
    private TextView tv_back,tv_register,tv_find_psw;//返回键,显示的注册，找回密码
    private Button btn_login;//登录按钮
    private String userName,psw,spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name,et_psw;//编辑框
    public TIMManager timManager = TIMManager.getInstance();
    private String userSig=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化tim配置

        //初始化 IM SDK 基本配置
        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(getApplicationContext())) {
            TIMSdkConfig config = new TIMSdkConfig(1400279726)
                    //.enableCrashReport(false)  //接口已废弃
                    .enableLogPrint(true)
                    .setLogLevel(TIMLogLevel.DEBUG)
                    .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

//            TIMManager timManager = TIMManager.getInstance();
            //初始化 SDK
            TIMManager.getInstance().init(getApplicationContext(), config);
        }

        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i("tim被其他终端踢下线", "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新 userSig 重新登录 IM SDK
                        Log.i("tim用户签名过期了，需要刷新 userSig 重新登录 IM SDK", "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i("tim设置连接状态事件监听器连接", "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i("tim设置连接状态事件监听器未连接", "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i("tim设置连接状态事件监听器onWifiNeedAuth", "onWifiNeedAuth");
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.i("tim设置群组事件监听器", "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("tim设置会话刷新监听器", "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.i("timonRefreshConversation", "onRefreshConversation, conversation size: " + conversations.size());
                    }
                })
                .setMessageRevokedListener(new TIMMessageRevokedListener()
                {
                    @Override
                    public void onMessageRevoked(TIMMessageLocator timMessageLocator) {
                        Log.i("tim消息撤回处理", "setback");
                    }
                })
                ;

        //禁用本地所有存储
        userConfig.disableStorage();
        //开启消息已读回执
        userConfig.enableReadReceipt(true);

        //将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);



        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }




    //获取界面控件
    private void init() {
        //从main_title_bar中获取的id
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back=findViewById(R.id.tv_back);
        //从activity_login.xml中获取的
        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
        btn_login=findViewById(R.id.btn_login);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        //返回键的点击事件
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录界面销毁
                LoginActivity.this.finish();
                Intent intent = new Intent();
            }
        });
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面（此页面暂未创建）
            }
        });
        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //开始登录，获取用户名和密码 getText().toString().trim();
                userName=et_user_name.getText().toString().trim();

                //isHadUserFromBack(userName);


                psw=et_psw.getText().toString().trim();
                //对当前用户输入的密码进行MD5加密再进行比对判断, MD5Utils.md5( ); psw 进行加密判断是否一致
                String md5Psw= MD5Utils.md5(psw);
                Log.d("从sp中获取密码","用户输入"+md5Psw);
                // md5Psw ; spPsw 为 根据从SharedPreferences中用户名读取密码
                // 定义方法 readPsw为了读取用户名，得到密码
                spPsw = readPsw(userName);


                // TextUtils.isEmpty
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else if(md5Psw.equals(spPsw)){
                    //TIM登陆
                    // identifier为用户名，userSig 为用户登录凭证
//                    Handler handler = new Handler()
//                    {
//                        public void handleMessage(Message msg)
//                        {
//                            userSig=msg.getData().getString("sign");
//                        }
//                    };
//                    getSignFromBack("http://"+R.string.ip+":"+R.string.port+"/getoneSign/"+userName,handler);

//                    while (true)
//                    {
//                        if (userSig==null)
//                        {
//                           // Log.d("userSign为null","wait");
//                        }else
//                        {
//                            Log.d("userSign不为空","="+userSig);
//                            break;
//                        }
//                    }

                    //userSig=GenerateTestUserSig.genTestUserSig(userName);
                    Log.d("userSIGN","="+userSig);
                    Log.d("userSIGN","username="+userName);
                    Log.d("userSIGNTEST","="+GenerateTestUserSig.genTestUserSig(userName));
                  //  userSig="eJwtzMEKgkAUheF3me2E3bk6jgpt3Ahli8hFRBthRrmFNqiUFr17Mro834H-y4r87L1MxxKGHrCN26RNO1BFjgX6gQzXp9eP0lrSLBEBAKpYYbg8ZrTUmdmllAgAiw7UOFM*xLEKca1QPYdL0HeePfemuByuIo34dNtiPX76Y6TTVvLT2GRTNrwF5bBjvz-rjTCU";
                    Log.d("userSIGN在线测试","="+userSig);
                    TIMManager.getInstance().login(userName, userSig, new TIMCallBack() {
                        @Override
                        public void onError(int code, String desc) {
                            //错误码 code 和错误描述 desc，可用于定位请求失败原因
                            //错误码 code 列表请参见错误码表
                            Log.d("tim后台登陆失败", "login failed. code: " + code + " errmsg: " + desc);
                        }

                        @Override
                        public void onSuccess() {
                            Log.d("tim后台登陆成功", "login succ");
                        }
                    });



                    //一致登录成功
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, userName);
                    //登录成功后关闭此页面进入主页
                    Intent data=new Intent(LoginActivity.this,MainActivity.class);

                    data.setAction("action");
                    data.putExtra("userName",userName);
                    data.putExtra("passWord",spPsw);
                    data.putExtra("isLogin","loginsuceed");
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK,data);
                    //销毁登录界面
                    LoginActivity.this.finish();

                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    startActivity(data);
                    return;
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //判断当前对话框内用户名是否在数据库中存在
    private void isHadUserFromBack(String userName)
    {
        Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 1:
                        User user = (User)msg.obj;
                        String getUserName = user.getDisplayName();
                        String getUserPassWord = user.getPassword();
                        String md5Psw = MD5Utils.md5(getUserPassWord);//把密码用MD5加密
                        Log.d("user.getDisplayName",":"+user.getDisplayName());
                        Log.d("msg.getData():",":"+msg.getData());
                        Log.d("msg.getData().getString:",":"+ getUserName);
                        //String getUserPsw = msg.getData().getString("PassWord");
                        if (getUserName !=null)
                        {
                            //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
                            SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
                            //获取编辑器
                            SharedPreferences.Editor editor=sp.edit();
                            //存入登录状态时的用户名
                           // editor.putString("UserName", getUserName);
                            editor.putString(getUserName,md5Psw);
                            //  editor.putString("PassWord",psw);
                            //提交修改
                            editor.commit();

                            Log.d("保存用户名密码成功","succees");
                        }else {
//                            SharedPreferences sp=getSharedPreferences("userInfo", MODE_PRIVATE);
//                            //获取编辑器
//                            SharedPreferences.Editor editor=sp.edit();
//                            //存入登录状态时的用户名
//                            editor.putString("UserName", null);
//                            //   editor.putString("PassWord",null);
//                            //提交修改
//                            editor.commit();
                            Log.d("从后台获取密码失败","username:"+getUserName);
                        }
                        break;
                }
            }
        };
        getDataFromBack("http://"+R.string.ip+":"+R.string.port+"/users/name/"+userName,handler);
//        if (handler.hasMessages(1))
//        {
//            return true;
//        }else
//        {
//            return false;
//        }
    }

    /**
     *从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName){
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);//loginInfo
        //sp.getString() userName, "";
        Log.d("从sp中获取密码",":"+sp.getString(userName , ""));
        return sp.getString(userName , "");
    }


    private void getDataFromBack(String address,Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(address)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        Log.d("kwwl","response.code()=="+response.code());
                        Log.d("kwwl","response.message()=="+response.message());
                        Log.d("kwwl","res=="+response.body().toString());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        User user = new User();
                        Gson gson = new Gson();
                        user =  gson.fromJson(response.body().string(),User.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Psw",user.getPassword());
                        bundle.putString("UserName",user.getDisplayName());
                        Log.d("Psw在LoginActivity",user.getPassword());
                        Log.d("UserName在LoginActivity",user.getDisplayName());
                        Message message = new Message();
                        message.what=1;
                        message.obj=user;
                        //message.setData(bundle);
                        handler.sendMessage(message);
                    }
                    else
                    {
                        Log.d("okhttp请求用户名错误","response faile");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void getSignFromBack(String address,Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(address)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        userSig = response.body().string();
                        Bundle bundle = new Bundle();
                        bundle.putString("sign",userSig);
                        Message message = new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                    else
                    {
                        Log.d("okhttp请求用户名错误","response faile");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }
    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
}