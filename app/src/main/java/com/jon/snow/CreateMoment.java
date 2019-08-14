package com.jon.snow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Entity.Article;
import Entity.Moment;
import Entity.PicList;
import Entity.User;
import Utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static Utils.HttpUtil.JSON;

public class CreateMoment extends AppCompatActivity {
    public User user = new User();
    private static final int REQUEST_CODE_CHOOSE = 25;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private Moment moment;
    public ImageView sendMoment;
    public ImageView createMomentBack;
    public EditText writeMoment;
    public ImageView addPic;
    public ImageView addLoc;
    public ImageView addTopic;
    public TextView loc;
    public TextView topic;
    public LinearLayout picGroup;
    public List<String> picUrlList=new ArrayList<>(); //图片存放URLList
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_moment);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        picGroup = (LinearLayout)findViewById(R.id.pic_group);
        sendMoment =(ImageView) findViewById(R.id.send_moment);
        createMomentBack = (ImageView)findViewById(R.id.createmoment_back);
        writeMoment = (EditText) findViewById(R.id.create_moment_tv);
        addPic = (ImageView)findViewById(R.id.create_moment_addpic);
        addLoc = (ImageView)findViewById(R.id.create_moment_addloc);
        addTopic = (ImageView)findViewById(R.id.create_moment_addtopic);
        loc = (TextView)findViewById(R.id.tv_loc);
        topic=(TextView)findViewById(R.id.tv_top);
        sendMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateMoment.this, "发送Moment", Toast.LENGTH_SHORT).show();
                Moment moment = new Moment();
                try
                {
                    SharedPreferences sharedPreferences =  getBaseContext().getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
                    String usrname = (String)sharedPreferences.getString("loginUserName",null);
                    moment.setUserName(usrname);

                    System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
                    HttpUtil.sendOkHttpRequest("http://http://49.232.63.6:8080/users/name/"+usrname, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("失败","GET失败USER");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //获取解析成功网络请求后的字符串，string（）方法为gson自带的方法
                            String responseString = response.body().string();
                            //当访问网络成功后会执行这一个方法
                            //下面我们使用gson来接收数据就可以了
                            //创建gson对象
                            Gson gson = new Gson();
                            //下面将json数据与实体类相关联
                            //因为json数据最外面是[]及json数组，所以我们使用gson解析数组的方法，较为繁琐
                            //创建list集合，通过TypeToken将希望解析成的数据传入fromJson中
                            user = gson.fromJson(responseString,new TypeToken<User>(){}.getType());
                            //这用province中就有了[]中的所有数据，下面遍历就可以了
                            if (user==null)
                            {
                                Log.d("错了","user没有根据name得到");
                            }
                        }
                    });
                    moment.setUser_id(user.getId());
                    moment.setMoment_Text(writeMoment.getText().toString());
                    moment.setLocation(loc.getText().toString());
                    moment.setTopic(topic.getText().toString());
                    Gson gson = new Gson(); //将图片URL转换为Json存储
                    String json = gson.toJson(picUrlList);
                    moment.setPicUrlList(json);
                    moment.setImageUrl(picUrlList.get(0));

                    Gson gson1 = new Gson();
                    String jsonObject = gson1.toJson(moment);
                    Log.d("createMomentJson","createMomJson错" + jsonObject);
                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            super.run();
                            try
                            {
                                System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
                                HttpUtil.OkHttpRequestPost("http://http://49.232.63.6:8080/addmoment", jsonObject);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    finish();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        createMomentBack.setOnClickListener(new View.OnClickListener()
        {     @Override
             public void onClick(View v)
            {
                if (loc.getText()!=null)
                {
                    mLocationClient.stop();
                }
             Toast.makeText(CreateMoment.this, "返回", Toast.LENGTH_SHORT).show();
             finish();
            }
        });

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse
                    .from(CreateMoment.this)
                    //选择图片
                    .choose(MimeType.ofImage())
                    //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                    .showSingleMediaType(true)
                    //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true,"com.jon.snow.fileprovider"))
                    //有序选择图片 123456...
                    .countable(true)
                    //最大选择数量为9
                    .maxSelectable(9)
                    //选择方向
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    //界面中缩略图的质量
                    .thumbnailScale(0.8f)
                    //蓝色主题
                    .theme(R.style.Matisse_Zhihu)
                    //Glide加载方式
                    .imageEngine(new GlideEngine())
                    //请求码
                    .forResult(REQUEST_CODE_CHOOSE);
            }
        });

        addLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.start();
            }
        });

        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateMoment.this, "添加话题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            loc.setText(addr);
        }
    }

    //获取图片的URL
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
            picGroup.removeAllViews();
            for (int i = 0; i < result.size(); i++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  //设置图片宽高
                imageView.setImageURI(result.get(i));
                picGroup.addView(imageView); //动态添加图片
                picUrlList.add(result.get(i).toString());
            }

        }
    }





}
