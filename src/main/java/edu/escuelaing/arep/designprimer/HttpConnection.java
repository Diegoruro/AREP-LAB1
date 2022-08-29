package edu.escuelaing.arep.designprimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Logger;

public class HttpConnection {
    private static final String USER_AGENT = "Mozilla/5.0";

    private static final Logger LOGGER = Logger.getLogger(HttpConnection.class.getName());

    public static String getStock(Request request) throws IOException {
        String getUrl = request.getProvider();
        getUrl = getUrl.replace("$STOCKNAME",request.getStockName());
        if (getUrl.contains("www.alphavantage.co")){
            if (!request.getTime().equals("INTRADAY")){
                getUrl.replace("&interval=60min","");
            }
            getUrl = getUrl.replace("$TIME", request.getTime());
        }
        else if (getUrl.contains("api.polygon.io")){
            LOGGER.info("Requesting stock with date "+ LocalDate.now().minusDays(3));
            getUrl = getUrl.replace("$TIME",LocalDate.now().minusDays(3).toString());
        }
        LOGGER.info("Requesting to URL: "+getUrl);
        URL obj = new URL(getUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");

        return "Wrong Stock Name";
    }

}
