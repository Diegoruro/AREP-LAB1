package edu.escuelaing.arep.designprimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class HttpConnection {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static String getStock(Request request) throws IOException {
        String getUrl = request.getProvider();
        getUrl = getUrl.replace("$STOCKNAME",request.getStockName());
        if (getUrl.contains("www.alphavantage.co")){
            getUrl = getUrl.replace("$TIME", request.getTime());
        }
        else if (getUrl.contains("api.polygon.io")){
            getUrl = getUrl.replace("$TIME",LocalDate.now().minusDays(2).toString());
        }
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
