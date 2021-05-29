package health_functional_food;

import java.sql.*;

public class Search {
	String query;

	public void factory_gmp(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		// �ش� ���ҿ� ���� GMP ���� ��Ȳ ��ȸ
		query = "select * from Gmp where BSSH_NM like '%?%';";
		p = conn.prepareStatement(query);
		p.setString(1, input);
		r = p.executeQuery();

		while(r.next()) {
			System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7));
		}

	}
	
	public void regist(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		// �ش� ���ҿ� ���� 	�ǰ���� �Ǹž��� ��� ���� ��ȸ
		// �ǰ���ɽ�ǰ �Ǹž�(retail)���� ���Ҹ�(BSSH_NM) �˻� ���
		query = "select * from Retail where BSSH_NM like '%?%';";
		p = conn.prepareStatement(query);
		p.setString(1, input);
		r = p.executeQuery();

		while(r.next()) {
			System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7));
		}
	}
	
	public void food(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		// �ش� ���ῡ ���� ���� ��ȸ
		// �ǰ���ɽ�ǰ ǰ�� �з� ��ȸ(item)�� �ǰ���ɽ�ǰ ��ɼ� ���� ���� ��Ȳ(Acknowledgement)���� ǰ���(PRDCT_NM) �˻�
		query = "select * from acknowledgment natural full outer join item where prdct_nm like '%?%';";
		p = conn.prepareStatement(query);
		p.setString(1, input);
		r = p.executeQuery();

		while(r.next()) {
			// ���߿� ���� ����
			System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7));
		}
	}
	
	public void food_recognize(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		
		
	}
}
