package health_functional_food;

import java.sql.*;

public class Search {
	String query;

	PreparedStatement p=null;
	ResultSet r = null;
	Connection conn = null;
	
	
	public void init() throws SQLException {
		
		String connectionUrl = "jdbc:postgresql:health_functional_food";
		String user = "postgres";
		String password = "1234";

		conn = DriverManager.getConnection(connectionUrl, user, password);        

	}

	
	public String factory_gmp(String input) throws SQLException {
		// 해당 업소에 대한 GMP 지정 현황 조회
		query = "select * from Gmp where BSSH_NM like '%"+input+"%';";

		String output="";

		p = conn.prepareStatement(query);
		r = p.executeQuery();
		String name = "업소명";
		System.out.printf("%-15s\t\t", name);
		//System.out.println("업소고유번호\t\tGMP 지정번호\t\t대표자명\t\tGMP취소일자\t\t");
		String temp = "업소고유번호\t\tGMP 지정번호\t\t대표자명\t\tGMP취소일자\t\t";
		while(r.next()) {
			output = output + temp + "\n";
			temp = r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5);
		}
		return output;
	}
	
	public String regist(String input) throws SQLException {
		// 해당 업소에 대한 	건강기능 판매업자 등록 유무 조회
		// 건강기능식품 판매업(retail)에서 업소명(BSSH_NM) 검색 기능
		
		query = "select * from Retail where BSSH_NM like '%"+input+"%';";
		String output="";

		p = conn.prepareStatement(query);
		r = p.executeQuery();
		//System.out.println("허가일자\t\t업소명\t\t전화번호\t\t인허가 번호\t\t대표자명\t\t기관명\t\t주소");
		String temp = "허가일자\t\t업소명\t\t전화번호\t\t인허가 번호\t\t대표자명\t\t기관명\t\t주소";
		while(r.next()) {
			output = output + temp + "\n";
			temp = r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7);
		}
		return output;
	}

	
	public String food(String input) throws SQLException {
		// 해당 원료에 대한 정보 조회
		// 건강기능식품 품목 분류 조회(item)와 건강기능식품 기능성 원료 인정 현황(Acknowledgement)에서 품목명(PRDCT_NM) 검색
		
		query = "select * from acknowledgment natural full outer join item where prdct_nm like '%"+input+"%';";
		String output="";
		
		p = conn.prepareStatement(query);
		r = p.executeQuery();
		
		//System.out.println("품목명\t\t섭취시 주의사항\t\t주된 기능성\t\t일일섭취량 상한\t\t업체명\t\t주소\t\t단위\t\t일일섭취량 하한");
		String temp = "<pre>품목명\t\t섭취시 주의사항\t\t주된 기능성\t\t일일섭취량 상한\t\t업체명\t\t주소\t\t단위\t\t일일섭취량 하한</pre>";
		while(r.next()) {
			output = output + temp + "<br/>";
			temp = "<pre>" + r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7)+"\t\t"+r.getString(8) + "</pre>";
		}
		System.out.println("Search: " + output);
		return output;
	}
	
	public void food_recognize(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		
		
	}
}