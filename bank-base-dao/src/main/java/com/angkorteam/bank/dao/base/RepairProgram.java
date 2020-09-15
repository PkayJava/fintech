package com.angkorteam.bank.dao.base;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class RepairProgram {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        File folder = new File("bank-base-dao/src/main/resources");

        String notNullKey = "notnull_key_";
        String foreignKey = "foreign_key_";
        String uniqueIndex = "unique_index_";
        String index = "index_";
        String primaryKey = "primary_key_";

        int notNullKeyRotation = 1;
        int primaryKeyRotation = 1;
        int uniqueIndexRotation = 1;
        int indexRotation = 1;
        int foreignKeyRotation = 1;

        Map<String, String> files = new TreeMap<>();
        int numberSize = 0;
        for (File temp : folder.listFiles()) {
            int i = temp.getName().indexOf('_');
            numberSize = Math.max(temp.getName().substring(1, i).length(), numberSize);
        }
        String zero = StringUtils.repeat("0", numberSize);
        DecimalFormat format = new DecimalFormat(zero);
        for (File temp : folder.listFiles()) {
            int i = temp.getName().indexOf('_');
            int ver = Integer.valueOf(temp.getName().substring(1, i));
            files.put(format.format(ver), temp.getName());
        }

        for (String file : files.values()) {
            System.out.println(file);
            int changesets = 1;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(folder, file));
            Element databaseChangeLog = doc.getDocumentElement();
            for (int i = 0; i < databaseChangeLog.getChildNodes().getLength(); i++) {
                if (databaseChangeLog.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element changeSet = (Element) databaseChangeLog.getChildNodes().item(i);
                    if ("changeSet".equals(changeSet.getNodeName())) {
                        changeSet.setAttribute("id", FilenameUtils.getBaseName(file) + "-" + (changesets++));
                        for (int j = 0; j < changeSet.getChildNodes().getLength(); j++) {
                            if (changeSet.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) changeSet.getChildNodes().item(j);
                                if ("addNotNullConstraint".equals(element.getNodeName())) {
                                    element.setAttribute("constraintName", notNullKey + notNullKeyRotation++);
                                } else if ("addForeignKeyConstraint".equals(element.getNodeName())) {
                                    element.setAttribute("constraintName", foreignKey + foreignKeyRotation++);
                                } else if ("addUniqueConstraint".equals(element.getNodeName())) {
                                    element.setAttribute("constraintName", uniqueIndex + uniqueIndexRotation++);
                                } else if ("createIndex".equals(element.getNodeName())) {
                                    element.setAttribute("indexName", index + indexRotation++);
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
                                                        if ("constraints".equals(constraints.getNodeName())) {
                                                            if (constraints.hasAttribute("nullable")) {
                                                                constraints.setAttribute("notNullConstraintName", notNullKey + notNullKeyRotation++);
                                                            }
                                                            if (constraints.hasAttribute("primaryKey")) {
                                                                constraints.setAttribute("primaryKeyName", primaryKey + primaryKeyRotation++);
                                                            }
                                                            if (constraints.hasAttribute("unique")) {
                                                                constraints.setAttribute("uniqueConstraintName", uniqueIndex + uniqueIndexRotation++);
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
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(folder, file));
            transformer.transform(source, result);
        }
    }
}
