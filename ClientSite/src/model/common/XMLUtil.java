package model.common;

import java.io.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.*;
import model.exception.*;

public class XMLUtil {

    public static <T> Writer generate(Writer out, T object)
            throws XMLGenerationException, IOException {
        try {
            Marshaller marsh = JAXBContext.newInstance(object.getClass())
                    .createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marsh.marshal(object, new StreamResult(out));
            return out;
        } catch (JAXBException e) {
            throw new XMLGenerationException(e);
        }
    }

} // CartXML