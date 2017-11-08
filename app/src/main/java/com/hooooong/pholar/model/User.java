package com.hooooong.pholar.model;

import java.util.List;

/**
 * Created by Heepie on 2017. 11. 8..
 */

public class User {
    public String user_id;
    public String nickname;
    public String status_msg;
    public String phone_number;
    public String profile_path;
    public List<PostThumbnail> post_thumbnail;

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status_msg='" + status_msg + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", post_thumbnail=" + post_thumbnail +
                '}';
    }
}