<%@page import="com.myezen.myapp.domain.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%// BoardVo bv = (BoardVo)request.getAttribute("bv"); %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판 글답변</title> 
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
		}
	
		fm.action = "${pageContext.request.contextPath}/board/boardReplyAction.do";
		fm.enctype ="multipart/form-data";
		fm.method="post";
		fm.submit();
		return;
		}


	</script>
	</head>  
	<body>
		<h1>게시판 글답변</h1>
		<form name="frm">
		<input type="hidden" name="bidx" value="${bv.bidx}">
		<input type="hidden" name="originbidx" value="${bv.originbidx}"> 
		<input type="hidden" name="depth" value="${bv.depth}">
		<input type="hidden" name="level_" value="${bv.level_}">
			<table class="main">
				<tr>
					<th>제목</th>
					<td><input id="board_title" class="board_input" name="subject" type="text" placeholder="제목을 입력해 주세요."></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea id="board_text" name="contents" rows="20" cols="90" placeholder="내용을 입력해 주세요."></textarea></td>
				</tr> 
				<tr>
					<th>작성자</th>
					<td><input id="board_writer" class="board_input" name="writer" type="text" placeholder="작성자를 입력해 주세요."></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input id="pwd" class="board_input" name="pwd" type="password" maxlength="20" placeholder="비밀번호를 입력해 주세요."></td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td><input id="board_file" name="filename" type="file"></td>
				</tr>	
			</table>
			<div class="top">
				<input class="btn1" id="board_submit" name="btn" type="button" value="확인" onclick="check();">
				<input class="btn1" id="board_reset" name="rst" type="reset" value="리셋">
			</div>
		</form>
	</body>
</html>