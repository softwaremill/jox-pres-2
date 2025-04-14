package pres;

import com.softwaremill.jox.Channel;

public class S02_Basics {
    public static void main(String[] args) throws InterruptedException {
        var ch = Channel.<Integer>newBufferedChannel(4);

        ch.send(1);
        ch.send(2);
        System.out.println(ch.receive());
    }
}
