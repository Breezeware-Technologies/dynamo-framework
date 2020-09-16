package net.breezeware.dynamo.apps.controller;

import java.util.Calendar;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

import net.breezeware.dynamo.apps.entity.DynamoApp;
import net.breezeware.dynamo.apps.service.api.AppService;

/**
 * Controller methods for apps and feature services.
 */
@Controller
@ConditionalOnProperty(value = "containermode.enabled", matchIfMissing = true)
@RequestMapping(value = "/admin/appManagement/*")
@PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
public class AppsManagementController {

    Logger logger = LoggerFactory.getLogger(AppsManagementController.class);

    @Autowired
    AppService appService;

    /**
     * Redirect to the all-apps page displaying the list of apps.
     * @param model      the holder for Model attributes
     * @param predicate  the interface for Boolean typed expressions. Supports
     *                   binding of HTTP parameters to QueryDSL predicate
     * @param pageable   the interface for pagination information
     * @param parameters the holder for HTTP parameters in request
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    public String listApps(Model model, @QuerydslPredicate(root = DynamoApp.class) Predicate predicate,
            @PageableDefault(sort = { "id" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters) {

        logger.info("Inside Apps Controller");
        logger.info("# of params = " + parameters.size());

        Page<DynamoApp> pagedApps = appService.findAllApps(predicate, pageable);
        logger.info("# of apps fetched = {}", pagedApps.getNumberOfElements());

        model.addAttribute("pagedApps", pagedApps);
        model.addAttribute("activeNav", "apps");
        logger.info("Leaving Apps Controller");
        return "apps/all-apps";

    }

    /**
     * Redirect to the view-app page.
     * @param id    the ID used to uniquely identify the app
     * @param model the holder for Model attributes
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/viewApp", method = RequestMethod.GET)
    public String viewApp(@RequestParam("id") long id, Model model) {

        logger.info("Inside viewApp Controller");
        DynamoApp app = appService.findAppById(id);

        model.addAttribute("app", app);
        model.addAttribute("activeNav", "apps");
        logger.info("Leaving viewApp Controller");
        return "apps/view-app";

    }

    /**
     * Redirect to the create app page.
     * @param model the holder for Model attributes
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/createApp", method = RequestMethod.GET)
    public String createApp(Model model) {

        DynamoApp app = new DynamoApp();
        model.addAttribute("dynamoApp", app);
        model.addAttribute("activeNav", "apps");
        return "apps/create-app";
    }

    /**
     * Create a new app. If any errors occurs during app creation, user is
     * redirected to the create-app page.
     * @param app           the DynamoApp entity that contains data from the form
     *                      submitted
     * @param bindingResult the interface to represent binding results
     * @param model         the holder for Model attributes
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/createApp", method = RequestMethod.POST)
    public ModelAndView createApp(@Valid @ModelAttribute("dynamoApp") DynamoApp app, BindingResult bindingResult,
            Model model) {

        app.setCreatedDate(Calendar.getInstance());
        app.setModifiedDate(Calendar.getInstance());

        logger.info("Dynamo App = {}", app);

        if (bindingResult.hasErrors()) {
            logger.info("There are binding result errors. # of field errors = {}. # of all errors = {}.",
                    bindingResult.getFieldErrors().size(), bindingResult.getAllErrors().size());
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }

            model.addAttribute("dynamoApp", app);
            model.addAttribute("activeNav", "apps");
            return new ModelAndView("apps/create-app");
        } else {
            try {
                app = appService.saveDynamoApp(app);
            } catch (Exception e) {
                model.addAttribute("dynamoApp", app);
                model.addAttribute("activeNav", "apps");
                bindingResult.addError(new ObjectError("dynamoApp", e.getMessage()));
                return new ModelAndView("apps/create-app");
            }

            logger.info("App details = {}", app);
        }

        model.addAttribute("activeNav", "apps");
        return new ModelAndView("redirect:/admin/appManagement/apps");
    }

    /**
     * Redirect to the edit-app page.
     * @param id    the ID used to uniquely identify the app
     * @param model the holder for Model attributes
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/editApp", method = RequestMethod.GET)
    public String editGroup(@RequestParam("id") String id, Model model) {

        DynamoApp dynamoApp = appService.findAppById(Long.valueOf(id));

        logger.info("App details = {}", dynamoApp);

        model.addAttribute("activeNav", "apps");
        model.addAttribute("dynamoApp", dynamoApp);
        return "apps/edit-app";

    }

    /**
     * Save the updated values for an app.
     * @param app           the DynamoApp entity that contains data from the form
     *                      submitted
     * @param bindingResult the interface to represent binding results.
     * @param model         the holder for Model attributes
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/editApp", method = RequestMethod.POST)
    public ModelAndView editApp(@Valid @ModelAttribute("dynamoApp") DynamoApp app, BindingResult bindingResult,
            Model model) {
        app.setCreatedDate(Calendar.getInstance());
        app.setModifiedDate(Calendar.getInstance());

        logger.info("Dynamo App = {}", app);

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                logger.info("Field error = " + fe.getField().toString() + ", " + fe.getDefaultMessage() + ", "
                        + fe.getCode());
            }
            model.addAttribute("dynamoApp", app);
            model.addAttribute("activeNav", "apps");
            return new ModelAndView("apps/edit-app");
        } else {
            try {
                appService.saveDynamoApp(app);
            } catch (Exception e) {
                bindingResult.addError(new ObjectError("dynamoApp", e.getMessage()));

                model.addAttribute("dynamoApp", app);
                model.addAttribute("activeNav", "apps");

                return new ModelAndView("apps/edit-app");
            }
        }

        model.addAttribute("activeNav", "apps");
        return new ModelAndView("redirect:/admin/appManagement/apps");
    }

}