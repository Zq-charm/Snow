package Entity;

import java.util.List;

public class Moment {

    private String moment_id;
    private int moment_userface;
    private String moment_userface_url;
    private String imageUrl;
    private String location;
    private String topic;
    private String picUrlList;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(String picUrlList) {
        this.picUrlList = picUrlList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMoment_userface_url() {
        return moment_userface_url;
    }

    public void setMoment_userface_url(String moment_userface_url) {
        this.moment_userface_url = moment_userface_url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private int positionId;

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getMoment_userface() {
        return moment_userface;
    }

    public String getMoment_id() {
        return moment_id;
    }

    private String moment_Text;

    private int imageId;

    public int getStar() {
        return star;
    }

    public int getComment() {
        return comment;
    }

    private int star;

    private int comment;

    public void setMoment_id(String moment_id) {
        this.moment_id = moment_id;
    }

    public void setMoment_userface(int moment_userface) {
        this.moment_userface = moment_userface;
    }

    public void setMoment_Text(String moment_Text) {
        this.moment_Text = moment_Text;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public Moment(String moment_Text,int ImageId,int Comment,int Star,String Moment_id,int Moment_userFace,int PositionId,int User_Id,String moment_userface_url,String imageUrl)
    {
        this.imageId = ImageId;
        this.moment_Text = moment_Text;
        this.comment = Comment;
        this.star = Star;
        this.moment_id = Moment_id;
        this.moment_userface = Moment_userFace;
        this.positionId = PositionId;
        this.user_id = User_Id;
        this.moment_userface_url = moment_userface_url;
        this.imageUrl=imageUrl;
    }

    public String getMoment_Text() {
        return moment_Text;
    }

    public int getImageId() {
        return imageId;
    }

    public Moment()
    {

    }
}
