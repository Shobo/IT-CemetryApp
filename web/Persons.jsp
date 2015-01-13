<%@ page import="edu.cs.ubbcluj.ro.utils.Constants" %>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Persoane</title>
  <link href="css/styles.css" rel="stylesheet" type="text/css">
  <script src="jquery-1.11.1.js"></script>
  <script src="jquery.js"></script>
</head>
<body>

<div class="menu">
  <div>
    <img class="logo" src="resources/logo.gif" alt="logo">
  </div>
</div>

<% if (session.getAttribute("option") != null) { %>
<div class="title-div">
  <% if (session.getAttribute("option").equals(Constants.OWNER_MANAGEMENT))
        out.print(Constants.OWNER_MANAGEMENT);
    else if (session.getAttribute("option").equals(Constants.DEAD_MANAGEMENT))
        out.print(Constants.DEAD_MANAGEMENT);
    else if (session.getAttribute("option").equals(Constants.VIEW_OWNED_DEAD))
        out.print(Constants.OWNED_DEAD_REGISTER);
    else if (session.getAttribute("option").equals(Constants.VIEW_UNOWNED_DEAD))
        out.print(Constants.UNOWNED_DEAD_REGISTER);%>

  <% if (session.getAttribute("option").equals(Constants.VIEW_OWNED_DEAD)){ %>
  <div class="selection-div">
    <label for="year-selection">Anul</label>
    <select id="year-selection" name="<%out.print(StringEscapeUtils.escapeHtml3(Constants.VIEW_OWNED_DEAD));%>">
      <%int latest = Calendar.getInstance().get(Calendar.YEAR);
      for (int i = latest; i >= latest - 10; i--) {
      %>
      <option value="<%out.print(i);%>"><%out.print(i);%></option>
      <% } %>
    </select>
  </div>
  <% } %>
</div>
<% } %>

<div class="content">
  <% if (session.getAttribute("option") != null) { %>
  <form action="/PersonServlet" method="GET">
    <% if (session.getAttribute("option").equals(Constants.OWNER_MANAGEMENT)) { %>
    <input class="img-button" type="image" src="resources/file_add.png" value="Add" name="act" title="Adăugare"/>
    <input class="img-button" type="image" src="resources/file_edit.png" value="Edit" name="act" disabled="true" title="Modificare"/>
    <input class="img-button" type="image" src="resources/file_delete.png" value="Delete" name="act" disabled="true" title="Ștergere">
    <input class="img-button" type="image" src="resources/file_save.png" value="Save" name="act" disabled="true" title="Descărcare">
    <input type="submit" class="search-box" name="act" value="Caută">
    <input type="text" class="search-box" placeholder="Caută" name="filter-value">
    <a hidden id="download" href="resources/temp/<% if (session.getAttribute("saved") != null)
                out.print(session.getAttribute("saved") + ".doc");
                session.removeAttribute("saved"); %>"></a>
    <% } %>

    <table class="data-table">

    </table>

  </form>
  <% session.removeAttribute("option"); %>
  <% } %>
</div>

</body>
</html>
