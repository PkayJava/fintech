package com.angkorteam.bank.dao.base;

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
import java.util.ArrayList;
import java.util.List;

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

        List<String> files = new ArrayList<>();
        files.add("V1__mifosplatform-core-ddl-latest-002.xml");
        files.add("V5__update-savings-product-and-account-tables.xml");
        files.add("V6__add_min_max_principal_column_to_loan.xml");
        files.add("V9__add_min_max_constraint_column_to_loan_loanproduct.xml");
        files.add("V10__interest-posting-fields-for-savings-002.xml");
        files.add("V11__add-payment-details.xml");
        files.add("V12__add_external_id_to_couple_of_tables.xml");
        files.add("V14__rename_status_id_to_enum-001.xml");
        files.add("V16__drop_min_max_column_on_loan_table.xml");
        files.add("V17__update_stretchy_reporting_ddl-001.xml");
        files.add("V17__update_stretchy_reporting_ddl-003.xml");
        files.add("V17__update_stretchy_reporting_ddl-005.xml");
        files.add("V17__update_stretchy_reporting_ddl-007.xml");
        files.add("V21__activation-permissions-for-clients-002.xml");
        files.add("V22__alter-group-for-consistency-add-permissions-002.xml");
        files.add("V23__remove-enable-disable-configuration-for-client-group-status-002.xml");
        files.add("V24__add-group-client-foreign-key-constraint-in-loan-table.xml");
        files.add("V26__add-support-for-withdrawal-fees-on-savings.xml");
        files.add("V27__add-loan-type-column-to-loan-table.xml");
        files.add("V28__accounting-abstractions-and-autoposting.xml");
        files.add("V30__add-referenceNumber-to-acc_gl_journal_entry.xml");
        files.add("V31__drop-autopostings.xml");
        files.add("V33__drop_unique_check_on_stretchy_report_parameter.xml");
        files.add("V34__add_unique_check_on_stretchy_report_parameter.xml");
        files.add("V35__add_hierarchy_column_for_acc_gl_account.xml");
        files.add("V36__add_tag_id_column_for_acc_gl_account-001.xml");
        files.add("V39__payment-channels-updates-002.xml");
        files.add("V39__payment-channels-updates-004.xml");
        files.add("V42__Add_default_value_for_id_for_acc_accounting_rule.xml");
        files.add("V43__accounting-for-savings-001.xml");
        files.add("V43__accounting-for-savings-003.xml");
        files.add("V44__document-increase-size-of-column-type.xml");
        files.add("V45__create_acc_rule_tags_table.xml");
        files.add("V47__staff-hierarchy-link-to-users.xml");
        files.add("V48__adding-S3-Support-002.xml");
        files.add("V48__adding-S3-Support-004.xml");
        files.add("V48__adding-S3-Support-005.xml");
        files.add("V49__track-loan-charge-payment-transactions.xml");
        files.add("V50__add-grace-settings-to-loan-product.xml");
        files.add("V51__track-additional-details-related-to-installment-performance.xml");
        files.add("V52__add_boolean_support_cols_to_acc_accounting_rule.xml");
        files.add("V53__track-advance-and-late-payments-on-installment.xml");
        files.add("V54__charge-to-income-account-mappings.xml");

        for (String file : files) {
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
