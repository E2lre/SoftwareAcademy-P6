package com.paymybuddy.paysystem.model.questions;

public class MyBuddy {


    private String myEmail; //Email of  the person witch want to add a friend (in the question)
    private String buddyEmail; //Friend email (in the question)
    private String buddyFirstname; // Firstname of the person (in the response)
    private String buddyLastname; // Lastame of the person (in the response)

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

/*    @Override
    public String toString() {
        return "MyBuddy{" +
                "myEmail='" + myEmail + '\'' +
                ", buddyEmail='" + buddyEmail + '\'' +
                ", buddyFirstname='" + buddyFirstname + '\'' +
                ", buddyLastname='" + buddyLastname + '\'' +
                '}';
    }*/

}
