<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건기식보</title>
</head>
<body>
	<h1>건기식보</h1>
	<h4>건강 기능 식품 검색</h4>
	<p>궁금한 원료/기능성/회사를 검색하세요.</p>
	<form action='itemShow.jsp' method='get'>
	  원료검색 <input type="text" name = "itemName" style = "width:200px" id="itemName"></input>
	  <br/>
	  <b>선택항목</b> <br/>
	  <input type='checkbox' name="itemNotice" value="itemNotice" checked>섭취시 주의사항</input>
	  <input type='checkbox' name="itemMainFunc" value="itemMainFunc" checked>주된 기능성</input>
	  <input type='checkbox' name="itemDayHigh" value="itemDayHigh" checked>일일섭취량 상한</input>
	  <input type='checkbox' name="itemCompanyName" value="itemCompanyName" checked>업체명</input>
	  <input type='checkbox' name="itemAdmitNum" value="itemAdmitNum" checked>인정번호</input>
	  <input type='checkbox' name="itemAddr" value="itemAddr" checked>주소</input>
	  <input type='checkbox' name="itemUnit" value="itemUnit" checked>단위</input>
  	  <input type='checkbox' name="itemDayLow" value="itemDayLow" checked>일일섭취량 하한</input>
	  <input type='submit' value="검색"></input>
	</form>
	  <br>
	<form action='funcShow.jsp' method='get'>
	  기능성검색 <input type = "text" name = "funcName" style = "width:200px" id="functionName"></input>
	  <br/>
	  <b>선택항목</b> <br/>
	  <input type='checkbox' name="funcNotice" value="funcNotice" checked>섭취시 주의사항</input>
	  <input type='checkbox' name="funcMainFunc" value="funcMainFunc" checked>주된 기능성</input>
	  <input type='checkbox' name="funcDayHigh" value="funcDayHigh" checked>일일섭취량 상한</input>
	  <input type='checkbox' name="funcCompanyName" value="funcCompanyName" checked>업체명</input>
	  <input type='checkbox' name="funcAdmitNum" value="funcAdmitNum" checked>인정번호</input>
	  <input type='checkbox' name="funcAddr" value="funcAddr" checked>주소</input>
	  <input type='checkbox' name="funcUnit" value="funcUnit" checked>단위</input>
  	  <input type='checkbox' name="funcDayLow" value="funcDayLow" checked>일일섭취량 하한</input>
	  <input type='submit' value="검색"></input>
	</form>
	<br/>
	<form action='companyShow.jsp' method='get'>
	  회사검색 <input type="text" name="companyName" style = "width:200px" id="companyName"></input>
	  <br/>
	  <b>선택항목</b> <br/>
	  <input type='checkbox' name="gmp" value="gmp" checked>GMP지정여부</input>
  	  <input type='checkbox' name='retail' value='retail' checked>건강기능식품판매업 등록 여부</input>
	  <input type='submit' value="검색"></input>
	</form>
	<form action='hotRanking.jsp' method='get'>
	  <br/>
	  <input type='submit' value="인기 검색어 보기"></input>
	</form>
</body>
</html>