import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NoValidationHandler extends DefaultHandler {
    private boolean isX;
    private boolean isY;

    private final ArrayList<Double> xList = new ArrayList<>();
    private final ArrayList<Double> yList = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        File xmlFile = new File("data.xml");

        startParse(xmlFile);

    }

    public static void startParse(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        NoValidationHandler handler = new NoValidationHandler();

        System.out.println("Start parsing");
        saxParser.parse(xmlFile, handler);
        System.out.println("Parsing finished");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("x")) {
            isX = true;
        }
        if (qName.equalsIgnoreCase("y")) {
            isY = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        if (isX) {
            double e = Double.parseDouble(str);
            xList.add(e);
            isX = false;
        }
        if (isY) {
            yList.add(Double.parseDouble(str));
            isY = false;
        }
    }

    public void endDocument(){
        int n = xList.size();

        double xSum = 0;
        double ySum = 0;
        double xySum = 0;
        double xSquareSum = 0;

        for (int i = 0; i < n; i++) {
            double x = xList.get(i);
            double y = yList.get(i);
            System.out.println("X = " + x + " Y = " + y);
            xSum += x;
            ySum += y;
            xySum += x * y;
            xSquareSum += x * x;
        }

        double xMean = xSum / n;
        double yMean = ySum / n;

        double k = (xySum - xSum * yMean) / (xSquareSum - xSum * xMean);
        double b = yMean - k * xMean;

        System.out.println("k = " + k);
        System.out.println("b = " + b);
    }
}