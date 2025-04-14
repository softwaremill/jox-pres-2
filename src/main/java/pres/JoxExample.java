package pres;

import com.softwaremill.jox.flows.Flows;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public class JoxExample {
    public static void main(String[] args) throws Exception {
        var nats = Flows.unfold(0, i -> Optional.of(Map.entry(i+1, i+1)));

        Flows.range(1, 100, 1)
                .throttle(1, Duration.ofSeconds(1))
                .mapPar(4, i -> {
                    Thread.sleep(5000);
                    var j = i*3;
                    return j+1;
                })
                .filter(i -> i % 2 == 0)
                .zip(nats)
                .runForeach(System.out::println);
    }
}
