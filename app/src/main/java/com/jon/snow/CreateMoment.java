package com.jon.snow;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.baidu.mapapi.http.AsyncHttpClient;
import com.baidu.mapapi.http.HttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entity.Article;
import Entity.Moment;
import Entity.PicList;
import Entity.User;
import Utils.HttpUtil;
import Utils.UploadUtil;
import Utils.UploadUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

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
    public List<Uri> picUrlList=new ArrayList<>(); //图片存放URLList
    private String result;
    public List<String>picUrIList=new ArrayList<>();
    public String RequestURL = "http://49.232.63.6:8080/momentpics";
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
                    HttpUtil.sendOkHttpRequest("http://49.232.63.6:8080/users/name/"+usrname, new Callback() {
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
//                    getRealPathFromURI(picUrlList.get(0));
//                    Gson gson = new Gson(); //将图片URL转换为Json存储
//                    String json = gson.toJson(picUrlList);
                   // moment.setPicUrlList(json);
                  //  moment.setImageUrl(picUrlList.get(0));
                    int i=0;

//                        for (String picUrI:picUrIList)
//                    {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

//                                File file = new File(picUrI);
                                result = UploadUtils.uploadFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),RequestURL);
                            }
                        }).start();
                        if (result ==null)
                        {
                            Log.d("result", ":为空");
                        }
                        if (i==0&&result!=null)
                        {
                            moment.setImageUrl(result);
                            Log.d("ImageUrl",":"+result);
                        }
                        i++;
//                    }
                    if (result!=null)
                    {
                        moment.setPicUrlList(result);
                        Log.d("PicUrlList",":"+result);
                    }
//                    try {
//                        Uri originalUri = data.getData(); // 获得图片的uri
//
//                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
//
//                        // 这里开始的第二部分，获取图片的路径：
//
//                        String[] proj = { MediaStore.Images.Media.DATA };
//
//                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
//                        @SuppressWarnings("deprecation")
//                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
//                        // 按我个人理解 这个是获得用户选择的图片的索引值
//                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
//                        cursor.moveToFirst();
//                        // 最后根据索引值获取图片路径
//                        String path = cursor.getString(column_index);
//                        iv_photo.setImageURI(originalUri);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    Gson gson1 = new Gson();
                    String jsonObject = gson1.toJson(moment);
                    Log.d("createMomentJson","createMomJson" + jsonObject);
                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            super.run();
                            try
                            {
                                System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
                                HttpUtil.OkHttpRequestPost("http://49.232.63.6:8080/addmoment", jsonObject);
                            }catch (Exception e)
                            {
                                Log.d("增加moment","失败");
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
            if (result.size() >= 9) {
                picGroup.removeAllViews();
            }
            for (int i = 0; i < result.size(); i++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  //设置图片宽高
                imageView.setImageURI(result.get(i));
                picGroup.addView(imageView); //动态添加图片
                //picUrlList.add(result.get(i).toString());

                picUrIList.add(getRealPathFromURI(result.get(i)));
                Log.d("picUrIList",":"+picUrIList.get(i));
            }
        }
    }

    //uri以content开始,从uri获取文件路径
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    /**
     * 通过Base32将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
