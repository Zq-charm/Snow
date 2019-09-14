package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import Entity.Moment;

import com.jon.snow.R;

import java.util.List;

public class MomentAdapter extends RecyclerView.Adapter<Adapters.MomentAdapter.ViewHolder> {

        private Context context;
        private List<Moment> data;

        public MomentAdapter(Context context,List<Moment> data)
        {
            this.context = context;
            this.data = data;
        }

        @Override
        public Adapters.MomentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.moment_card,parent,false);
            return new Adapters.MomentAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(Adapters.MomentAdapter.ViewHolder holder, int position)
        {
            if(data.get(position).getImageId()!=0)
            {


                Glide.with(holder.itemView.getContext())
                        .load(data.get(position).getImageId())
                        .into(holder.image);
            }
            else
            {
//
                Glide.with(holder.itemView.getContext())
                        .load(data.get(position).getImageUrl())
                        .into(holder.image);
            }
        }

        @Override
        public int getItemCount()
        {
            Log.i("MomentSize" , "Msize"+data.size());
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView image;
            public ViewHolder(View itemView)
            {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image_moment);
            }
        }
    }

