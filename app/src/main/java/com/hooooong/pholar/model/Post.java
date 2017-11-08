package com.hooooong.pholar.model;

import java.util.List;

/**
 * Created by Heepie on 2017. 11. 8..
 *
 * User가 작성한 글의 테이블
 */

public class Post {
    public String post_id;
    public String date;
    public String comtent;
    public String writer;
    private List<String> tag;
    public List<Photo> photo;
    public List<Comment> comment;
    public List<Like> like;

    @Override
    public String toString() {
        return "Post{" +
                "post_id='" + post_id + '\'' +
                ", date='" + date + '\'' +
                ", comtent='" + comtent + '\'' +
                ", writeer='" + writer + '\'' +
//                ", tag=" + tag +
                ", photo=" + photo +
                ", comment=" + comment +
                ", like=" + like +
                '}';
    }
}