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
import java.util.Date;
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
    resolver.setPrefix("/WEB-INF/temp/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML5");
    resolver.setOrder(engine.getTemplateResolvers().size());
    resolver.setCacheable(false);
    engine.addTemplateResolver(resolver);
  }

@Override
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
  Context context = new Context(Locale.US);

    context.setVariable("currentTime", currentTime);
    context.setVariable("timezone", timezone != null ? timezone : "UTC");

    engine.process("timeTemplate", context, response.getWriter());


  }
}

