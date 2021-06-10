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
			
			
			
			// 순위 테이블생성
			String rankingCreate="create table Ranking(PRDCT_NM varchar(70),VIEWS integer);\r\n";
			stm.executeUpdate(rankingCreate);
			System.out.println("\n< <Ranking 테이블 생성> >\n");
			
			
			
			String rankingInsert="insert into Ranking(PRDCT_NM) (select PRDCT_NM from Item);\r\n"
									+"update Ranking set views=0 where views is null;\r\n";
								
			stm.executeUpdate(rankingInsert); 
			System.out.println("< <Ranking 테이블 데이터 초기화> >\n");
			// 데이터 초기화, view 0으로 
			
			
			// 임의로 뷰 값 설정(예시)
			String tempSql="update Ranking set VIEWS=VIEWS+1 where PRDCT_NM='베타카로틴';\r\n"
						+"update Ranking set VIEWS=VIEWS+2 where PRDCT_NM='공액리놀레산';\r\n"
						+"update Ranking set VIEWS=VIEWS+3 where PRDCT_NM='스쿠알렌';\r\n"
						+"update Ranking set VIEWS=VIEWS+4 where PRDCT_NM='나이아신';\r\n"
						+"update Ranking set VIEWS=VIEWS+5 where PRDCT_NM='엽산';\r\n"
						+"update Ranking set VIEWS=VIEWS+6 where PRDCT_NM='은행잎 추출물';\r\n"
						+"update Ranking set VIEWS=VIEWS+7 where PRDCT_NM='가르시니아캄보지아 추출물';\r\n"
						+"update Ranking set VIEWS=VIEWS+8 where PRDCT_NM='아연';\r\n"
						+"update Ranking set VIEWS=VIEWS+9 where PRDCT_NM='귀리식이섬유';\r\n"
						+"update Ranking set VIEWS=VIEWS+10 where PRDCT_NM='대두이소플라본';\r\n";
			
			stm.executeUpdate(tempSql);
			
			
			/* 조건에 맞는 뷰 필드값 증가
			String rankingViews="update Ranking set VIEWS=VIEWS+1 where PRDCT_NM=user;\r\n";
			stm.executeUpdate(rankingViews); */
			
					
			
					
			// 실시간 순위 출력
			
			System.out.println("< <원료별 실시간 순위 > >\n");
			String rankingShow="select rank() over(order by VIEWS desc) as RANK , * from Ranking limit 10;\r\n"; // 순위별로 정렬하여 출력
			ResultSet resultRanking=stm.executeQuery(rankingShow); 
			System.out.println("RANK    PRDCT_NM                VIEWS");
			
			while(resultRanking.next()) 
			{
				String rank=resultRanking.getString("RANK");
				String prdct_nm=resultRanking.getString("PRDCT_NM");
				String views=resultRanking.getString("VIEWS");
				
				System.out.println(rank+"\t"+prdct_nm+"\t\t\t"+views);  
			}
			
			
			conn.close();
			
		} catch(SQLException ex) {
			throw ex;
		}
	}

}
