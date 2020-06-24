package net.breezeware.dynamo.auth.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
			throws IOException, ServletException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			logger.warn(
					"User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
		}

		// response.sendRedirect(request.getContextPath() + "/accessDenied");
		response.getWriter().print("You do not required permissions to access this resource.");
	}
}