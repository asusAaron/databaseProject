<%--
  Created by IntelliJ IDEA.
  User: XPS
  Date: 2018/7/16
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="/add" method="post">
      <table border="1" align="center">
          <caption>Person表</caption>
          <tr>
              <td colspan="4">员工数据</td>
          </tr>
          <tr>
              <td>姓名</td>
              <td>
                  <input type="text" name="pname" autofocus="autofocus">
              </td>
              <td>身份证</td>
              <td>
                  <input type="text" name="pnumber">
              </td>
          </tr>
          <tr>
              <td>生日</td>
              <td>
                  <input type="date" name="pbirthday">
              </td>
              <td>性别</td>
              <td>
                  <input type="radio" name="psex">男
                  <input type="radio" name="psex">女
              </td>
          </tr>
          <tr>
              <td>民族</td>
              <td>
                  <select name="pnation" title="none">
                      <option value="01">汉族</option>
                      <option value="02">满族</option>
                      <option value="03">壮族</option>
                  </select>
              </td>
              <td>邮件</td>
              <td>
                  <input type="email" name="pemail">
              </td>
          </tr>
          <tr>
              <td>学历</td>
              <td>
                  <select name="pedu">
                      <option value="1">中学</option>
                      <option value="2">大学</option>
                      <option value="3">硕士</option>
                      <option value="4">博士</option>
                  </select>
              </td>
              <td>薪资</td>
              <td>
                  <input type="number" name="psal">
              </td>
          </tr>
          <tr>
              <td>爱好</td>
              <td>
                  <input type="checkbox" name="phobby" value="1">抽烟
                  <input type="checkbox" name="phobby" value="2">喝酒
                  <input type="checkbox" name="phobby" value="3">打麻将
                  <input type="checkbox" name="phobby" value="4">烫头
              </td>
          </tr>
          <tr>
              <td>备注</td>
              <td>
                  <textarea rows="4" cols="60" name="pmemo"></textarea>
              </td>
          </tr>
          <tr>
              <td>
                  <input type="submit" name="psub" value="添加">
              </td>
          </tr>
      </table>
  </form>
  </body>
</html>
