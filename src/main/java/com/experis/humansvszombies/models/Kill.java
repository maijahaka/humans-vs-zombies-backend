package com.experis.humansvszombies.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Kill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "story")
    private String story;
    @Column(name="lat")
    private float lat;
    @Column(name="lng")
    private float lng;
    //@Column(name ="time_of_death")
    //private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    //public Date getDate() {
    //    return date;
    //}

    //public void setDate(Date date) {
    //    this.date = date;
    //}
}
