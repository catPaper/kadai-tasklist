<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${task != null}">
                <h2>タスク編集ページ</h2>

                <form method="POST"
                    action="${pageContext.request.contextPath}/update">
                    <%-- type属性hiddenでtaskのidを送信する --%>
                    <input type="hidden" name="id" value="${task.id}">
                    <c:import url="_form.jsp" />
                </form>

                <p>
                    <a href="#" onclick="confirmDestroy();">このタスクを削除する</a>
                </p>
                <form method="POST"
                    action="${pageContext.request.contextPath}/destroy">
                    <%-- type属性hiddenでtaskのidを送信する --%>
                    <input type="hidden" name="id" value="${task.id}"> <input
                        type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
            function confirmDestroy() {
                if (confirm("[確認] 本当に削除してよろしいですか？")) {
                    document.forms[1].submit();
                }
            }
        </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは存在しません。</h2>
            </c:otherwise>
        </c:choose>
        <p><a href="${pageContext.request.contextPath}/index">一覧に戻る</a>
    </c:param>
</c:import>