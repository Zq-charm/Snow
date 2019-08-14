package Factories;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import Bases.BaseFragment;
import Fragments.ColorsFragment;
import Fragments.FollowFragment;
import Fragments.FujinFragment;
import Fragments.MyselfFragment;

public class FragmentFactoury
{
    /*
    五彩斑斓
     */
    public static final int TAB_COLORS = 0;
    /*
    家长里短
     */
    public static final int TAB_FUJIN =1;
    /*
    情有独钟
     */
    public static final  int TAB_FOLLOW = 3;
    /*
    独善其身
     */
    public static final int TAB_MYSELF =4;

    private static Map<Integer,Fragment> mFragmentMap = new HashMap<>();


    public static Fragment createFragment(int index)
    {
        Fragment fragment = mFragmentMap.get(index);
      //  Fragment fragment = new Fragment();
        //如果之前没有创建，就创建新的
        if (fragment == null){
            switch (index){
                case TAB_COLORS:
                    fragment = new ColorsFragment();
                    break;
                case TAB_FUJIN:
                    fragment = new FujinFragment();
                    break;
                case TAB_FOLLOW:
                    fragment = new FollowFragment();
                    break;
                case TAB_MYSELF:
                    fragment = new MyselfFragment();
                    break;
            }
            //把创建的fragment存起来
            mFragmentMap.put(index,fragment);
        }
        return fragment;
    }
}
