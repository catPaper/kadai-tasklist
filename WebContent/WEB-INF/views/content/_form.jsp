<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br>
        <ul>
            <c:forEach var="error" items="${errors}">
                <li><c:out value="${error}" /></li>
            </c:forEach>
        </ul>
    </div>
</c:if>
<label for="content">タスク</label>
<input type="text" name="content" value="${task.content}" />
<br>
<br>

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">作成</button>