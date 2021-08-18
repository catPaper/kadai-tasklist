<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>タスク一覧</h2>
        <ul>
            <%-- numberはタスクに１から順番に番号をふるための変数 --%>
            <c:set var="number" value="1" />
            <c:forEach var="task" items="${tasks}">
                <li>
                    <a href="${pageContext.request.contextPath}/show?id=${task.id}">
                        <c:out value="${number}" />
                    </a>
                    - <c:out value="${task.content}" />
                </li>
                <c:set var="number" value="${number+1}" />
            </c:forEach>
        </ul>
    </c:param>
</c:import>