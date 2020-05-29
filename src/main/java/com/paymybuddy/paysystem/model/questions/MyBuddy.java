package com.paymybuddy.paysystem.model.questions;

public class MyBuddy {


    private String myEmail;
    private String buddyEmail;
    private String buddyFirstname;
    private String buddyLastname;

    public MyBuddy() {
    }
    public MyBuddy(String myEmail, String buddyEmail, String buddyFirstname, String buddyLastname) {
        this.myEmail = myEmail;
        this.buddyEmail = buddyEmail;
        this.buddyFirstname = buddyFirstname;
        this.buddyLastname = buddyLastname;
    }


    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }

    public String getBuddyEmail() {
        return buddyEmail;
    }

    public void setBuddyEmail(String buddyEmail) {
        this.buddyEmail = buddyEmail;
    }

    public String getBuddyFirstname() {
        return buddyFirstname;
    }

    public void setBuddyFirstname(String buddyFirstname) {
        this.buddyFirstname = buddyFirstname;
    }

    public String getBuddyLastname() {
        return buddyLastname;
    }

    public void setBuddyLastname(String buddyLastname) {
        this.buddyLastname = buddyLastname;
    }

    @Override
    public String toString() {
        return "MyBuddy{" +
                "myEmail='" + myEmail + '\'' +
                ", buddyEmail='" + buddyEmail + '\'' +
                ", buddyFirstname='" + buddyFirstname + '\'' +
                ", buddyLastname='" + buddyLastname + '\'' +
                '}';
    }

}
