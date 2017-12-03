package com.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.xpath.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by viet.phamquoc on 12/8/2016.
 */

public class AndroidControl{

    private Document doc = null;
    private String ADBS ="";
    private String deviceID;
    XPathFactory xPathfactory = XPathFactory.newInstance();
    XPath xpath = xPathfactory.newXPath();

    public AndroidControl(String deviceID) {
        ADBS = String.format("adb -s %s shell ",deviceID);
        this.deviceID = deviceID;
    }

    public void wakeUpDevice() {
        input_key("KEYCODE_WAKEUP");
        swipe(360, 1020, 360, 500);
    }
    public void input(String text){
        executeCommand(ADBS+ "input text "+ text);
    }

    public void click_on(int x, int y){
        executeCommand(ADBS+ String.format("input tap %i %i",x,y));
    }

    public void click_on(Element element){
        String[]Coordinate = element.getAttribute("bounds").split("([\\]\\[\\,])");
        String x  = Coordinate[1];
        String y  = Coordinate[2];
        executeCommand(ADBS+ String.format("input tap %s %s",x,y));
    }

    public void input_key(String keycode){
        executeCommand(ADBS+ "input keyevent "+ keycode);
    }

    public void swipe(int startX,int startY,int endX,int endY){
        executeCommand(ADBS + String.format("input touchscreen swipe %d %d %d %d",startX,startY,endX,endY));
    }

    public void start_app_package(String packageName){

    }

    public void start_app_activity(String activityName){
        executeCommand(ADBS+"am start -n "+ activityName);
    }

    public void read_screen() throws ParserConfigurationException, IOException, SAXException {
        executeCommand(String.format("adb -s %s exec-out uiautomator dump sdcard/view.xml",deviceID));
        executeCommand(String.format("adb -s %s pull sdcard/view.xml %s",deviceID,System.getProperty("java.io.tmpdir")));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(System.getProperty("java.io.tmpdir")+"view.xml");
    }

    public Element get_element_by_Xpath(String xpathquery) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
        read_screen();

        //XPathExpression expr = xpath.compile(xpathquery);
        return (Element) xpath.compile(xpathquery).evaluate(doc.getDocumentElement(),XPathConstants.NODE);
    }

    public Element get_text_by_ID(String id) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
        read_screen();
        Element documentElement = doc.getDocumentElement();
        return (Element) xpath.compile(".//*['@resource-id="+id+"']").evaluate(doc.getDocumentElement(),XPathConstants.NODE);
    }

    private static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}


