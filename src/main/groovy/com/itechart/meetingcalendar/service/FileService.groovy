package com.itechart.meetingcalendar.service

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

class FileService {

    static boolean saveBase64(String base64, String path) {
        OutputStream os = null
        try {
            os = new FileOutputStream(path)
            byte[] decode = Base64.decoder.decode base64
            os.write decode
            true
        } catch (IOException e) {
            e.printStackTrace()
            false
        } finally {
            if (os) {
                try {
                    os.close()
                } catch (Exception e) {
                    e.printStackTrace()
                }
            }
        }
    }

    static void createFileIfNeed(String path, boolean folder) {
        def file = new File(path)
        if (!file.exists()) {
            if (folder) {
                file.mkdirs()
            } else {
                file.createNewFile()
            }
        }
    }

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
