package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jon.snow.R;

import java.util.Date;

import Entity.ActivityItem;
import me.drakeet.multitype.ItemViewBinder;

public class ActivityItemViewBinder extends ItemViewBinder<ActivityItem, ActivityItemViewBinder.ActivityHolder>
{
    static class ActivityHolder extends RecyclerView.ViewHolder
    {
        private @NonNull final ImageView userFace;
        private @NonNull final TextView actTitle;
        private @NonNull final ImageView man;
        private @NonNull final ImageView woman;
        private @NonNull final TextView createTime;
        private @NonNull final TextView senderName;
        private @NonNull final TextView date;
        private @NonNull final TextView content;
        private @NonNull final TextView place;
        private @NonNull final TextView peopleNum;
        private @NonNull final TextView distance;

        ActivityHolder(@NonNull View itemView)
        {
            super(itemView);
            this.userFace = (ImageView)itemView.findViewById(R.id.act_face);
            this.actTitle = (TextView) itemView.findViewById(R.id.act_title);
            this.man = (ImageView) itemView.findViewById(R.id.man);
            this.woman = (ImageView) itemView.findViewById(R.id.woman);
            this.createTime=(TextView) itemView.findViewById(R.id.act_creatTime);
            this.senderName=(TextView) itemView.findViewById(R.id.act_id);
            this.date = (TextView) itemView.findViewById(R.id.act_date);
            this.content = (TextView) itemView.findViewById(R.id.act_cotent);
            this.place = (TextView)itemView.findViewById(R.id.act_location);
            this.peopleNum = (TextView) itemView.findViewById(R.id.act_peopleNum);
            this.distance = (TextView) itemView.findViewById(R.id.act_distance);
        }
    }

    @NonNull @Override
    protected ActivityHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        View root = inflater.inflate(R.layout.activity_item,parent,false);
        return new ActivityHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ActivityHolder holder,@NonNull ActivityItem activityItem)
    {
        holder.userFace.setImageResource(R.drawable.curryface);
        holder.actTitle.setText(activityItem.activity1.getTitle());
        holder.senderName.setText(activityItem.activity1.getUser().getDisplayName());
        holder.createTime.setText(activityItem.activity1.getCreateTime());
       // holder.man.setImageResource(activityItem);
        //holder.woman.setImageResource();
        holder.date.setText(activityItem.activity1.getDate());
        holder.content.setText(activityItem.activity1.getActivity_content());
        holder.place.setText(activityItem.activity1.getPlace());
        holder.peopleNum.setText(activityItem.activity1.getPeopleNum());
        holder.distance.setText(activityItem.activity1.getDistance());
    }

}
