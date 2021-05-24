package apiData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class openData {
    private String openAPI(String relation, String start, String end) {
        BufferedReader br = null;
        String serviceKey = "384fb31b44854692a1aa";

        String serviceName = "";
        if (relation == "Acknowledgement")
            serviceName = "I-0040";
        else if (relation == "Item")
            serviceName = "I2710";
        else if (relation == "Retail")
            serviceName = "I1290";
        else if (relation == "Gmp")
            serviceName = "I0630";

        String dataType = "xml";
        String startIdx = start;
        String endIdx = end;

        String result = "";
        try {
            String urlstr = "http://openapi.foodsafetykorea.go.kr/api/"
                    + serviceKey + "/" + serviceName + "/" + dataType + "/" + startIdx + "/" + endIdx;
            URL url = new URL(urlstr);
            System.out.println("A");
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            System.out.println("B");
            urlconnection.setRequestMethod("GET");
            System.out.println("C");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            System.out.println("D");
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line + "\n";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public String getResult(String relation, String start, String end) {
        String result = openAPI(relation, start, end);
        return result;
    }
}
