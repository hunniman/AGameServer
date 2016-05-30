package com.znl.net;

import com.znl.protocal.M1;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by pengweiyuan on 5/30/16.
 */
public class Client {
    private Channel sendchannel = null;
    private final static String host = "localhost";
    private final static int port = 9909;


    public void run() {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ClientChannelInitializer(this));// 这里为了简便直接使用内部类

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
            sendchannel = f.channel();
            if (f.isDone()) {
                System.out.println("连接成功");
            }

        } catch (InterruptedException e) {// 链接失败
            e.printStackTrace();
        } finally {
        }

    }

    public void sendMsg(Message msgEntity) {
        sendchannel.writeAndFlush(msgEntity);
        System.out.println("发送数据成功,命令码:\t" + msgEntity.getCmd());
    }



    public static void main(String[] args) {
        Client client = new Client();
        client.run();// 开启客户端netty
        M1.M9999.C2S.Builder builder= M1.M9999.C2S.newBuilder();
        builder.setAccount("helloAccount");
        builder.setType(2);
        builder.setAreId(444);

        Message msg=new Message();
        msg.setCmd(9999);
        msg.setData(builder.build().toByteArray());
        // 发送名字检查
        client.sendMsg(msg);
    }
}
