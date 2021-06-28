<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/bootstrap.css"/>
<script src="../resources/jquery/jquery-3.6.0.js"></script>
	
</head>
<body>
<script type="text/javascript">
	function checkValidate(f){
		if(f.pass.value==""){
			alert("패스워드를 입력하세요");
			f.pass.focus();
			return false;
		}
	}
</script>
<div class="container">
	<h2>회원제 게시판 - 패스워드검증폼</h2>
	
	<!-- 패스워드 검증에 실패했을때 에러메세지 출력용 -->
	<span style="color:red; font-size:1.8em;">
		${isCorrMsg }
	</span>
	
	<form name="writeFrm" method="post" 
		action="./passwordAction.do"
		onsubmit="return checkValidate(this);">
		
	<!-- 컨트롤러를 거치지 않고 다른 페이지에서 파라미터를 받아올려면
	EL식을 사용하면된다. -->	
	
	<!-- 일련번호는 컨트롤러에서 model에 저장한 값을 가져옴 -->
	<input type="hid den" name="idx" value="${idx }" />
	<!-- 아래 2개의 값은 EL의 param내장객체를 통해 즉시 가져옴(방법2) -->
	<input type="hid den" name="mode" value="${param.mode }" />
	<input type="hid den" name="nowPage" value="${param.nowPage }" />
		
	<table border=1 width=800>
	<colgroup>
		<col width="25%"/>
		<col width="*"/>
	</colgroup>
	
	<tr>
		<td>패스워드</td>
		<td>
			<input type="password" name="pass" style="width:30%;" />
		</td>
	</tr>
	 
	<tr>
		<td colspan="2" align="center">
			<button type="submit">작성완료</button>
			<button type="reset">RESET</button>
			<button type="button" onclick="location.href='./list.do?nowPage=${param.nowPage}';">
				리스트바로가기
			</button>
		</td>
	</tr>
	</table>	
	</form>
</div>


</body>
</html>