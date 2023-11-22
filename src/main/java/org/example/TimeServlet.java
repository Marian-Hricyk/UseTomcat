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

    response.setContentType("text/html");

    PrintWriter out = response.getWriter();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
    String currentTime = sdf.format(new Date());


    out.println("<html>");
    out.println("<head><title>Current Time (UTC)</title></head>");
    out.println("<body>");
    out.println("<h1>Current Time (UTC)</h1>");
    out.println("<p>" + currentTime + "</p>");
    out.println("</body>");
    out.println("</html>");
  }
}
