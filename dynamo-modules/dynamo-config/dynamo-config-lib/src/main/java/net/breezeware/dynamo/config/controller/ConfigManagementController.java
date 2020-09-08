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
 */
@Controller
@RequestMapping(value = "/admin/configManagement/*")
@PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN')")
public class ConfigManagementController {

    Logger logger = LoggerFactory.getLogger(ConfigManagementController.class);

    @Autowired
    AppConfigService appConfigService;

    /**
     * Re-direct to the page displaying all configuration properties page.
     * @param model      the holder for Model attributes
     * @param predicate  the interface for Boolean typed expressions. Supports
     *                   binding of HTTP parameters to QueryDSL predicate
     * @param pageable   the interface for pagination information
     * @param parameters the holder for HTTP parameters in request
     * @return a string to identify the Thymeleaf template
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
     * Get the id of the configuration property and re-directs to the view property
     * page.
     * @param id    the ID to uniquely identify an AppProperty
     * @param model the holder for Model attributes
     * @return a string to identify the Thymeleaf template
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
     * Redirect to the edit configuration page.
     * @param id    the ID to uniquely identify an AppProperty
     * @param model the holder for Model attributes
     * @return a string to identify the Thymeleaf template
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

    /**
     * Save the changes to the AppProperty entity.
     * @param config        the AppProperty entity to be saved
     * @param bindingResult the interface to represent binding results
     * @param model         the holder for Model attributes
     * @param session       the HTTPSession entity
     * @return reference to the view page that is the result of this operation
     */
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