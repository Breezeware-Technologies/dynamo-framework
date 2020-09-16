package net.breezeware.dynamo.util.mail.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Create a body content by substituting the place holders in the email template
     * with the values provided.
     * @param keyVals      the values which are inserted in the template
     * @param templateName the template containing the place holders
     * @return the body content after inserting values into template
     */
    public String build(Map<String, String> keyVals, String templateName) {
        Context context = new Context();

        for (String key : keyVals.keySet()) {
            context.setVariable(key, keyVals.get(key));
        }

        return templateEngine.process(templateName, context);
    }
}