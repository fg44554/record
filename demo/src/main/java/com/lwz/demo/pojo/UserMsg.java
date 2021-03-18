package com.lwz.demo.pojo;

import org.springframework.stereotype.Repository;

@Repository
public class UserMsg {
    private int id;
    private String username;
    private String password;
    private String admin;
    private String root;
    private int admin_id;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin='" + admin + '\'' +
                ", root='" + root + '\'' +
                ", admin_id=" + admin_id +
                ", email='" + email + '\'' +
                '}';
    }
}
