package com.example.bhalder.yohang_4;

/**
 * Created by bhalder on 7/16/16.
 */
public class db_user {
    public String fullName;
    public String userId;
    public String email;
    public String _id;
    public int points;
    public db_user() {};
    public db_user( String fullName, String userId, String email, String _id, int points) {
        this.fullName = fullName;
        this.userId = userId;
        this._id = _id;
        this.points = points;
        this.email = email;
    }
}
