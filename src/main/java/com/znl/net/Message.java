package com.znl.net;

/**
 * Created by Administrator on 2016/5/29 0029.
 */
public class Message {

    private int cmd;

    private byte[]data;// 存放实际数据,用于protobuf解码成对应message

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
