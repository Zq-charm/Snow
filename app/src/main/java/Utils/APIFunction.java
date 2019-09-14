package Utils;

import android.database.Observable;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIFunction {


    //上传单张图片
    @POST("服务器地址")
    Observable<Object> imageUpload(@Part() MultipartBody.Part img);
    //上传多张图片
    @POST("服务器地址")
    Observable<Object> imagesUpload(@Part() List<MultipartBody.Part> imgs);
}