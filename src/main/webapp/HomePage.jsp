<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.personlist.dao.DBCreator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
<h2>Person List</h2>
 <jsp:useBean class="com.personlist.model.pojo.Employee" id="user" scope="session"/>
 <input type="text"
 <h1>Hello, <jsp:getProperty name="user" property="name"/></h1>
                    <h:inputText id="name" value="#{customer.name}" />
</body>
</html>