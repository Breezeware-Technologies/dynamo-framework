package net.breezeware.dynamo.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.breezeware.dynamo.auth.AuthenticationAuthorizationService;
import net.breezeware.dynamo.auth.springsecurity.SecurityContextUtils;
import net.breezeware.dynamo.util.usermgmt.CurrentUserDto;

@Controller
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	AuthenticationAuthorizationService authenticationAuthorizationService;

	/**
	 * 
	 * Re-directs to the 'login' page.
	 * 
	 * @param model
	 * @return returns the string to 'login' template.
	 */
	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		logger.info("Entering login()");

		logger.info("Leaving login()");
		return "login";
	}

	/**
	 * 
	 * Re-directs to the dashboard of the application.
	 * 
	 * @param model
	 * @param session
	 * @return returns the string to the controller mapping.
	 */
	@RequestMapping("/dashboard")
	public String dashboard(Model model, HttpSession session,
			@RequestParam(value = "callSource", required = false) String callSource,
			@RequestParam(value = "newUser", required = false) String newUser) {

		String username = SecurityContextUtils.getUserIdFromSecurityContext();
		logger.info("Username = '{}'", username);

		CurrentUserDto userDto = authenticationAuthorizationService.getCurrentUserDto(username);
		logger.info("UserDto = {} ", userDto);
		session.setAttribute("currentUser", userDto);
		model.addAttribute("activeNav", "dashboard");

		if (callSource != null && !callSource.isEmpty()) {
			model.addAttribute("callSource", callSource);
		}

		if (newUser != null && !newUser.isEmpty()) {
			model.addAttribute("newUser", newUser);
		}

		return "dashboard";
	}
}