package com.example.bhalder.yohang_4;

/**
 * Created by bhalder on 7/16/16.
 */
public class db_mission {
    public float lat;
    public float lon;
    public boolean status;
    public int pointsEarned;

    public db_mission() {};
    public db_mission( float lat, float lon, boolean status, int pointsEarned ) {
        this.lat = lat;
        this.lon = lon;
        this.status = status;
        this.pointsEarned = pointsEarned;
    }
}
