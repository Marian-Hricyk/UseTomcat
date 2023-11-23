package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");

    String timezone = request.getParameter("timezone");

    SimpleDateFormat sdf;
    if (timezone != null && !timezone.isEmpty()) {
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
      sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT" + timezone));
    } else {
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
      sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
    }


    String currentTime = sdf.format(new Date());


    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Current Time</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h2>Current Time</h2>");
      out.println("<p>Time: " + currentTime + "</p>");
      out.println("<p>Timezone: " + (timezone != null ? timezone : "UTC") + "</p>");
      out.println("</body>");
      out.println("</html>");
    }
  }
}

