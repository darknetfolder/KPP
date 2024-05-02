import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class ValidationHandler {

    private static final File xsdFile = new File("data.xsd");

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("data.xml");

        validateXmlFile(xmlFile);
        NoValidationHandler.startParse(xmlFile);

    }

    public static void validateXmlFile(File xmlFile) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsdFile);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));

            System.out.println("XML is valid");

        } catch (SAXException e) {
            System.out.println("XML is not valid: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}