package Entity;

import android.support.annotation.NonNull;

public class Activity
{
    private int id;
    private User user;
    private int userId;
    private String title;
    private String createTime;
    private String date;
    private String place;
    private String distance;
    private String peopleNum;
    private String activity_content;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Activity(){};

    public Activity(int id, User user, String title, String createTime, String date, String place, String distance, String peopleNum, String activity_content) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.createTime = createTime;
        this.date = date;
        this.place = place;
        this.distance = distance;
        this.peopleNum = peopleNum;
        this.activity_content = activity_content;
    }

    public Activity(User user, String title, String createTime, String date, String place, String distance, String peopleNum, String activity_content)
    {
        this.user = user;
        this.title = title;
        this.createTime = createTime;
        this.date = date;
        this.place = place;
        this.distance = distance;
        this.peopleNum = peopleNum;
        this.activity_content = activity_content;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getActivity_content() {
        return activity_content;
    }

    public void setActivity_content(String activity_content) {
        this.activity_content = activity_content;
    }
}
