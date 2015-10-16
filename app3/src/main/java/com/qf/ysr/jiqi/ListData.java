package com.qf.ysr.jiqi;

/**
 * Created by Administrator on 2015/10/9.
 */
public class ListData {
    private String content;
    public static final int SEND = 1;
    public static final int RECEIVE = 2;
    private int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public ListData(String content,int flag) {
        setContent(content);
        setFlag(flag);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
