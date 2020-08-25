package com.angkorteam.bank.dao.tenant;

import org.apache.commons.io.FilenameUtils;
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

public class RepairProgram {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        File folder = new File("bank-tenant-dao/src/main/resources");

        int notNullConstraintName = 1;
        int primaryKeyName = 1;
        int uniqueConstraintName = 1;
        int indexName = 1;
        int foreignKeyConstraint = 1;

        for (File file : folder.listFiles()) {
            int changesets = 1;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            Element databaseChangeLog = doc.getDocumentElement();
            for (int i = 0; i < databaseChangeLog.getChildNodes().getLength(); i++) {
                if (databaseChangeLog.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element changeSet = (Element) databaseChangeLog.getChildNodes().item(i);
                    if ("changeSet".equals(changeSet.getNodeName())) {
                        changeSet.setAttribute("id", FilenameUtils.getBaseName(file.getName()) + "-" + (changesets++));
                        for (int j = 0; j < changeSet.getChildNodes().getLength(); j++) {
                            if (changeSet.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) changeSet.getChildNodes().item(j);
                                if ("addNotNullConstraint".equals(element.getNodeName())) {
                                    element.setAttribute("constraintName", "null_key_" + notNullConstraintName++);
                                } else if ("addForeignKeyConstraint".equals(element.getNodeName())) {
                                    element.setAttribute("constraintName", "fk_key_" + foreignKeyConstraint++);
                                } else if ("addUniqueConstraint".equals(element.getNodeName())) {
                                    element.setAttribute("constraintName", "unique_" + uniqueConstraintName++);
                                } else if ("createIndex".equals(element.getNodeName())) {
                                    element.setAttribute("indexName", "index_" + indexName++);
                                } else if ("createTable".equals(element.getNodeName()) || "addColumn".equals(element.getNodeName())) {
                                    for (int k = 0; k < element.getChildNodes().getLength(); k++) {
                                        if (element.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE) {
                                            Element column = (Element) element.getChildNodes().item(k);
                                            if ("column".equals(column.getNodeName())) {
                                                if (column.hasAttribute("type")) {
                                                    String type = column.getAttribute("type");
                                                    if (type.startsWith("VARCHAR") && type.contains("(") && type.contains(")")) {
                                                        int size = Integer.valueOf(type.substring(type.indexOf("(") + 1, type.lastIndexOf(")")));
                                                        if (size > 4000) {
                                                            column.setAttribute("type", "VARCHAR(4000)");
                                                        }
                                                    }
                                                }
                                                for (int m = 0; m < column.getChildNodes().getLength(); m++) {
                                                    if (column.getChildNodes().item(m).getNodeType() == Node.ELEMENT_NODE) {
                                                        Element constraints = (Element) column.getChildNodes().item(m);
                                                        if (!"constraints".equals(constraints.getNodeName())) {
                                                            continue;
                                                        }
                                                        if (constraints.hasAttribute("nullable")) {
                                                            constraints.setAttribute("notNullConstraintName", "null_key_" + notNullConstraintName++);
                                                        }
                                                        if (constraints.hasAttribute("primaryKey")) {
                                                            constraints.setAttribute("primaryKeyName", "primary_key_" + primaryKeyName++);
                                                        }
                                                        if (constraints.hasAttribute("unique")) {
                                                            constraints.setAttribute("uniqueConstraintName", "unique_" + uniqueConstraintName++);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
    }
}
