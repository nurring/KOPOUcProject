<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!--SEMENTIC UI -->
<link rel="stylesheet" type="text/css" href="/resources/css/semantic.css">
<script src="/resources/js/semantic.js"></script>
<script type="text/javascript">
function checking(){
	if ($('#phone').val()==""){
        alert('번호를 입력해 주세요');
        return false;
	}
	var userid = document.getElementById("userid").value;
	var phone = document.getElementById("phone").value;
	var carrier = document.getElementById("carrier").value;
	var set = {'phone': phone, 'carrier': carrier, 'userid': userid};
	
	$.ajax({
		url: "/save2.html",
		type: "POST",
		data: set,
		success: function(){
			alert("번호 등록 완료");
			location.replace("/oneview.html/"+ userid);
		},
		error: function(){
			alert("번호 중복");
		}	
	});
}

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
//3자리 단위마다 콤마 생성
 function addCommas(x) {
     return x.toString().replace(/\B(?=(\d{4})+(?!\d))/g, "-");
 }
  
//모든 콤마 제거
 function removeCommas(x) {
     if(!x || x.length == 0) return "";
     else return x.split("-").join("");
 }
 
 $(document).ready(function(){
 $('#phone').on("focus", function() {
	    var x = $(this).val();
	    x = removeCommas(x);
	    $(this).val(x);
	}).on("focusout", function() {
	    var x = $(this).val();
	    if(x && x.length > 0) {
	        if(!$.isNumeric(x)) {
	            x = x.replace(/[^0-9]/g,"");
	        }
	        x = addCommas(x);
	        $(this).val(x);
	    }
	}).on("keyup", function() {
	    $(this).val($(this).val().replace(/[^0-9]/g,""));
	}); 
 });
</script>
<head>
<meta charset="UTF-8">
<title>번호 등록</title>
</head>
<body>
	<div class="ui main container" style="padding-top: 30px;">
		<h3 class="ui hearder">번호 등록</h3>
		<div class="ui divider"></div>
		<br>
		<form class="ui form" method="POST">
			<div class="field">
				<label>번호</label>
				<input type="hidden" name="userid" id="userid" value="${id}">
				<input type="text" name="phone" id="phone" placeholder="전화번호를 입력하세요."
						maxlength="11" minlength="10" required>
			</div>
			<div class="field">
				<label>통신사</label> <select name="carrier" id="carrier" required>
					<option value="SKT">SKT</option>
					<option value="KT">KT</option>
					<option value="U+">U+</option>
					<option value="other">알뜰폰</option>
				</select>
			</div>
			<input type=button class="ui button" onclick="checking()" id="submit" value="추가">
			<button class="ui button" type="button" onclick="history.go(-1)">뒤로가기</button>
			<button class="ui button" type="button"
				onclick="location.href='/0'">메인</button>
		</form>
	</div>
</body>
</html>