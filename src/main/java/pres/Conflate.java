package pres;

import com.softwaremill.jox.Channel;
import com.softwaremill.jox.Sink;
import com.softwaremill.jox.Source;

import static com.softwaremill.jox.Select.select;

public class Conflate {
    sealed interface Result permits Received, Sent {}
    record Received(String value) implements Result {}
    record Sent() implements Result {}

    private static void conflate(Source<String> from, Sink<Integer> to) throws InterruptedException {
        var soFar = 0;
        while (true) {
            Result result;
            if (soFar > 0) {
                result = select(
                        to.sendClause(soFar, Sent::new),
                        from.receiveClause(Received::new)
                );
            } else {
                result = new Received(from.receive());
            }

            switch(result) {
                case Received r -> soFar += r.value.length();
                case Sent s -> soFar = 0;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        Channel<String> source = new Channel<>();
//        Channel<Integer> target = new Channel<>();
//
//        Thread.startVirtualThread(() -> {
//            while (true) {
//                try {
//                    source.send("ABC");
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        Thread.startVirtualThread(() -> {
//            try {
//                while (true) {
//                    System.out.println("Got: " + target.receive());
//                    Thread.sleep(1000L);
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        conflate(source, target);
    }
}
