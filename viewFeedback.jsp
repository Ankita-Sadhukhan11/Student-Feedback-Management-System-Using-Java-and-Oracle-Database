
<%@ page import="java.sql.*" %>
<%@ page import="feedback.DBConnection" %>

<html>
<head>
    <title>View Feedback</title>
</head>
<body>

<h2>Student Feedback Records</h2>

<table border="1" cellpadding="8">

<tr>
    <th>ID</th>
    <th>UID</th>
    <th>Name</th>
    <th>Email</th>
    <th>Category</th>
    <th>Rating</th>
    <th>Feedback</th>
    <th>Actions</th>
</tr>

<%
try {
    Connection con = DBConnection.getConnection();

    Statement stmt = con.createStatement();

    ResultSet rs = stmt.executeQuery(
        "SELECT * FROM feedback ORDER BY id"
    );

    while (rs.next()) {

        int id = rs.getInt("id");
%>

<tr>
    <td><%= id %></td>
    <td><%= rs.getString("uid") %></td>
    <td><%= rs.getString("name") %></td>
    <td><%= rs.getString("email") %></td>
    <td><%= rs.getString("category") %></td>
    <td><%= rs.getInt("rating") %></td>
    <td><%= rs.getString("comments") %></td>

    <td>

        <form action="feedback" method="post">

            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= id %>">

            Rating:
            <input type="number" name="rating"
                   min="1" max="5" required>

            <br><br>

            Feedback:
            <input type="text" name="comments" required>

            <br><br>

            <input type="submit" value="Update">

        </form>

        <br>

        <form action="feedback" method="post">

            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="<%= id %>">

            <input type="submit" value="Delete">

        </form>

    </td>
</tr>

<%
    }

    rs.close();
    stmt.close();
    con.close();

} catch (Exception e) {
    out.println("Unable to load feedback.");
    application.log("Database error", e);
}
%>

</table>

<br>

<a href="index.html">Back to Feedback Form</a>

</body>
</html>
