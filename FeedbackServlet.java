
package feedback;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

public class FeedbackServlet extends HttpServlet {

    // VIEW FEEDBACK
    public void doGet(HttpServletRequest req,
                      HttpServletResponse res) throws IOException {

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<h2>Student Feedback Records</h2>");

        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM feedback ORDER BY id"
            );

            out.println("<table border='1' cellpadding='10'>");

            out.println("<tr><th>ID</th><th>UID</th><th>Name</th>"
                    + "<th>Email</th><th>Category</th>"
                    + "<th>Rating</th><th>Feedback</th></tr>");

            while (rs.next()) {

                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("u_id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("category") + "</td>");
                out.println("<td>" + rs.getInt("rating") + "</td>");
                out.println("<td>" + rs.getString("comments") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            out.println("<p>Error loading feedback!</p>");
            e.printStackTrace();
        }

        out.println("<br><a href='index.html'>Back to Form</a>");
    }

    // SUBMIT FEEDBACK
    public void doPost(HttpServletRequest req,
                       HttpServletResponse res) throws IOException {

        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");

        PrintWriter out = res.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO feedback "
                    + "(u_id, name, email, category, rating, comments) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, req.getParameter("uid"));
            ps.setString(2, req.getParameter("name"));
            ps.setString(3, req.getParameter("email"));
            ps.setString(4, req.getParameter("category"));
            ps.setInt(5, Integer.parseInt(req.getParameter("rating")));
            ps.setString(6, req.getParameter("comments"));

            ps.executeUpdate();

            ps.close();
            con.close();

            out.println("<h2>Feedback Submitted Successfully!</h2>");
            out.println("<a href='index.html'>Back to Form</a>");
            out.println("<br><a href='feedback?action=view'>View Feedback</a>");

        } catch (Exception e) {
            out.println("<h2>Error saving feedback!</h2>");
            e.printStackTrace();
        }
    }
}
