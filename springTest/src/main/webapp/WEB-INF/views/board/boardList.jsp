<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- 태그 라이브러리 선언 Core -->

    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판목록</title>
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
			.board_num{border: none; margin-left:auto;margin-right:auto;}
			.board_num1{border: none; text-align: right; width: 920px;}
			.board_num2{border: none; text-align: center; width: 300px; display: block;}
			.board_num3{border: none; text-align: left; width: 1040px;}
			.size{width: 1920px;}
		</style>
	</head> 
	<body>
		<h1>게시판 목록</h1>
			<form name="frm" action="${pageContext.request.contextPath}/board/boardList.do" method="post" id="board_search">
				<select class="btn2" name="searchType">
					<option value="subject">제목</option>
					<option value="writer">작성자</option> 
				</select>
				<input class="btn2" name="keyword" type="text" >
				<input class="btn2" type="submit" name="submit" value="검색">
			</form> 
			<table class="clear">
				<tr>
					<th>게시물번호</th> 
					<th>제목</th>
					<th>작성자</th>
					<th>날짜</th>
				</tr>
				<c:forEach var="bv" items="${blist}"><!--for문-->
					<tr>
						<td>${bv.bidx}</td>
						<td>	
							<c:forEach var="i" begin="1" end="${bv.level_}" step="1"><!--for문-->
								&nbsp;&nbsp;
								<c:if test="${i == bv.level_}"><!--if문-->
									ㄴ
								</c:if><!--if문-end-->
		
							</c:forEach><!--for문-end-->
							
						<a href="${pageContext.request.contextPath}/board/boardContents.do?bidx=${bv.bidx}">${bv.subject}</a></td>
						<td>${bv.writer}</td>
						<td>${bv.writeday.substring(0, 10)}</td>
					</tr>
				</c:forEach><!--for문-end-->
			</table>
			<table class="board_num"><%//페이징부분 %>
				<tr class="board_num size" >
					<td class="board_num1">
						
						<c:if test="${pm.prev == true}">
							<a href="${pageContext.request.contextPath}/board/boardList.do?page=${pm.startPage-1}&serchType=${pm.scri.searchType}&keyword=${pm.encoding(pm.scri.keyword)}">◀</a>
						</c:if><!--if문-end-->
						
					</td>
					<td class="board_num2">
						
						<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}" step="1">
						<a href="${pageContext.request.contextPath}/board/boardList.do?page=${i}&serchType=${pm.scri.searchType}&keyword=${pm.encoding(pm.scri.keyword)}">${i}</a>
						</c:forEach><!--for문-end-->
						
					</td>
					<td class="board_num3">
						
						<c:if test="${pm.next && pm.endPage > 0}">
							<a href="${pageContext.request.contextPath}/board/boardList.do?page=${pm.endPage+1}&serchType=${pm.scri.searchType}&keyword=${pm.encoding(pm.scri.keyword)}">▶</a>
						</c:if><!--if문-end-->
						
					</td> 
				</tr>
			</table>
			<div>
				<input class="btn1" name="board_submit" type="button" value="글쓰기" onclick="location='${pageContext.request.contextPath}/board/boardWrite.do'">
			</div>
	</body>
</html>