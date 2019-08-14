package Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jon.snow.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Entity.Article;
import Utils.SetImageViewUtil;

public class PopularRecyclerAdapter extends RecyclerView.Adapter<PopularRecyclerAdapter.ViewHolder>
{

    private List<Article> mDatas;
    public void articleAdapter(List<Article> data)
    {
        this.mDatas = data;
    }
    // 用于创建控件
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int i)
    {

        // 获得列表项控件（LinearLayer对象）

        // list_basic_item.xml布局文件中只包含一个<LinearLayer>标签，在该标签中包含
        // 了一个<TextView>标签
        //  item是LinearLayout对象
        View item = LayoutInflater.from(parentViewGroup.getContext()).inflate(
                R.layout.article_item, parentViewGroup, false);

        return new ViewHolder(item);

    }

    public Bitmap returnBitMap(String url)
    {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try
        {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }try
        {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }


    // 为控件设置数据
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {
        //  获取当前item中显示的数据
        final Article nowData = mDatas.get(position);

        //  设置要显示的数据
        viewHolder.articleTitle.setText(nowData.getTitle());
        viewHolder.articleContent.setText(nowData.getContent());

        //String转URL转Bitmap
        String str = nowData.getImageUrl();
        //SetImageViewUtil.setImageToImageView(viewHolder.articleImage,nowData.getImageUrl());
        Glide.with(viewHolder.itemView.getContext())
                .load(nowData.getImageUrl())
                .into(viewHolder.articleImage);
        //viewHolder.articleImage.setImageBitmap(returnBitMap( nowData.getImageUrl()));
        viewHolder.itemView.setTag(nowData);
    }

    @Override
    public int getItemCount()
    {

        return mDatas.size();
    }
    //  删除指定的Item
    public void removeData(int position)
    {
        mDatas.remove(position);
        //  通知RecyclerView控件某个Item已经被删除
        notifyItemRemoved(position);

    }
    //  在指定位置添加一个新的Item
    public void addItem(int positionToAdd)
    {
        Date date =new Date();
        mDatas.add(positionToAdd,new Article("firstTitle","firstContent",date,100,10,"love","C:\\Users\\JJJJ\\Desktop\\snow\\image_id.png"));
        //  通知RecyclerView控件插入了某个Item
        notifyItemInserted(positionToAdd);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView articleTitle;
        private TextView articleContent;
        private ImageView articleImage;

        public ViewHolder(View itemView)
        {
            super(itemView);

            articleTitle = (TextView) itemView
                    .findViewById(R.id.aritcle_item_title);

            articleContent = (TextView) itemView
                    .findViewById(R.id.article_item_content);

            articleImage = (ImageView) itemView
                    .findViewById(R.id.article_item_iv);
        }
    }

}
