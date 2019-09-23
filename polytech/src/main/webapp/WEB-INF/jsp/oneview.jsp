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
<head>
<meta charset="UTF-8">
<title>한 명</title>
</head>
<body>
<div class="ui main container" style="padding-top: 30px;">
	<h3 class="ui hearder">회원 "${user.name}" 기본 정보</h3>
	<div class="ui divider"></div>

	<table class="ui striped table">
		<tr>
			<td>이름</td>
			<td><input type=hidden name="id" id="id" value="${user.id}">
				${user.name}</td>
		</tr>
		<tr>
			<td>소속</td>
			<td>${user.businessName}</td>
		</tr>
		<tr>
			<td>부서</td>
			<td>${user.departmentName}</td>
		</tr>
		<tr>
			<td>주소</td>
			<td>${user.address}</td>
		</tr>
	</table>
	<button class="ui button" onclick="location.href='./${user.id}/editform'">수정</button>
	<button class="ui button" onclick="location.href='/delete.html/${user.id}'">삭제</button>
	<button class="ui button" onclick="location.href='/0'">메인</button>
	<br>
	<br>
	<br>
	<h3 class="ui hearder">연락처 정보</h3>
	<div class="ui divider"></div>
	<br>
	<table class="ui striped table">
		<tr>
			<td width=150><p align=center>번호</p></td>
			<td width=100><p align=center>통신사</p></td>
			<td width=150><p align=center></p></td>
		</tr>
		<c:choose>
			<c:when test="${empty user.phones}">
				<tr>
					<td colspan=3><spring:message code="common.listEmpty" /></td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${phones }" var="e">
					<tr>
						<td width=50><p align=center>${e.phone}</a>
							</p></td>
						<td width=50><p align=center>${e.carrier}</a>
							</p></td>
						<td width=50><p align=center>
								<a href="./${user.id}/editform2/${e.id}">수정</a> 
								<a href="./${user.id}/delete2/${e.id}">삭제</a>
							</p></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	<button class="ui button" onclick="location.href='./${user.id}/newform2'">연락처 추가</button>
</div>
</body>
</html>