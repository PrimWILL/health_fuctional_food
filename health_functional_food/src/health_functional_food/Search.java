package health_functional_food;

import java.sql.*;

public class Search {
	String query;

	public void factory_gmp(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		// 해당 업소에 대한 GMP 지정 현황 조회
		query = "select * from Gmp where BSSH_NM like '%?%';";
		p = conn.prepareStatement(query);
		p.setString(1, input);
		r = p.executeQuery();

		while(r.next()) {
			System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7));
		}

	}
	
	public void regist(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		// 해당 업소에 대한 	건강기능 판매업자 등록 유무 조회
		// 건강기능식품 판매업(retail)에서 업소명(BSSH_NM) 검색 기능
		query = "select * from Retail where BSSH_NM like '%?%';";
		p = conn.prepareStatement(query);
		p.setString(1, input);
		r = p.executeQuery();

		while(r.next()) {
			System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7));
		}
	}
	
	public void food(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		// 해당 원료에 대한 정보 조회
		// 건강기능식품 품목 분류 조회(item)와 건강기능식품 기능성 원료 인정 현황(Acknowledgement)에서 품목명(PRDCT_NM) 검색
		query = "select * from acknowledgment natural full outer join item where prdct_nm like '%?%';";
		p = conn.prepareStatement(query);
		p.setString(1, input);
		r = p.executeQuery();

		while(r.next()) {
			// 나중에 갯수 정함
			System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7));
		}
	}
	
	public void food_recognize(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		
		
	}
}
