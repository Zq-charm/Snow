package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jon.snow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapters.ActivityItemViewBinder;
import Entity.Activity;
import Entity.ActivityItem;
import Entity.User;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class FujinFragment_New extends Fragment
{
    private MultiTypeAdapter adapter;
    private Items items;
    private List<ActivityItem> activityItemList = new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fujin_fragment_new,null);

        // 获取RecyclerView对象
        final RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.new_recycler_view); ;



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
            user.setDisplayName("福克斯");
            Date date1 = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String createTime = simpleDateFormat.format(date1);
            Activity activity = new Activity(user,"南京地下freestyle比赛"+i,createTime,"活动日期：2019/9/9 10:00pm","江宁区-百家湖","5km","64/99","csc,jonyj,活死人等说唱实力唱将汇聚一堂");
            ActivityItem activityItem = new ActivityItem(activity);
            activityItemList.add(activityItem);
        }
        adapter.setItems(activityItemList);
        adapter.notifyDataSetChanged();

        return view;
    }
}
