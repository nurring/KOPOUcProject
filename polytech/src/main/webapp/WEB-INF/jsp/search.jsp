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
<title>검색 결과</title>
</head>
<body>
<div class="ui main container" style="padding-top: 30px;">
	<h3>"${search}"의 검색 결과</h3>
	<div class="ui divider"></div>
	<br>
	<table class="ui striped table">
	<thead>
		<tr>
			<th><p align=center>No</p></th>
			<th><p align=center>이름</p></th>
			<th><p align=center>소속</p></th>
			<th><p align=center>부서</p></th>
			<th><p align=center>주소</p></th>
			<th><p align=center>등록일</p></th>
			<th><p align=center>최종 수정일</p></th>
		</tr>
		</thead>
			<tbody>
		<c:choose>
			<c:when test="${empty searches}">
				<tr>
					<td colspan=3><spring:message code="common.listEmpty" /></td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${searches }" var="e" varStatus="status">
					<tr>
						<td><p align=center>${status.count}</p></td>
						<td><p align=center><a href="/oneview.html/${e.id }">${e.name}</a></p></td>
						<td><p align=center>${e.businessName}</p></td>
						<td><p align=center>${e.departmentName}</p></td>
						<td><p align=center>${e.address}</p></td>
						<td><p align=center>${e.registeredOn}</p></td>
						<td><p align=center>${e.lastUpdatedOn}</p></td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	<button class="ui button" type="button" onclick="location.href='/0'">메인</button>
</div>
</body>
</html>