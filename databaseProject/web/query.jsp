<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XPS
  Date: 2018/7/17
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%
    request.setCharacterEncoding("UTF-8");
    String path=request.getContextPath();
%>
<html>
<head>
    <title>查询员工</title>
    <script>
        //选中checkbox个数
        var count=0;
        function onSelect(obj) {
            with(document.forms['myform'])
            {
                obj?++count:--count;
                next[2].disabled=(count==0);
            }
        }

        function onEdit(obj) {
            with (document.forms['myform'])
            {
                action="<%=path%>/Find.do?pid="+obj;
                submit();
            }
        }
    </script>
</head>
<body>
${msg }
<br>
<form id="myform" action="<%=path%>/Query.do" method="post">
    <table border="1" align="center" width="90%">
        <caption align="center">
            员工表
        </caption>
        <tr>
            <td colspan="4">查询条件</td>
        </tr>
        <tr>
            <td>姓名</td>
            <td>
                <input name="qpname" autofocus="autofocus">
            </td>
            <td>编号</td>
            <td>
                <input name="qpnumber">
            </td>
        </tr>
        <tr>
            <td>性别</td>
            <td>
                <input type="radio" name="qpsex" value="1">男
                <input type="radio" name="qpsex" value="2">女
                <input type="radio" name="qpsex" value="" checked="checked">--不限--
            </td>
            <td>民族</td>
            <td>
                <select name="qpnation">
                    <option value="1">汉族</option>
                    <option value="2">满族</option>
                    <option value="3">藏族</option>
                    <option value="4">朝鲜族</option>
                    <option value="" selected="selected">--不限--</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>生日区间：</td>
            <td>
                <input type="date" name="qspbirthday">
            </td>
            <td>至</td>
            <td>
                <input type="date" name="qepbirthday">
            </td>
        </tr>
    </table>
    <!-- 数据迭代 -->
    <table border="1" align="center" width="90%">
        <tr>
            <td></td>
            <td>序号</td>
            <td>姓名</td>
            <td>编号</td>
            <td>性别</td>
            <td>生日</td>
            <td>民族</td>
            <td>状态</td>
        </tr>
        <c:choose>
            <c:when test="${rows!=null}">
                <c:forEach var="ins" items="${rows }" varStatus="vs">
                    <tr>
                        <td>
                            <input type="checkbox" name="idlist" value="${ins.pid }" onclick="onSelect(this.checked)">
                        </td>
                        <td>${vs.count}</td>
                        <td>
                            <a href="#" onclick="onEdit('${ins.pid}')">
                                    ${ins.pname}
                            </a>
                        </td>
                        <td>${ins.pnumber}</td>
                        <td>${ins.psex}</td>
                        <td>${ins.pbirthday}</td>
                        <td>${ins.pnation}</td>
                        <td>${ins.pstate}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach begin="1" step="1" end="10">
                    <tr>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>
    <table border="1" width="90%" align="center">
        <tr>
            <td align="center">
                <input type="submit" name="next" value="查询">
                <input type="submit" name="next" value="添加" formaction="<%=path %>/add.jsp">
                <input type="submit" name="next" value="删除" disabled="disabled" formaction="<%=path %>/Delete.do">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
