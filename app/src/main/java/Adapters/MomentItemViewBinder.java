package Adapters;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jon.snow.R;

import org.w3c.dom.Text;

import Entity.ActivityItem;
import Entity.Friend;
import Entity.Moment;
import Entity.MomentItem;
import Entity.MyselfItem;
import me.drakeet.multitype.ItemViewBinder;


public class MomentItemViewBinder extends ItemViewBinder<MyselfItem, MomentItemViewBinder.MomentHolder>
{
    static class MomentHolder extends RecyclerView.ViewHolder
    {
        @NonNull final ImageView momentIV;
        @NonNull final TextView userName;
        @NonNull final TextView momentTitle;
        @NonNull final TextView momentCreateDate;

        MomentHolder(@NonNull View itemView)
        {
            super(itemView);
            this.momentIV = (ImageView) itemView.findViewById(R.id.moment_iv);
            this.userName = (TextView) itemView.findViewById(R.id.moment_username);
            this.momentTitle = (TextView) itemView.findViewById(R.id.moment_title);
            this.momentCreateDate = (TextView) itemView.findViewById(R.id.moment_createdate);

        }
    }

    @NonNull @Override
    protected MomentHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        View root = inflater.inflate(R.layout.moment_item,parent,false);
        return new MomentHolder(root);
    }
    @Override
    protected void onBindViewHolder(@NonNull MomentHolder holder,@NonNull MyselfItem myselfItem)
    {

        RequestOptions options = new RequestOptions();
        options.fitCenter();
        Glide.with(holder.itemView.getContext()).load(R.drawable.curryface).apply(options).into(holder.momentIV);

        holder.userName.setText("curry");
        holder.momentTitle.setText("i can do all things");
        holder.momentCreateDate.setText("9月5日" );
       // holder.momentIV.setImageResource(R.drawable.curryface);

    }
}
