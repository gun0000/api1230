<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>회원가입</title>
        <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <style>
        	*{font-size: 18px; /*outline: 1px solid #f00;*/}
        	body{background-color: #bfbfbfb0;}
			h1{font-size: 2em;}
            .main{margin: 0px auto; padding-top:50px; font-size: 18px;}
            tr td h2 label{font-size: 23px; margin-bottom: 5px}
            #btn1{height: 50px; width: 385px; color: #fff; background-color: #444444; font-size: 18px; cursor : pointer;}
            .btn2{height: 50px; width: 380px; font-size: 18px; border: solid 2px #444444;}
            .btn3{height: 25px; width: 100px;  }
            #memberidCheck{height: 50px; width: 150px; color: #fff; background-color: #444444; font-size: 18px; cursor : pointer;}
			.main_out{ background-color: #fff; width: 800px; height: 1400px; margin: 200px auto;
					   border-radius: 1.5em; box-shadow: 0px 11px 35px 2px rgba(0, 0, 0, 0.30);}
			
        </style>
        <script>
            $(document).ready(function(){
                $("#btn1").click(function(){
                	let getId = RegExp(/^[A-Za-z0-9]+$/);//아이디형식
                    let getName = RegExp(/^[가-힣a-zA-Z]+$/);//이름형식
                    let getPhone = RegExp(/^[0-9]{11}$/);//전화번호형식
                    let getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);//이메일형식
                    let getCheck= RegExp(/^[a-zA-Z0-9]{4,12}$/);//비밀번호형식
                    let getBirth= RegExp(/^[0-9]+$/);//생년월일형식
                    
        			
                    /*
                    if(!getId.test($("#memberId").val())){//test()  조건식을 만족하는지 여부를 boolean 타입으로 리턴
                        alert("형식에 맞춰서 아이디를 입력해주세요");
                        $("#memberId").val("");
                        $("#memberId").focus();
                        return false;
                    }
                    else if(!getCheck.test($("#memberPwd").val())) {
                        alert("형식에 맞춰서 비밀번호를 입력해주세요.");
                        $("#memberPwd").val("");
                        $("#memberPwd").focus(); 
                        return false;
                    }else if($("#memberPwd2").val() != $("#memberPwd").val()) {
                    	alert("비밀번호같게 입력해주세요.");
                    	$("#memberPwd").focus();
                    	return false;
					}
                    else if(!getName.test($("#memberName").val())){//test()  조건식을 만족하는지 여부를 boolean 타입으로 리턴
                        alert("형식에 맞게 이름을 입력해주세요");
                        $("#memberName").val("");
                        $("#memberName").focus();
                        return false;
                    }
                    else if(!getPhone.test($("#memberPhone").val())){//test()  조건식을 만족하는지 여부를 boolean 타입으로 리턴
                        alert("형식에 맞게 전화번호를 입력해주세요");
                        $("#memberPhone").val("");
                        $("#memberPhone").focus();
                        return false;
                    }   
                    else if(!getMail.test($("#memberEmail").val())){
                        alert("이메일형식에 맞게 입력해주세요");
                        $("#memberEmail").val("");
                        $("#memberEmail").focus();
                        return false;
                    }
                    else if(!getBirth.test($("#memberBirth").val())){
                        alert("생년월일형식에 맞게 입력해주세요");
                        $("#memberBirth").val("");
                        $("#memberBirth").focus();
                        return false; 
                    }*/
                	/* else if ($("#memberIdCheck").val() != "사용가능"){
                		alert("아이디 중복체크를 하세요");
                		$("#memberId").focus();
                		return;
                	}  */

 
//"---2023-03-09----jsp------"
             //폼태그 값 보내기 
             var fm = document.frm;
       			 //이 경로로 데이터를 감추어서 전송한다 //자바스크립트로 frm.속성부여하기 //2023.03.13 추가 가상주소
       			 fm.action ="<%=request.getContextPath()%>/member/memberJoinAction.do";  
       			 fm.method = "POST";
       			 fm.submit();
  
//"---2023-03-09----jsp-end-----"
                });
            });
            //"---2023-03-14----jsp 아이디중복체크-----" 	
                	function idCheck(){
                		alert($("#memberId").val());
                		let memberId = $("#memberId").val();
                			$.ajax({
                				url:"<%=request.getContextPath()%>/member/memberIdCheck.do", 
                				method:"POST",
                				data:{"memberId":memberId},  
                				
                				dataType:"json",
                				success: function(data){
                					alert("데이터 받기성공"); 
                					if(data.value == "0"){
                						alert("사용가능한 아이디입니다.");
                						$("#memberIdCheck").val("사용가능");
                					}else{
                						alert("사용불가한 아이디입니다."); 
                					}
                				},
                				error: function(request,status,error){
                					alert("다시 시도하시오");
                				}
                			});
                		return;
                	}
        </script>
    </head>
    <body>
	    <div class="main_out">
	    <form id="frm" name="frm">
	        <table class="main">
	            <tr><td><h1>회원가입</h1></td></tr>
	            <tr><td><h2><label for="memberId">아이디</label></h2></td></tr>
	            <tr>
	            	<td>
	            		<input id="memberId" name="memberId" class="btn2" type="text" placeholder="예)hong1234" value="">
	            		<input type="button" id="memberIdCheck" name="memberIdCheck" value="아이디중복체크" onclick='idCheck();'>
	            	</td>
	            </tr>
	            <tr><td><h2><label for="memberPwd">비밀번호</label></h2></td></tr>
	            <tr><td><input id="memberPwd" name="memberPwd" class="btn2" type="password" placeholder="최소 5자리"></td></tr>
	            <tr><td><h2><label for="memberPwd2">비밀번호 확인</label></h2></td></tr>
	            <tr><td><input id=memberPwd2 name="memberPwd2" class="btn2" type="password" placeholder="최소 5자리"></td></tr>
	            <tr><td><h2><label for="memberName">이름</label></h2></td></tr>
	            <tr><td><input id="memberName" name="memberName" class="btn2" type="text" placeholder="예)홍길동"></td></tr>
	            <tr><td><h2><label for="memberPhone">전화번호</label></h2></td></tr>
	            <tr><td><input id="memberPhone" name="memberPhone" class="btn2" type="text" placeholder="예)010-1234-1234"></td></tr>
	            <tr><td><h2><label for="memberEmail">Email</label></h2></td></tr>
	            <tr><td><input id="memberEmail" name="memberEmail" class="btn2" type="text" placeholder="예)hong1234@gmail.com"></td></tr>
	            <tr><td><h2><label for="memberGender">성별</label></h2></td></tr>
	            <tr>
	            	<td>
	            		<input class="btn3" type="radio" name="memberGender" id="memberGender"checked="checked" value="남성">남성
	            		<input class="btn3" type="radio" name="memberGender" id="memberGender" value="여성">여성
	            	</td>
	            </tr>
	            <tr><td><h2><label for="memberAddr">주소</label></h2></td></tr>
	            <tr><td><select id="memberAddr" name="memberAddr" class="btn2"  >
	                        <option class="btn2" value="서울">서울</option>
	                        <option class="btn2" value="대전">대전</option>
	                        <option class="btn2" value="전주" selected>전주</option>
	                    </select></td></tr>
	            <tr><td><h2><label for="memberBirth">생년월일</label></h2></td></tr>
	     		<tr><td><input id="memberBirth" name="memberBirth"  class="btn2" type="text" placeholder="예)19901231"></td></tr>
	            <tr><td><input class="btn2" id="btn1" type="submit" value="확인"></td></tr>
	        </table>
	    </form>
	    </div>
    </body>
</html>