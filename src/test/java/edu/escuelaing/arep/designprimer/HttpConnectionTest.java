package edu.escuelaing.arep.designprimer;


import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HttpConnectionTest extends TestCase {

    private static final List<String> COMPANIES = new ArrayList<>();
    private static final List<String> PROVIDERS = new ArrayList<>();
    private static final List<String> TIME = new ArrayList<>();

    private static List<ThreadRequester> threads = new ArrayList<>();

    public HttpConnectionTest() {
        super();
    }

    public void setUp(){
        COMPANIES.add("IBM");
        COMPANIES.add("MSFT");
        COMPANIES.add("AAPL");
        PROVIDERS.add("https://www.alphavantage.co/query?function=TIME_SERIES_$TIME&symbol=$STOCKNAME&apikey=M0S4OCCQ7ELSDKH3");
        PROVIDERS.add("https://api.polygon.io/v1/open-close/$STOCKNAME/$TIME?adjusted=true&apiKey=6aOpL4kJgNw8Ep47rJrGBG7YwrAu0Py2");
        TIME.add("DAILY");
        TIME.add("WEEKLY");
        TIME.add("MONTHLY");
    }

    public void testConcurrencyRequest(){
        setUp();
        int numberOfThreads = 5;
        for (int i = 0; i < numberOfThreads; i++) {

            threads.add(new ThreadRequester(requestFeeder()));
            threads.get(i).start();
        }
        threads.forEach(thread-> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Request requestFeeder(){

        Request request = Request.newBuilder()
                .withStockName(getRandomCompany())
                .withProvider(getRandomProvider())
                .withTime(getRandomTime())
                .build();
        return request;
    }

    public static String getRandomProvider(){
        return PROVIDERS.get(new Random().nextInt(PROVIDERS.size()-1));
    }
    public static String getRandomTime(){
        return TIME.get(new Random().nextInt(TIME.size()-1));
    }
    public static String getRandomCompany(){
        return COMPANIES.get(new Random().nextInt( COMPANIES.size()-1));
    }

}
