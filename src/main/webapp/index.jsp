<%@ page import="ecs189.querying.github.GithubQuerier" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel='stylesheet' href='main.css'>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Github Stalker</title>
  </head>
  <body>
  <div id="core">
    <h2>What Have I Been Up To?</h2>
    <img id="icon" src="https://s-media-cache-ak0.pinimg.com/originals/dc/ef/3a/dcef3abedf0e0761203aaeb85886a6f3.jpg" style="width:500px;height:500px;">
    <form action="index.jsp" method="GET">
      <div class="form-group">
        <label for="user"><h3>Github username:</h3></label>
        <input type="user" name="user_name" class="form-control" id="user"
               value="<%= request.getParameter("user_name") == null ? "" : request.getParameter("user_name")%>">
      </div>
      <%String user=request.getParameter("user_name"); %>
      <button type="submit" class="btn btn-default">
        <c:choose>
          <c:when test="${empty user}">
            Submit
          </c:when>
          <c:otherwise>
            Refresh
          </c:otherwise>
        </c:choose>
      </button>
    </form>
    <div id="activity">
      <%if (user != null && !user.isEmpty()){%>
          <%=GithubQuerier.eventsAsHTML(user)%>
        <% } else { %>
            Tell me who you are, and I will tell you what you did last ... week? Month? Summer? Not sure yet.
        <% }%>
    </div>
  </div>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css">

  <script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
  <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  </body>
</html>
