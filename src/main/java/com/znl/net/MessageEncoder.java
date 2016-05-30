package com.znl.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Administrator on 2016/5/29 0029.
 * 服务端这里继承<code>MessageToByteEncoder</code>更加方便
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        int dataLength = message.getData() == null ? 0 : message.getData().length;
        byteBuf.ensureWritable(4 + dataLength);
        byteBuf.writeInt(dataLength);
        byteBuf.writeShort(message.getCmd());
        if (dataLength > 0) {
            byteBuf.writeBytes(message.getData());
        }
    }
}
