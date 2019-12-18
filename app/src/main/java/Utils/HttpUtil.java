package Utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import Entity.Moment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil
{
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        //下面是OkHttp的使用步骤
        //首先创建OkHttpClient的对象
        OkHttpClient client = new OkHttpClient();
        //通过创建Request对象将URL绑定
        Request request = new Request.Builder().get().url(address).build();
        //添加回调方法，这样OkHttp的使用就完成了，在需要访问网络时只需要调用这个方法就可以了
        client.newCall(request).enqueue(callback);

    }

    private void getDataAsync() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://192.168.137.212:8080/moments")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("kwwl","获取数据成功了");
                    Log.d("kwwl","response.code()=="+response.code());
                    Log.d("kwwl","response.body().string()=="+response.body().string());

                }
            }
        });
    }

    public static void OkHttpRequestGet(String address)
    {
        String result;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).get().build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i("WY","打印GET响应的数据：" + response.body().string());
                        final String result=response.body().string();  //得到返回的json字符串
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void OkHttpRequestPost(String address,String json)
    {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(address).post(body).build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    if (response.isSuccessful())
                    {
                        Log.i("wy","打印POST"+response.body().string());
                    }else
                    {
                        throw new IOException("Unexected code"+response);
                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
