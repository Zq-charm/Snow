package Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jon.snow.R;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import Adapters.PopularRecyclerAdapter;
import Entity.Article;
import Entity.Moment;

public class FujinFragment_Popular extends Fragment
{

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

        // 获取RecyclerView对象
        final RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.popular_recycler_view); ;


        // 创建线性布局管理器（默认是垂直方向）
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // 为RecyclerView指定布局管理对象
        recyclerView.setLayoutManager(layoutManager);
        // 创建Adapter
        final PopularRecyclerAdapter popularRecyclerAdapter = new PopularRecyclerAdapter();

        popularRecyclerAdapter.articleAdapter(list);

        // 填充Adapter
        recyclerView.setAdapter(popularRecyclerAdapter);

        return view;
    }






}
