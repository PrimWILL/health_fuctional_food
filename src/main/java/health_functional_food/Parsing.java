package health_functional_food;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.*;

public class Parsing {
	
	public void Acknowledgment(NodeList list, Connection conn) {

		try {
			String sql = "insert into Acknowledgment values(?,?,?,?,?,?,?)";

			for(int i = 0; i<list.getLength(); i++) {
				System.out.println(i);
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element)node;
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, getTagValue("BSSH_NM", eElement));
					stmt.setString(2, getTagValue("APLC_RAWMTRL_NM", eElement));
					stmt.setString(3, getTagValue("HF_FNCLTY_MTRAL_RCOGN_NO", eElement));
					stmt.setString(4, getTagValue("IFTKN_ATNT_MATR_CN", eElement));
					stmt.setString(5, getTagValue("ADDR", eElement));
					stmt.setString(6, getTagValue("FNCLTY_CN", eElement));
					stmt.setString(7, getTagValue("DAY_INTK_CN", eElement));
					
					stmt.executeUpdate();
					//System.out.println("sucess to save");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void Item(NodeList list, Connection conn) {

		try {
			String sql = "insert into Item values(?,?,?,?,?,?)";

			for(int i = 0; i<list.getLength(); i++) {
				System.out.println(i);
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element)node;
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, getTagValue("INTK_UNIT", eElement));
					stmt.setString(2, getTagValue("IFTKN_ATNT_MATR_CN", eElement));
					stmt.setString(3, getTagValue("DAY_INTK_HIGHLIMIT", eElement));
					stmt.setString(4, getTagValue("PRDCT_NM", eElement));
					stmt.setString(5, getTagValue("DAY_INTK_LOWLIMIT", eElement));
					stmt.setString(6, getTagValue("PRIMARY_FNCLTY", eElement));
					
					stmt.executeUpdate();
					//System.out.println("sucess to save");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void Gmp(NodeList list, Connection conn) {

		try {
			String sql = "insert into Gmp values(?,?,?,?,?)";

			for(int i = 0; i<list.getLength(); i++) {
				System.out.println(i);
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element)node;
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, getTagValue("BSSH_NM", eElement));
					stmt.setLong(2, Long.parseLong(getTagValue("LCNS_NO", eElement)));
					stmt.setLong(3, Integer.parseInt(getTagValue("GMP_APPN_NO", eElement)));
					stmt.setString(4, getTagValue("PRSDNT_NM", eElement));
					stmt.setString(5, getTagValue("APPN_CANCL_DT", eElement));
					
					stmt.executeUpdate();
					//System.out.println("sucess to save");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	// integer가 아니고 long..... 이것도 수정 필요!
	public void Retail(NodeList list, Connection conn) {
		
		try {
			String sql = "insert into Retail values(?,?,?,?,?,?,?)";

			for(int i = 0; i<list.getLength(); i++) {
				System.out.println(i);
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element)node;
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, Integer.parseInt(getTagValue("PRMS_DT", eElement)));
					stmt.setString(2, getTagValue("BSSH_NM", eElement));
					stmt.setString(3, getTagValue("TELNO", eElement));
					stmt.setLong(4, Long.parseLong(getTagValue("LCNS_NO", eElement)));
					stmt.setString(5, getTagValue("PRSDNT_NM", eElement));
					stmt.setString(6, getTagValue("INSTT_NM", eElement));
					stmt.setString(7, getTagValue("LOCP_ADDR", eElement));
					stmt.executeUpdate();
					//System.out.println("sucess to save");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	public static String getTagValue(String tag, Element eElement) {
		//System.out.println("getTagValue!!!");
		NodeList nlist = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlist.item(0);
		if(nValue==null) return null;
		return nValue.getNodeValue();
	}
	
	public void parsing(String relation, Connection conn){
		String serviceName=null;
		if (relation == "Acknowledgment")
			serviceName = "I-0040";
		else if (relation == "Item")
			serviceName = "I2710";
		else if (relation == "Retail")
			serviceName = "I1290";
		else if (relation == "Gmp")
			serviceName = "I0630";
		
		
		// 여기서 각각 url 들고오고, 각 변수에 맞게 parsing 한 다음 insert문으로 간다?
		try {
			//System.out.println("run()!!!");
		
			String url = "http://openapi.foodsafetykorea.go.kr/api/384fb31b44854692a1aa/"+serviceName+"/xml/1/5";
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    
			// 파싱
		    //System.out.println("파싱!!!!!!");
		  
		    Document doc = db.parse(url);
		    doc.getDocumentElement().normalize();

		    NodeList list = doc.getElementsByTagName("row");
		    System.out.println(list.getLength());
		    
		    
		    // 그냥 입력부터 serviceName형식으로 주면 코드는 줄어들텐데...
		    if (relation == "Acknowledgment") Acknowledgment(list, conn);
		    else if (relation == "Item") Item(list, conn);
		    else if (relation == "Retail") Retail(list, conn);
		    else if (relation == "Gmp") Gmp(list, conn);
		    
		    
		} catch (Exception e) {
			System.out.println("에러 : " + e.getMessage());
		}
		
	}
}