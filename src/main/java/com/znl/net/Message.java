package com.znl.net;

/**
 * Created by Administrator on 2016/5/29 0029.
 */
public class Message {

    private Short cmd;

    private byte[]data;// 存放实际数据,用于protobuf解码成对应message

    public Short getCmd() {
        return cmd;
    }

    public void setCmd(Short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
