package info.jab.concurrent;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

public class BenchmarkTests {

    @Test
    public void given_jmh_when_executeBenchmark_then_ok() throws RunnerException {

        Options options = new OptionsBuilder()
                .include(ProblemBrenaBenchmarkTest.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("target/jmh-results.json")
                //.verbosity(VerboseMode.EXTRA)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(5))
                .measurementTime(TimeValue.milliseconds(1))
                .measurementIterations(10)
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(3)
                //.shouldFailOnError(true)
                .shouldDoGC(true)
                .forks(2)
                .jvmArgs("-Xmx6144m", "-Xms6144m")
                //.addProfiler(StackProfiler.class)
                .addProfiler(GCProfiler.class)
                //.addProfiler(LinuxPerfProfiler.class)
                //.addProfiler(ClassloaderProfiler.class)
                //.addProfiler(CompilerProfiler.class)
                //.addProfiler(JmhFlightRecorderProfiler.class)
                .build();

        new Runner(options).run();
    }

}
