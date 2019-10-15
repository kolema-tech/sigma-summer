package com.sigma.soa;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * @author SteveGlory Zeng.
 * @version 1.0
 * date-time: 2018/7/26-17:28
 * desc:
 **/
@Slf4j
public class SoapMessageUtil {


    public static String soapMessageToXml(SOAPMessage soapMessage) {

        if (soapMessage == null) {
            return null;
        }

        try {
            SOAPPart soapPart = soapMessage.getSOAPPart();
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            @Cleanup StringWriter sw = new StringWriter();
            trans.transform(new DOMSource(soapPart.getEnvelope()), new StreamResult(sw));
            String soapMessageXml = sw.toString();
            if (sw != null) {
                sw.close();
            }
            return soapMessageXml;
        } catch (TransformerConfigurationException e) {
            log.error("soapMessageToXml ex.", e);
        } catch (TransformerException e) {
            log.error("soapMessageToXml ex.", e);
        } catch (SOAPException e) {
            log.error("soapMessageToXml ex.", e);
        } catch (IOException e) {
            log.error("soapMessageToXml ex.", e);
        }
        return null;
    }


    public static SOAPMessage xmlToSoapMessage(String xmlRequest) {

        if (xmlRequest == null) {
            return null;
        }

        try {
            MessageFactory msgFactory = MessageFactory.newInstance();
            @Cleanup ByteArrayInputStream bais = new ByteArrayInputStream(xmlRequest.getBytes(Charset.forName("UTF-8")));
            SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(), bais);
            reqMsg.saveChanges();
            if (bais != null) {
                bais.close();
            }
            return reqMsg;
        } catch (SOAPException e) {
            log.error("xmlToSoapMessage ex.", e);
        } catch (IOException e) {
            log.error("xmlToSoapMessage ex.", e);
        }
        return null;
    }


    public static String toSoapXml(Node node) {

        if (node == null) {
            return null;
        }

        try {
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.STANDALONE, "yes");
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            @Cleanup StringWriter sw = new StringWriter();
            trans.transform(new DOMSource(node), new StreamResult(sw));
            String soapXml = sw.toString();
            if (sw != null) {
                sw.close();
            }
            return soapXml;
        } catch (TransformerException e) {
            log.error("toSoapXml ex.", e);
        } catch (IOException e) {
            log.error("toSoapXml ex.", e);
        }
        return null;
    }
}
