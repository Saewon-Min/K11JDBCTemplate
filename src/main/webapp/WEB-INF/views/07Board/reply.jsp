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
		if(f.name.value==""){
			alert("이름을 입력하세요");
			f.name.focus();
			return false;
		}
		if(f.pass.value==""){
			alert("패스워드를 입력하세요");
			f.pass.focus();
			return false;
		}
		if(f.title.value==""){
			alert("제목을 입력하세요");
			f.title.focus();
			return false;
		}
		if(f.contents.value==""){
			alert("내용을 입력하세요");
			f.contents.focus();
			return false;
		}
	}
</script>
<div class="container">
	<h2>비회원제 게시판 - 답변글쓰기 폼</h2>
	
	<form name="writeFrm" method="post" 
		action="./replyAction.do" 
		onsubmit="return checkValidate(this);">
		
	<!--  
	답변글 처리를 위해서는 원본글에 대한 idx값과 추가적으로
	bgroup, bstep, bindent값이 필요하다. 해당값을 통해
	관련있는 게시물을 하나의 그룹으로 묶어주고, 그룹내에서
	한번 더 정렬을 하게 된다.
	-->
	<input type="hid den" name="idx" value="${replyRow.idx }">
	<input type="hid den" name="nowPage" value="${param.nowPage }"> 
	<input type="hid den" name="bgroup" value="${replyRow.bgroup }"> 
	<input type="hid den" name="bstep" value="${replyRow.bstep }"> 
	<input type="hid den" name="bindent" value="${replyRow.bindent }">
	<table border=1 width=800>
	<colgroup>
		<col width="25%"/>
		<col width="*"/>
	</colgroup>
	<tr>
		<td>작성자</td>
		<td>
			<input type="text" name="name" style="width:50%;" />
		</td>
	</tr>
	<tr>
		<td>패스워드</td>
		<td>
			<input type="password" name="pass" style="width:30%;"/>
		</td>
	</tr>
	<tr>
		<td>제목</td>
		<td>
			<input type="text" name="title" style="width:90%;" value="${replyRow.title }"  />
		</td>
	</tr>
	<tr>
		<td>내용</td>
		<td>
			<textarea name="contents" 
				style="width:90%;height:200px;">${replyRow.contents }</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<button type="submit">작성완료</button>
			<button type="reset">RESET</button>
			<button type="button" onclick="location.href='./list.do';">
				리스트바로가기
			</button>
		</td>
	</tr>
	</table>	
	</form>


</div>


</body>
</html>