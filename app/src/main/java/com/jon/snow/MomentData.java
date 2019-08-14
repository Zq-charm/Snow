package com.jon.snow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


import Entity.Moment;
import Utils.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MomentData
{
    private HttpUtil httpUtil;
    private static final String STORAGE = "moment";
    private List<Moment> moments;
    public static MomentData get()
    {
        return new MomentData();
    }

    private SharedPreferences storage;

    final OkHttpClient client = new OkHttpClient();
    private  MomentData()
    {

        storage = App.getInstance().getSharedPreferences(STORAGE,Context.MODE_PRIVATE);

    }

    public List<Moment> getData() {
        List<Moment> list = Arrays.asList(
                new Moment("This is moment", R.drawable.angry, 99, 520, "1", R.drawable.ic_useface_1, 0, 1,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_1","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/angry"),
                new Moment("This is happy", R.drawable.happy, 98, 13, "2", R.drawable.ic_useface_2, 1, 1,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_2","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/happy"),
                new Moment("This is lonly", R.drawable.lonly, 100, 14, "3", R.drawable.ic_useface_3, 2, 2,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_3","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/lonly"),
                new Moment("This is qaq", R.drawable.sad, 10, 24, "4", R.drawable.ic_useface_4, 3, 4,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_4","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/sad")
        );
        for (int i = 0; list.get(i) != null; i++) {
            Gson gson = new Gson();
            String jsonObject = gson.toJson(list.get(i));
            httpUtil.OkHttpRequestPost("127.0.0.1:8080/addmoment", jsonObject);
        }
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Gson gson = new Gson();
                        moments = gson.fromJson((String)msg.obj,new TypeToken<List<Moment>>(){}.getType());
                        break;
                }
            }

        };
        httpUtil.OkHttpRequestGet("127.0.0.1:8080/moments",handler);
        return moments;
    }



    public boolean isStared(int positionId)
    {
        return storage.getBoolean(String.valueOf(positionId),false);
    }

    public void setStar(int positionId, boolean isStared)
    {
        storage.edit().putBoolean(String.valueOf(positionId),isStared).apply();
    }
}

