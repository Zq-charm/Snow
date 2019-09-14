package Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jon.snow.MainActivity;
import com.jon.snow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import Adapters.ActivityItemViewBinder;
import Adapters.PopularRecyclerAdapter;
import Entity.Activity;
import Entity.ActivityItem;
import Entity.Article;
import Entity.Moment;
import Entity.User;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class FujinFragment_Popular extends Fragment
{
    private MultiTypeAdapter adapter;
    private Items items;
    private List<ActivityItem> activityItemList = new ArrayList();

    List<String> listLabel;
    private LinearLayout linearLayoutLabel;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
    Date date =new Date();

    List<Article> list = Arrays.asList(
            new Article("firstTitle","firstContent",date,100,10,"love","C:/Users/JJJJ/Desktop/snow/image_id.png"),
            new Article("SecondTitle","sendContent",date,190,11,"lo","C:/Users/JJJJ/Desktop/snow/friends.png"),
            new Article("ThirdTitle","thirdContent",date,199,635,"loxx","C:/Users/JJJJ/Desktop/snow/moment.png"),
            new Article("FourTitle","fourContent",date,999,199,"fox","C:/Users/JJJJ/Desktop/snow/curry.png")
    );

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fujin_fragment_popular,null);

        linearLayoutLabel = (LinearLayout) view.findViewById(R.id.horizontal_label);

        //添加标签内容
        listLabel = new ArrayList<String>();
        String basketball_label = "篮球";
        String football_label = "足球";
        String movie = "电影";
        String club = "Club";
        String ktv = "Ktv";
        String date = "约会";
        listLabel.add(basketball_label);
        listLabel.add(football_label);
        listLabel.add(movie);
        listLabel.add(club);
        listLabel.add(ktv);
        listLabel.add(date);

        //初始化标签
        initMarksView();

        // 获取RecyclerView对象
        final RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.popular_recycler_view); ;



        // 创建线性布局管理器（默认是垂直方向）
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // 为RecyclerView指定布局管理对象
        recyclerView.setLayoutManager(layoutManager);
        // 创建Adapter
        // final PopularRecyclerAdapter popularRecyclerAdapter = new PopularRecyclerAdapter();
        //popularRecyclerAdapter.articleAdapter(list);
        // 填充Adapter
        // recyclerView.setAdapter(popularRecyclerAdapter);
        adapter = new MultiTypeAdapter();
        adapter.register(ActivityItem.class,new ActivityItemViewBinder());
        recyclerView.setAdapter(adapter);

        for (int i =0;i<20;i++)
        {
            User user = new User();
            user.setDisplayName("Stephen_curry");
            Date date1 = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String createTime = simpleDateFormat.format(date1);
            Activity activity = new Activity(user,"打篮球在线约球啦啦啦"+i,createTime,"活动日期：2019/9/9 10:00am","南京工程学院-江宁校区-东区篮球场","1.41km","5/10","球场史蒂芬霍金在线约球，自带酒水，球已备好");
            ActivityItem activityItem = new ActivityItem(activity);
            activityItemList.add(activityItem);
        }
        adapter.setItems(activityItemList);
        adapter.notifyDataSetChanged();
        return view;
    }

    //初始化标签
    private void initMarksView() {
        for (int i = 0; i < listLabel.size(); i++) {
            View view = View.inflate(getActivity(), R.layout.label, null);
            TextView tv = (TextView) view.findViewById(R.id.labelView1);
            tv.setText(listLabel.get(i));
            tv.setTag(i);
            view.setTag(false);
            // 设置view的点击事件，与onClick中的View一致
            //否则需要在onClick中，去findViewById，找出设置点击事件的控件进行操作
            //若不如此，则无法触发点击事件
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    TextView tv = (TextView) v.findViewById(R.id.labelView1);
                    Log.i("dxl", "TextView click");
                    if ((Boolean) v.getTag()) {
                        v.setTag(false);
                        tv.setEnabled(false);
                        Toast.makeText(getActivity(), "你取消了选择标签" + tv.getTag(), Toast.LENGTH_SHORT).show();
                    } else {
                        v.setTag(true);
                        tv.setEnabled(true);
                        Toast.makeText(getActivity(), "你选择了标签" + tv.getTag(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            linearLayoutLabel.addView(view);
        }
    }

}
