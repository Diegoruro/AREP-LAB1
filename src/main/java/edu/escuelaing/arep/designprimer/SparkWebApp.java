package edu.escuelaing.arep.designprimer;

import java.util.HashMap;

import static spark.Spark.*;

/**
 * Minimal web app example for Heroku using SparkWeb
 *
 * @author daniel
 */
public class SparkWebApp {

    private static HashMap cache = new HashMap();

    private static String requestInfo;

    /**
     * This main method uses SparkWeb static methods and lambda functions to
     * create a simple Hello World web app. It maps the lambda function to the
     * /hello relative URL.
     */
    public static void main(String[] args) {
        port(getPort());
        System.out.println(getPort());
        staticFiles.location("/public");
        get("/stocks", (req,res)->{
            String stockName = req.queryParams("stockName");
            String time = req.queryParams("time");
            int providerNumber = Integer.parseInt(req.queryParams("provider"));
            String provider = getProvider(providerNumber, stockName, time);
            Request request = Request.newBuilder()
                    .withStockName(stockName)
                    .withTime(time)
                    .withProvider(provider)
                    .build();

            if (cache.containsKey(requestInfo)){
                return cache.get(requestInfo);
            }else {
                String response = HttpConnection.getStock(request);
                cache.put(requestInfo,response);
                return response;
            }
        });

    }

    public static String getProvider(int number, String stockName, String time){
        switch (number){
            case 0:
                requestInfo = stockName+", "+time +", https://www.alphavantage.co/query?function=TIME_SERIES_$TIME&symbol=$STOCKNAME&interval=60min&apikey=M0S4OCCQ7ELSDKH3";
                return "https://www.alphavantage.co/query?function=TIME_SERIES_$TIME&symbol=$STOCKNAME&interval=60min&apikey=M0S4OCCQ7ELSDKH3";
            case 1:
                requestInfo = stockName+", https://api.polygon.io/v1/open-close/$STOCKNAME/$TIME?adjusted=true&apiKey=6aOpL4kJgNw8Ep47rJrGBG7YwrAu0Py2";
                return "https://api.polygon.io/v1/open-close/$STOCKNAME/$TIME?adjusted=true&apiKey=6aOpL4kJgNw8Ep47rJrGBG7YwrAu0Py2";
        }
        return null;
    }


    /**
     * This method reads the default port as specified by the PORT variable in
     * the environment.
     * <p>
     * Heroku provides the port automatically so you need this to run the
     * project on Heroku.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

}
