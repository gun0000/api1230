<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- 태그 라이브러리 선언 Core -->  <!--jsp jstl-라이브러리----"  -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>B</title>
	</head>
	<body>
		<a href="<%=request.getContextPath()%>/member/memberJoin.do">회원가입하기</a>
		<a href="<%=request.getContextPath()%>/member/memberList.do">회원목록</a>
		<a href="<%=request.getContextPath()%>/board/boardList.do">게시판목록</a>

		<c:choose>
			<c:when test="${sessionScope.midx != null}">
				<span>회원번호는: ${sessionScope.midx}</span>
				<span>회원이름는: ${sessionScope.memberName}</span>
				<a href="<%=request.getContextPath()%>/member/memberLogOut.do">회원로그아웃</a>
			</c:when>
			<c:otherwise>
				<a href="<%=request.getContextPath()%>/member/memberLogin.do">회원로그인</a>
			</c:otherwise>
		</c:choose>
		<div>A가수정함</div>
		
		
		
		
		
		
		
		
		
		
	</body>
</html>
