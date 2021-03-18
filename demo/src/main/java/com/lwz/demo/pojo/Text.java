package com.lwz.demo.pojo;

public class Text {
    private String title;
    private String text;
    private int time;
    private int bTime;
    private int eTime;
    private int uid;
    private String phone;
    private int times;
    private int mood;
    private String mood_stat;
    private int id;
    private int user_id;
    private int admin_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getId() {
        return id;
    }

    public String getMood_stat() {
        return mood_stat;
    }

    public void setMood_stat(String mood_stat) {
        this.mood_stat = mood_stat;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getbTime() {
        return bTime;
    }

    public void setbTime(int bTime) {
        this.bTime = bTime;
    }

    public int geteTime() {
        return eTime;
    }

    public void seteTime(int eTime) {
        this.eTime = eTime;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Text{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                ", bTime=" + bTime +
                ", eTime=" + eTime +
                ", uid=" + uid +
                ", phone='" + phone + '\'' +
                ", times=" + times +
                ", mood=" + mood +
                ", mood_stat='" + mood_stat + '\'' +
                ", id=" + id +
                ", user_id=" + user_id +
                ", admin_id=" + admin_id +
                '}';
    }
}
