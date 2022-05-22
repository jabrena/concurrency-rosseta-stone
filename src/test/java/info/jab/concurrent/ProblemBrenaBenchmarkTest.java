package info.jab.concurrent;

import org.openjdk.jmh.annotations.*;

public class ProblemBrenaBenchmarkTest {

    @State(Scope.Benchmark)
    public static class St{

        IProblem1 problem1 = new Problem1_V3();
        IProblem1 problem2 = new Problem1_V4();
        IProblem1 problem3 = new Problem1_V5();
    }

    @Benchmark
    public void syncronizedScenario(St st) {
        st.problem1.increment();
    }

    @Benchmark
    public void atomicScenario(St st) {
        st.problem2.increment();
    }

    @Benchmark
    public void lockScenario(St st) {
        st.problem3.increment();
    }

}
