package Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jon.snow.ChatMessageActivity;
import com.jon.snow.R;
import com.jon.snow.TUImessage;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapters.FriendItemViewBinder;

import Entity.Friend;
import Entity.MyMessage;
import Entity.User;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jiguang.imui.messages.ptr.PtrDefaultHeader;
import cn.jiguang.imui.messages.ptr.PtrHandler;
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout;
import cn.jiguang.imui.utils.DisplayUtil;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public  class FollowFragment extends Fragment
{
    private MsgListAdapter<MyMessage> mAdapter;
    private AppCompatActivity mActivity;
    private Button button_chat;
    private Items items;

    private MultiTypeAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.follow_fragment,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friends_list);

        adapter = new MultiTypeAdapter();
        adapter.register(Friend.class, new FriendItemViewBinder());
        recyclerView.setAdapter(adapter);

        User user = new User("520",R.drawable.image_id, "curry", "5220018", "775982750@qq.com", 153, "i can do all things", 999,999,999, 5 );

        items = new Items();
        List<Friend> friends = new ArrayList<>();
        for (int i=0;i<20;i++)
        {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String recivedTime = simpleDateFormat.format(date);
            Friend friend = new Friend(user,"love u gays"+i,recivedTime);
            friends.add(friend);
            items.add(friend);
        }

        adapter.setItems(items);
        adapter.notifyDataSetChanged();

//        button_chat = (Button)view.findViewById(R.id.button_chat);
//        button_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),TUImessage.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

}