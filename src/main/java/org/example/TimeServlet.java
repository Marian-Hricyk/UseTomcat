package org.example;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String timezoneParam = request.getParameter("timezone");
    if (timezoneParam == null) {
      timezoneParam = "Etc/GMT";
    } else {
      timezoneParam = timezoneParam.replace("UTC+", "Etc/GMT-").replace("UTC-", "Etc/GMT+");
    }
    ZoneId zoneId = ZoneId.of(timezoneParam);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
    String formTime = currentTime.format(format).replace("GMT", "UTC");


    response.setContentType("text/html; charset=utf-8");
    response.getWriter().println("<html>");
    response.getWriter().println("<body>");
    response.getWriter().println(formTime);
    response.getWriter().println("</body>");
    response.getWriter().println("</html>");
    response.getWriter().close();

  }
}

