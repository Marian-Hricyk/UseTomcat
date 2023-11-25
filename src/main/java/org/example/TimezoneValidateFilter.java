package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    String timezone = request.getParameter("timezone");

    if (timezone == null || isValidTimezone(timezone)) {

      chain.doFilter(request, response);
    } else {

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().println("Invalid timezone");
      response.getWriter().println("Please write zone in valid format");
      response.getWriter().close();
    }
  }

  @Override
  public void destroy() {
  }

  private boolean isValidTimezone(String timezone) {
    String zone = timezone.replace("UTC+", "Etc/GMT-").replace("UTC-", "Etc/GMT+");
    Set<String> availableTimezones = ZoneId.getAvailableZoneIds();

    return availableTimezones.contains(zone);
  }
}
