package Fragments;

import android.app.ActionBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jon.snow.CreateMoment;
import com.jon.snow.R;
import com.jon.snow.RegisterActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import Adapters.ActivityItemViewBinder;
import Adapters.MomentItemViewBinder;
import Adapters.MyGlideEngine;
import Adapters.MyselfItemViewBinder;
import Bases.BaseFragment;
import Entity.ActivityItem;
import Entity.Moment;
import Entity.MomentItem;
import Entity.MyselfItem;
import Entity.User;
import Utils.HttpUtil;
import Utils.MD5Utils;
import Utils.UploadUtil;
import Utils.UploadUtil1;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;

public  class MyselfFragment extends Fragment
{
    private static final int REQUEST_CODE_CHOOSE = 1;

    private static final int REQUEST_CODE_BACKGROUND =2;

    private static final int REQUEST_CODE_FACE =3;
    private View mView;
    private MultiTypeAdapter adapter;
    private Items items;
    private List<MyselfItem> myselfItemList = new ArrayList();
    private String userJson;
    private ImageView background;
    private String[] imageName;
    private String imageNamelast;
    private String imageNamelastface;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
     //   mView = getActivity().getLayoutInflater().inflate(R.layout.activity_main, null);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.myself_fragment,container,false);
        background = (ImageView) view.findViewById(R.id.myself_background);

        // 获取RecyclerView对象
        final RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.myself_recycler_view); ;
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.myself_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.myself_coordinatorlayout);


        // 创建线性布局管理器（默认是垂直方向）
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        // 为RecyclerView指定布局管理对象
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MultiTypeAdapter();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // ...
                switch (v.getId())
                {
                    case R.id.myself_background:
                        Matisse.from(MyselfFragment.this)
                            .choose(MimeType.ofImage())//图片类型
                            .countable(true)//true:选中后显示数字;false:选中后显示对号
                            .maxSelectable(1)//可选的最大数
                            .capture(true)//选择照片时，是否显示拍照
                            .captureStrategy(new CaptureStrategy(true, "com.jon.snow.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            .imageEngine(new MyGlideEngine())//图片加载引擎
                            .forResult(REQUEST_CODE_BACKGROUND);//
                        Toast.makeText(v.getContext(), "背景图片在fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myself_face:
                        Matisse.from(MyselfFragment.this)
                                .choose(MimeType.ofImage())//图片类型
                                .countable(true)//true:选中后显示数字;false:选中后显示对号
                                .maxSelectable(1)//可选的最大数
                                .capture(true)//选择照片时，是否显示拍照
                                .captureStrategy(new CaptureStrategy(true, "com.jon.snow.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                .imageEngine(new MyGlideEngine())//图片加载引擎
                                .forResult(REQUEST_CODE_FACE);//
                        Toast.makeText(v.getContext(), "头像在fragment", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        adapter.register(MyselfItem.class).to(
                new MyselfItemViewBinder(listener),
                new MomentItemViewBinder()
        ).withClassLinker((position, data) -> {
            if (data.type == MyselfItem.TYPE_Moment) {
                return MomentItemViewBinder.class;
            } else {
                return MyselfItemViewBinder.class;
            }
        });


//        adapter.register(MyselfItem.class,new MyselfItemViewBinder());
//        adapter.register(MomentItem.class,new MomentItemViewBinder());
        recyclerView.setAdapter(adapter);

        Bundle bundle= MyselfFragment.this.getArguments();
        String userName = bundle.getString("userName");
        String passWord = bundle.getString("passWord");
        Log.d("最终取得的用户名:",userName);


        User user = new User();
        user.setDisplayName("库里zzzzq");
        user.setFansNumber(19999);
        user.setFollowsPeopleNumber(520);
        user.setAge(21);
        user.setEmotion("单身aaa");
        user.setHomeTown("内蒙古巴彦淖尔市");
        user.setNowLocation("南京市江宁区");
        user.setSex("男");


        getUserFromBack(userName);

        SharedPreferences sp=getActivity().getSharedPreferences("MyselfJson", Context.MODE_MULTI_PROCESS);
        Log.d("从sp中获取用户json",":"+sp.getString("UserJson" , ""));
       String jsonFromuser = sp.getString("UserJson" , "");




        if (jsonFromuser!="")//从本地sp拿用户信息
       {
           User userJ = new User();
           userJ = new Gson().fromJson(jsonFromuser,User.class);

           OkHttpClient client=new OkHttpClient();
           Request request = new Request.Builder()
                   .url("http://"+R.string.ip+":"+R.string.port+"/users/"+userJ.getId())
                   .get()
                   .build();
           try{
               Response response = client.newCall(request).execute();
               //System.out.println(response.body().string());//这里就可以打印返回的结果了
               Log.d("response.body获取网络用户","="+response.body().string());
           }catch (Exception e){
               Log.i("json------", e.getMessage()+"/"+e.getCause());
           }



           Log.d("User的backgrond",""+userJ.getBackGround());
           MyselfItem myselfItem = new MyselfItem(userJ,1);
           myselfItemList.add(myselfItem);
           Intent data = new Intent();
           data.putExtra("userName", userName);
           getActivity().setResult(RESULT_OK, data);
       }else
       {
           MyselfItem myselfItem = new MyselfItem(user,1);
           myselfItemList.add(myselfItem);
       }

        for (int i=0;i<10;i++)
        {
            Moment moment = new Moment();
            MyselfItem myselfItem1 = new MyselfItem(moment,2);
            myselfItemList.add(myselfItem1);
        }
        adapter.setItems(myselfItemList);
        adapter.notifyDataSetChanged();

//        MyselfItem myselfItem = new MyselfItem((User)adapter.getItems().get(0),1);

        //adapter.getItems().get(0)

        return view;
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        myselfItemList.clear();
    }

    private void getUserFromBack(String userName)
    {
        Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 2:
                        User user1 = (User)msg.obj;
                      Log.d("user.getDisplayName在myself",":"+user1.getDisplayName());

                        userJson = new Gson().toJson(user1);

                        Log.d("UserJson:",userJson);
                        SharedPreferences sp=getActivity().getSharedPreferences("MyselfJson",Context.MODE_MULTI_PROCESS);
                        //获取编辑器
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("UserJson",userJson);
                        editor.commit();

                        Log.d("保存用户名密码成功 get user from back","succees");
                        break;
                }
            }
        };

        getDataFromBack("http://"+R.string.ip+":"+R.string.port+"/users/name/"+userName,handler);

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
                        if (user == null)
                        {
                            Log.d("MyselfF user为null","");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("Psw",user.getPassword());
                        bundle.putString("UserName",user.getDisplayName());
                        Log.d("Psw在Myself",user.getPassword());
                        Log.d("UserName在Myself",user.getDisplayName());
                        Message message = new Message();
                        message.what=2;
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


    /**
     * 保存图片到本地
     *
     * @param name
     * @param bmp
     * @return
     */
    private String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BACKGROUND && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
            try {
                if (result != null) {
                    //if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                    //调用URL转file方法
                    File file = uri2File(result.get(0), data);

                    SharedPreferences sp = getActivity().getSharedPreferences("MyselfJson", Context.MODE_MULTI_PROCESS);
                    Log.d("从sp中获取用户json在onactivityresult", ":" + sp.getString("UserJson", ""));
                    String jsonFromuser = sp.getString("UserJson", "");

                    User userJ = new User();
                    userJ = new Gson().fromJson(jsonFromuser, User.class);

                    Log.d("Url转file地址：", "" + file.getAbsolutePath());
                    imageName = file.getAbsolutePath().split("/");
                    Log.d("文件名=", "" + imageName[imageName.length - 1]);
                    // imageName[imageName.length-1]=userJ.getId()+".jpg";
                    imageNamelast = imageName[imageName.length - 1];

                    adapter.setItems(myselfItemList);
                    adapter.notifyDataSetChanged();

                    //上传图片
                    UploadUtil1 uploadUtil1 = new UploadUtil1();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            uploadUtil1.uploadFile(file, "http://"+R.string.ip+":"+R.string.port+"/uploadAudio");//49.232.63.6 10.15.187.94
                        }
                    }).start();


                    //addimg(encode, file);

                    // }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferences sp = getActivity().getSharedPreferences("MyselfJson", Context.MODE_MULTI_PROCESS);
            Log.d("从sp中获取用户json在onactivityresult", ":" + sp.getString("UserJson", ""));
            String jsonFromuser = sp.getString("UserJson", "");

            User userJ = new User();
            userJ = new Gson().fromJson(jsonFromuser, User.class);
            //userJ.setBackGround(result.get(0).toString());
            if (imageNamelast != null) {
                userJ.setBackGround("http://"+R.string.ip+":"+R.string.port+"/myself/" + imageNamelast);
            }
            if (imageNamelastface != null) {
                userJ.setAvatar("http://"+R.string.ip+":"+R.string.port+"/myself/" + imageNamelastface);
            }
            MyselfItem myselfItem = new MyselfItem(userJ, 1);
            myselfItemList.set(0, myselfItem);
            Log.d("User更新", "aaa" + userJ.toString());
            //background.setImageURI(result.get(0));
            String userJtoJson = new Gson().toJson(userJ);
            Log.d("User更新json", "aaa" + userJtoJson);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
                    HttpUtil.OkHttpRequestPost("http://"+R.string.ip+":"+R.string.port+"/users", userJtoJson);
                }
            }).start();

        }
        if (requestCode == REQUEST_CODE_FACE && resultCode == RESULT_OK) //头像上传
        {
            List<Uri> result = Matisse.obtainResult(data);
            try {
                Log.d("进入头像上传", "=" + REQUEST_CODE_FACE);
                File file = uri2File(result.get(0), data);

                SharedPreferences sp = getActivity().getSharedPreferences("MyselfJson", Context.MODE_MULTI_PROCESS);
                Log.d("从sp中获取用户json在onactivityresult", ":" + sp.getString("UserJson", ""));
                String jsonFromuser = sp.getString("UserJson", "");

                User userJ = new User();
                userJ = new Gson().fromJson(jsonFromuser, User.class);

                Log.d("Url转file地址：", "" + file.getAbsolutePath());
                imageName = file.getAbsolutePath().split("/");
                Log.d("文件名=", "" + imageName[imageName.length - 1]);
                // imageName[imageName.length-1]=userJ.getId()+".jpg";
                imageNamelastface = imageName[imageName.length - 1];

                adapter.setItems(myselfItemList);
                adapter.notifyDataSetChanged();

                //上传图片
                UploadUtil1 uploadUtil1 = new UploadUtil1();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadUtil1.uploadFile(file, "http://"+R.string.ip+":"+R.string.port+"/uploadAudio");//49.232.63.6 10.15.187.94
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferences sp = getActivity().getSharedPreferences("MyselfJson", Context.MODE_MULTI_PROCESS);
            Log.d("从sp中获取用户json在onactivityresult", ":" + sp.getString("UserJson", ""));
            String jsonFromuser = sp.getString("UserJson", "");

            User userJ = new User();
            userJ = new Gson().fromJson(jsonFromuser, User.class);
            //userJ.setBackGround(result.get(0).toString());
            if (imageNamelast != null) {
                userJ.setBackGround("http://"+R.string.ip+":"+R.string.port+"/myself/" + imageNamelast);
            }
            if (imageNamelastface != null) {
                userJ.setAvatar("http://"+R.string.ip+":"+R.string.port+"/myself/" + imageNamelastface);
            }
            MyselfItem myselfItem = new MyselfItem(userJ, 1);
            myselfItemList.set(0, myselfItem);
            Log.d("User更新", "aaa" + userJ.toString());
            //background.setImageURI(result.get(0));
            String userJtoJson = new Gson().toJson(userJ);
            Log.d("User更新json", "aaa" + userJtoJson);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
                    HttpUtil.OkHttpRequestPost("http://"+R.string.ip+":"+R.string.port+"/users", userJtoJson);
                }
            }).start();
        }
    }





    private File uri2File(Uri uri,Intent data) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
            Log.d("img_path1","="+img_path);
        } else {
          //  img_path=Matisse.obtainPathResult(data).get(0);
//           try
//           {
//               int actual_image_column_index = actualimagecursor
//                       .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//               actualimagecursor.moveToFirst();
//               img_path = actualimagecursor
//                       .getString(actual_image_column_index);
//               Log.d("img_path2","="+img_path);
            img_path=Matisse.obtainPathResult(data).get(0);
//           }catch (Exception e)
//           {
//               e.printStackTrace();
//
//               img_path=Matisse.obtainPathResult(data).get(0);
//           }



        }
        File file = new File(img_path);

        return file;
    }


}
