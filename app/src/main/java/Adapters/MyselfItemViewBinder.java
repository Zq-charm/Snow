package Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jon.snow.App;
import com.jon.snow.CreateMoment;
import com.jon.snow.R;
//import com.tencent.qcloud.tim.uikit.component.video.listener.ClickListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;


import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import Entity.MyselfItem;
import Entity.User;
import Fragments.MyselfFragment;
import Utils.HttpUtil;
import me.drakeet.multitype.ItemViewBinder;

public class MyselfItemViewBinder extends ItemViewBinder<MyselfItem, MyselfItemViewBinder.MyselfHolder>
{
    public final @Nullable
    View.OnClickListener listener;

    public MyselfItemViewBinder(@NonNull View.OnClickListener listener){this.listener = listener;}
    public @NonNull
    View.OnClickListener getListener() {
        return listener;
    }
    static class MyselfHolder extends RecyclerView.ViewHolder
    {
        private @NonNull final TextView myUserName;
        private @NonNull final TextView myFollowUser;
        private @NonNull final ImageView myBackGround;
        private @NonNull final ImageView myFace;
        private @NonNull final TextView myLocation;
        private @NonNull final TextView myHomeTown;
        private @NonNull final TextView myEmotion;
        private @NonNull final TextView myAge;
        private @NonNull final TextView myFans;
        private @NonNull final TextView myStars;
        private @NonNull final TextView mySex;
        MyselfHolder(@NonNull View itemView)
        {
            super(itemView);
            this.myUserName = (TextView) itemView.findViewById(R.id.myself_user_name);
            this.myFollowUser = (TextView) itemView.findViewById(R.id.myself_user_follow);
            this.myBackGround = (ImageView)itemView.findViewById(R.id.myself_background);
            this.myFace = (ImageView)itemView.findViewById(R.id.myself_face);
            this.myLocation = (TextView) itemView.findViewById(R.id.myself_location);
            this.myHomeTown = (TextView) itemView.findViewById(R.id.myself_hometown);
            this.myEmotion = (TextView) itemView.findViewById(R.id.myself_emotion);
            this.myAge = (TextView) itemView.findViewById(R.id.myself_age);
            this.myFans = (TextView) itemView.findViewById(R.id.myself_user_fans);
            this.myStars = (TextView) itemView.findViewById(R.id.myself_stars);
            this.mySex = (TextView) itemView.findViewById(R.id.myself_sex);


        }
    }

    @NonNull @Override
    protected MyselfHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        View root = inflater.inflate(R.layout.myself_item,parent,false);
        return  new MyselfHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyselfHolder holder,@NonNull MyselfItem myselfItem)
    {
        holder.myUserName.setText(myselfItem.user.getName());
        holder.myFollowUser.setText("关注："+myselfItem.user.getFollowsPeopleNumber());

        //背景图片点击事件
      //  Uri uri=Uri.parse(myselfItem.user.getBackGround());
        Glide.with(holder.itemView.getContext())
                .load(myselfItem.user.getBackGround())
                .into(holder.myBackGround);
        try
        {
            InputStream is = (InputStream) new URL(myselfItem.user.getBackGround()).getContent();
            Drawable d = Drawable.createFromStream(is, "src");
            holder.myBackGround.setImageDrawable(d);
            is.close();
        }catch (Exception e)
        {
            Log.d("url转drawable失败","fail");
            e.printStackTrace();
        }

        //Drawable d=Drawable.createFromStream(getContentResolver().openInputStream(uri,null));


        //头像图片点击事件
        Uri uri1=Uri.parse(myselfItem.user.getAvatar());
        Glide.with(holder.itemView.getContext())
                .load(myselfItem.user.getAvatar())
                .into(holder.myFace);
        try
        {
            InputStream is = (InputStream) new URL(myselfItem.user.getAvatar()).getContent();
            Drawable d1 = Drawable.createFromStream(is, "src");
            holder.myFace.setImageDrawable(d1);
            is.close();
        }catch (Exception e)
        {
            Log.d("url转drawable失败","fail");
            e.printStackTrace();
        }



        holder.myLocation.setText("所在地"+myselfItem.user.getNowLocation());
        holder.myEmotion.setText("情感状况："+myselfItem.user.getEmotion());
        holder.myAge.setText("年龄："+myselfItem.user.getAge());
        holder.mySex.setText("性别："+myselfItem.user.getSex());
        holder.myFans.setText("粉丝数："+myselfItem.user.getFansNumber());
        holder.myStars.setText("获赞："+myselfItem.user.getStar());
        holder.myHomeTown.setText("家乡："+myselfItem.user.getHomeTown());
        holder.myBackGround.setOnClickListener(listener);//添加监听器
        holder.myFace.setOnClickListener(listener);
    }
}
