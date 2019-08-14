package Utils;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * url不变而图片变化的情况，使用此类加载
 */

public class GlideUtil {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5L, TimeUnit.SECONDS)
            .build();
    //  目前将lastModified保存在内存中，也就是每次打开app都会刷新图片，如有必要，可以保存在本地磁盘中
    private static HashMap<String,String> timeMap = new HashMap<>();
    public static void load(Context context, String url, ImageView imageView, int defaultResId){
        String lastModified = timeMap.get(url);
        if (lastModified == null) lastModified = "";
        request(context,url,imageView,lastModified,defaultResId);
    }

    private static void request(final Context context
            , final String url
            , final ImageView imageView
            , final String lastModified
            , final int defaultResId){

        Request request = new Request.Builder()
                .addHeader("If-Modified-Since",lastModified)
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(context)
                                .load(url)
                                .signature(new StringSignature(lastModified))
                                .error(defaultResId)
                                .into(imageView);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                StringSignature signature = null;
                if (code == 304){
                    // 说明图片没变
                    signature = new StringSignature(lastModified);
                }else if(code /100 == 2){
                    // 图片变了
                    final String newTime = response.header("Last-Modified");
                    timeMap.put(url,newTime);
                    signature = new StringSignature(newTime);

                }
                final StringSignature stringSignature = signature;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DrawableTypeRequest<String> load = Glide.with(context)
                                .load(url);
                        if(stringSignature != null){
                            load.signature(stringSignature)
                                    .error(defaultResId)
                                    .into(imageView);
                        }else{
                            load.error(defaultResId)
                                    .into(imageView);
                        }


                    }
                });
            }
        });
    }

}
