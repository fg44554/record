<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.lwz.demo.pojo.Msg" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'main.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>

<body>
<%
    List lis=(List)request.getAttribute("msg1");
%>
<table>
    <tr>
        <td>name</td>
    </tr>
    <%
        Iterator iter = lis.iterator();
        while(iter.hasNext()) {
            Msg p=(Msg) iter.next();
    %>
    <tr>
        <td><%=p.getName() %></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
