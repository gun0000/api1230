<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% String msg = ""; %>
    <% if (request.getAttribute("msg") != null){
    	msg = (String)request.getAttribute("msg");
    }%>
    
    
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>회원로그인</title>
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
			.main_out{ background-color: #fff; width: 800px; height: 500px; margin: 200px auto;
					   border-radius: 1.5em; box-shadow: 0px 11px 35px 2px rgba(0, 0, 0, 0.30);}
			
        </style>
        <script>
            $(document).ready(function(){
            	var msg ="<%=msg%>";
            	if (msg !=""){
            		alert(msg);
            	}
            	
            
                $("#btn1").click(function(){
                	let getId = RegExp(/^[A-Za-z0-9]+$/);//아이디형식
                    let getCheck= RegExp(/^[a-zA-Z0-9]{4,12}$/);//비밀번호형식                  
                    
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
                    }

             //폼태그 값 보내기 
             var fm = document.frm;
       			 //이 경로로 데이터를 감추어서 전송한다 //자바스크립트로 frm.속성부여하기 //2023.03.13 추가 가상주소
       			 fm.action ="<%=request.getContextPath()%>/member/memberLoginAction.do";  
       			 fm.method = "POST";
       			 fm.submit();
 
                });//$("#btn1").click(function(){-----end
            });//$(document).ready(function(){-----end
     
                	
        </script>
    </head>
    <body>
	    <div class="main_out">
	    <form id="frm" name="frm">
	        <table class="main">
	            <tr><td><h1>회원로그인</h1></td></tr>
	            <tr><td><h2><label for="memberId">아이디</label></h2></td></tr>
	            <tr>
	            	<td>
	            		<input id="memberId" name="memberId" class="btn2" type="text" placeholder="예)hong1234" value="">
	            	</td>
	            </tr>
	            <tr><td><h2><label for="memberPwd">비밀번호</label></h2></td></tr>
	            <tr><td><input id="memberPwd" name="memberPwd" class="btn2" type="password" placeholder="최소 5자리"></td></tr>
	            
	            <tr><td><input class="btn2" id="btn1" type="submit" value="확인"></td></tr>
	        </table>
	    </form>
	    </div>
    </body>
</html>