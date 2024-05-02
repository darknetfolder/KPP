import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class DOMHandler {

    private static Document document;
    private static final File xmlFile = new File("data.xml");
    private static final File updXmlFile = new File("updatedData.xml");

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DOMHandler parser = new DOMHandler();

        ValidationHandler.validateXmlFile(xmlFile);

        parser.parseXmlFile();
        NoValidationHandler.startParse(xmlFile);

        System.out.println("\n\nUpdated XML File:");
        parser.updateXmlFile();
        NoValidationHandler.startParse(updXmlFile);
        parser.rewriteXmlFile();

    }

    public void parseXmlFile() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateXmlFile(){
        NodeList nodeList = document.getElementsByTagName("data");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            double xValue = Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent());
            double yValue = Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent());
            element.getElementsByTagName("x").item(0).setTextContent(String.valueOf(xValue * 0.8));
            element.getElementsByTagName("y").item(0).setTextContent(String.valueOf(yValue * 0.8));
        }
    }

    public void rewriteXmlFile() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("updatedData.xml"));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}