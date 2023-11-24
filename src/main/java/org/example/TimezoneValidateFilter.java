package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    String timezone = request.getParameter("timezone");

    if (timezone != null && isValidTimezone(timezone)) {
      // Valid timezone, proceed with the filter chain
      chain.doFilter(request, response);
    } else {
      // Invalid timezone, send an error response
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(getCurrentTime());
      ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @Override
  public void destroy() {
  }

  private boolean isValidTimezone(String timezone) {
    return timezone.matches("[\\w\\-\\+]+") && TimeZone.getTimeZone(timezone).getID().equals(timezone);
  }

  private String getCurrentTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return "Current Time: " + sdf.format(new Date());
  }
}
