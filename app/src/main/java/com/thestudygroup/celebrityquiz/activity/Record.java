package com.thestudygroup.celebrityquiz.activity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Record implements Serializable {
    String useremail;
    String totalQuizNum;
    String TotalScore;

    public Record(String username, String totalQuizNum, String TotalScore){
        this.useremail = username;
        this.totalQuizNum = totalQuizNum;
        this.TotalScore = TotalScore;
    }

    public String getUsername() {
        return useremail;
    }
    public void setUsername(String username) {
        this.useremail = username;
    }

    public String getTotalQuizNum() {
        return totalQuizNum;
    }
    public void setTotalQuizNum(String totalQuizNum) {
        this.totalQuizNum = totalQuizNum;
    }

    public String getTotalScore() {
        return TotalScore;
    }
    public void setTotalScore(String TotalScore) {
        this.TotalScore = TotalScore;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("useremail", useremail);
        result.put("TotalQuizNum", totalQuizNum);
        result.put("TotalScore", TotalScore);

        return result;
    }



}
