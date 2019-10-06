package capstone.is4103capstone.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class MailContentBuilder {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(Map<String, Object> model, String template) {
        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process(template, context);
        return html;
    }
}
