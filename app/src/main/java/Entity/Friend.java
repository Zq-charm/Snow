package Entity;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

public class Friend
{
    public final @NonNull User friend;
    public final @NonNull String fri_text;
    public final @NonNull String recive_time;

    public Friend(@NonNull User friend,String fri_text,String recive_time)
    {
        this.friend = friend;
        this.fri_text = fri_text;
        this.recive_time = recive_time;
    }

}
