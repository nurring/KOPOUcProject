<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!--SEMENTIC UI -->
<link rel="stylesheet" type="text/css" href="resources/css/semantic.css">
<script src="resources/js/semantic.js"></script>

<head>
<meta charset="UTF-8">
<title>회원 등록</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#submit").click(function(){
		if($("#name").val()!="" && $("#businessName").val()!="" && $("#departmentName").val()!=""&& $("#address").val()!="")  
		{   alert("회원 등록 완료"); 
		}  
	});
});
$(document).ready(function(){
	$("input").keyup(function(){
		var value = $(this).val();
		var arr_char = new Array();
		arr_char.push("'");
		arr_char.push("\"");
		arr_char.push("<");
		arr_char.push(">");
	
		for(var i=0 ; i<arr_char.length ; i++)	{
			if(value.indexOf(arr_char[i]) != -1)
			{	window.alert("<, >, ', \" 특수문자는 사용하실 수 없습니다.");
				value = value.substr(0, value.indexOf(arr_char[i]));
				$(this).val(value);		
			}
		}
	});
});
</script>
</head>
<body>
<div class="ui main container" style="padding-top: 30px;">	
	<h3 class="ui hearder">회원 등록</h3>
	<hr>
	<br>

		<form class="ui form" method="POST" action="./save.html">
			<div class="field">
				<label>이름</label> <input type="text" name="name" id="name"
					placeholder="이름을 입력하세요." maxlength="20" required>
			</div>
			<div class="field">
				<label>소속</label> <input type="text" name="businessName" id="businessName"
					placeholder="소속(회사, 학교, 단체명 등)을 입력하세요." maxlength="50" required>
			</div>
			<div class="field">
				<label>부서</label> <input type="text" name="departmentName" id="departmentName"
					placeholder="부서(학과 등)를 입력하세요." maxlength="50" required>
			</div>
			<div class="field">
				<label>주소</label> <input type="text" name="address" id="address"
					placeholder="주소지를 입력하세요." maxlength="50" required>
			</div>
			<button class="ui button" type="submit" id="submit">추가</button>
			<button class="ui button" type="button" onclick="location.href='/0'">메인</button>
		</form>
		
</div>
</body>
</html>