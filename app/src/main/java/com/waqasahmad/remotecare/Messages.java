package com.waqasahmad.remotecare;
import java.util.Date;
public class Messages {

    String msg, suid , timestamp;


    public Messages(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Messages(String msg, String suid , String timestamp) {
        this.msg = msg;
        this.suid = suid;
        this.timestamp = timestamp;
    }

    public Messages() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }
}
