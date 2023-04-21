<%@ page import="com.myezen.myapp.domain.*" %>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원목록</title>
		<style type="text/css">
			*{font-size: 18px;}
			h1{font-size: 2em;}
			#board_search{float: right;}
			.clear{clear: both;}
			table {margin-top: 60px; width: 100%; height: 100%; border: 1px solid #444444; border-collapse: collapse;}
			th, td {border: 2px solid #444444; font-size: 18px;}
			th{background: #cfcfcf}
			ul{list-style-type: none; text-align: center;}
            .btn1{height: 30px; width: 80px; color: #fff; background-color: #444444; font-size: 18px; cursor : pointer; border: 2px solid #444444;}
            .btn2{font-size: 18px; border: solid 2px #444444; color: #fff; background-color: #444444; font-size: 18px; cursor : pointer; border: 2px solid #444444;}
		</style>
	</head> 
	<body>
		<h1>회원목록</h1>
		<table class="clear">
			<tr>
				<th>회원번호</th>
				<th>회원아이디</th>
				<th>회원이름</th>
				<th>탈퇴여부</th>
				<th>가입일</th>
			</tr>
			<c:forEach var="mv" items="${alist}">
				<tr>
					<td>${mv.midx}</td>
					<td>${mv.memberid}</td>
					<td>${mv.membername}</td>
					<td>${mv.delyn}</td>
					<td>${mv.writeday}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>