package com.znl.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by pengweiyuan on 5/30/16.
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private Client gameClient;

    public ClientChannelInitializer(Client gameClient) {
        this.gameClient = gameClient;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        // 特别地.netty的handler可以动态改变,如流量大可以增加流量控制handler
        ch.pipeline().addLast("protobufDecoder", new MessageDecoder())// 解码和编码使用与服务端相同配置

                .addLast("protobufEncoder", new MessageEncoder())//

                .addLast("handler", new clientHanlder(gameClient));
    }
}


class clientHanlder extends SimpleChannelInboundHandler<Message> {
    private Client gameClient;

    public clientHanlder(Client gameClient) {
        this.gameClient = gameClient;
    }

    public void messageReceived(ChannelHandlerContext ctx, Message msgEntity) throws Exception {
        /*if (msgEntity.getCmdCode() == CmdConstant.NAME_CHECK) {// 名字检查回包
            NameCheckResp resp = null;
            resp = NameCheckResp.parseFrom(msgEntity.getData());
            if (resp.getIsExist()) {
                System.out.println("改名字已经存在,请换一个名字");
            } else {// 如果链接成功,则发言
                gameClient.sendHello("fuck the GFW");
            }
        } else if (msgEntity.getCmdCode() == CmdConstant.SAY_HELLO) {
            SayHelloResp resp = SayHelloResp.parseFrom(msgEntity.getData());
            System.out.println("发言人:\t" + resp.getSpeaker() + "\t内容:\t" + resp.getContent());
        }*/

        // ParseData.FriendList(nettyMessageVO);
        // ParseData.FriendOnline(nettyMessageVO);
        // ParseData.AgreeAddFriend(nettyMessageVO);
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
       System.err.println(message.getCmd());
    }
}
