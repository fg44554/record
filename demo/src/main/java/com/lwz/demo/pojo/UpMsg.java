package com.lwz.demo.pojo;

public class UpMsg {
    private String time;
    private String name;
    private String size;
    private String type;
    private String text;
    private String stat;
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

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UpMsg{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", stat='" + stat + '\'' +
                ", id=" + id +
                ", user_id=" + user_id +
                ", admin_id=" + admin_id +
                '}';
    }
}
