package com.znl.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class ServerHanlder  extends SimpleChannelInboundHandler<Message> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if(message==null){
            return;
        }
        
    }
}
