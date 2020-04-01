<%--
  Created by IntelliJ IDEA.
  User: 900142
  Date: 2019/12/11
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
</head>
<body>
<form name="serForm" action="${pageContext.request.contextPath }/fileUpload.action" method="post"  enctype="multipart/form-data">
    <h1>采用流的方式上传文件</h1>
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>

<form name="Form2" action="${pageContext.request.contextPath }/fileUpload2.action" method="post"  enctype="multipart/form-data">
    <h1>采用multipart提供的file.transfer方法上传文件</h1>
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>

<form name="Form2" action="${pageContext.request.contextPath }/springUpload.action" method="post"  enctype="multipart/form-data">
    <h1>使用spring mvc提供的类的方法上传文件</h1>
    <input type="file" name="file">
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>

<h2>用户注册</h2>
<form action="${pageContext.request.contextPath }/register.action" enctype="multipart/form-data" method="post">
    <table>
        <tr>
            <td>用户头像：</td>
            <td><input type="file" name="pic"></td>
        </tr>
        <tr>
            <td>上传：</td>
            <td><input type="submit" value="上传"></td>
        </tr>
    </table>
</form>


</body>
</html>
