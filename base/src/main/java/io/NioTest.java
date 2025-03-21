package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author yjl
 * @since 2024/8/12
 */
public class NioTest {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(9527));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            int n = selector.select();
            if (n <= 0) continue;
            Iterator it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey key = (SelectionKey)it.next();
                if (key.isAcceptable()){
                    SocketChannel sc= ((ServerSocketChannel) key.channel()).accept();
                    sc.configureBlocking(false);
                    sc.register(key.selector(), SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                }
                if (key.isReadable()){
                    SocketChannel channel = ((SocketChannel) key.channel());
                    ByteBuffer bf = ByteBuffer.allocate(10);
                    int read = channel.read(bf);
                    System.out.println("read "+read+" : "+new String(bf.array()).trim());
                }
                if (key.isWritable()){
                    SocketChannel channel = ((SocketChannel) key.channel());
                    channel.write(ByteBuffer.wrap(new String("hello client").getBytes()));
                }
                it.remove();
            }
        }
    }
}
