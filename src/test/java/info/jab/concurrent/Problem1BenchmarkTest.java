package info.jab.concurrent;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Mode;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Problem1BenchmarkTest {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 3, warmups = 2)
    public void measureScenario3() {
        List<Long> threads = LongStream.rangeClosed(1, 2).boxed().collect(Collectors.toList());
        IProblem1 problem1 = new Problem1_V3();
        List<CompletableFuture> listFutures = threads.stream().map(thr -> getCFIncrement100Times(problem1)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(listFutures.toArray(new CompletableFuture[listFutures.size()]));

        allFutures.thenApply(future -> {
            return listFutures.stream()
                    .map(completableFuture -> completableFuture.join())
                    .collect(Collectors.toList());
        });

        /**Thread thread1 = new Thread(() -> {LongStream.rangeClosed(1, 100).boxed().forEach(x -> problem1.increment());});
        Thread thread2 = new Thread(() -> {LongStream.rangeClosed(1, 100).boxed().forEach(x -> problem1.increment());});
        thread1.start();
        thread2.start();*/

    }

    private CompletableFuture getCFIncrement100Times(IProblem1 problem1) {
        return CompletableFuture.supplyAsync(()-> LongStream.rangeClosed(1, 10).boxed().collect(Collectors.toList()).stream().map(n -> problem1.increment()));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 3, warmups = 2)
    public void measureScenario4() {
        List<Long> threads = LongStream.rangeClosed(1, 2).boxed().collect(Collectors.toList());
        IProblem1 problem1 = new Problem1_V4();
        List<CompletableFuture> listFutures = threads.stream().map(thr -> getCFIncrement100Times(problem1)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(listFutures.toArray(new CompletableFuture[listFutures.size()]));

        allFutures.thenApply(future -> {
            return listFutures.stream()
                    .map(completableFuture -> completableFuture.join())
                    .collect(Collectors.toList());
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 3, warmups = 2)
    public void measureScenario5() {
        List<Long> threads = LongStream.rangeClosed(1, 2).boxed().collect(Collectors.toList());
        IProblem1 problem1 = new Problem1_V5();
        List<CompletableFuture> listFutures = threads.stream().map(thr -> getCFIncrement100Times(problem1)).collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(listFutures.toArray(new CompletableFuture[listFutures.size()]));

        allFutures.thenApply(future -> {
            return listFutures.stream()
                    .map(completableFuture -> completableFuture.join())
                    .collect(Collectors.toList());
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureBrena() {
        IProblem1 problem1 = new Problem1_V3();
        problem1.increment();
    }
}
