package com.example.sothengchheang.ckcc_joul_pteas;

public class ProfileSingleton {

    private UserProfile userProfile;

    private static ProfileSingleton instance;

    private ProfileSingleton(){

    }

    public static ProfileSingleton getInstance(){
        if (instance == null){
            instance = new ProfileSingleton();
        }
        return instance;
    }
    public static void resetProfileSingleton (){
        instance = null;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
