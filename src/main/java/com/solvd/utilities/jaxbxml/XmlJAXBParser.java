package com.solvd.utilities.jaxbxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlJAXBParser<T> {

    private static final Logger LOGGER = LogManager.getLogger(XmlJAXBParser.class);

    public void marshall(T t, File file) {
        try {
            Marshaller marshaller = JAXBContext.newInstance(t.getClass()).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(t, file);
        } catch (JAXBException e) {
            LOGGER.error("Error in marshall for class" + t.getClass() + e);
        }
    }

    public T unmarshall(Class unMarshallTo, File path) {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(unMarshallTo).createUnmarshaller();
            return (T) unmarshaller.unmarshal(path);
        } catch (JAXBException e) {
            LOGGER.error("Error in unmarshall for class" + unMarshallTo.getSimpleName() + e);
        }
        return null;
    }
}
