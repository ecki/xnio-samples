package org.xnio.samples;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.xnio.IoFuture;
import org.xnio.IoUtils;
import org.xnio.OptionMap;
import org.xnio.StreamConnection;
import org.xnio.Xnio;
import org.xnio.XnioWorker;
import org.xnio.channels.Channels;
import org.xnio.channels.StreamSinkChannel;
import org.xnio.channels.StreamSourceChannel;

public final class SimpleHelloWorldBlockingClient {

    public static void main(String[] args) throws Exception {
        final Charset charset = Charset.forName("utf-8");
        final Xnio xnio = Xnio.getInstance();
        final XnioWorker worker = xnio.createWorker(OptionMap.EMPTY);

        try {
            final IoFuture<StreamConnection> futureConnection = worker.openStreamConnection(new InetSocketAddress("localhost", 12345), null, OptionMap.EMPTY);
            final StreamConnection connection = futureConnection.get(); // blocks and throws exceptions
            final StreamSinkChannel outChannel = connection.getSinkChannel();
            final StreamSourceChannel inChannel = connection.getSourceChannel();
            try {
                // Send the greeting
                Channels.writeBlocking(outChannel, ByteBuffer.wrap("Hello world!\n".getBytes(charset)));
                // Make sure all data is written
                Channels.flushBlocking(outChannel);
                // And send EOF
                outChannel.shutdownWrites();
                System.out.println("Sent greeting string!  The response is...");
                ByteBuffer recvBuf = ByteBuffer.allocate(128);
                // Now receive and print the whole response
                while (Channels.readBlocking(inChannel, recvBuf) != -1) {
                    recvBuf.flip();
                    final CharBuffer chars = charset.decode(recvBuf);
                    System.out.print(chars);
                    recvBuf.clear();
                }
            } finally {
                IoUtils.safeClose(inChannel);
                IoUtils.safeClose(outChannel);
                IoUtils.safeClose(connection);
            }
        } finally {
            worker.shutdown();
        }
    }
}