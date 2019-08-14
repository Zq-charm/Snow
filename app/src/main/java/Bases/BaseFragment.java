package Bases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jon.snow.R;

//
//public class BaseFragment extends Fragment {
//
//    public BaseFragment() {}
//
//    public static BaseFragment newInstance(String text){
//        Bundle bundle = new Bundle();
//        bundle.putString("text",text);
//        BaseFragment blankFragment = new BaseFragment();
//        blankFragment.setArguments(bundle);
//        return  blankFragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_blank, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        TextView textView = (TextView) view.findViewById(R.id.pager_text);
//        textView.setText(getArguments().getString("text"));
//    }
//}

//Fragment的生命周期
//onAttach()-->onCreate()-->onCreateView()-->>onViewCreated()-->>onDestroyView()-->>onDestroy()
public abstract class BaseFragment extends Fragment
{
    /*
    用户设置的ContenView
     */
    protected View mContentView;

    /*
    View有没有加载过
     */
    protected boolean isViewInitiated;

    /*
    页面是否可见
     */
    protected boolean isVisibleToUser;

    /*
    是否加载过
     */
    protected boolean isDataInitiated;

    private Activity mActivity;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mContentView = createContentView(container);
        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        loadData();
    }

    @Override
    public void onDestroyView()
    {
        unbindView(mContentView);
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        mActivity = null;
        mContentView = null;
        super.onDestroy();
    }

    /*
    创建View
     */
    private View createContentView(ViewGroup parent)
    {
        Object layout  = getContentLayout();
        View contentView = null;
        if (layout instanceof View)
        {
            contentView = (View) layout;
        }else if (layout instanceof Integer)
        {
            contentView = getLayoutInflater().inflate((Integer)layout,parent,false);
        }
        if (contentView == null)
        {
            new IllegalArgumentException("getContentLayout must View or LayoutId");
        }
        return contentView;
    }

    public abstract Object getContentLayout();

    /*
    1.初始化诗句，包括上个页面传递过来的数据在这个方法做
     */
    protected void initData(Bundle savedInstanceState)
    {

    }

    /*
    如果要创建标题
     */
    protected void initTitle()
    {

    }

    /*
    绑定View
     */
    protected void bindView(View contentView)
    {

    }

    /*
    初始化View
     */
    protected void initView(View contentView)
    {

    }

    /*
    初始化网络
     */
    protected void initNet()
    {

    }

    /*
    懒加载
     */
    private void loadData()
    {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated))
        {
            isDataInitiated = true;
            lazyLoad();
        }
    }

    /*
    懒加载，Fragment可见的时候调用这个方法，而且只调用一次
     */
    protected void lazyLoad()
    {

    }

    /*
    解绑contentView
     */
    protected void unbindView(View contentView)
    {

    }

    /*
    打开Activity
     */
    public final void startActivity(Class<?> clazz)
    {
        startActivity(clazz,null);
    }

    /*
    打开Activity
     */
    public final void startActivity(Class<?> clazz,@Nullable Bundle options)
    {
        Intent intent = new Intent(getAppActivity(),clazz);
        if (options != null)
        {
            intent.putExtras(options);
        }
        startActivity(intent);
    }

    /*
    获取当前的Activity
     */
    public final Activity getAppActivity()
    {
        return mActivity;
    }

    /*
    设置TextView
     */
    public void setText(TextView textView, CharSequence text)
    {
        if (textView != null && text != null)
        {
            textView.setText(text);
        }
    }

    /*
    关闭页面
     */
    public void finish()
    {
        if (mActivity != null)
        {
            mActivity.finish();
        }
    }
}
