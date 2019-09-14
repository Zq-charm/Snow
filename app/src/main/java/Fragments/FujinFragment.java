package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jon.snow.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.fujinFragmentAdapter;
import Bases.BaseFragment;

public  class FujinFragment extends Fragment
{
    private List<Fragment> mFragments;
    private fujinFragmentAdapter fjAdapter;
    private ViewPager viewPager;
    private Context context;
    private TabLayout tabLayout;
    private String[] titles = {"推荐","关注","附近"};
    private Toolbar mToolbar;
    @Nullable
    private AppCompatActivity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fujin_fragment, null);
        setHasOptionsMenu(true);
        context = getActivity();
        viewPager = view.findViewById(R.id.viewPager_fujin);
        tabLayout = view.findViewById(R.id.tab_layout_fujin);
        init();
        return view;
    }

    private void init()
    {
        mFragments = new ArrayList<>();
        mFragments.add(new FujinFragment_Popular());
        mFragments.add(new FujinFragment_Follow());
        mFragments.add(new FujinFragment_New());
        fjAdapter = new fujinFragmentAdapter(getChildFragmentManager(),titles,mFragments,context);
        viewPager.setAdapter(fjAdapter);
        tabLayout.setupWithViewPager(viewPager);

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
        mToolbar = mActivity.findViewById(R.id.tool_bar_fujin);
       // mActivity.setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.search_menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu ,MenuInflater inflater) {
        mToolbar.getMenu().clear();
        inflater.inflate(R.menu.search_menu,menu);  //search_menu是在menu里定义的，
        MenuItem item = menu.findItem(R.id.search_view_menu); //search_menu.xml的一个对应的item的id
        final SearchView searchView  = (SearchView) MenuItemCompat.getActionView(item);
        //一进入便自动获得焦点
        searchView.setIconified(false);
        //true为让SearchView显示为一个 搜索图标，点击才展开输入框
        searchView.setIconifiedByDefault(true);
        //显示提交按钮
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("搜索感兴趣的帖子和用户");//显示提示
        //设置SearchView的 EditTxt， search_src_text为自带的id标志
        SearchView.SearchAutoComplete st = searchView.findViewById(R.id.search_src_text);
        st.setHintTextColor(getResources().getColor(android.R.color.black)); //设置银色
        st.setTextColor(getResources().getColor(android.R.color.black));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { //搜索提交
//                key = query;
//                mBookInfos.clear();
//                search(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

}
