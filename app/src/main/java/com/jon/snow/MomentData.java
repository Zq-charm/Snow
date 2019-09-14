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

        storage = DemoApplication.getInstance().getSharedPreferences(STORAGE,Context.MODE_PRIVATE);

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

