package Entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class MyselfItem
{
    public static final int TYPE_User = 1;
    public static final int TYPE_Moment = 2;

//
//    public final @NonNull User user;
//
//    public final @NonNull Moment moment;
    public @SerializedName("user") User user;
    public @SerializedName("moment") Moment moment;
    public @SerializedName("type") int type;
    public MyselfItem(@NonNull User user,int type )
    {
        this.user = user;
        this.type = type;
    }

    public MyselfItem(@NonNull Moment moment,int type)
    {
        this.moment = moment;
        this.type = type;
    }


}
