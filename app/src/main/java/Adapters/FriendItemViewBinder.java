package Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;

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
import com.jon.snow.App;
import com.jon.snow.ChatMessageActivity;
import com.jon.snow.LoginActivity;
import com.jon.snow.R;
import com.jon.snow.RegisterActivity;

import Entity.Friend;
import me.drakeet.multitype.ItemViewBinder;

import static com.jon.snow.App.getContext;

public class FriendItemViewBinder extends ItemViewBinder<Friend, FriendItemViewBinder.FriendHolder>
{
    public final @Nullable
    View.OnClickListener listener;

    public FriendItemViewBinder(@NonNull View.OnClickListener listener){this.listener = listener;}
    public @NonNull
    View.OnClickListener getListener() {
        return listener;
    }

    static class FriendHolder extends RecyclerView.ViewHolder
    {
         @NonNull
         final TextView fri_name;
         @NonNull final TextView fri_text;
         @NonNull final ImageView fri_face;
         @NonNull final TextView msg_time;

        FriendHolder(@NonNull View itemView)
        {
            super(itemView);
            this.fri_name = (TextView) itemView.findViewById(R.id.friend_name);
            this.fri_text = (TextView) itemView.findViewById(R.id.friend_message);
            this.fri_face = (ImageView) itemView.findViewById(R.id.friend_face);
            this.msg_time = (TextView) itemView.findViewById(R.id.msg_time);
        }
    }

    @NonNull @Override
    protected FriendHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        View root = inflater.inflate(R.layout.friendmessage,parent,false);
        return new FriendHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull FriendHolder holder,@NonNull Friend friend)
    {
        holder.fri_name.setText(friend.friend.getDisplayName());
        holder.fri_text.setText(friend.fri_text);
        Log.d("positionFrienView","="+getPosition(holder));
        Intent intentFriendIdinFri = new Intent();
        intentFriendIdinFri.putExtra("messagePosId",getPosition(holder));

        Glide.with(holder.itemView.getContext())
                .load(friend.friend.getAvatar())
                .into(holder.fri_face);
       // holder.fri_face.setImageResource(friend.friend.getAvatar());
        holder.msg_time.setText(friend.recive_time);
        holder.fri_text.setOnClickListener(listener);
        holder.fri_face.setOnClickListener(listener);


    }
}
