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

    public String build(Map<String, String> keyVals, String templateName) {
        Context context = new Context();

        for (String key : keyVals.keySet()) {
            context.setVariable(key, keyVals.get(key));
        }

        return templateEngine.process(templateName, context);
    }
}