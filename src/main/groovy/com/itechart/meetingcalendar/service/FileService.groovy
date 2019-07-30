package com.itechart.meetingcalendar.service

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

class FileService {

    static String getTemplate(EmailTemplate template) {
        try {
            getTemplateUnsafe(template)
        } catch (Exception e) {
            ""
        }
    }

    private static String getTemplateUnsafe(EmailTemplate template) throws Exception {
        Resource resource = new ClassPathResource("/static/templates/${template.getTemplateName()}")
        String path = resource.getURL().getPath()
        FileReader fr = new FileReader(path)
        StringBuilder sb = new StringBuilder()
        int i
        while ((i = fr.read()) != -1) {
            sb.append((char) i)
        }
        fr.close()
        return sb.toString()
    }

}
