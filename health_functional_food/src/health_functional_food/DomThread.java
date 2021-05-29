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

		// ���� �ڷ��� ���������� �պ��� �� �ʿ伺 ����!! varchar�� �Ұ���, �ƴϸ� integer ������ �� ������
		// -> ���İ����� �� �ʿ� ������ �׳� varchar�� �θ� ���ٵ� �׷� ������ ���°� �ʹ� �����ο���.

		try {

			Scanner scan = new Scanner(System.in);

			System.out.println("Connecting PostgreSQL database");
			// JDBC�� �̿��� PostgreSQL ���� �� �����ͺ��̽� ����
			String connectionUrl = "jdbc:postgresql:health_functional_food";
			String user = "postgres";
			String password = "gksk63^#";


			conn = DriverManager.getConnection(connectionUrl, user, password);                          


			System.out.println("< < Creating Acknowledgment, Item, Retail, Gmp relations > >");
			// 4�� ���׺� ���� : Create table�� �̿�


			stm = conn.createStatement();
			// table ������ ������ ���� �ʿ�!
			String create = "create table Acknowledgment(BSSH_NM varchar(20), PRDCT_NM varchar(200), HF_FNCLTY_MTRAL_RCOGN_NO varchar(20), IFTKN_ATNT_MATR_CN varchar(100), INDUTY_NM varchar(15), ADDR varchar(100), PRIMARY_FNCLTY varchar(300), DAY_INTK_CN varchar(30));\r\n"
					+ "create table Item(INTK_UNIT varchar(5), IFTKN_ATNT_MATR_CN varchar(200), DAY_INTK_HIGHLIMIT numeric(20,4), PRDCT_NM varchar(60), DAY_INTK_LOWLIMIT numeric(20, 4), PRIMARY_FNCLTY varchar(300), INTK_MEMO varchar(200));\r\n"
					+ "create table Retail(PRMS_DT integer, BSSH_NM varchar(30), TELNO varchar(11), LCNS_NO bigint, PRSDNT_NM varchar(5), INDUTY_NUM varchar(11), INSTT_NM varchar(20), LOCP_ADDR varchar(100));\r\n"
					+ "create table Gmp(BSSH_NM varchar(30), LCNS_NO bigint, GMP_APPN_NO integer, PRSDNT_NM varchar(5), INDUTY_CD_NM varchar(11), APPN_CANCL_DT varchar(11));";
			
			stm.executeUpdate(create);
			
			System.out.println("< < create table �Ϸ� > >\n");
			
			System.out.println("< < Insert ���� > >");

			//// ���⼭ parsing �θ��� �ű� �ȿ��� ��  4�� table�� ������ �θ���.

			Parsing par = new Parsing();
			System.out.println("Acknowledgment ����");
			par.parsing("Acknowledgment", conn);
			System.out.println("Acknowledgment ��");

			System.out.println("Item ����");
			par.parsing("Item", conn);
			System.out.println("Item ��");

			System.out.println("Retail ����");
			par.parsing("Retail", conn);
			System.out.println("Retail ��");

			System.out.println("Gmp ����");
			par.parsing("Gmp", conn);
			System.out.println("Gmp ��");

			System.out.println("< < Insert �Ϸ� > >");

			
			
			
			//// �������� xml������ db�� �ҷ����� �� ���� ����
			//// ���� db�� �ִ°� search �ϴ°͸� ����

			// �ְ��� �ϴ°��� input�̶�� �ΰ�
			String input=null;
			
			Search search = new Search();
			search.factory_gmp(input, p, conn, r);
			search.regist(input, p, conn, r);
			search.food(input, p, conn, r);
			
			
			
			
			
			
			
			
			
			
			
		} catch(SQLException ex) {
			throw ex;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	// ******* �ϳ��� Ȯ�ο뵵 ******** //
	
	public static String getTagValue(String tag, Element eElement) {
		System.out.println("getTagValue!!!");
		NodeList nlist = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlist.item(0);
		if(nValue==null) return null;
		return nValue.getNodeValue();
	}
	
	
	public void run() {
		
		try {
			System.out.println("���!!!!!!!!!");
			
			String url = "http://openapi.foodsafetykorea.go.kr/api/384fb31b44854692a1aa/I0630/xml/1/5";
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    
			// �Ľ�
		    System.out.println("�Ľ�!!!!!!");
		  
		    Document doc = db.parse(url);
		    doc.getDocumentElement().normalize();

		    NodeList list = doc.getElementsByTagName("row");
		    System.out.println(list.getLength());
		    
		    for(int i = 0; i<list.getLength(); i++) {
		    	Node node = list.item(i);
		    	if(node.getNodeType() == Node.ELEMENT_NODE) {
		    		Element eElement = (Element)node;
		    		System.out.println("###########");
		    		
		    		System.out.println("�������� : " + Integer.parseInt(getTagValue("APPN_DT", eElement)));
		    		System.out.println("���Ҹ� : " + getTagValue("BSSH_NM", eElement));
		    		System.out.println("���������ȣ : " + Long.parseLong(getTagValue("LCNS_NO", eElement)));
		    		System.out.println("������ȣ : " + Integer.parseInt(getTagValue("GMP_APPN_NO", eElement)));
		    		System.out.println("��ǥ�ڸ� : " + getTagValue("PRSDNT_NM", eElement));
		    		System.out.println("������ : " + getTagValue("INDUTY_CD_NM", eElement));
		    		System.out.println("������� : " + getTagValue("APPN_CANCL_DT", eElement));
		    		
		    		
		    		
		    		/*
		    		System.out.println("�㰡���� : " + Integer.parseInt(getTagValue("PRMS_DT", eElement)));
		    		System.out.println("���Ҹ� : " + getTagValue("BSSH_NM", eElement));
		    		System.out.println("��ȭ��ȣ : " + getTagValue("TELNO", eElement));
		    		System.out.println("���㰡 ��ȣ : " + Long.parseLong(getTagValue("LCNS_NO", eElement)));
		    		System.out.println("��ǥ�ڸ� : " + getTagValue("PRSDNT_NM", eElement));
		    		System.out.println("���� : " + getTagValue("INDUTY_NM", eElement));
		    		System.out.println("����� : " + getTagValue("INSTT_NM", eElement));
		    		System.out.println("�ּ� : " + getTagValue("LOCP_ADDR", eElement));
		    		*/
		    	}
		    }			
			
		} catch (Exception e) {
			System.out.println("���� : " + e.getMessage());
		}

	}
	
}
