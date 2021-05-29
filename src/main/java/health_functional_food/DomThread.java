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
		
		// 변수 자료형 여러가지로 손봐야 할 필요성 있음!! varchar로 할건지, 아니면 integer 형으로 할 것인지
		// -> 정렬같은거 할 필요 없으면 그냥 varchar로 두면 될텐데 그럼 보고서에 적는게 너무 단조로워짐.

		try {

			Scanner scan = new Scanner(System.in);

			System.out.println("Connecting PostgreSQL database");
			// JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
			
		
			String connectionUrl = "jdbc:postgresql:health_functional_food";
			String user = "postgres";
			String password = "1234";


			conn = DriverManager.getConnection(connectionUrl, user, password);                          
			
	

			System.out.println("< < Creating Acknowledgment, Item, Retail, Gmp relations > >");
			// 4개 테잉블 생성 : Create table문 이용

			// commit!
			stm = conn.createStatement();
			// table 생성문 보고서에 수정 필요!
			String create = "create table Acknowledgment(BSSH_NM varchar(20), PRDCT_NM varchar(200), HF_FNCLTY_MTRAL_RCOGN_NO varchar(20), IFTKN_ATNT_MATR_CN varchar(100), ADDR varchar(100), PRIMARY_FNCLTY varchar(300), DAY_INTK_HIGHLIMIT varchar(30));\r\n"
					+ "create table Item(INTK_UNIT varchar(5), IFTKN_ATNT_MATR_CN varchar(200), DAY_INTK_HIGHLIMIT varchar(30), PRDCT_NM varchar(60), DAY_INTK_LOWLIMIT varchar(30), PRIMARY_FNCLTY varchar(300));\r\n"
					+ "create table Retail(PRMS_DT integer, BSSH_NM varchar(30), TELNO varchar(11), LCNS_NO bigint, PRSDNT_NM varchar(5), INSTT_NM varchar(20), LOCP_ADDR varchar(100));\r\n"
					+ "create table Gmp(BSSH_NM varchar(30), LCNS_NO bigint, GMP_APPN_NO integer, PRSDNT_NM varchar(5), APPN_CANCL_DT varchar(11));";
			
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
			conn.close();
			//Search search = new Search();
			//search.init();
			//input = "#";
			
			//System.out.println(search.regist(input));
			//search.regist(input, p, conn, r);
			//search.food(input, p, conn, r);
			
			
			
			//지윤이가 set으로 키워드 넘겨주고
			//내가 키워드로 ㅣ나ㅓㅇㄹ니어린 해서 결과 저장하면 get으로 들고갈 수 있게끔
			

			
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