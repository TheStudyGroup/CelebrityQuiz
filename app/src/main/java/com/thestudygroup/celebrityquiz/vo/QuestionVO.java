package com.thestudygroup.celebrityquiz.vo;

import java.io.Serializable;

public class QuestionVO implements Serializable
{
    public String question;
    public String imageUrl;
    public String one;
    public String two;
    public String three;
    public String four;
    public int correctAnswer;
    public int userAnswer;

    public QuestionVO(
            final String question,
            final String imageUrl,
            final String one,
            final String two,
            final String three,
            final String four,
            final int    correctAnswer,
            final int    userAnswer)
    {
        this.question      = question;
        this.imageUrl      = imageUrl;
        this.one           = one;
        this.two           = two;
        this.three         = three;
        this.four          = four;
        this.correctAnswer = correctAnswer;
        this.userAnswer    = userAnswer;
    }
}
