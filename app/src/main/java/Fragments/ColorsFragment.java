package Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import Entity.Moment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jon.snow.CreateMoment;
import com.jon.snow.MainActivity;
import com.jon.snow.MapLocationActivity;
import com.jon.snow.MomentData;
import com.jon.snow.R;
import com.nightonke.boommenu.Animation.EaseEnum;
import com.nightonke.boommenu.BoomButtons.BoomButtonBuilder;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.MomentAdapter;
import Utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public  class ColorsFragment extends Fragment implements DiscreteScrollView.OnItemChangedListener,View.OnClickListener
{
    private BoomMenuButton boomMenuButton;
    private List<Moment>data = new ArrayList<>();

    private MomentData momentData;
    private ImageView currentMomentuserface;
    private TextView currentMomentId;
    private TextView currentMomentText;
    private ImageView button_star_moment;
    private DiscreteScrollView momentPicker;
    private InfiniteScrollAdapter infiniteScrollAdapter;

    private BoomMenuButton bmb;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle toggle;
    @Nullable
    private AppCompatActivity mActivity;
    @Nullable
    private DrawerLayout mDrawerLayout;
    private View view;
   // private List<Moment> moments;
    private HttpUtil httpUtil;




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
//        if(savedInstanceState!= null){
//            String FRAGMENTS_TAG = "android:support:fragments";
//            savedInstanceState.remove(FRAGMENTS_TAG);
//        }

        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {



       view =(LinearLayout) inflater.inflate(R.layout.colors_fragment,container,false);
        setHasOptionsMenu(true);




        bmb = (BoomMenuButton) view.findViewById(R.id.bmb_colors);


        //        initRecyclerView();
//
//        initData();
        data = ((MainActivity)getActivity()).getMomentList();
        if (data.size()<=0)
        {
            Log.d("错了","data没有传过对象");
        }
        return view;
    }





    private void initDiscreteScrollView()
    {
//        data=new ArrayList<>();
//        if (data==null)
//        {
//            initData();
//            getHttpMoments();
//        }
//        else
//        {
//            getHttpMoments();
//        }
//        //访问获得

      //  data = moment.getData();
     //   DiscreteScrollView scrollView = (DiscreteScrollView)view.findViewById(R.id.moment_picker);
       // scrollView.setAdapter(new MomentAdapter(getActivity(),data));
        currentMomentuserface=(ImageView)view.findViewById(R.id.moment_userface);
        currentMomentText = (TextView)view.findViewById(R.id.moment_text);
        currentMomentId = (TextView)view.findViewById(R.id.moment_id);
        button_star_moment =(ImageView)mActivity.findViewById(R.id.moment_star_button);
        if (button_star_moment == null)
        {
            System.out.println("无对象");
        }
        momentData = MomentData.get();

        momentPicker = (DiscreteScrollView) view.findViewById(R.id.moment_picker);
        momentPicker.setOrientation(DSVOrientation.HORIZONTAL);
        momentPicker.addOnItemChangedListener(this);
        infiniteScrollAdapter = InfiniteScrollAdapter.wrap(new MomentAdapter(mActivity,data));
        momentPicker.setAdapter(infiniteScrollAdapter);
      //  momentPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        momentPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        if (data.size()<=0)
        {
            Log.d("错了","data无对象");
        }
        onMomentChanged((Moment)data.get(0));

       // final LinearLayout linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.colors_fragment,null);


        mActivity.findViewById(R.id.moment_star_button).setOnClickListener(this);
        mActivity.findViewById(R.id.moment_follw).setOnClickListener(this);
        mActivity.findViewById(R.id.moment_comment).setOnClickListener(this);


    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) getActivity();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        initDiscreteScrollView();
//        button_star = (ImageView) mActivity.findViewById(R.id.moment_star);
//        button_comment = (ImageView)mActivity.findViewById(R.id.moment_comment);
//        currentMomentText = (TextView)mActivity.findViewById(R.id.moment_text);
//        currentMomentComment = (TextView) mActivity.findViewById(R.id.moment_comment_number);
//        currentMomentStar = (TextView)mActivity.findViewById(R.id.moment_stars_number);
//        // currentImageView = (ImageView) view.findViewById(R.id.moment_image);
//        currentMomentId = (TextView) mActivity.findViewById(R.id.moment_id);
        mToolbar = (Toolbar)mActivity.findViewById(R.id.toolbar_color);

        boomMenuButton = (BoomMenuButton)mActivity.findViewById(R.id.bmb);
        boomMenuButton.setShowMoveEaseEnum(EaseEnum.EaseOutBack);
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) { //监听BOOM
            switch (i) {
                case 0:
                    TextOutsideCircleButton.Builder builder0 = new TextOutsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Toast.makeText(mActivity, "create_moment", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mActivity, CreateMoment.class);    //跳转设置
                                    startActivity(intent);
                                    boomMenuButton.clearBuilders();
                                }
                            }).normalText("记录瞬间")
                            .normalImageRes(R.drawable.ic_create_moment_)
                            .shadowEffect(true)//设置阴影
                            .rotateImage(false);//图标可旋转

                    if (boomMenuButton.getBuilders().size()<=0)
                    {
                        boomMenuButton.addBuilder(builder0);
                        break;
                    }else
                    {
                        for (BoomButtonBuilder bbb :boomMenuButton.getBuilders())
                        {
                            if (bbb==builder0)
                            {
                                break;
                            }
                        }
                        boomMenuButton.addBuilder(builder0);
                        break;
                    }

                case 1:
                    TextOutsideCircleButton.Builder builder1 = new TextOutsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Toast.makeText(mActivity, "weather", Toast.LENGTH_SHORT).show();
                                 //   boomMenuButton.clearBuilders();
                                }
                            }).normalText("天气")
                            .normalImageRes(R.drawable.ic_show_weather);
                    if (boomMenuButton.getBuilders().size()<=0)
                    {
                        boomMenuButton.addBuilder(builder1);
                        break;
                    }else
                    {
                        for (BoomButtonBuilder bbb :boomMenuButton.getBuilders())
                        {
                            if (bbb==builder1)
                            {
                                break;
                            }
                        }
                        boomMenuButton.addBuilder(builder1);
                        break;
                    }

                case 2:
                    TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Toast.makeText(mActivity, "match friends", Toast.LENGTH_SHORT).show();
                                    Intent intent_location = new Intent(mActivity,MapLocationActivity.class);
                                    startActivity(intent_location);
                                    boomMenuButton.clearBuilders();
                                }
                            }).normalText("在线匹配好友")
                            .normalImageRes(R.drawable.ic_match_friends);
                    if (boomMenuButton.getBuilders().size()<=0)
                    {
                        boomMenuButton.addBuilder(builder2);
                        break;
                    }else
                    {
                        for (BoomButtonBuilder bbb :boomMenuButton.getBuilders())
                        {
                            if (bbb==builder2)
                            {
                                break;
                            }
                        }
                        boomMenuButton.addBuilder(builder2);
                        break;
                    }
                default:
            }

        }
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        bmb.setShowMoveEaseEnum(EaseEnum.EaseOutBack);
        bmb.clearBuilders();
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) { //监听BOOM
            switch (i) {
                case 0:
                    HamButton.Builder builder0 = new HamButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Toast.makeText(getActivity(), "问题反馈", Toast.LENGTH_SHORT).show();
                                    //bmb.clearBuilders();
                                }
                            }).normalText("问题反馈")
                            .normalImageRes(R.drawable.ic_create_moment_)
                            .shadowEffect(false)//设置阴影
                            .rotateImage(false);//图标可旋转
                    if (bmb.getBuilders().size()<=0)
                    {
                        bmb.addBuilder(builder0);
                        break;
                    }else
                    {
                        for (BoomButtonBuilder bbb :bmb.getBuilders())
                        {
                            if (bbb==builder0)
                            {
                                break;
                            }
                        }
                        bmb.addBuilder(builder0);
                        break;
                    }
                case 1:
                    HamButton.Builder builder1 = new HamButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Toast.makeText(getActivity(), "about", Toast.LENGTH_SHORT).show();
                                   // bmb.clearBuilders();
                                }
                            }).normalText("关于我们")
                            .normalImageRes(R.drawable.ic_show_weather);
                    if (bmb.getBuilders().size()<=0)
                    {
                        bmb.addBuilder(builder1);
                        break;
                    }else
                    {
                        for (BoomButtonBuilder bbb :bmb.getBuilders())
                        {
                            if (bbb==builder1)
                            {
                                break;
                            }
                        }
                        bmb.addBuilder(builder1);
                        break;
                    }
                case 2:
                    HamButton.Builder builder2 = new HamButton.Builder()
                            .listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Toast.makeText(getActivity(), "setting", Toast.LENGTH_SHORT).show();
                                   // bmb.clearBuilders();
                                }
                            }).normalText("设置")
                            .normalImageRes(R.drawable.ic_match_friends);
                    if (bmb.getBuilders().size()<=0)
                    {
                        bmb.addBuilder(builder2);
                        break;
                    }else
                    {
                        for (BoomButtonBuilder bbb :bmb.getBuilders())
                        {
                            if (bbb==builder2)
                            {
                                break;
                            }
                        }
                        bmb.addBuilder(builder2);
                        break;
                    }
                default:
            }

        }
        mDrawerLayout = (DrawerLayout)mActivity.findViewById(R.id.drawer_layout);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);////设置返回键可用
//           actionBar.setHomeAsUpIndicator(R.drawable.ic_caidan);
        }
        toggle = new ActionBarDrawerToggle(mActivity,mDrawerLayout,mToolbar,R.string.open,R.string.close)
        {   @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        toggle.syncState();
        mDrawerLayout.setDrawerListener(toggle);


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.moment_star_button:
                Toast.makeText(getActivity(), "点赞", Toast.LENGTH_SHORT).show();
                int realPosition = infiniteScrollAdapter.getRealPosition(momentPicker.getCurrentItem());
                Moment current = data.get(realPosition);
               // momentData.setStar(current.getPositionId(), !momentData.isStared(current.getPositionId()));
                momentData.setStar(current.getPositionId(), !momentData.isStared(current.getPositionId()));
                changeStarButtonState(current);
                break;
            case R.id.moment_comment:
                Toast.makeText(getActivity(), "评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.moment_follw:
                Toast.makeText(getActivity(), "follow", Toast.LENGTH_SHORT).show();
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }
    public Bitmap returnBitMap(String url)
    {URL myFileUrl = null;
    Bitmap bitmap = null;
    try
    {
        myFileUrl = new URL(url);
    } catch (MalformedURLException e)
    {
        e.printStackTrace();
    }try
    {
        HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
        conn.setDoInput(true);
       conn.connect();
       InputStream is = conn.getInputStream();
       bitmap = BitmapFactory.decodeStream(is);
      is.close();
    } catch (IOException e)
    {
        e.printStackTrace();
    }
    return bitmap;
    }


    private  void onMomentChanged(Moment moment)
    {
        if (moment.getMoment_userface()!=0)
        {
            currentMomentuserface.setImageResource(moment.getMoment_userface());
        } else
        {

            currentMomentuserface.setImageBitmap(returnBitMap(moment.getImageUrl()));
        }
        currentMomentText.setText(moment.getMoment_Text());
        currentMomentId.setText(moment.getMoment_id());
        changeStarButtonState(moment);
    }

    private void changeStarButtonState(Moment moment)
    {
        //final LinearLayout linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.colors_fragment,null);
        if (momentData.isStared(moment.getPositionId()))
        {

            button_star_moment.setImageResource(R.drawable.ic_star_black_24dp);
            button_star_moment.setColorFilter(ContextCompat.getColor(mActivity, R.color.shopRatedStar));
        } else {
            button_star_moment.setImageResource(R.drawable.ic_star_border_black_24dp);
            button_star_moment.setColorFilter(ContextCompat.getColor(mActivity, R.color.shopSecondary));
            }
    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder,int position)
    {
        int positionInDateSet = infiniteScrollAdapter.getRealPosition(position);
        onMomentChanged(data.get(positionInDateSet));
    }

    private void showUnsupportedSnackBar()
    {
        Snackbar.make(momentPicker, R.string.msg_unsupported_op, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity= null;
    }
}
