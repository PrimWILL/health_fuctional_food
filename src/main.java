import apiData.openData;
import apiData.xmlData;

public class main {
    public static void main(String[] args) {
        openData getOpenData = new openData();
        String acknowledgementResult = getOpenData.getResult("Acknowledgement", "1", "5");
        //String ItemResult = getOpenData.getResult("Item", "1", "5");
        //String RetailResult = getOpenData.getResult("Retail", "1", "5");
        //String GmpResult = getOpenData.getResult("Gmp", "1", "5");
        System.out.println(acknowledgementResult);

        xmlData myxmlData = new xmlData();
        myxmlData.setOpenResult(acknowledgementResult);

        //
    }
}
