package com.example.sothengchheang.ckcc_joul_pteas;

public class UserProfile {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public Location getLocation() {
        return location;
    }

    public String id;
    public String name;
    public String email;
    public String gender;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String birthday;
    public Location location;

    class Location {
        String id;
        String name;
    }


}
