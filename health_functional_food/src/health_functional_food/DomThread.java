package health_functional_food;

import java.net.URLDecoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;


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


		try {

			Scanner scan = new Scanner(System.in);

			System.out.println("Connecting PostgreSQL database");
			// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
			String connectionUrl = "jdbc:postgresql:health_functional_food";
			String user = "postgres";
			String password = "gksk63^#";


			conn = DriverManager.getConnection(connectionUrl, user, password);                          


			System.out.println("Creating Acknowledgment, Item, Retail, Gmp relations");
			// 4개 테잉블 생성 : Create table문 이용


			stm = conn.createStatement();
			// table 생성문 보고서에 수정 필요!
			String create = "create table Acknowledgment(BSSH_NM varchar(20), PRMS_DT integer, APLC_RAWMTRL_NM varchar(200), HF_FNCLTY_MTRAL_RCOGN_NO varchar(20), IFTKN_ATNT_MATR_CN varchar(100), INDUTY_NM varchar(15), ADDR varchar(100), FNCLTY_CN varchar(300), DAY_INTK_CN varchar(30));\r\n"
					+ "create table Item(SKLL_IX_IRDNT_RAWMTRL varchar(30), LAST_UPDT_DTM integer, INTK_UNIT varchar(5), IFKN_ATNT_MATR_CN varchar(200), DAY_INTK_HIGHLIMIT numeric(20,4), PRDCT_NM varchar(60), CRET_DTM integer, DAY_INTK_LOWLIMIT numeric(20, 4), PRIMARY_FNCLTY varchar(100), INTK_MEMO varchar(200));\r\n"
					+ "create table Retail(PRMS_DT integer, BSSH_NM varchar(30), TELNO varchar(11), LCNS_NO integer, PRSDNT_NM varchar(5), INDUTY_NUM varchar(11), INSTT_NM varchar(20), LOCP_ADDR varchar(100));\r\n"
					+ "create table Gmp(APPN_DT integer, BSSH_NM varchar(30), LCNS_NO integer, GMP_APPN_NO integer, PRSDNT_NM varchar(5), INDUTY_CD_NM varchar(11), APPN_CANCL_DT integer);";

			stm.executeUpdate(create);
			System.out.println("Inserting tuples");

			//ConnectDB connectDB = new ConnectDB();
			//Connection conn = ConnectDB.getConnection();
			//String sql = "insert into ........ values(?,?,?,?)";
			//// 여기서 parsing 부르고 거기 안에서 또  4개 table로 나눠서 부른다.

			Parsing par = new Parsing();
			System.out.println("Acknowledgment 시작!!!!");
			par.parsing("Acknowledgment", conn);
			System.out.println("Acknowledgment 끝!!!!");

			System.out.println("Item 시작!!!!");
			par.parsing("Item", conn);
			System.out.println("Item 끝!!!!");

			System.out.println("Retail 시작!!!!");
			par.parsing("Retail", conn);
			System.out.println("Retail 끝!!!!");

			System.out.println("Gmp 시작!!!!");
			par.parsing("Gmp", conn);
			System.out.println("Gmp 끝!!!!");


			DomThread dom = new DomThread();
			System.out.println("start!");
			dom.start();
		} catch(SQLException ex) {
			throw ex;
		}
	}
	
	public static String getTagValue(String tag, Element eElement) {
		System.out.println("getTagValue!!!");
		NodeList nlist = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlist.item(0);
		if(nValue==null) return null;
		return nValue.getNodeValue();
	}
	
	public void run() {
		
		try {
			System.out.println("run()!!!");
			//User user = null;
			//String addr = "http://openapi.foodsafetykorea.go.kr/api/384fb31b44854692a1aa/I1290/xml/1/5";
			//InputSource is = new InputSource(new InputStreamReader(new FileInputStream("C:\\Users\\User\\Desktop\\1000.xml"), "UTF-8"));
			
			String url = "http://openapi.foodsafetykorea.go.kr/api/384fb31b44854692a1aa/I1290/xml/1/5";
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
		    		System.out.println("인허가 번호 : " + getTagValue("LCNS_NO", eElement));
		    		System.out.println("업소명 : " + getTagValue("BSSH_NM", eElement));
		    		System.out.println("대표자명 : " + getTagValue("PRSDNT_NM", eElement));
		    		System.out.println("업종 : " + getTagValue("INDUTY_NM", eElement));
		    		System.out.println("허가일자 : " + Integer.parseInt(getTagValue("PRMS_DT", eElement)));
		    		System.out.println("전화번호 : " + getTagValue("TELNO", eElement));
		    		System.out.println("주소 : " + getTagValue("LOCP_ADDR", eElement));
		    		System.out.println("기관명 : " + getTagValue("INSTT_NM", eElement));
		    	}
		    }			
			
		} catch (Exception e) {
			System.out.println("에러 : " + e.getMessage());
		}

	}
	
}
