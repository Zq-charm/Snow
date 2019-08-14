package Entity;

import java.util.Date;
import java.util.List;

public class Article
{
    private String title;

    private String author;

    private Date create_date;

    private Integer commentnum;

    private Integer likenum;

    private String content;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article(String title, String author, Date create_date, Integer commentnum, Integer likenum, String content, String imageUrl) {
        this.title = title;
        this.author = author;
        this.create_date = create_date;
        this.commentnum = commentnum;
        this.likenum = likenum;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public Article()
    {

    }
}
