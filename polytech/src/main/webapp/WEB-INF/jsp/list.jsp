<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<!--SEMENTIC UI -->
<link rel="stylesheet" type="text/css" href="resources/css/semantic.css">
<script src="resources/js/semantic.js"></script>
<head>
<meta charset="UTF-8">
<title>전체 리스트</title>
<script>
$( document ).ready( function() {
    $( '#checkall' ).click( function() {
      $( '.select' ).prop( 'checked', this.checked );
    } );
  } );


if ('SpeechRecognition' in window) {
    console.log("음성인식을 지원하는 브라우저입니다.")
 }
 try {
    var recognition = new(window.SpeechRecognition || window.webkitSpeechRecognition || window.mozSpeechRecognition || window.msSpeechRecognition)();
 } catch (e) {
    console.error(e);
 }
 
 var getvoices = false;
 
function check_speech(){  
    recognition.lang = 'ko-KR';
    recognition.interimResults = false;
    recognition.maxAlternatives = 1;
    recognition.continuous = true;
    if(!getvoices) speech_to_text();
    else stop();
 }
 function speech_to_text() {
    getvoices = true;

    recognition.start();
    var resText = "";

    recognition.onstart = function() {
       console.log("음성인식이 시작 되었습니다.")
       document.getElementById("speech").value = "인식중..";
    }
    recognition.onresult = function(event) {
       console.log('You said: ', event.results[0][0].transcript);
       resText = resText + event.results[0][0].transcript;
       document.getElementById("search").value = resText;
    };
    recognition.onend = function() {
       document.getElementById("speech").value = "음성인식";
    }
 }
 function stop() {
    recognition.stop();
    document.getElementById("speech").value = "음성인식";
    getvoices = false;
 }

</script>
<style>
.paging {
	text-align: center;
}
#speech {
	background-color: white;
	font-weight: normal;
}
</style>
</head>
<body>
	<div class="ui main container" style="padding-top: 30px;">
		<form method="Post" action="./search.html">
			<div class="ui action input">
				<input type="text" name="search" id="search" placeholder="검색어 입력"
					style="width: 400px;" required> 
				<input type=button class="ui button" id="speech" OnClick="check_speech()" value="음성인식">
				<select
					class="ui compact selection dropdown" name="type" id="type"
					style="height: 45px;" required>
					<option value="byall">전체</option>
					<option value="byname">이름</option>
					<option value="bybusiness">소속</option>
					<option value="bydepartment">부서</option>
					<option value="byaddress">주소</option>
					<option value="byphone">연락처</option>
				</select>
				<button class="ui button" type="submit">검색</button>
				
			</div>
		</form>

		<h3 class="ui hearder">전체 데이터 (총 ${totalCount } 건)</h3>

		<div class="ui divider"></div>
		
		<form class="ui form" method="POST" action="/deletes.html">
			<table class="ui striped table">
				<thead>
					<tr>
						<th><p align=center></p>
						<input type="checkbox" name="checkall" id="checkall"/></th>
						<th><p align=center>이름</p></th>
						<th><p align=center>소속</p></th>
						<th><p align=center>부서</p></th>
						<th class="three wide"><p align=center>주소</p></th>
						<th class="two wide"><p align=center>등록일</p></th>
						<th class="two wide"><p align=center>최종 수정일</p></th>
						<th class="two wide"><p align=center>Edit</p></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty users}">
							<tr>
								<td colspan=3><spring:message code="common.listEmpty" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${users.content }" var="e">
								<tr>
									<td><input type="checkbox" name="select" class="select" value="${e.id}">
										</td>
									<td><p align=center>
											<a href="/oneview.html/${e.id}">${e.name}</a>
										</p></td>
									<td><p align=center>${e.businessName}</p></td>
									<td><p align=center>${e.departmentName}</p></td>
									<td><p align=center>${e.address}</p></td>
									<td><p align=center>
											<fmt:formatDate value="${e.registeredOn}"
												pattern="yyyy-MM-dd HH:mm:ss" />
										</p></td>
									<td><p align=center>
											<fmt:formatDate value="${e.lastUpdatedOn}"
												pattern="yyyy-MM-dd HH:mm:ss" />
										</p></td>
									<td><p align=center>
											<a href="/oneview.html/${e.id}/editform">수정</a>&nbsp; <a
												href="./delete.html/${e.id}">삭제</a>
										</p></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<input type="hidden" name="page" value="${page }">
			<button type="submit" class="ui button">선택 회원 삭제</button>
			<button type="button" class="ui button"
				onclick="location.href='./newform.html'">회원 추가</button>
		</form>
		<div class="ui divider"></div>
		<div class="paging">
			<div class="ui animated button" tabindex="0"
				onclick="location.href='/0'">
				<div class="visible content">《</div>
				<div class="hidden content">Head</div>
			</div>
			<c:if test="${!users.first}">
				<div class="ui animated button" tabindex="0"
					onclick="location.href='${users.number-1}'">
					<div class="visible content">〈</div>
					<div class="hidden content">Prev</div>
				</div>
			</c:if>

			<c:forEach begin="${startRange }" end="${endRange }" var="e">
				<button type="button" class="ui button" id="button${e }"
					onclick="location.href='${e }'">${e+1 }</button>
			</c:forEach>

			<c:if test="${!users.last}">
				<div class="ui animated button" tabindex="0"
					onclick="location.href='${users.number+1}'">
					<div class="visible content">〉</div>
					<div class="hidden content">Next</div>
				</div>
			</c:if>
			<div class="ui animated button" tabindex="0"
				onclick="location.href='/${totalPage-1 }'">
				<div class="visible content">》</div>
				<div class="hidden content">Tail</div>
			</div>
		</div>

	</div>

</body>
</html>