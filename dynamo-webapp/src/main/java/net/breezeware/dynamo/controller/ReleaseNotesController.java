package net.breezeware.dynamo.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReleaseNotesController {
	Logger logger = LoggerFactory.getLogger(ReleaseNotesController.class);

	@RequestMapping("/releasenotes")
	public String viewReleaseNotes(Model model, HttpSession session) {
		logger.info("Entering viewReleaseNotes().");
		logger.info("Leaving viewReleaseNotes().");

		model.addAttribute("activeNav", "release");

		return "release-notes/release-notes";
	}
}