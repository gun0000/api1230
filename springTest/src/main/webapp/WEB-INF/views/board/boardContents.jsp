<%@page import="com.myezen.myapp.domain.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- 태그 라이브러리 선언 Core -->
    
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판 내용보기</title>
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<style type="text/css">
			*{font-size: 18px;}
			h1{font-size: 2em;}
			.board{width: 100%; height: 500px; border: 1px solid #444444; border-collapse: collapse;}
			.board th, .board td {border: 2px solid #444444;}
			.board th{width: 12%;}
	 		.btn_right{float: right;}
	 		.board_detail{height: 80%;}
			.main{width: 100%; height: 100%; border: 2px solid #444444; border-collapse: collapse;}
			.top{margin-top: 10px; text-align: center;}
            .btn1{width: 80px; color: #fff; background-color: #444444; font-size: 18px; cursor : pointer; margin-left: 5px}

		</style>
		<script type="text/javascript">
			$(document).ready(function(){
				
				var originalFileName = getOriginalFileName("${bv.filename}");//파일이름받아옴 그 중에 _에 다음되는것만 뽑아와서 원본파일이름 변수에 담는다 
				//alert(originalFileName);
				
				var str="";
				var str2 = getImageLink("${bv.filename}");
				
				
				str = "<div><a href='${pageContext.request.contextPath}/board/displayFile.do?fileName="+str2+"&down=1'>"+originalFileName+"</a></div>";
				
				$("#download").html(str);//아이디가 다운로드인 html에 위에 str 태그를 넣는다
				
				$.boardCommentList(0);//함수호출  2023-04-19 //리스트꺼내오기 
				
				//댓글달기 2023-04-19
				$("#save").click(function(){//확인버튼클릭
					var bidx = ${bv.bidx};
					var cwriter = $("#cwriter").val();
					var ccontents = $("#ccontents").val();
					var nextBlock = $("#nextBlock").val();
					var midx = ${sessionScope.midx};
					
					$.ajax({
							
						type: "post",
						url: "${pageContext.request.contextPath}/comment/commentWrite.do",
						dataType: "json",
						data: {
							"bidx": bidx,
							"cwriter": cwriter,
							"ccontents": ccontents,
							"nextBlock": nextBlock,
							"midx": midx
							
						},
						cache: false,
						success: function(data) {
							//alert("등록성공");
							$.boardCommentList(0);//함수호출  2023-04-19 //리스트꺼내오기 
							$("#cwriter").val("");//빈칸으로 만들어주기  
							$("#ccontents").val("");
						},
						error: function() {
							alert("등록실패");
						}
					});//ajax-end
	
				});//#save-end
				//댓글더보기 2023-04-20
				$("#more").click(function() {//더보기 버튼 클릭시 밸류증가
					var nextBlock = $("#nextBlock").val();
					$.ajax({
						type: "get",
						url: "${pageContext.request.contextPath}/comment/${bv.bidx}/"+nextBlock+"/more.do",
						dataType: "json",
						cache: false,
						success: function(data){
							//alert("성공");
							console.log(data);
							$("#nextBlock").val(data.nextBlock); //받은 data에 nextBlock 키 값 
							$.boardCommentList(nextBlock);
						},
						error: function() {
							alert("댓글 더보기 실패");
						}
						
					});//ajax-end
				});//#more-end
				
			});
			//제이쿼리 함수 
			//댓글리스트 뽑아오기 2023-04-19
			$.boardCommentList = function (nb) {//매개변수 /더보기 버튼 클릭시 밸류증가
				
				var nextBlock;
				if (nb ==0) {
					nextBlock = 1;
				}else{
					nextBlock = nb;
				}
				
				
				$.ajax({
					type: "get",
					url: "${pageContext.request.contextPath}/comment/${bv.bidx}/"+nextBlock+"/commentList.do",
					dataType: "json",
					cache: false,
					success: function(data) {
						//alert("성공"); 
						console.log(data);
						console.log(data.alist);
						//alert(JSON.stringify(data))
						commentList(data.alist);
						if (data.moreView == "N") {//더보기 버튼 //댓글더보기 2023-04-20
							$("#morebtn").css("display","none");
						}else {
							$("#morebtn").css("display","block");
						}
					},
					error: function() {
						alert("댓글리스트 뽑아오기 실패");
					}
				});//ajax-end
			}
			//댓글리스트 만들어주기 2023-04-19
			function commentList(data) {//$.boardCommentList에서 받아옴
				var str = "";
				str = "<tr><td>이름</td><td>내용</td></tr>"
				btn = ""
				
				$(data).each(function(){//제이쿼리 반복문
				str =str+ "<tr><td>"+this.cwriter+"</td><td>"+this.ccontents+"<button class='delete' onclick=commentDelete("+this.cidx+");>삭제</button></td></tr>";
				});
				
					
				$("#tbl").html("<table>"+str+"</table>");
				return;
			}
			
			function getOriginalFileName(fileName){
				
				 var idx = fileName.lastIndexOf("_")+1;
				 
				 return fileName.substr(idx);
				
			}
			//댓글삭제 2023-04-20
			function commentDelete(cidx) {
				
				//alert(cidx);
				$.ajax({
					type: "get",
					url: "${pageContext.request.contextPath}/comment/${bv.bidx}/"+cidx+"/commentDelete.do",
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.value == 1) {
							//alert("삭제"+data.value);
							$.boardCommentList(0);
						}
						
						
					},
					error: function() {
						alert("삭제 실패");
					}
				});//ajax-end
				
			}// commentDelete-end
			
			//파일이 이미지일 경우
			function getImageLink(fileName){
				
				//이미지파일이 아니면 그냥 리턴해라 왜냐하면 이미지 파일이면 substr으로 s-을 잘라줘야해서
				if(!checkImageType(fileName)){
					return fileName;
				}
				
				//위치 폴더뽑기
				var front = fileName.substr(0,12);
				//파일이름 뽑기 
				var end = fileName.substr(14);
				return front+end;
			}
			//파일확장자가 이미지파일인지 체크하는 함수
			function checkImageType(fileName){
				var pattern = /jpg$|gif$|png$|jpeg$/i;
				return fileName.match(pattern);
			}

		</script>
	</head>
	<body>
				<h1>게시판 내용보기</h1>
		<form action="">
			<table class="board">
				<tr>
					<th>제목</th>
					<td>${bv.subject} &nbsp;&nbsp;&nbsp;&nbsp;조회수: (${bv.viewcnt})</td>
				</tr>
				<tr>
					<th>파일다운로드</th>
					<td>
						<!--<a href="${pageContext.request.contextPath}/board/fileDownload.do?filename=${bv.filename}">${bv.filename}</a>-->
						<div id="download"></div> 
					</td>
				</tr> 
				<tr>
					<th>이미지</th>
					<td>
						<c:set var="exp" value="${bv.filename.substring(bv.filename.length()-3,bv.filename.length())}"></c:set>

						<c:if test="${exp.equals('jpg')||exp.equals('gif')||exp.equals('png')}">
							<img src="${pageContext.request.contextPath}/board/displayFile.do?fileName=${bv.filename}">
						</c:if>
							
					</td>
				</tr>
				<tr class="board_detail">
					<th>내용</th>
					<td>${bv.contents}</td>
				</tr> 
				<tr>
					<th>작성자</th>
					<td>${bv.writer}</td>
				</tr>
			</table>
			<div class="top">
				<input class="btn_right btn1"  type="button" value="목록" 
						onclick="location='${pageContext.request.contextPath}/board/boardList.do'">
				<input class="btn_right btn1"  type="button" value="답변" 
						onclick="location='${pageContext.request.contextPath}/board/boardReply.do?bidx=${bv.bidx}&originbidx=${bv.originbidx}&depth=${bv.depth}&level_=${bv.level_}'">
				<input class="btn_right btn1"  type="button" value="삭제" 
						onclick="location='${pageContext.request.contextPath}/board/boardDelete.do?bidx=${bv.bidx}'">
				<input class="btn_right btn1"  type="button" value="수정"
						 onclick="location='${pageContext.request.contextPath}/board/boardModify.do?bidx=${bv.bidx}'">
			</div>
		</form>
		
		<!-- 댓글달기 -->
		<table>
			<tr>
				<th>이름</th>
				<td><input id="cwriter" type="text" name="cwriter" size="10"></td>
				<td></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea id="ccontents" name="ccontents" rows="3" cols="20" placeholder="내용을 입력하세요" ></textarea></td>
				<td><input id="save" type="button" name="btn" value="확인"></td>
			</tr>
		</table>
		<input id="nextBlock" type="hidden" value="2"><!-- 더보기 클릭 -->
		<div id="tbl"></div><!-- 댓글 리스트 -->
		<div id="morebtn">
			<button id="more" >더보기</button>
		</div>
		
		
		
		
		
		
		
	</body>
</html>