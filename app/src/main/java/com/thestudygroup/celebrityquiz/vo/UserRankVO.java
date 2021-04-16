package com.thestudygroup.celebrityquiz.vo;

public class UserRankVO implements Comparable<UserRankVO>
{
    public String name;
    public long   score;

    public UserRankVO(final String name, final long score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(UserRankVO o) {
        return (this.score < o.score ? 1 : -1);
    }
}
