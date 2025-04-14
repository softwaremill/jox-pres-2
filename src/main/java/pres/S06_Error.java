package pres;

import com.softwaremill.jox.Channel;

public class S06_Error {
    public static void main(String[] args) throws InterruptedException {
        var ch = Channel.<String>newBufferedDefaultChannel();

        ch.send("hello");
        ch.error(new RuntimeException("error"));

        System.out.println("Received: " + ch.receiveOrClosed());
        System.out.println("Received: " + ch.receiveOrClosed());
    }
}
