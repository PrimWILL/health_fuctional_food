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
			
			System.out.println("< < create table success > >\n");
			
			System.out.println("< < Insert start > >");


			Parsing par = new Parsing();
			System.out.println("Acknowledgment start");
			par.parsing("Acknowledgment", conn);
			System.out.println("Acknowledgment end");

			System.out.println("Item start");
			par.parsing("Item", conn);
			System.out.println("Item end");

			System.out.println("Retail start");
			par.parsing("Retail", conn);
			System.out.println("Retail end");

			System.out.println("Gmp start");
			par.parsing("Gmp", conn);
			System.out.println("Gmp end");

			System.out.println("< < Insert success > >");

			String ackItemView = "create view AckItem as (select * from acknowledgment natural full outer join item);";
			stm.executeUpdate(ackItemView);
			
			
			
			// create ranking table
			String rankingCreate="create table Ranking(PRDCT_NM varchar(70), SEARCH_CNT integer);\r\n";
			stm.executeUpdate(rankingCreate);
			System.out.println("\n< <Ranking Table Create> >\n");
			
			/*
			String rankingShow="select rank() over(order by VIEWS desc) as RANK , * from Ranking limit 10;\r\n";
			ResultSet resultRanking=stm.executeQuery(rankingShow); 
			System.out.println("RANK    PRDCT_NM                VIEWS");
			
			while(resultRanking.next()) 
			{
				String rank=resultRanking.getString("RANK");
				String prdct_nm=resultRanking.getString("PRDCT_NM");
				String views=resultRanking.getString("VIEWS");
				
				System.out.println(rank+"\t"+prdct_nm+"\t\t\t"+views);  
			}*/
			
			
			conn.close();
			
		} catch(SQLException ex) {
			throw ex;
		}
	}

}
