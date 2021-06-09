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
	
		try {

			Scanner scan = new Scanner(System.in);

			System.out.println("Connecting PostgreSQL database");
			
		
			String connectionUrl = "jdbc:postgresql:health_functional_food";
			String user = "postgres";
			String password = "1234";


			conn = DriverManager.getConnection(connectionUrl, user, password);                          
			
	

			System.out.println("< < Creating Acknowledgment, Item, Retail, Gmp relations > >");

			stm = conn.createStatement();

			String create = "create table Acknowledgment(BSSH_NM varchar(30), PRDCT_NM varchar(200), HF_FNCLTY_MTRAL_RCOGN_NO varchar(20), IFTKN_ATNT_MATR_CN varchar(100), ADDR varchar(100), PRIMARY_FNCLTY varchar(300), DAY_INTK_HIGHLIMIT varchar(60));\r\n"
					+ "create table Item(INTK_UNIT varchar(10), IFTKN_ATNT_MATR_CN varchar(200), DAY_INTK_HIGHLIMIT varchar(30), PRDCT_NM varchar(70), DAY_INTK_LOWLIMIT varchar(30), PRIMARY_FNCLTY varchar(310));\r\n"
					+ "create table Retail(PRMS_DT integer, BSSH_NM varchar(30), TELNO varchar(12), LCNS_NO bigint, PRSDNT_NM varchar(20), INSTT_NM varchar(20), LOCP_ADDR varchar(100));\r\n"
					+ "create table Gmp(BSSH_NM varchar(30), LCNS_NO bigint, GMP_APPN_NO integer, PRSDNT_NM varchar(10), APPN_CANCL_DT varchar(11));";
			
			stm.executeUpdate(create);
			
			System.out.println("< < create table 완료 > >\n");
			
			System.out.println("< < Insert 시작 > >");


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

			String ackItemView = "create view AckItem as (select * from acknowledgment natural full outer join item);";
			stm.executeUpdate(ackItemView);
			
			conn.close();
			
		} catch(SQLException ex) {
			throw ex;
		}
	}

}