<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>

<style type="text/css">
#header {
	background-color: gray;
	width: 100%;
	height: 70px;
	text-align: center;
}

#footer {
	width: 100%;
	text-align: center;
	background-color: gray;
	bottom: 0;
	height: 50px;
	position: absolute;
}

.any {
	float: left;
	padding: 15px;
	margin-left: 25px;
	
}

.logout {
	float: right;
	padding: 15px;
	margin-right: 25px;
}

#pagination{
  float: right;
  margin-right: 450px;
}

#sidebar-left {
	position: absolute;
	height: 80%;
	float: left;
	width: 15%;
	background-color: gray;
}

#main {
	top: 70px;
	height: 420px;
	float: right;
	width: 85%;
	background-color: lightgray;
}

table {
	text-align: center;
	table-layout: auto;
	padding: inherit;
	height: 180px;
}
</style>
</head>
<body>

	<div id="header">
		<div class="any">
			<a href="listDept"><font color="white"><spring:message
						code="label.home"></spring:message></font></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${deptlang eq 'language'}">

				<a href="listDept?siteLanguage=en"><font color="white">English</font></a>||<a
					href="listDept?siteLanguage=fr"><font color="white">French</font></a>
			</c:if>

			<c:if test="${emplang eq 'lang'}">
				<a href="home?siteLanguage=en"><font color="white">English</font></a>||<a
					href="home?siteLanguage=fr"><font color="white">French</font></a>
			</c:if>

		</div>
		<div class="logout">


			<a href="logout"><font color="white"><spring:message
						code="label.logout"></spring:message></font></a>
		</div>
		<h4>
			<font color="white"><spring:message code="label.app"></spring:message></font>
		</h4>
		<br>

	</div>



	<div id="sidebar-left">

		<form action="home">
			<br> <input type="submit" value="+">
			<spring:message code="label.department"></spring:message>
		</form>

		<c:if test="${check eq 'checklist'}">
			<c:forEach var="dept" items="${deptLst}">
				<c:if test="${not empty dept}">

					<input type="hidden" name="deptId" value="${dept.deptId}">

					<br>
					
					&nbsp;&nbsp;<button type="button" class="btn btn-success" style="width: 100px;">
						<a href="listEmp?deptId=${dept.deptId}"><font
							color="black">${dept.deptName} </font></a>
					</button>
					<br>

				</c:if>
			</c:forEach>

		</c:if>

	</div>

	<div id="main">


		<c:if test="${home ne 'homemp'}">


			<div align="center">


				<c:if test="${Register eq 'NewFormDept'}">
					<form action="saveDept" method="post">
				</c:if>
				<c:if test="${Register ne 'NewFormDept'}">
					<form action="updateDept" method="post">
				</c:if>

				<table border="1" style="width: 70%">

					<thead>
						<tr>
							<th colspan="2.5" style="text-align: center"><font
								color="green"><spring:message code="label.getAllDept"></spring:message></font></th>
							<th colspan="3" style="text-align: center"><a href="newDept"><spring:message
										code="label.addDept"></spring:message> </a></th>


						</tr>
						<tr>
							<th><spring:message code="label.deptId"></spring:message></th>
							<th><spring:message code="label.deptName"></spring:message></th>
							<th><spring:message code="label.update"></spring:message></th>
							<th><spring:message code="label.delete"></spring:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${deptList}" var="dept">

							<c:if test="${departId eq dept.deptId}">
								<tr>
									<td><input type="" name="deptId" value="${dept.deptId}"
										readonly="readonly" /></td>
									<td><input type="text" name="deptName"
										value="${dept.deptName}" /></td>

									<td><input type="submit" value="update" /></td>
								</tr>
							</c:if>
							<c:if test="${departId ne dept.deptId}">
								<tr>

									<td>${dept.deptId}</td>
									<td>${dept.deptName}</td>

									<td><a href="editDept?id=${dept.deptId}"><spring:message
												code="label.update"></spring:message></a></td>
									<td><a href="deleteDept?id=${dept.deptId}"><spring:message
												code="label.delete"></spring:message></a></td>
								</tr>
							</c:if>
						</c:forEach>
						<c:if test="${insertDept eq 'newDept'}">
							<tr>
								<td><input type="hidden" name="depId" /></td>
								<td><input type="text" name="deptName" /></td>

								<td colspan="2"><input type="submit" value="save" /></td>
							</tr>
						</c:if>
					</tbody>
				</table>

			</div>
			<div id="pagination">

 

                        <c:url value="/listDept" var="prev">
                            <c:param name="page" value="${page-1}" />
                        </c:url>
                        <c:if test="${page > 1}">
                            <a href="<c:out value="${prev}" />" class="pn prev">Prev</a>
                        </c:if>

 

                        <c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
                            <c:choose>
                                <c:when test="${page == i.index}">
                                    <span>${i.index}</span>
                                </c:when>
                                <c:otherwise>
                                    <c:url value="/listDept" var="url">
                                        <c:param name="page" value="${i.index}" />
                                    </c:url>
                                    <a href='<c:out value="${url}" />'>${i.index}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:url value="/listDept" var="next">
                            <c:param name="page" value="${page + 1}" />
                        </c:url>
                        <c:if test="${page + 1 <= maxPages}">
                            <a href='<c:out value="${next}" />' class="pn next">Next</a>
                        </c:if>
                    </div>

		</c:if>




		<c:if test="${home eq 'homemp'}">
			<div align="center">

				<c:if test="${Register ne 'NewForm'}">
					<form action="updateEmp" method="post">
				</c:if>
				<c:if test="${Register eq 'NewForm'}">
					<form action="saveEmp" method="post">
				</c:if>


				<table border="2"background-color:#eee;>
					<thead>
						<tr>
							<th colspan="4" style="text-align: center"><spring:message
									code="label.getAllEmp"></spring:message></th>
							<th colspan="3" style="text-align: center"><a href="newEmp"><spring:message
										code="label.addEmp"></spring:message> </a></th>
						</tr>
						<tr>
							<th><spring:message code="label.empId"></spring:message></th>
							<th><spring:message code="label.empName"></spring:message></th>
							<th><spring:message code="label.age"></spring:message></th>
							<th><spring:message code="label.deptId"></spring:message></th>
							<th><spring:message code="label.update"></spring:message></th>
							<th><spring:message code="label.delete"></spring:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${empLst}" var="emp">
							<c:if test="${employeeid eq emp.empId}">
								<c:if test="${deptId eq emp.deptId}">
									<tr>
										<td><input type="" name="empId" value="${emp.empId}"
											readonly="readonly" /></td>
										<td><input type="text" name="empName"
											value="${emp.empName}" /></td>
										<td><input type="text" name="age" value="${emp.age}" /></td>
										<td><input type="text" name="deptId"
											value="${emp.deptId}" /></td>
											
											

										<td><input type="submit" value="update" /></td>
									</tr>
								</c:if>
							</c:if>
							<c:if test="${employeeid ne emp.empId}">
								<tr>

									<td>${emp.empId}</td>
									<td>${emp.empName}</td>
									<td>${emp.age}</td>
									<td>${emp.deptId}</td>

									<td><a
										href="editEmp?empId=${emp.empId}&deptId=${emp.deptId}"><spring:message
												code="label.update"></spring:message></a></td>
									<td><a
										href="deleteEmp?empId=${emp.empId}&deptId=${emp.deptId}"><spring:message
												code="label.delete"></spring:message></a></td>
								</tr>
							</c:if>
						</c:forEach>
						<c:if test="${addEmp eq 'regEmp'}">
							<tr>
								<td><input type="text" name="empId" /></td>
								<td><input type="text" name="empName" /></td>
								<td><input type="text" name="age" /></td>


								<td><input type="text" name="deptId" /></td>
								



								<td colspan="2"><input type="submit" value="save" /></td>
							</tr>

						</c:if>
					</tbody>
				</table>
			</div>


		
		<div id="pagination">
 

                        <c:url value="/listEmp" var="prev">
                        <c:param name="deptId" value="${deptId}" />
                            <c:param name="page" value="${page-1}" />
                        </c:url>
                        <c:if test="${page > 1}">
                            <a href="<c:out value="${prev}" />" class="pn prev">Prev</a>
                        </c:if>

 

                        <c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
                            <c:choose>
                                <c:when test="${page == i.index}">
                                    <span>${i.index}</span>
                                </c:when>
                                <c:otherwise>
                                    <c:url value="/listEmp" var="url">
                                    <c:param name="deptId" value="${deptId}" />
                                        <c:param name="page" value="${i.index}" />
                                    </c:url>
                                    <a href='<c:out value="${url}" />'>${i.index}</a>
                                </c:otherwise>
                            </c:choose>
                        
                        
                        <c:url value="/listEmp" var="next">
                        <c:param name="deptId" value="${deptId}" />
                            <c:param name="page" value="${page + 1}" />
                        </c:url>
                        </c:forEach>
                        <c:if test="${page + 1 <= maxPages}">
                            <a href='<c:out value="${next}" />' class="pn next">Next</a>
                        </c:if>
                       
                      
                    </div>
                    </c:if>
	</div>


</body>
</html>