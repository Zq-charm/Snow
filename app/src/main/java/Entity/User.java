package Entity;

import cn.jiguang.imui.commons.models.IUser;

public class User implements IUser
{
    private String id; //用户id  int

    private int imageId;  //用户头像

    private String displayName;  //用户名

    private String name; //用户昵称

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

    private String emotion;//情感状态

    private int age;//年龄

    private String backGround;//背景图

    private String sex; //性别

    private long star;

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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




    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String id, int imageId, String displayName, String name, String password, String mail, int phone, String signaTure, int fansNumber, int followsPeopleNumber, int momentsNumber, int friendsNumber, String avatar, String nowLocation, String homeTown, String emotion, int age, String backGround, String sex, long star) {
        this.id = id;
        this.imageId = imageId;
        this.displayName = displayName;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.phone = phone;
        this.signaTure = signaTure;
        this.fansNumber = fansNumber;
        this.followsPeopleNumber = followsPeopleNumber;
        this.momentsNumber = momentsNumber;
        this.friendsNumber = friendsNumber;
        this.avatar = avatar;
        this.nowLocation = nowLocation;
        this.homeTown = homeTown;
        this.emotion = emotion;
        this.age = age;
        this.backGround = backGround;
        this.sex = sex;
        this.star = star;
    }

    public User()
    {

    }

    public User(User user)
    {
        this.setName(user.getName());
        this.setSex(user.getSex());
        this.setNowLocation(user.getNowLocation());
        this.setHomeTown(user.getHomeTown());
        this.setEmotion(user.getEmotion());
        this.setAge(user.getAge());
        this.setFollowsPeopleNumber(user.getFollowsPeopleNumber());
        this.setFansNumber(user.getFansNumber());
        this.setDisplayName(user.getDisplayName());
        this.setAvatar(user.getAvatar());
        this.setBackGround(user.getBackGround());
        this.setFriendsNumber(user.getFriendsNumber());
        this.setId(user.getId());
        this.setImageId(user.getImageId());
        this.setMail(user.getMail());
        this.setPassword(user.getPassword());
        this.setMomentsNumber(user.getMomentsNumber());
        this.setPhone(user.getPhone());
        this.setSignaTure(user.getSignaTure());
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
