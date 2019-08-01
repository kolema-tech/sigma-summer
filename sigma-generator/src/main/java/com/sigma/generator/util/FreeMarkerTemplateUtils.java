package com.sigma.generator.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-20:52
 * desc: FreeMarker模板
 **/
public class FreeMarkerTemplateUtils {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_22);

    static {
        //这里比较重要，用来指定加载模板所在的路径
        try {
            CONFIGURATION.setTemplateLoader(new FileTemplateLoader(new File("D:\\Users\\zhenpeng\\Documents\\GitHub\\sigma-summer\\sigma-generator\\src\\main\\resources\\templates")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    private FreeMarkerTemplateUtils() {
    }

    public static Template getTemplate(String templateName) throws IOException {
        try {
            return CONFIGURATION.getTemplate(templateName);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void clearCache() {
        CONFIGURATION.clearTemplateCache();
    }
}