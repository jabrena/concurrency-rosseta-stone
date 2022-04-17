package info.jab.concurrent;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = "10", expect = Expect.FORBIDDEN, desc = "Both actors came up with the same value: atomicity failure.")
@Outcome(id = "40", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@Outcome(id = "20", expect = Expect.ACCEPTABLE, desc = "actor1 incremented, then actor2.")
@State
public class ConcurrentAccountTest {

    Account account = new Account(50);

    @Actor
    void actor1(II_Result r) {
        r.r1 = account.withdraw(10); // record result from actor1 to field r1
    }

    @Actor
    void actor2(II_Result r) {
        r.r1 = account.withdraw(30); // record result from actor2 to field r2
    }
}
