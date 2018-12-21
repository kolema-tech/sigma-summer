package com.sigma.soa;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.xml.bind.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import static javax.xml.bind.JAXBContext.newInstance;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/8/6-22:33
 * desc:
 **/
@Slf4j
public class JaxbUtil {

    /**
     * 对象转xml
     *
     * @param obj
     * @return
     */
    public static String toXmlDocument(Object obj) {

        if (obj == null) {
            return null;
        }

        try {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            JAXBContext context = newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(obj, byteArrayOutputStream);
            String result = new String(byteArrayOutputStream.toByteArray());
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            return result;
        } catch (IOException e) {
            log.error("toXmlDocument ex.", e);
        } catch (PropertyException e) {
            log.error("toXmlDocument ex.", e);
        } catch (JAXBException e) {
            log.error("toXmlDocument ex.", e);
        }

        return null;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml
     * @param c
     * @return
     */
    public static <T> T fromXml(String xml, Class<T> c) {

        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            log.error("xml to JavaBean ex.", e);
        }

        return t;
    }
}

