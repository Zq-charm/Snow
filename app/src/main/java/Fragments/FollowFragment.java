package Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jon.snow.ChatMessageActivity;
import com.jon.snow.R;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
//import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout;
//import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapters.FriendItemViewBinder;

import Entity.Friend;
import Entity.MyMessage;
import Entity.User;
import cn.jiguang.imui.messages.MsgListAdapter;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public  class FollowFragment extends Fragment {
    private MsgListAdapter<MyMessage> mAdapter;
    private AppCompatActivity mActivity;
    private Button button_chat;
    private Items items;

    private MultiTypeAdapter adapter;
    private List<User> friendList=new ArrayList<>();
    private List<User> friends = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.follow_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friends_list);
        adapter = new MultiTypeAdapter();


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // ...
                switch (v.getId())
                {
                    case R.id.friend_message:
                        Intent intentFriendId = getActivity().getIntent();
                        Integer itemPosId = intentFriendId.getIntExtra("messagePosId",0);

                         Friend friend = (Friend)adapter.getItems().get(itemPosId);
//                        Intent intentsendFriId = new Intent();
//                        intentsendFriId.putExtra("friendId",friend.friend.getId());


                        Intent intent=new Intent(view.getContext(), ChatMessageActivity.class);
                        intent.putExtra("FriendId",friend.friend.getId());
                        Log.d("friendId在FollowFrag中","="+friend.friend.getId());
                        startActivity(intent);
                        Toast.makeText(v.getContext(), "friend message在fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.friend_face:
                        Toast.makeText(v.getContext(), "头像在fragment", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        adapter.register(Friend.class, new FriendItemViewBinder(listener));
        recyclerView.setAdapter(adapter);
        //模拟数据
//        User user = new User();
//        user.setId("13");
//        user.setImageId(R.drawable.image_id);
//        user.setDisplayName("curry");
//        user.setSignaTure("i can do all things");
//        items = new Items();
//        List<Friend> friends = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Date date = new Date();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String recivedTime = simpleDateFormat.format(date);
//            Friend friend = new Friend(user, "love u gays" + i, recivedTime);
//            friends.add(friend);
//            items.add(friend);
//        }

        //获取当前登陆用户
        SharedPreferences sp = getActivity().getSharedPreferences("MyselfJson", Context.MODE_MULTI_PROCESS);
        Log.d("从sp中获取用户json", ":" + sp.getString("UserJson", ""));
        String jsonFromuser = sp.getString("UserJson", "");

        User userJ = new User();
        userJ = new Gson().fromJson(jsonFromuser, User.class);//json转User类


        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        User friend = (User) msg.obj;
                        friendList.add(friend);
                        Log.d("friendList在handler中","="+friendList.get(0).getId().toString());
                        break;
                        default:
                            User friend1 = (User) msg.obj;
                            friendList.add(friend1);
                            Log.d("friendList在handler中","="+friendList.get(1).getId().toString());
                            break;
                }
            }
        };
            //从后台获取数据
        getDataFromBack("http://"+R.string.ip+":"+R.string.port+"/getfriends/" + userJ.getId(), handler);

        while (true)
        {
            if (friendList.isEmpty())
            {

            }else
            {
                break;
            }
        }

        //填充数据
        items = new Items();
        if (friendList.isEmpty()!=true)
        {
            List<Friend> userFriends = new ArrayList<>();
            for (User friend:friendList)
            {
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String recivedTime = simpleDateFormat.format(date);
                Log.d("friend在item赋值","="+friend.getName());
                Friend friend1 = new Friend(friend,"test1",recivedTime);
                items.add(friend1);
            }
        }else
        {
            Log.d("friendList为空","!!!!!!!!!!!!");
        }
        adapter.setItems(items);
        adapter.notifyDataSetChanged();


        //设置消息监听器，收到新消息时，通过此监听器回调
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
                //消息的内容解析请参考消息收发文档中的消息解析说明
                return true; //返回true将终止回调链，不再调用下一个新消息监听器
            }
        });
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


    private void getDataFromBack(String address, Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(address)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String responseString = response.body().string();
                        Gson gson = new Gson();
                        friendList = gson.fromJson(responseString, new TypeToken<List<User>>() {
                        }.getType());
                        Log.d("friendsList","="+friendList.get(0).getId().toString());
                        //这用province中就有了[]中的所有数据，下面遍历就可以了
                        if (friendList.size() <= 0) {
                            Log.d("错了", "friend无对象");
                        }
                        int i = 3;
                        for (User user : friendList) {
                            Log.e("FriendList", "json数组: " + user.getId());
                            Message message = new Message();
                            message.what = i;
                            message.obj = user;
                            handler.sendMessage(message);
                           // i++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}