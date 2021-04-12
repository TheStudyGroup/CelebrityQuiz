package com.thestudygroup.celebrityquiz.vo;

import java.util.ArrayList;

public class MenuHeaderVO
{
    private String                 name;
    private ArrayList<MenuChildVO> list;

    public String                 getName() { return name; }
    public ArrayList<MenuChildVO> getList() { return list; }
    public void setName(String name)                 { this.name = name; }
    public void setList(ArrayList<MenuChildVO> list) { this.list = list; }
}
