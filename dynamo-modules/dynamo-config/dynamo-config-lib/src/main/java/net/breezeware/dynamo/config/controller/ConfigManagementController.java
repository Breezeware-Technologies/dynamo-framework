package net.breezeware.dynamo.config.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.config.entity.AppProperty;
import net.breezeware.dynamo.config.service.api.AppConfigService;

/**
 * Controller methods for config management.
 * 
 */
@Controller
@RequestMapping(value = "/admin/configManagement/*")
@PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN')")
public class ConfigManagementController {

	Logger logger = LoggerFactory.getLogger(ConfigManagementController.class);

	@Autowired
	AppConfigService appConfigService;

	/**
	 * 
	 * Re-directs to the page displaying all configuration properties page.
	 * 
	 * @param model
	 * @param predicate
	 * @param pageable
	 * @param parameters
	 * @return
	 */
	@RequestMapping(value = "/listConfigs", method = RequestMethod.GET)
	public String listConfigs(Model model, @QuerydslPredicate(root = AppProperty.class) Predicate predicate,
			@PageableDefault(sort = { "id" }, page = 0, size = 12) Pageable pageable,
			@RequestParam MultiValueMap<String, String> parameters) {

		logger.info("Entering listConfigs()");
		logger.info("# of params = " + parameters.size());

		Page<AppProperty> pagedConfigs = appConfigService.findAppProperties(predicate, pageable);
		logger.info("# of properties fetched = {}", pagedConfigs.getNumberOfElements());

		model.addAttribute("pagedConfigs", pagedConfigs);
		model.addAttribute("activeNav", "config");
		logger.info("Leaving listConfigs()");
		return "config/list-configs";

	}

	/**
	 * 
	 * Gets the id of the configuration property and re-directs to the view property
	 * page.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewConfig", method = RequestMethod.GET)
	public String viewConfig(@RequestParam("id") long id, Model model) {

		logger.info("Entering viewConfig()");
		AppProperty config = appConfigService.getAppPropertyById(id);

		model.addAttribute("config", config);
		model.addAttribute("activeNav", "config");
		logger.info("Leaving viewConfig()");
		return "config/view-config";
	}

	/**
	 * Redirects to the edit configuration page.
	 */
	@RequestMapping(value = "/editConfig", method = RequestMethod.GET)
	public String editConfig(@RequestParam("id") long id, Model model) {

		logger.info("Entering editConfig()");

		AppProperty config = appConfigService.getAppPropertyById(id);

		logger.info("App property = {}", config);

		model.addAttribute("config", config);
		model.addAttribute("activeNav", "config");

		logger.info("Leaving editConfig()");
		return "config/edit-config";

	}

	@RequestMapping(value = "/editConfig", method = RequestMethod.POST)
	public ModelAndView editApp(@Valid @ModelAttribute("config") AppProperty config, BindingResult bindingResult,
			Model model, HttpSession session) {

		logger.info("App property = {}", config);

		if (bindingResult.hasErrors()) {
			for (FieldError fe : bindingResult.getFieldErrors()) {
				logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
						+ fe.getCode());
			}
			model.addAttribute("config", config);
			model.addAttribute("activeNav", "config");
			return new ModelAndView("config/edit-config");
		} else {
			try {
				appConfigService.saveAppProperty(config);
			} catch (Exception e) {
				bindingResult.addError(new ObjectError("config", e.getMessage()));

				model.addAttribute("config", config);
				model.addAttribute("activeNav", "config");

				return new ModelAndView("config/edit-config");
			}
		}

		model.addAttribute("activeNav", "config");
		return new ModelAndView("redirect:/admin/configManagement/listConfigs");
	}
}