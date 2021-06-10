<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="health_functional_food.MainApp" %>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
  <h1 id = "top">건기식보</h1>
  <h4>회사 검색 결과</h4>
   <form action='index.jsp'>
	  <input type='submit' value="다시 검색하기"></input>
   </form>
   <%
   
   String companyName = request.getParameter("companyName");
   String gmpCheck = request.getParameter("gmp");
   String retailCheck = request.getParameter("retail");
   
   PreparedStatement p=null;
	ResultSet r = null;
	Connection conn = null;
   
   String query;
   String connectionUrl = "jdbc:postgresql:health_functional_food";
	String user = "postgres";
	String password = "1234";
	
	boolean check = false;
	if (gmpCheck == null && retailCheck == null) {
		out.print("검색 결과가 없습니다. 다시 검색하세요.");
	}
	if (gmpCheck != null && gmpCheck.compareTo("gmp") == 0) {
		conn = DriverManager.getConnection(connectionUrl, user, password);  
	    query = "select * from Gmp where BSSH_NM like '%"+companyName+"%';";
		
		p = conn.prepareStatement(query);
		r = p.executeQuery();
		out.print("<p>다음은 '" + companyName + "' 에 대한 GMP 지정 여부 검색 결과입니다.</p>");
		out.print("<table border='1'>");
		out.print("<th>업소명</th>");
		out.print("<th>업소고유번호</th>");
		out.print("<th>GMP 지정번호</th>");
		out.print("<th>대표자명</th>");
		out.print("<th>GMP 취소일자</th>");
		
		boolean cp = false;
		while(r.next()) {
			cp = true;
			out.print("<tr>");
			out.print("<td>"+r.getString(1)+"</td>");
			out.print("<td>"+r.getString(2)+"</td>");
			out.print("<td>"+r.getString(3)+"</td>");
			out.print("<td>"+r.getString(4)+"</td>");
			out.print("<td>"+r.getString(5)+"</td>");
			out.print("</tr>");
		}
		out.print("</table><br/><br/>");
		
		if (cp == false) {
			out.print("검색 결과가 없습니다. 다시 검색하세요.");
		}
		else
			check = true;
	}
	
	if (retailCheck != null && retailCheck.compareTo("retail") == 0) {
		conn = DriverManager.getConnection(connectionUrl, user, password);  
	    query = "select * from Retail where BSSH_NM like '%"+companyName+"%';";
		
		p = conn.prepareStatement(query);
		r = p.executeQuery();
		
		out.print("<p>다음은 '" + companyName + "' 에 대한 건강기능식품판매업 등록 여부 검색 결과입니다.</p>");
		out.print("<table border='2'>");
		out.print("<th>허가일자</th>");
		out.print("<th>업소명</th>");
		out.print("<th>전화번호</th>");
		out.print("<th>인허가 번호</th>");
		out.print("<th>대표자명</th>");
		out.print("<th>기관명</th>");
		out.print("<th>주소</th>");
		
		boolean cp = false;
		while(r.next()) {
			cp = true;
			out.print("<tr>");
			out.print("<td>"+r.getString(1)+"</td>");
			out.print("<td>"+r.getString(2)+"</td>");
			out.print("<td>"+r.getString(3)+"</td>");
			out.print("<td>"+r.getString(4)+"</td>");
			out.print("<td>"+r.getString(5)+"</td>");
			out.print("<td>"+r.getString(6)+"</td>");
			out.print("<td>"+r.getString(7)+"</td>");
			out.print("</tr>");
		}
		out.print("</table>");
		if (cp == false) {
			out.print("검색 결과가 없습니다. 다시 검색하세요.");
		}
		else {
			check = true;
		}
	}
	
	if (check) {
		query = "select * from Ranking where PRDCT_NM = '"+companyName+"';";
		
		p = conn.prepareStatement(query);
		r = p.executeQuery();
		if (r.next()) {
			query = "update Ranking set SEARCH_CNT=SEARCH_CNT+1 where PRDCT_NM = '"+companyName+"';";
			p = conn.prepareStatement(query);
			p.executeUpdate();
		}
		else {
			query = "insert into Ranking values ('"+companyName+"', 1);";
			p = conn.prepareStatement(query);
			p.executeUpdate();
		}
		conn.close();
	}
   %>
   <br/>
   <br/>
   <button type="button" onclick="location.href='#top'">위로 올라가기</button>
</body>
</html>