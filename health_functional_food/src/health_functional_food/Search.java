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
		String password = "gksk63^#";

		conn = DriverManager.getConnection(connectionUrl, user, password);        

	}

	
	public String factory_gmp(String input) throws SQLException {
		// �ش� ���ҿ� ���� GMP ���� ��Ȳ ��ȸ
		//query = "select * from Gmp where BSSH_NM like '%?%';";
		query = "select * from Gmp where BSSH_NM like '%"+input+"%';";

		String output=null;

		p = conn.prepareStatement(query);
		//p.setString(1, input);
		r = p.executeQuery();
		String name = "���Ҹ�";
		System.out.printf("%-15s\t\t", name);
		System.out.println("���Ұ�����ȣ\t\tGMP ������ȣ\t\t��ǥ�ڸ�\t\tGMP�������\t\t");

		while(r.next()) {
			output = output + r + "\n";
			System.out.printf("%-15s\t\t%s\t\t%s\t\t%s\t\t%s\n",r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5));
		}
		return output;
	}
	
	public String regist(String input) throws SQLException {
		// �ش� ���ҿ� ���� 	�ǰ���� �Ǹž��� ��� ���� ��ȸ
		// �ǰ���ɽ�ǰ �Ǹž�(retail)���� ���Ҹ�(BSSH_NM) �˻� ���
		query = "select * from Retail where BSSH_NM like '%"+input+"%';";
		String output=null;

		p = conn.prepareStatement(query);
		//p.setString(1, input);
		r = p.executeQuery();
		System.out.println("�㰡����\t\t���Ҹ�\t\t��ȭ��ȣ\t\t���㰡 ��ȣ\t\t��ǥ�ڸ�\t\t�����\t\t�ּ�");

		while(r.next()) {
			output = output + r + "\n";
			//System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7));
		}
		return output;
	}
	
	public String food(String input) throws SQLException {
		// �ش� ���ῡ ���� ���� ��ȸ
		// �ǰ���ɽ�ǰ ǰ�� �з� ��ȸ(item)�� �ǰ���ɽ�ǰ ��ɼ� ���� ���� ��Ȳ(Acknowledgement)���� ǰ���(PRDCT_NM) �˻�
		query = "select * from acknowledgment natural full outer join item where prdct_nm like '%"+input+"%';";
		String output=null;
		p = conn.prepareStatement(query);
		//p.setString(1, input);
		r = p.executeQuery();
		
		System.out.println("ǰ���\t\t����� ���ǻ���\t\t�ֵ� ��ɼ�\t\t���ϼ��뷮 ����\t\t��ü��\t\t�ּ�\t\t����\t\t���ϼ��뷮 ����");
		while(r.next()) {
			// ���߿� ���� ����
			output = output + r + "\n";
			//System.out.println(r.getString(1)+"\t\t"+r.getString(2)+"\t\t"+r.getString(3)+"\t\t"+r.getString(4)+"\t\t"+r.getString(5)+"\t\t"+r.getString(6)+"\t\t"+r.getString(7)+"\t\t"+r.getString(8));
		}
		return output;
	}
	
	public void food_recognize(String input,PreparedStatement p, Connection conn, ResultSet r) throws SQLException {
		
		
	}
}
