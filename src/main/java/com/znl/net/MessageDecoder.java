package com.znl.net;

import com.znl.protocal.M1;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by Administrator on 2016/5/29 0029.
 */
public class MessageDecoder  extends LengthFieldBasedFrameDecoder {

    /**
     * @param byteOrder
     * @param maxFrameLength
     *            字节最大长度,大于此长度则抛出异常
     * @param lengthFieldOffset
     *            开始计算长度位置,这里使用0代表放置到最开始
     * @param lengthFieldLength
     *            描述长度所用字节数
     * @param lengthAdjustment
     *            长度补偿,这里由于命令码使用2个字节.需要将原来长度计算加2
     * @param initialBytesToStrip
     *            开始计算长度需要跳过的字节数
     * @param failFast
     */
    public MessageDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }
    public MessageDecoder() {
        this(ByteOrder.BIG_ENDIAN, 100000, 0, 4, 2, 4, true);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, byteBuf);
        if (frame == null) {
            return null;
        }
        int cmd = frame.readInt();// 先读取两个字节命令码
       // int length= frame.readInt();
        byte[] data = new byte[frame.readableBytes()];// 其它数据为实际数据
        frame.readBytes(data);
        Message msg=new Message();
        msg.setCmd(cmd);
        msg.setData(data);
        M1.M9999.C2S c2S = M1.M9999.C2S.parseFrom(data);
        System.err.println(c2S.getAccount());
        return msg;
    }
}
