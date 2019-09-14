package Fragments;

import android.app.ActionBar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jon.snow.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.ActivityItemViewBinder;
import Adapters.MyselfItemViewBinder;
import Bases.BaseFragment;
import Entity.ActivityItem;
import Entity.MyselfItem;
import Entity.User;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public  class MyselfFragment extends Fragment
{
    private MultiTypeAdapter adapter;
    private Items items;
    private List<MyselfItem> myselfItemList = new ArrayList();
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.myself_fragment,container,false);
        // 获取RecyclerView对象
        final RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.myself_recycler_view); ;
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.myself_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.myself_coordinatorlayout);

        // 创建线性布局管理器（默认是垂直方向）
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // 为RecyclerView指定布局管理对象
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MultiTypeAdapter();
        adapter.register(MyselfItem.class,new MyselfItemViewBinder());
        recyclerView.setAdapter(adapter);

        User user = new User();
        user.setDisplayName("库里zzzzq");
        user.setFansNumber(19999);
        user.setFollowsPeopleNumber(520);
        user.setAge(21);
        user.setEmotion("单身");
        user.setHomeTown("内蒙古巴彦淖尔市");
        user.setNowLocation("南京市江宁区");
        user.setSex("男");

        MyselfItem myselfItem = new MyselfItem(user);
        myselfItemList.add(myselfItem);
        adapter.setItems(myselfItemList);
        adapter.notifyDataSetChanged();
        return view;
    }



}
