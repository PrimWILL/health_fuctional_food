<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="health_functional_food.DomThread" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
  <h1 id = "top">건기식보</h1>
  <h4>원료 검색 결과</h4>
  <form action='index.jsp'>
	  <input type='submit' value="다시 검색하기"></input>
   </form>
   <%
   
   String itemName = request.getParameter("itemName");
   
   String itemNotice = request.getParameter("itemNotice");
   String itemMainitem = request.getParameter("itemMainitem");
   String itemDayHigh = request.getParameter("itemDayHigh");
   String itemCompanyName = request.getParameter("itemCompanyName");
   String itemAdmitNum = request.getParameter("itemAdmitNum");
   String itemAddr = request.getParameter("itemAddr");
   String itemUnit = request.getParameter("itemUnit");
   String itemDayLow = request.getParameter("itemDayLow");
   
   PreparedStatement p=null;
	ResultSet r = null;
	Connection conn = null;
   
   String query;
   String connectionUrl = "jdbc:postgresql:health_functional_food";
	String user = "postgres";
	String password = "1234";

	conn = DriverManager.getConnection(connectionUrl, user, password);  
   query = "select * from AckItem where prdct_nm like '%"+itemName+"%';";
	
	p = conn.prepareStatement(query);
	r = p.executeQuery();
	
	out.print("<p>다음은 '" + itemName + "' 에 대한 검색 결과입니다.</p>");
	out.print("<table border='1'>");
	out.print("<th>품목명</th>");
	
	ArrayList<Integer> table = new ArrayList<>();
	table.add(1);
	if (itemNotice != null && itemNotice.compareTo("itemNotice") == 0) {
		table.add(2);
		out.print("<th>섭취시 주의사항</th>");
	}
	
	if (itemMainitem != null && itemMainitem.compareTo("itemMainitem") == 0) {
		table.add(3);
		out.print("<th>주된 기능성</th>");
	}
	
	if (itemDayHigh != null && itemDayHigh.compareTo("itemDayHigh") == 0) {
		table.add(4);
		out.print("<th>일일섭취량 상한</th>");
	}
	
	if (itemCompanyName != null && itemCompanyName.compareTo("itemCompanyName") == 0) {
		table.add(5);
		out.print("<th>업체명</th>");
	}
	
	if (itemAdmitNum != null && itemAdmitNum.compareTo("itemAdmitNum") == 0) {
		table.add(6);
		out.print("<th>인정번호</th>");
	}
	
	if (itemAddr != null && itemAddr.compareTo("itemAddr") == 0) {
		table.add(7);
		out.print("<th>주소</th>");
	}
	
	if (itemUnit != null && itemUnit.compareTo("itemUnit") == 0) {
		table.add(8);
		out.print("<th>단위</th>");
	}
	
	if (itemDayLow != null && itemDayLow.compareTo("itemDayLow") == 0) {
		table.add(9);
		out.print("<th>일일섭취량 하한</th>");
	}
	
	boolean cp = false;
	while(r.next()) {
		cp = true;
		out.print("<tr>");
		for (int i = 0; i < table.size(); i++) {
			int value = table.get(i);			
			out.print("<td>"+r.getString(value)+"</td>");
		}
		out.print("</tr>");
	}
	out.print("</table><br/><br/>");
	
	if (cp == false) {
		out.print("검색 결과가 없습니다. 다시 검색하세요.");
	}
	else {
		query = "select * from Ranking where PRDCT_NM = '"+itemName+"';";
		
		p = conn.prepareStatement(query);
		r = p.executeQuery();
		if (r.next()) {
			query = "update Ranking set SEARCH_CNT=SEARCH_CNT+1 where PRDCT_NM = '"+itemName+"';";
			System.out.println("B");
			p = conn.prepareStatement(query);
			p.executeUpdate();
		}
		else {
			query = "insert into Ranking values ('"+itemName+"', 1);";
			System.out.println("A");
			p = conn.prepareStatement(query);
			p.executeUpdate();
		}
	}
	conn.close();
   %>
   <br/>
   <br/>
   <button type="button" onclick="location.href='#top'">위로 올라가기</button>
</body>
</html>