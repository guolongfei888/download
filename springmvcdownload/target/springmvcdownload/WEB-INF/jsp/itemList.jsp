<%--
  Created by IntelliJ IDEA.
  User: 900142
  Date: 2019/12/11
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>文件下载</h3> -->

    <a href="${pageContext.request.contextPath }/download.action?filename=${requestScope.items.pic.originalFilename}">
        用户头像：${requestScope.items.pic.originalFilename}
    </a>


</body>
</html>
