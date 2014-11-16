package model.common;

import java.io.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.*;
import model.exception.*;

public class XMLUtil {

    private XMLUtil() { } // no constructor

    /**
     * Generates an XML output, given a output writer
     * and an object, and specifying the object's type
     * via the generic T.
     *
     * @param  out      output writer
     * @param  object   to generate XML for
     * @return          the given out writer
     * @throws          XMLGenerationException, IOException
     */
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