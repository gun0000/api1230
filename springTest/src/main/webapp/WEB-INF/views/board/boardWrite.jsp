<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% //태그 라이브러리 선언 Core -->  <!--"---2023-03-23----jsp jstl-라이브러리----" 
//"---2023-03-17----jsp 로그인을 안하면 글쓰기 불가-----"
	/*if (session.getAttribute("midx") == null) { //MemberController.java에 세션에 담은 값 가져오기
		out.println("<script>alert('로그인 하셔야 합니다.'); history.back(-1);</script>"); 
	}*/
%>  

<!DOCTYPE html> 
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판 글쓰기</title>
		<style type="text/css">
			*{font-size: 18px;}
			h1{font-size: 2em;}
			textarea::placeholder{font-size: 18px; font-family: sans-serif;} 
			th, td {border: 2px solid #444444;}
			.main{width: 100%; height: 100%; border: 2px solid #444444; border-collapse: collapse;}
			#board_text{resize: none; border: none; }
			#board_text:focus {outline: none;}
			.board_input{border: none;}
			.board_input:focus {outline: none;}
			.top{margin-top: 10px; text-align: center;}
            .btn1{width: 200px; color: #fff; background-color: #444444; font-size: 18px; cursor : pointer;}
		</style>
		<script type="text/javascript">
			function check(){	
			
				var fm = document.frm;	
				if (fm.subject.value == "" ){
					alert("제목을 입력하세요");
					fm.subject.focus();
					return;
				}else if (fm.contents.value == ""){
					alert("내용을 입력하세요");
					fm.contents.focus();
					return;
				}else if (fm.writer.value == ""){
					alert("작성자를 입력하세요");
					fm.writer.focus();
					return;
				}else if (fm.pwd.value == ""){//"---2023-03-17----jsp 게시판-삭제-비밀번호추가---" 
					alert("비밀번호를 입력하세요");
					fm.pwd.focus();
					return;
				}
				
				fm.action = "${pageContext.request.contextPath}/board/boardWriteAction.do";
				fm.enctype ="multipart/form-data";
				fm.method="post";
				fm.submit();
				return;
			}
		</script>
	</head>  
	<body>
		
		<c:if test="${empty sessionScope.midx}">
			<c:out escapeXml="false" value="<script>alert('로그인 하셔야 합니다.'); history.back(-1);</script>"></c:out>
		</c:if>
		<!-- c:when test="${empty name}"이 의미는 name 변수의 값이 비었냐(empty)는 의미입니다.null일 경우 true, null이 아닐 경우 true를 리턴합니다. -->
		<!--escapeXml = false 위와 같은 경우 문자열이 출력되지 않고 해당 스크립트가 실행되어 alert 경고창이 뜹니다. 이때 escapeXml 속성은 false인 경우이고 el태그를 그대로 사용할 때 이런 상태가 됩니다.-->
		<h1>게시판 글쓰기</h1>
		<form  name="frm">
			<table class="main">
				<tr>
					<th>제목</th>
					<td><input id="subject" class="board_input" name="subject" type="text" placeholder="제목을 입력해 주세요."></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea id="contents" name="contents" rows="20" cols="90" placeholder="내용을 입력해 주세요."></textarea></td>
				</tr> 
				<tr>
					<th>작성자</th>
					<td><input id="writer" class="board_input" name="writer" type="text" placeholder="작성자를 입력해 주세요."></td>
				</tr>
				<tr>
					<th>비밀번호</th><!-- //"---2023-03-17----jsp 게시판-삭제-비밀번호추가---"  -->
					<td><input id="pwd" class="board_input" name="pwd" type="password" maxlength="20" placeholder="비밀번호를 입력해 주세요."></td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td><input id="board_file" name="filename" type="file"></td>
				</tr>	
			</table>
			<div class="top">
				<input class="btn1" id="btn" name="btn" type="button" value="확인" onclick="check();">
				<input class="btn1" id="rst" name="rst" type="reset" value="리셋">
			</div>
		</form>
	</body>
</html>