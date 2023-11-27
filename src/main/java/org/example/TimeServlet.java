package org.example;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
  private TemplateEngine engine;

  @Override
  public void init() throws ServletException {
    engine = new TemplateEngine();

    JavaxServletWebApplication jswa =
            JavaxServletWebApplication.buildApplication(this.getServletContext());

    WebApplicationTemplateResolver
            resolver = new WebApplicationTemplateResolver(jswa);
    resolver.setPrefix("/templates/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML5");
    resolver.setOrder(engine.getTemplateResolvers().size());
    resolver.setCacheable(false);
    engine.addTemplateResolver(resolver);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String timezoneParam = request.getParameter("timezone");
    if (timezoneParam == null) {
      timezoneParam = "Etc/GMT";
    } else {
      timezoneParam = timezoneParam.replace("UTC+", "Etc/GMT-").replace("UTC-", "Etc/GMT+");
    }
    ZoneId zoneId = ZoneId.of(timezoneParam);
    ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    String formattedTime = currentTime.format(format).replace("GMT", "UTC");

    Context context = new Context(Locale.US);

    context.setVariable("currentTime", formattedTime);
    context.setVariable("timezone", timezoneParam != null ? timezoneParam : "UTC");

    engine.process("timeTemplate", context, response.getWriter());
  }
}