package health_functional_food;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;
import java.io.*;


public class DomThread extends Thread{
	
	private String xml;
	
	List<String> data = new ArrayList<String>();
	
	public static void main(String[] args) throws SQLException {

		String query;
		PreparedStatement p = null;
		ResultSet r = null;
		Statement stm =null;
		Connection conn = null;

		// 변수 자료형 여러가지로 손봐야 할 필요성 있음!! varchar로 할건지, 아니면 integer 형으로 할 것인지
		// -> 정렬같은거 할 필요 없으면 그냥 varchar로 두면 될텐데 그럼 보고서에 적는게 너무 단조로워짐.

		try {

			Scanner scan = new Scanner(System.in);

			System.out.println("Connecting PostgreSQL database");
			// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
			String connectionUrl = "jdbc:postgresql:health_functional_food";
			String user = "postgres";
			String password = "gksk63^#";


			conn = DriverManager.getConnection(connectionUrl, user, password);                          


			System.out.println("< < Creating Acknowledgment, Item, Retail, Gmp relations > >");
			// 4개 테잉블 생성 : Create table문 이용


			stm = conn.createStatement();
			// table 생성문 보고서에 수정 필요!
			String create = "create table Acknowledgment(BSSH_NM varchar(20), PRDCT_NM varchar(200), HF_FNCLTY_MTRAL_RCOGN_NO varchar(20), IFTKN_ATNT_MATR_CN varchar(100), INDUTY_NM varchar(15), ADDR varchar(100), PRIMARY_FNCLTY varchar(300), DAY_INTK_CN varchar(30));\r\n"
					+ "create table Item(INTK_UNIT varchar(5), IFTKN_ATNT_MATR_CN varchar(200), DAY_INTK_HIGHLIMIT numeric(20,4), PRDCT_NM varchar(60), DAY_INTK_LOWLIMIT numeric(20, 4), PRIMARY_FNCLTY varchar(300), INTK_MEMO varchar(200));\r\n"
					+ "create table Retail(PRMS_DT integer, BSSH_NM varchar(30), TELNO varchar(11), LCNS_NO bigint, PRSDNT_NM varchar(5), INDUTY_NUM varchar(11), INSTT_NM varchar(20), LOCP_ADDR varchar(100));\r\n"
					+ "create table Gmp(BSSH_NM varchar(30), LCNS_NO bigint, GMP_APPN_NO integer, PRSDNT_NM varchar(5), INDUTY_CD_NM varchar(11), APPN_CANCL_DT varchar(11));";
			
			stm.executeUpdate(create);
			
			System.out.println("< < create table 완료 > >\n");
			
			System.out.println("< < Insert 시작 > >");

			//// 여기서 parsing 부르고 거기 안에서 또  4개 table로 나눠서 부른다.

			Parsing par = new Parsing();
			System.out.println("Acknowledgment 시작");
			par.parsing("Acknowledgment", conn);
			System.out.println("Acknowledgment 끝");

			System.out.println("Item 시작");
			par.parsing("Item", conn);
			System.out.println("Item 끝");

			System.out.println("Retail 시작");
			par.parsing("Retail", conn);
			System.out.println("Retail 끝");

			System.out.println("Gmp 시작");
			par.parsing("Gmp", conn);
			System.out.println("Gmp 끝");

			System.out.println("< < Insert 완료 > >");

			
			
			
			//// 위에까지 xml파일을 db로 불러오는 것 까지 성공
			//// 이제 db에 있는거 search 하는것만 남음

			// 넣고자 하는것을 input이라고 두고
			String input=null;
			
			Search search = new Search();
			search.factory_gmp(input, p, conn, r);
			search.regist(input, p, conn, r);
			search.food(input, p, conn, r);
			
			
			
			
			
			
			
			
			
			
			
		} catch(SQLException ex) {
			throw ex;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	// ******* 하나씩 확인용도 ******** //
	
	public static String getTagValue(String tag, Element eElement) {
		System.out.println("getTagValue!!!");
		NodeList nlist = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlist.item(0);
		if(nValue==null) return null;
		return nValue.getNodeValue();
	}
	
	
	public void run() {
		
		try {
			System.out.println("출력!!!!!!!!!");
			
			String url = "http://openapi.foodsafetykorea.go.kr/api/384fb31b44854692a1aa/I0630/xml/1/5";
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    
			// 파싱
		    System.out.println("파싱!!!!!!");
		  
		    Document doc = db.parse(url);
		    doc.getDocumentElement().normalize();

		    NodeList list = doc.getElementsByTagName("row");
		    System.out.println(list.getLength());
		    
		    for(int i = 0; i<list.getLength(); i++) {
		    	Node node = list.item(i);
		    	if(node.getNodeType() == Node.ELEMENT_NODE) {
		    		Element eElement = (Element)node;
		    		System.out.println("###########");
		    		
		    		System.out.println("지정일자 : " + Integer.parseInt(getTagValue("APPN_DT", eElement)));
		    		System.out.println("업소명 : " + getTagValue("BSSH_NM", eElement));
		    		System.out.println("업고고유번호 : " + Long.parseLong(getTagValue("LCNS_NO", eElement)));
		    		System.out.println("지정번호 : " + Integer.parseInt(getTagValue("GMP_APPN_NO", eElement)));
		    		System.out.println("대표자명 : " + getTagValue("PRSDNT_NM", eElement));
		    		System.out.println("업종명 : " + getTagValue("INDUTY_CD_NM", eElement));
		    		System.out.println("취소일자 : " + getTagValue("APPN_CANCL_DT", eElement));
		    		
		    		
		    		
		    		/*
		    		System.out.println("허가일자 : " + Integer.parseInt(getTagValue("PRMS_DT", eElement)));
		    		System.out.println("업소명 : " + getTagValue("BSSH_NM", eElement));
		    		System.out.println("전화번호 : " + getTagValue("TELNO", eElement));
		    		System.out.println("인허가 번호 : " + Long.parseLong(getTagValue("LCNS_NO", eElement)));
		    		System.out.println("대표자명 : " + getTagValue("PRSDNT_NM", eElement));
		    		System.out.println("업종 : " + getTagValue("INDUTY_NM", eElement));
		    		System.out.println("기관명 : " + getTagValue("INSTT_NM", eElement));
		    		System.out.println("주소 : " + getTagValue("LOCP_ADDR", eElement));
		    		*/
		    	}
		    }			
			
		} catch (Exception e) {
			System.out.println("에러 : " + e.getMessage());
		}

	}
	
}
