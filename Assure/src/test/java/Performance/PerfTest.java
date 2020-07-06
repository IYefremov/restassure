package Performance;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static Performance.ChartCreator.toLineChartPict;

public class PerfTest {

    @Test
    public void performanceTest() throws InterruptedException {
        int threadNumber = 100;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            RestAssThread request = new RestAssThread(i);
            Thread thread = new Thread(request);
            thread.start();
            threads.add(thread);
        }

        for (Thread th : threads) {
            th.join();
        }

        System.out.println(RestAssThread.times);
        toLineChartPict("Endpoint performance", "Time Of Responses", RestAssThread.times);

        assertEquals(String.format("There is %s unsuccessful responses",RestAssThread.failures), 0, RestAssThread.failures);
    }
}