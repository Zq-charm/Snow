package Entity;

import cn.jiguang.imui.commons.models.IUser;

public class User implements IUser
{
    private String id; //用户id  int

    private int imageId;  //用户头像

    private String displayName;  //用户名

    private String password;//密码

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String mail; //邮箱

    private int phone;  //电话

    private String signaTure;  //签名

    private int fansNumber; //粉丝数

    private int followsPeopleNumber;  //关注的人数

    private int momentsNumber;  //动态数

    private int friendsNumber;  //朋友数

    private String avatar;//用户头像

    private String nowLocation;//所在地

    private String homeTown; //家乡

    private String Emotion;//情感状态

    private int Age;//年龄

    private String backGround;//背景图

    private String Sex; //性别

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getNowLocation() {
        return nowLocation;
    }

    public void setNowLocation(String nowLocation) {
        this.nowLocation = nowLocation;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getEmotion() {
        return Emotion;
    }

    public void setEmotion(String emotion) {
        Emotion = emotion;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User(String id, int imageId, String DisplayName, String passWord, String mail, int phone, String signaTure, int fansNumber, int followsPeopleNumber, int momentsNumber, int friendsNumber) {
        this.id = id;
        this.imageId = imageId;
        this.displayName = DisplayName;
        this.mail = mail;
        this.phone = phone;
        this.signaTure = signaTure;
        this.fansNumber = fansNumber;
        this.followsPeopleNumber = followsPeopleNumber;
        this.momentsNumber = momentsNumber;
        this.friendsNumber = friendsNumber;
        this.password = passWord;
    }



    public User()
    {

    }








    public void setDisplayName(String DisplayName) {
        this.displayName = DisplayName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getSignature() {
        return signaTure;
    }

    public void setSignature(String signature) {
        this.signaTure = signature;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getSignaTure() {
        return signaTure;
    }

    public void setSignaTure(String signaTure) {
        this.signaTure = signaTure;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public int getFollowsPeopleNumber() {
        return followsPeopleNumber;
    }

    public void setFollowsPeopleNumber(int followsPeopleNumber) {
        this.followsPeopleNumber = followsPeopleNumber;
    }

    public int getMomentsNumber() {
        return momentsNumber;
    }

    public void setMomentsNumber(int momentsNumber) {
        this.momentsNumber = momentsNumber;
    }

    public int getFriendsNumber() {
        return friendsNumber;
    }

    public void setFriendsNumber(int friendsNumber) {
        this.friendsNumber = friendsNumber;
    }


    public User(String id, String displayName, String avatar) {
        this.id = id;
        this.displayName = displayName;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAvatar() {
        return avatar;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getAvatarFilePath() {
        return avatar;
    }
}
