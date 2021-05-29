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
	static String input=null;

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
			String password = "1234";


			conn = DriverManager.getConnection(connectionUrl, user, password);                          
			
	

			System.out.println("< < Creating Acknowledgment, Item, Retail, Gmp relations > >");
			// 4�� ���׺� ���� : Create table�� �̿�

			// commit!
			stm = conn.createStatement();
			// table ������ ������ ���� �ʿ�!
			String create = "create table Acknowledgment(BSSH_NM varchar(20), PRDCT_NM varchar(200), HF_FNCLTY_MTRAL_RCOGN_NO varchar(20), IFTKN_ATNT_MATR_CN varchar(100), ADDR varchar(100), PRIMARY_FNCLTY varchar(300), DAY_INTK_HIGHLIMIT varchar(30));\r\n"
					+ "create table Item(INTK_UNIT varchar(5), IFTKN_ATNT_MATR_CN varchar(200), DAY_INTK_HIGHLIMIT varchar(30), PRDCT_NM varchar(60), DAY_INTK_LOWLIMIT varchar(30), PRIMARY_FNCLTY varchar(300));\r\n"
					+ "create table Retail(PRMS_DT integer, BSSH_NM varchar(30), TELNO varchar(11), LCNS_NO bigint, PRSDNT_NM varchar(5), INSTT_NM varchar(20), LOCP_ADDR varchar(100));\r\n"
					+ "create table Gmp(BSSH_NM varchar(30), LCNS_NO bigint, GMP_APPN_NO integer, PRSDNT_NM varchar(5), APPN_CANCL_DT varchar(11));";
			
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
			conn.close();
			//Search search = new Search();
			//search.init();
			//input = "#";
			
			//System.out.println(search.regist(input));
			//search.regist(input, p, conn, r);
			//search.food(input, p, conn, r);
			
			
			
			//�����̰� set���� Ű���� �Ѱ��ְ�
			//���� Ű����� �ӳ��ä����Ͼ �ؼ� ��� �����ϸ� get���� ��� �� �ְԲ�
			

			
		} catch(SQLException ex) {
			throw ex;
		}
	}
	

	public String getItem() throws SQLException{
		Search search = new Search();
		search.init();
		return search.food(this.input);
	}
	
	public String getCompany() throws SQLException{
		Search search = new Search();
		search.init();
		return search.regist(input);
	}
	
	public String getGmp() throws SQLException{
		Search search = new Search();
		search.init();
		return search.factory_gmp(input);
	}
	
	public void setItemName(String itemName) {
		this.input = itemName;
		System.out.println("setItemName" + this.input);
	}
	
	public void setCompanyName(String companyName) {
		this.input = companyName;
	}
	
}