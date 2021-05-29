<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="health_functional_food.DomThread" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
  <h1>건기식보</h1>
  <h4>원료 검색 결과</h4>
   <%
   String itemName = request.getParameter("itemName");
   DomThread dom = new DomThread();
   dom.setItemName(itemName);
   String result = dom.getItem();
   %>
   <p><%= result %></p>
</body>
</html>