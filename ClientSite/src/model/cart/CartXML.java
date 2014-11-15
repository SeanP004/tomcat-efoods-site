package model.cart;

import java.io.*;
import javax.xml.bind.*;
import javax.xml.transform.stream.*;
import model.exception.XMLGenerationException;

public class CartXML {

    public Writer generate(Writer out, Cart cart)
            throws XMLGenerationException, IOException {
        try {
            Marshaller marsh = JAXBContext.newInstance(cart.getClass())
                    .createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marsh.marshal(cart, new StreamResult(out));
            return out;
        } catch (JAXBException e) {
            throw new XMLGenerationException(e);
        }
    }

} // CartXML
