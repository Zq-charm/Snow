package com.jon.snow;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import Fragments.*;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

//import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entity.Moment;
import Fragments.ColorsFragment;
import Fragments.FollowFragment;

import Utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int PERMISSIONS_REQUEST_CODE = 24;
//    static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//    }
    public List<Moment> data=new ArrayList<>();
    private long exitTime;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Fragment fragment = new Fragment();
    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;
    private ArrayList<String> titles ;
    private ArrayList<Fragment> fragments;
    private int[] Vp_icons={R.drawable.world_talk,R.drawable.fujin,R.drawable.follow,R.drawable.search};

//
//    private SwipeRefreshLayout swipeRefreshLayout; //下拉刷新逻辑

 //   private BottomNavigationView mBNB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(savedInstanceState!= null){
//            String FRAGMENTS_TAG = "android:support:fragments";
//            savedInstanceState.remove(FRAGMENTS_TAG);
//        }
        closeAndroidPDialog();
        hasPermission();
        if (data==null)
        {
            Log.d("初始化","准备初始化");
         //   initData();
            getHttpMoments();
        }
        else
        {
            Log.d("获取数据","开始读取");
       //     initData();
            getHttpMoments();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Context mContext =  this.getApplicationContext();
        Resources resources = mContext.getResources();


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);



        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        mToolbar = findViewById(R.id.tool_bar);
//        setSupportActionBar(mToolbar);
        titles = new ArrayList<>();
        fragments = new ArrayList<>();

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.ViewPager);
        fragments.add(new ColorsFragment());
        fragments.add(new FujinFragment());
        fragments.add(new FollowFragment());
        fragments.add(new MyselfFragment());


        //访问获得
        setMomentList(data);
        titles.add("首页");
        titles.add("广场");
        titles.add("朋友");
        titles.add("自己");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }

        //ViewPager与fragment的绑定
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }



        });
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.getTabAt(i).setIcon(Vp_icons[i]);
            mTabLayout.getTabAt(i).setText(titles.get(i));
        }
        fragment = showNowFragment();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*
        侧滑窗口
         */

        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        View view_header = navView.inflateHeaderView(R.layout.slidingdatacard);
        view_header.findViewById(R.id.icon_image).setOnClickListener(this);
        navView.setCheckedItem(R.id.sil_moment);//默认选中项
        navView.setItemIconTintList(null);//显示图标
       navView.setCheckedItem(R.id.sil_friends);
//        navView.setCheckedItem(R.id.sil_setting);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.sil_friends:
                        Toast.makeText(MainActivity.this, "Friends", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sil_moment:
                        Toast.makeText(MainActivity.this, "Moment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sil_setting:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();

                        switchFragment(R.id.ViewPager,new SettingFragment()).commit();
                        mDrawerLayout.closeDrawers();//关闭抽屉
                        break;
                    default:
                }
                return true;
            }
        });

    }
    private void initData()//模拟数据
    {
        List<Moment> list = Arrays.asList(
//                new Moment("This is moment", R.drawable.angry, 99, 520, "1", R.drawable.ic_useface_1, 0, 1,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_1","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/angry"),
//                new Moment("This is happy", R.drawable.happy, 98, 13, "2", R.drawable.ic_useface_2, 1, 1,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_2","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/happy"),
//                new Moment("This is lonly", R.drawable.lonly, 100, 14, "3", R.drawable.ic_useface_3, 2, 2,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_3","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/lonly"),
//                new Moment("This is qaq", R.drawable.sad, 10, 24, "4", R.drawable.ic_useface_4, 3, 4,"C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/ic_useface_4","C:/Users/JJJJ/AndroidStudioProjects/snow/app/src/main/res/drawable/sad")
                new Moment("This is moment", R.drawable.angry, 99, 520, 1, R.drawable.ic_useface_1, 0, "1","http://49.232.63.6:8080/momentpics/ic_useface_1.xml","http://49.232.63.6:8080/momentpics/angry.png"),
                new Moment("This is happy", R.drawable.happy, 98, 13, 2, R.drawable.ic_useface_2, 1, "1","http://49.232.63.6:8080/momentpics/ic_useface_2.xml","http://49.232.63.6:8080/momentpics/happy.png"),
                new Moment("This is lonly", R.drawable.lonly, 100, 14, 3, R.drawable.ic_useface_3, 2, "2","http://49.232.63.6:8080/momentpics/ic_useface_3.xml","http://49.232.63.6:8080/momentpics/lonly.png"),
                new Moment("This is qaq", R.drawable.sad, 10, 24, 4, R.drawable.ic_useface_4, 3, "4","http://49.232.63.6:8080/momentpics/ic_useface_4.xml","http://49.232.63.6:8080/momentpics/sad.png")
        );
        for (int i = 0; i<list.size(); i++) {
            Gson gson = new Gson();
            String jsonObject = gson.toJson(list.get(i));
            Log.d("MomentJson","MomJson错" + jsonObject);
            new Thread()
            {
                @Override
                public void run()
                {
                    super.run();
                    try
                    {
                        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
                        HttpUtil.OkHttpRequestPost("http://49.232.63.6:8080/addmoment", jsonObject);//"http://192.168.0.100:8080/addmoment"
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Log.d("MomentJson","Post错" + jsonObject);
                    }
                }
            }.start();

        }


    }

    public List<Moment> getMomentList()
    {
        return data;
    }

    public void setMomentList(List<Moment> data)
    {
        this.data = data;
    }
    private void getHttpMoments()
    {
        // data= new ArrayList<>();
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
        HttpUtil.sendOkHttpRequest("http://49.232.63.6:8080/moments", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("失败","GET失败");
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
                data = gson.fromJson(responseString,new TypeToken<List<Moment>>(){}.getType());
                //这用province中就有了[]中的所有数据，下面遍历就可以了
                if (data.size()<=0)
                {
                    Log.d("错了","GET时data无对象");
                }
                for(Moment moment:
                        data){
                    Log.e("Moment", "json数组: "+moment.getImageUrl());
                }

            }
        });

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }
    private Fragment showNowFragment()
    {
        Fragment fragment = new Fragment();
        //获取当前显示的fragment
        for (int i = 0; i < fragments.size(); i++)
        {
            fragment = fragments.get(i);
            if (fragment != null && fragment.isAdded() && fragment.isVisible())
            {
                if (i==0)//如果是首页，则刷新数据
                {
                    getHttpMoments();
                }
                break;
            }
        }
        return fragment;
    }



    private FragmentTransaction switchFragment(int FragmentViewId,Fragment targetFragment)
    {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded())
        {


            if (fragment != null) {
                transaction.hide(fragment);
            }
            transaction.add(FragmentViewId, targetFragment, targetFragment.getClass().getName());
        }
        else
        {
           transaction.hide(fragment).show(targetFragment);
        }
        fragment = targetFragment;
        return transaction;
    }
    /**
     * 重写返回键，实现双击退出效果
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.icon_image:
                Intent intent_userface = new Intent(MainActivity.this, UserFaceActivity.class);
                startActivity(intent_userface);
            default:
                break;
        }
    }
    /**
     * 获取权限
     * @return
     */
    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_CODE);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "开启拍照权限陈工", Toast.LENGTH_SHORT).show();
                }
                break;
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

    //解决Detected problewm with API compatibility
    private void closeAndroidPDialog(){
        if (Build.VERSION.SDK_INT >= 28){

            try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog(){
        if (Build.VERSION.SDK_INT < 28)return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
