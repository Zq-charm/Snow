package Entity;

public class User
{
    private int id; //用户id

    private int imageId;  //用户头像

    private String name;  //用户名

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

    private String userfaceurl;//用户头像

    public String getUserfaceurl() {
        return userfaceurl;
    }

    public void setUserfaceurl(String userfaceurl) {
        this.userfaceurl = userfaceurl;
    }

    public User(int id, int imageId, String name, String passWord, String mail, int phone, String signaTure, int fansNumber, int followsPeopleNumber, int momentsNumber, int friendsNumber) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
