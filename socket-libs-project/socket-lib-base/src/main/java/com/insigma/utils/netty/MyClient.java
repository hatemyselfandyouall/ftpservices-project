package com.insigma.utils.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClient {
    public static void main(String[] args) throws Exception{
        sendMsg("127.0.0.1",8999,"Hello Jetty");
    }

    public static void sendMsg(String targetHost, Integer targetPort, MyClientInitializer channal){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(channal);
            ChannelFuture channelFuture = bootstrap.connect(targetHost,targetPort).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
           log.error("异常信息记录",e);
        }  finally{
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void sendMsg(String targetHost, Integer targetPort,String msg){
        sendMsg(targetHost,targetPort,new MyClientInitializer(msg));
    }
}