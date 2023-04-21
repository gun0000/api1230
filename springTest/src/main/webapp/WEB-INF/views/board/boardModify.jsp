<%@page import="com.myezen.myapp.domain.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%// BoardVo bv = (BoardVo)request.getAttribute("bv"); %><!-- 가져오는게 오브젝트타입이라서 형변환을 시켜줘야한다 -->

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판 글수정</title>
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
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
		$(document).ready(function(){
			
			var originalFileName = getOriginalFileName("${bv.filename}");//파일이름받아옴 그 중에 _에 다음되는것만 뽑아와서 원본파일이름 변수에 담는다
			$("#file").html(originalFileName);//아이디가 다운로드인 html에 위에 str 태그를 넣는다
			
		});
		function getOriginalFileName(fileName){
			
			 var idx = fileName.lastIndexOf("_")+1;
			 
			 return fileName.substr(idx);
		}
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
			}else if (fm.pwd.value == ""){
				alert("비밀번호를 입력하세요");
				fm.pwd.focus();
				return;
			}
	
		fm.action = "${pageContext.request.contextPath}/board/boardModifyAction.do";
		fm.enctype ="multipart/form-data";
		fm.method="post";
		fm.submit();
		return;
		}
		

		


	</script>
	</head>  
	<body>
		<h1>게시판 글수정</h1>
		<form action="" name="frm">
			<input type="hidden" name="bidx" value="${bv.bidx}"> 
			<table class="main">
				<tr>
					<th>제목</th>
					<td><input id="board_title" class="board_input" name="subject" type="text" placeholder="제목을 입력해 주세요." value="${bv.subject}"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea id="board_text" name="contents" rows="20" cols="90" placeholder="내용을 입력해 주세요.">${bv.contents}</textarea></td>
				</tr> 
				<tr>
					<th>작성자</th>
					<td><input id="board_writer" class="board_input" name="writer" type="text" placeholder="작성자를 입력해 주세요." value="${bv.writer}"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input id="pwd" class="board_input" name="pwd" type="password" maxlength="20" placeholder="비밀번호를 입력해 주세요."></td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td><input id="board_file" name="filename" type="file">
						<div id="file"></div>
					</td>
				</tr>	
			</table>
			<div class="top">
				<input class="btn1" id="board_submit" name="btn" type="button" value="확인" onclick="check();">
				<input class="btn1" id="board_reset" name="rst" type="reset" value="리셋">
			</div>
		</form>
	</body>
</html>