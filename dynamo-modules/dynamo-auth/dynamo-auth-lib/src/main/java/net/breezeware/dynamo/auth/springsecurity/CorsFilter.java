package net.breezeware.dynamo.auth.springsecurity;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
// @Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request != null && response != null) {
            logger.debug("Entering doFilter(). Request details: context path = '{}'. URL = '{}'",
                    request.getContextPath(), request.getRequestURL());

            logger.debug("Entering doFilter(). Request details: method = '{}' type = {}.", request.getMethod(),
                    request.getContentType());

            logger.debug("Setting following headers: Access-Control-Allow-Origin, Access-Control-Allow-Methods, "
                    + "Access-Control-Max-Age, Access-Control-Allow-Credentials, Access-Control-Allow-Headers");

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,HEAD,OPTIONS,PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers",
                    "Access-Control-Allow-Origin, Content-Type, X-Requested-With, accept, Origin, "
                            + "Access-Control-Request-Method, Access-Control-Request-Headers, "
                            + "auth_username, auth_password, Authorization");

            if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
                // chain.doFilter(req, res);
                response.getWriter().print("OK");
                logger.debug(
                        "Leaving doFilter(). Request method is OPTIONS. Setting response to OK and flushing writer.");

                res.getWriter().flush();
            } else {
                logger.debug("Leaving doFilter(). Request method = '{}'. Calling doFilter in chain.",
                        request.getMethod());

                chain.doFilter(req, res);
            }
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}