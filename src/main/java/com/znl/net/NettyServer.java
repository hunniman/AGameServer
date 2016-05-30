package com.znl.net;

import com.znl.cache.GamePropertiesCache;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pengweiyuan on 5/28/16.
 */
public class NettyServer {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(2,new DefaultThreadFactory("netboss"));
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(18,new DefaultThreadFactory("network"));
    /**
     * 启动游戏服务器sockt服务器
     * @throws Exception
     */
    public static void run() throws Exception{
        final ServerHanlder logicHandler = new ServerHanlder();
        //int port = GamePropertiesCache.serverPort;
        int port = 9909;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE,true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            //默认发送数据的最大值为：64*1024
                            ch.config().setWriteBufferHighWaterMark(2*1024*1024);
                            ch.pipeline().addLast(logicHandler,new MessageDecoder(),new MessageEncoder());
                        }
                    });
            //游戏服端口
            b.bind(port);
            logger.info("net server start complete on port="+port);
        } catch (Exception e) {
            throw new Exception("start net server err");
        }
    }

    /**
     * 关闭服务
     */
    public static void shutDown(){
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }


    public static void main(String[]args){
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
