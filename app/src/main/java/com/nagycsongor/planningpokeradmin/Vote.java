package com.nagycsongor.planningpokeradmin;

public class Vote {
    public String userId;
    public String userName;
    public String mark;

    public Vote(){

    }

    public Vote(String userId, String userName, String mark) {
        this.userId = userId;
        this.userName = userName;
        this.mark = mark;
    }
}

