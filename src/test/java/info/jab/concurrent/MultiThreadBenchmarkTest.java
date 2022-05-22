package info.jab.concurrent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class MultiThreadBenchmarkTest {

    @State(Scope.Benchmark)
    public static class St{

        IProblem1 problem1 = new Problem1_V3();
        IProblem1 problem2 = new Problem1_V4();
        IProblem1 problem3 = new Problem1_V5();
    }

    @Benchmark
    public void syncronizedScenario(St st) {
        List<Long> threads = LongStream.rangeClosed(1, 2).boxed().collect(Collectors.toList());
        IProblem1 problem1 = new Problem1_V5();
        List<CompletableFuture> listFutures = threads.stream().map(thr -> getCFIncrement100Times(st.problem1)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(listFutures.toArray(new CompletableFuture[listFutures.size()]));

        /**Thread thread1 = new Thread(() -> {LongStream.rangeClosed(1, 100).boxed().forEach(x -> problem1.increment());});
         Thread thread2 = new Thread(() -> {LongStream.rangeClosed(1, 100).boxed().forEach(x -> problem1.increment());});
         thread1.start();
         thread2.start();*/
    }

    private CompletableFuture getCFIncrement100Times(IProblem1 problem) {
        return CompletableFuture.supplyAsync(()-> LongStream.rangeClosed(1, 10).boxed().collect(Collectors.toList()).stream().map(n -> problem.increment()));
    }

    @Benchmark
    public void atomicScenario(St st) {
        List<Long> threads = LongStream.rangeClosed(1, 2).boxed().collect(Collectors.toList());
        IProblem1 problem1 = new Problem1_V5();
        List<CompletableFuture> listFutures = threads.stream().map(thr -> getCFIncrement100Times(st.problem2)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(listFutures.toArray(new CompletableFuture[listFutures.size()]));
    }

    @Benchmark
    public void lockScenario(St st) {
        List<Long> threads = LongStream.rangeClosed(1, 2).boxed().collect(Collectors.toList());
        IProblem1 problem1 = new Problem1_V5();
        List<CompletableFuture> listFutures = threads.stream().map(thr -> getCFIncrement100Times(st.problem3)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(listFutures.toArray(new CompletableFuture[listFutures.size()]));
    }

}
