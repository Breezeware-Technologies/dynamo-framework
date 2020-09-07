package net.breezeware.dynamo.auth.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller methods for access denied.
 */
@Controller
public class AccessDeniedController {
    Logger logger = LoggerFactory.getLogger(AccessDeniedController.class);

    /**
     * Maps to the access denied message page.
     * @param model the holder for Model attributes
     * @return returns a string that maps to the 'access-denied' template.
     */
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        return "access-denied";
    }

}