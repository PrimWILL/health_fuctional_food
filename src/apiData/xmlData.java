package apiData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class xmlData {
    String openResult = "";
    String xmlResult = "";

    private String parseXml() {
        // parse
        // String?
        return xmlResult;
    }

    public void setOpenResult(String xmlData) {
        openResult = xmlData;
    }

    public String getXmlResult() {
        return xmlResult;
    }
}
