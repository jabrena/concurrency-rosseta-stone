package info.jab.concurrent;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.WireMockServer;

//@JCStressTest
@Outcome(id = "1, 1", expect = Expect.FORBIDDEN, desc = "Both actors came up with the same value: atomicity failure.")
@Outcome(id = "2, 2", expect = Expect.FORBIDDEN, desc = "actor1 incremented, then actor2.")
@Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
@Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 incremented, then actor1.")
@State
public class WiremockTest {

    private WireMockServer wireMockServer;

    private HttpClient client;

    public WiremockTest() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        //Alternative 1
        String address = "/v1/color?id=blue";
        wireMockServer.stubFor(get(urlEqualTo(address))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("1")));

        //Alternative 2
        String address2 = "/v1/color?id=red";
        wireMockServer.stubFor(get(urlEqualTo(address2))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("2")));

        client = new HttpClient();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                wireMockServer.stop();
            }
        });
    }

    @Actor
    public void actor1(II_Result r) {
        r.r1 = client.getValue("?id=red");
    }

    @Actor
    public void actor2(II_Result r) {
        r.r2 = client.getValue("?id=blue");
    }

}

