package model.common;

import java.io.*;
import java.util.*;
import javax.xml.bind.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import javax.xml.validation.Validator;
import org.w3c.dom.*;
import model.exception.*;
import static javax.xml.XMLConstants.*;

public class XMLUtil {

    private XMLUtil() { } // no constructor
    
    // Private Static
    
    private static final SchemaFactory sf
                = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
    private static final TransformerFactory tf
                = TransformerFactory.newInstance();
    private static final DocumentBuilderFactory dbf
                = DocumentBuilderFactory.newInstance();
    private static final Map<Class<? extends Object>, JAXBContext> jaxbCtx
                = new HashMap<Class<? extends Object>, JAXBContext>();

    /**
     * For performance, we will cache our {@link JAXBContext}
     * instances and only create a new ones if we never created one
     * previously. JAXBContext objects are thread safe.
     * 
     * @param o     the object to use as a reference type
     * @return      the JAXBContext for that type
     * @throws      JAXBException
     */
    private synchronized static JAXBContext getJAXBCtx(Object o) throws JAXBException {
        Class<? extends Object> cls = o.getClass();
        if (!jaxbCtx.containsKey(cls)) {
            jaxbCtx.put(cls, JAXBContext.newInstance(cls));
        }
        return jaxbCtx.get(cls);
    }

    // Public Static

    /**
     * Generates an XML output, given a output writer
     * and an object, validated against the specified
     * XML schema and specifying the object's type
     * via the generic T.
     *
     * @param  out      output writer
     * @param  o        object to generate XML for
     * @param  xsd      schema to validate against (optional)
     * @param  xslt     transformation to perform (optional)
     * @return          the given out writer
     * @throws          XMLGenerationException
     */
    public static Writer generate(Writer out, Object o, File xsd, File xslt)
            throws XMLGenerationException {
        try {
            Result result = new StreamResult(out);
            Marshaller marsh = getJAXBCtx(o).createMarshaller();

            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marsh.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            if (xslt != null) {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();
                Source source = new DOMSource(doc); // Could be VERY SLOW!

                Transformer trans = tf.newTransformer(new StreamSource(xslt));
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

                marsh.marshal(o, doc);
                trans.transform(source, result);

                // TODO: Valid Schema

            } else {
                if (xsd != null) {
                    Schema schema = sf.newSchema(xsd);
                    marsh.setSchema(schema);
                }
                marsh.marshal(o, result);
            }
            return out;
        } catch (Exception e) {
            throw new XMLGenerationException(e);
        }
    }
    public static Writer generate(Writer out, Object o, File xsd) throws XMLGenerationException {
        return XMLUtil.generate(out, o, xsd, null);
    }
    public static Writer generate(Writer out, Object o) throws XMLGenerationException {
        return XMLUtil.generate(out, o, null);
    }

} // CartXML