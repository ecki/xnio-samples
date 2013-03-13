package org.jboss.xnio.samples;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.xnio.ChannelListener;
import org.xnio.IoUtils;
import org.xnio.OptionMap;
import org.xnio.Xnio;
import org.xnio.XnioWorker;
import org.xnio.channels.AcceptingChannel;
import org.xnio.channels.Channels;
import org.xnio.channels.ConnectedStreamChannel;

public final class SimpleEchoServer {

    public static void main(String[] args) throws Exception {

        // First define the listener that actually is run on each connection.
        final ChannelListener<ConnectedStreamChannel> readListener = new ChannelListener<ConnectedStreamChannel>() {
            public void handleEvent(ConnectedStreamChannel channel) {
                final ByteBuffer buffer = ByteBuffer.allocate(512);
                int res;
                try {
                    while ((res = channel.read(buffer)) > 0) {
                        buffer.flip();
                        Channels.writeBlocking(channel, buffer);
                    }
                    // make sure everything is flushed out
                    Channels.flushBlocking(channel);
                    if (res == -1) {
                        channel.close();
                    } else {
                        channel.resumeReads();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    IoUtils.safeClose(channel);
                }
            }
        };

        // Create an accept listener.
        final ChannelListener<AcceptingChannel<ConnectedStreamChannel>> acceptListener = new ChannelListener<AcceptingChannel<ConnectedStreamChannel>>() {
            public void handleEvent(
                    final AcceptingChannel<ConnectedStreamChannel> channel) {
                try {
                    ConnectedStreamChannel accepted;
                    // channel is ready to accept zero or more connections
                    while ((accepted = channel.accept()) != null) {
                        System.out.println("accepted "
                                + accepted.getPeerAddress());
                        // stream channel has been accepted at this stage.
                        accepted.getReadSetter().set(readListener);
                        // read listener is set; start it up
                        accepted.resumeReads();
                    }
                } catch (IOException ignored) {
                }
            }
        };

        final XnioWorker worker = Xnio.getInstance().createWorker(
                OptionMap.EMPTY);
        // Create the server.
        AcceptingChannel<? extends ConnectedStreamChannel> server = worker
                .createStreamServer(new InetSocketAddress(12345),
                        acceptListener, OptionMap.EMPTY);
        // lets start accepting connections
        server.resumeAccepts();

        System.out.println("Listening on " + server.getLocalAddress());
    }
}