<%@page import="com.myezen.myapp.domain.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%// BoardVo bv = (BoardVo)request.getAttribute("bv"); %><!-- 가져오는게 오브젝트타입이라서 형변환을 시켜줘야한다 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판삭제</title>
		<style type="text/css">
			*{font-size: 18px;}
			h1{font-size: 2em;}
			.main{margin: 200px auto; font-size: 18px;}
            tr td h2 label{font-size: 23px; margin-bottom: 5px}
            tr td h2 {font-size: 25px; margin-bottom: 5px}
            #btn1{height: 50px; width: 385px; color: #fff; background-color: #444444; font-size: 18px; cursor : pointer;}
            .btn2{height: 50px; width: 380px; font-size: 18px; border: solid 2px #444444;}
            
		</style>
		<script type="text/javascript">
			function check(){	
	
				var fm = document.frm;	
					
				if (fm.pwd.value == "" ){
					alert("비밀번호를 입력하세요");
					fm.pwd.focus();
					return;
				}
				
				var flag = confirm("삭제하시겠습니까?");
				if (flag == false) {
					return;
				}
				
		
				fm.action = "${pageContext.request.contextPath}/board/boardDeleteAction.do";
				fm.method="post";
			
				fm.submit();
				return;
				}
		</script>
	</head>
	<body>
		 <form name="frm" id="frm" method="post">
	        <table class="main">
	        	<tr><td><input type="hidden" name="bidx" value="${bv.bidx}"> </td></tr>
	            <tr><td><h1>글 삭제</h1></td></tr>
	            <tr><td><h2>글 제목 : ${bv.subject}</h2></td></tr>
	            <tr><td><h2><label for="memberPwd">비밀번호</label></h2></td></tr>
	            <tr><td><input id="pwd" name="pwd" class="btn2" type="password" placeholder="비밀번호를 입력하시오">
	            <tr><td>
	            		<input class="btn2" id="btn1" type="submit" value="확인" onclick="check();">
	            </td></tr>
	           
	        </table>
	    </form>
	</body>
</html>