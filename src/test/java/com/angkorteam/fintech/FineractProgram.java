package com.angkorteam.fintech;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class FineractProgram {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        // 1. run liquibase
        // mysql
        // liquibase --driver=com.mysql.cj.jdbc.Driver --changeLogFile="fineract_tenant_changes.xml" --url="jdbc:mysql://192.168.1.6:3306/fineract_tenants?useSSL=true" --username="bank" --password="password" update
        // oracle
        // liquibase --driver=oracle.jdbc.driver.OracleDriver --changeLogFile="fineract_tenant_changes.xml" --url="jdbc:oracle:thin:@192.168.1.6:1521:ORCLCDB" --username="skhauv" --password="password" update

        // 2. run initial fineract db

    }
}
