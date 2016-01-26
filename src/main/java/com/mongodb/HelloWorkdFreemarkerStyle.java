package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stevenrowney on 25/01/2016.
 */
public class HelloWorkdFreemarkerStyle {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorkdFreemarkerStyle.class, "/");

        try {

            Template helloTemplate = configuration.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = new HashMap();
            helloMap.put("name", "Freemarker");

            helloTemplate.process(helloMap, writer);

            System.out.println(writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
