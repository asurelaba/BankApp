package com.solvd.db.jaxbxml;

import com.solvd.db.model.Bank;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class ParseXMLJaxB<T> {

    public void marshall(T t, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(t, file);
        } catch (JAXBException e) {
            System.out.println("Error in marshall for class" + t.getClass() + e);
        }
    }

    public T unmarshall(Class unMarshallTo, File path) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(unMarshallTo);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T) unmarshaller.unmarshal(path);
        } catch (JAXBException e) {
            System.out.println("Error in unmarshall for class" + unMarshallTo.getSimpleName() + e);
        }
        return null;
    }
}
