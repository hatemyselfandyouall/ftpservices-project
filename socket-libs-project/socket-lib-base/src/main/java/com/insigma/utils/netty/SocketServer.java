package com.insigma.utils.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Slf4j
public class SocketServer implements Runnable{

    private Integer port;

    private ChannelInitializer<SocketChannel> channelChannelInitializer;

    public SocketServer(Integer port) {
        this.port = port;
    }

    public SocketServer(Integer port, ChannelInitializer<SocketChannel> channelChannelInitializer) {
        this.port = port;
        this.channelChannelInitializer = channelChannelInitializer;
    }

//    public static void main(String[] args) throws Exception {
//        createServer(8899);
//    }

    public static void main(String[] args) throws Exception {
        Thread thread=new Thread();
        thread.start();
        System.out.println("test");
    }
    public void test(){
        Thread thread=new Thread(new SocketServer(this.port,this.channelChannelInitializer));
        thread.start();
    }
    @Autowired
    public void run(){
        log.info("启动netty-socket服务");
        if (channelChannelInitializer==null) {
            channelChannelInitializer=new MyServerInitializer();
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //在服务器端的handler()方法表示对bossGroup起作用，而childHandler表示对wokerGroup起作用
            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(channelChannelInitializer);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("启动socket服务异常",e);
        } finally{
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }

//    public static void createServer(Integer port, ChannelInitializer<SocketChannel> channelChannelInitializer){
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup wokerGroup = new NioEventLoopGroup();
//
//        try{
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            //在服务器端的handler()方法表示对bossGroup起作用，而childHandler表示对wokerGroup起作用
//            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
//                    .childHandler(channelChannelInitializer);
//            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
//            channelFuture.channel().closeFuture().sync();
//        }catch (Exception e){
//            log.error("启动socket服务异常",e);
//        } finally{
//            bossGroup.shutdownGracefully();
//            wokerGroup.shutdownGracefully();
//        }
//    }
//
//    public static void createServer(Integer port){
//        createServer(port,new MyServerInitializer());
//    }
}
