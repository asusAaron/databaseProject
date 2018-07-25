<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XPS
  Date: 2018/7/18
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>

<%--员工添加和修改操作的执行--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%
    request.setCharacterEncoding("UTF-8");
    String path=request.getContextPath();
%>

<html>
<head>
    <title>员工${empty param.pid?'添加':'修改' }</title>
</head>
<body>
${msg }
<br>
<form id="myform" action="<%=path%>/Add.do" method="post">
    <table border="1" width="55%" align="center">
        <caption>
            <%--判断pid是否为空，pid为空则是修改操作，否则为添加操作--%>
            员工${empty param.pid?'添加':'修改' }
            <hr width="160">
        </caption>
        <tr>
            <td colspan="4">员工信息</td>
        </tr>
        <tr>
            <td>姓名</td>
            <td>
                <input name="pname" required="required" value="${ins.pname }"/>
            </td>
            <td>身份证</td>
            <td>
                <input name="pnumber"  required="required" value="${ins.pnumber }"/>
            </td>
        </tr>
        <tr>
            <td>性别</td>
            <td>
                <input type="radio" name="psex" value="1" checked="checked"/>男
                <input type="radio" name="psex" value="2" checked=""/>女
                <input type="radio" name="psex" value="3" checked=""/>--不限--
            </td>
            <td>生日</td>
            <td>
                <input type="date" name="pbirthday"  required="required" value="${ins.pbirthday }"/>
            </td>
        </tr>
        <tr>
            <td>民族</td>
            <td>
            <select name="pnation">
                <option name="option" value="1" selected="selected">汉族</option>
                <option name="option" value="2" selected="">满族</option>
                <option name="option" value="3" selected="">藏族</option>
                <option name="option" value="4" selected="">朝鲜族</option>
            </select>

            </td>
            <td>工资</td>
            <td>
                <input type="number" step="0.01" name="psal"  required="required" value="${ins.psal}"/>
            </td>
        </tr>
        <tr>
            <td>备注</td>
            <td colspan="3">
                <textarea rows="5" cols="65" name="pmemo">${ins.pmemo }</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <input type="submit" name="next" value="${empty param.pid?'添加':'修改' }"
                       formaction="<%=path%>/${empty param.pid?'Add':'Update' }.do">
                <input type="submit" name="next" value="返回" formaction="<%=path%>/Query.do" formnovalidate="formnovalidate">
            </td>
        </tr>
    </table>
    <input type="hidden" name="pid" value="${param.pid }">
    <input type="hidden" name="qpname" value="${param.pname }">
    <input type="hidden" name="qpnumber">
    <input type="hidden" name="qpsex">
    <input type="hidden" name="qspbirthday">
    <input type="hidden" name="qepbirthday">
    <input type="hidden" name="qpnation">
</form>
</body>
</html>



