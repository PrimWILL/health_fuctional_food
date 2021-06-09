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
  <h4>기능성 검색 결과</h4>
  <form action='index.jsp'>
	  <input type='submit' value="다시 검색하기"></input>
   </form>
   <%
   
   String funcName = request.getParameter("funcName");
   
   String funcNotice = request.getParameter("funcNotice");
   String funcMainFunc = request.getParameter("funcMainFunc");
   String funcDayHigh = request.getParameter("funcDayHigh");
   String funcCompanyName = request.getParameter("funcCompanyName");
   String funcAdmitNum = request.getParameter("funcAdmitNum");
   String funcAddr = request.getParameter("funcAddr");
   String funcUnit = request.getParameter("funcUnit");
   String funcDayLow = request.getParameter("funcDayLow");
   
   
   PreparedStatement p=null;
	ResultSet r = null;
	Connection conn = null;
   
   String query;
   String connectionUrl = "jdbc:postgresql:health_functional_food";
	String user = "postgres";
	String password = "1234";

	conn = DriverManager.getConnection(connectionUrl, user, password);  
   query = "select * from AckItem where primary_fnclty like '%"+funcName+"%';";
	
	p = conn.prepareStatement(query);
	r = p.executeQuery();
	
	out.print("<p>다음은 '" + funcName + "' 키워드가 포함된 기능성에 대한 검색 결과입니다.</p>");
	out.print("<table border='1'>");
	out.print("<th>품목명</th>");
	
	ArrayList<Integer> table = new ArrayList<>();
	table.add(1);
	if (funcNotice != null && funcNotice.compareTo("funcNotice") == 0) {
		table.add(2);
		out.print("<th>섭취시 주의사항</th>");
	}
	
	if (funcMainFunc != null && funcMainFunc.compareTo("funcMainFunc") == 0) {
		table.add(3);
		out.print("<th>주된 기능성</th>");
	}
	
	if (funcDayHigh != null && funcDayHigh.compareTo("funcDayHigh") == 0) {
		table.add(4);
		out.print("<th>일일섭취량 상한</th>");
	}
	
	if (funcCompanyName != null && funcCompanyName.compareTo("funcCompanyName") == 0) {
		table.add(5);
		out.print("<th>업체명</th>");
	}
	
	if (funcAdmitNum != null && funcAdmitNum.compareTo("funcAdmitNum") == 0) {
		table.add(6);
		out.print("<th>인정번호</th>");
	}
	
	if (funcAddr != null && funcAddr.compareTo("funcAddr") == 0) {
		table.add(7);
		out.print("<th>주소</th>");
	}
	
	if (funcUnit != null && funcUnit.compareTo("funcUnit") == 0) {
		table.add(8);
		out.print("<th>단위</th>");
	}
	
	if (funcDayLow != null && funcDayLow.compareTo("funcDayLow") == 0) {
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
   %>
   <br/>
   <br/>
   <button type="button" onclick="location.href='#top'">위로 올라가기</button>
</body>
</html>