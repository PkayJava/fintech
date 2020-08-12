//package com.angkorteam.fintech.installation;
//
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//import org.w3c.dom.Comment;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//public class ERDData {
//
//    private static final int CHAR_WIDTH = 8;
//    private static final BigDecimal LINE_HEIGHT = new BigDecimal(13.96875d);
//    private static final BigDecimal TOP_PADDING = new BigDecimal(4d);
//
//    @Test
//    public void initERDData() throws Exception {
//
//        String output = System.getProperty("outputFile");
//        File inputFile = new File("src/main/resources/link.xml");
//
//        if (output == null || "".equals(output)) {
//            output = "erd.graphml";
//        } else {
//            if (!FilenameUtils.getExtension(output).equals("graphml")) {
//                output = output + "." + "graphml";
//            }
//        }
//        JAXBContext jaxb = JAXBContext.newInstance(Database.class, Table.class, Field.class);
//        Unmarshaller unmarshaller = jaxb.createUnmarshaller();
//
//        Database databaseInput = (Database) unmarshaller.unmarshal(inputFile);
//
//        // get erd info
//        File tablesDirectory = new File("src/main/resources/tables");
//        Database database = new Database();
//        for (File tableFile : tablesDirectory.listFiles()) {
//            if (FilenameUtils.getExtension(tableFile.getName()).equalsIgnoreCase("xml")) {
//                Database temp = (Database) unmarshaller.unmarshal(tableFile);
//                for (Table table : temp.getTables()) {
//                    database.getTables().add(table);
//                }
//            }
//        }
//
//        for (Table tmp : databaseInput.getTables()) {
//            for (Table tmp1 : database.getTables()) {
//                if (tmp.getName().equals(tmp1.getName())) {
//                    for (Field pp : tmp.getFields()) {
//                        for (Field pp1 : tmp1.getFields()) {
//                            if (pp.getName().equals(pp1.getName())) {
//                                pp1.setReferenceTable(pp.getReferenceTable());
//                                pp1.setReferenceField(pp.getReferenceField());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        List<String> errors = new ArrayList<>();
//        for (Table table : database.getTables()) {
//            for (Field field : table.getFields()) {
//                if (field.isLink()) {
//                    String referenceTable = field.getReferenceTable();
//                    String referenceField = field.getReferenceField();
//                    boolean found = false;
//                    for (Table tempTable : database.getTables()) {
//                        if (tempTable.getName().equals(referenceTable)) {
//                            for (Field tempField : tempTable.getFields()) {
//                                if (tempField.getName().equals(referenceField)) {
//                                    found = true;
//                                }
//                            }
//                        }
//                    }
//                    if (!found) {
//                        errors.add("table '" + table.getName() + "' field '" + field.getName() + "' is reference link broken");
//                    }
//                }
//            }
//        }
//
//        if (!errors.isEmpty()) {
//            for (String error : errors) {
//                System.err.println(error);
//            }
//            return;
//        }
//
//        Map<String, String> tableDictionary = new HashMap<>();
//
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//        Document document = dBuilder.newDocument();
//
//        Element graphmlElement = document.createElement("graphml");
//        graphmlElement.setAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
//        graphmlElement.setAttribute("xmlns:java", "http://www.yworks.com/xml/yfiles-common/1.0/java");
//        graphmlElement.setAttribute("xmlns:sys", "http://www.yworks.com/xml/yfiles-common/markup/primitives/2.0");
//        graphmlElement.setAttribute("xmlns:x", "http://www.yworks.com/xml/yfiles-common/markup/2.0");
//        graphmlElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        graphmlElement.setAttribute("xmlns:y", "http://www.yworks.com/xml/graphml");
//        graphmlElement.setAttribute("xmlns:yed", "http://www.yworks.com/xml/yed/3");
//        graphmlElement.setAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd");
//        document.appendChild(graphmlElement);
//        Comment comment = document.createComment("Created by yEd 3.17.1");
//        graphmlElement.appendChild(comment);
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("attr.name", "Description");
//            keyElement.setAttribute("attr.type", "string");
//            keyElement.setAttribute("for", "graph");
//            keyElement.setAttribute("id", "d0");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("yfiles.type", "portgraphics");
//            keyElement.setAttribute("for", "port");
//            keyElement.setAttribute("id", "d1");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("yfiles.type", "portgeometry");
//            keyElement.setAttribute("for", "port");
//            keyElement.setAttribute("id", "d2");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("yfiles.type", "portuserdata");
//            keyElement.setAttribute("for", "port");
//            keyElement.setAttribute("id", "d3");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("attr.type", "string");
//            keyElement.setAttribute("for", "node");
//            keyElement.setAttribute("id", "d4");
//            keyElement.setAttribute("attr.name", "url");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("attr.type", "string");
//            keyElement.setAttribute("for", "node");
//            keyElement.setAttribute("id", "d5");
//            keyElement.setAttribute("attr.name", "description");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("yfiles.type", "nodegraphics");
//            keyElement.setAttribute("for", "node");
//            keyElement.setAttribute("id", "d6");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("yfiles.type", "resources");
//            keyElement.setAttribute("for", "graphml");
//            keyElement.setAttribute("id", "d7");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("attr.type", "string");
//            keyElement.setAttribute("for", "edge");
//            keyElement.setAttribute("id", "d8");
//            keyElement.setAttribute("attr.name", "url");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("attr.type", "string");
//            keyElement.setAttribute("for", "edge");
//            keyElement.setAttribute("id", "d9");
//            keyElement.setAttribute("attr.name", "description");
//            graphmlElement.appendChild(keyElement);
//        }
//        {
//            Element keyElement = document.createElement("key");
//            keyElement.setAttribute("yfiles.type", "edgegraphics");
//            keyElement.setAttribute("for", "edge");
//            keyElement.setAttribute("id", "d10");
//            graphmlElement.appendChild(keyElement);
//        }
//
//        Element graphElement = document.createElement("graph");
//        graphElement.setAttribute("edgedefault", "directed");
//        graphElement.setAttribute("id", "G");
//        graphmlElement.appendChild(graphElement);
//
//        Element dataD0Element = document.createElement("data");
//        dataD0Element.setAttribute("key", "d0");
//        graphElement.appendChild(dataD0Element);
//
//        Map<String, Table> midTables = new HashMap<>();
//        String temps = StringUtils.lowerCase(System.getProperty("table"));
//        if (temps != null && !"".equals(temps)) {
//            String temp = (String) temps;
//            for (String filter : temp.split(",")) {
//                filter = StringUtils.trimToEmpty(filter);
//                if (!"".equals(filter)) {
//                    if (!midTables.containsKey(filter)) {
//                        midTables.put(filter, queryForTable(database, filter));
//                    }
//                }
//            }
//        } else {
//            for (Table table : database.getTables()) {
//                midTables.put(table.getName(), table);
//            }
//        }
//
//        Map<String, Table> leftTables = new HashMap<>();
//        for (Table table : database.getTables()) {
//            if (!midTables.containsKey(table.getName())) {
//                for (Field field : table.getFields()) {
//                    if (field.isLink() && midTables.containsKey(field.getReferenceTable())) {
//                        if (!leftTables.containsKey(table.getName())) {
//                            leftTables.put(table.getName(), table);
//                        }
//                    }
//                }
//            }
//        }
//
//        Map<String, Table> rightTables = new HashMap<>();
//        for (Table table : midTables.values()) {
//            for (Field field : table.getFields()) {
//                if (!midTables.containsKey(field.getReferenceTable())) {
//                    if (field.isLink() && !rightTables.containsKey(field.getReferenceTable())) {
//                        rightTables.put(field.getReferenceTable(), queryForTable(database, field.getReferenceTable()));
//                    }
//                }
//            }
//        }
//
//        Map<String, Long> identifiers = new HashMap<>();
//        Map<String, Table> all = new HashMap<>();
//        all.putAll(leftTables);
//        all.putAll(rightTables);
//        all.putAll(midTables);
//        Long shift = 1l;
//        for (String table : all.keySet()) {
//            identifiers.put(table, shift);
//            shift = shift << 1;
//        }
//
//        int index = 0;
//
//        for (Table table : all.values()) {
//            List<Map<String, Object>> fields = queryForFields(table);
//            int maxWidth = 0;
//            maxWidth = Math.max(maxWidth, table.getName().length() * CHAR_WIDTH);
//            List<String> lines = new LinkedList<>();
//            for (Map<String, Object> field : fields) {
//                String name = (String) field.get("field");
//                String type = (String) field.get("type");
//                String key = (String) field.get("key");
//                String commonType = parseType(name, type);
//                String prefix = "  -";
//                String suffix = "";
//                if ("PRI".equals(key)) {
//                    prefix = " *";
//                    suffix = "(PK)";
//                }
//                if ("UNI".equals(key)) {
//                    prefix = "  -";
//                    suffix = "(UQ)";
//                }
//                String line = prefix + " " + name + suffix + " :: " + commonType;
//                maxWidth = Math.max(maxWidth, (line.length() * CHAR_WIDTH) + 20);
//                lines.add(line);
//            }
//
//            BigDecimal height = LINE_HEIGHT.multiply(new BigDecimal(fields.size() + 1)).add(TOP_PADDING);
//            tableDictionary.put(table.getName(), "n" + index);
//            Element nodeElement = document.createElement("node");
//            nodeElement.setAttribute("id", "n" + index);
//            graphElement.appendChild(nodeElement);
//
//            Element dataD6Element = document.createElement("data");
//            dataD6Element.setAttribute("key", "d6");
//            nodeElement.appendChild(dataD6Element);
//
//            Element genericNodeElement = document.createElement("y:GenericNode");
//            genericNodeElement.setAttribute("configuration", "com.yworks.entityRelationship.big_entity");
//            dataD6Element.appendChild(genericNodeElement);
//
//            Element geometryElement = document.createElement("y:Geometry");
//            geometryElement.setAttribute("height", String.valueOf(height.add(new BigDecimal(26))));
//            geometryElement.setAttribute("width", String.valueOf(maxWidth));
//            genericNodeElement.appendChild(geometryElement);
//
//            Element fillElement = document.createElement("y:Fill");
//            fillElement.setAttribute("color", "#E8EEF7");
//            fillElement.setAttribute("color2", "#B7C9E3");
//            fillElement.setAttribute("transparent", "false");
//            genericNodeElement.appendChild(fillElement);
//
//            Element borderStyleElement = document.createElement("y:BorderStyle");
//            borderStyleElement.setAttribute("color", "#000000");
//            borderStyleElement.setAttribute("type", "line");
//            borderStyleElement.setAttribute("width", "1.0");
//            genericNodeElement.appendChild(borderStyleElement);
//
//            {
//                Element nodeLabelElement = document.createElement("y:NodeLabel");
//                nodeLabelElement.setAttribute("alignment", "center");
//                nodeLabelElement.setAttribute("autoSizePolicy", "content");
//                nodeLabelElement.setAttribute("backgroundColor", "#B7C9E3");
//                nodeLabelElement.setAttribute("configuration", "com.yworks.entityRelationship.label.name");
//                nodeLabelElement.setAttribute("fontFamily", "Dialog");
//                nodeLabelElement.setAttribute("fontSize", "12");
//                nodeLabelElement.setAttribute("fontStyle", "plain");
//                nodeLabelElement.setAttribute("hasLineColor", "false");
//                nodeLabelElement.setAttribute("horizontalTextPosition", "center");
//                nodeLabelElement.setAttribute("iconTextGap", "4");
//                nodeLabelElement.setAttribute("modelName", "internal");
//                nodeLabelElement.setAttribute("modelPosition", "t");
//                nodeLabelElement.setAttribute("textColor", "#000000");
//                nodeLabelElement.setAttribute("verticalTextPosition", "bottom");
//                nodeLabelElement.setAttribute("visible", "true");
//                nodeLabelElement.setTextContent(table.getName());
//                genericNodeElement.appendChild(nodeLabelElement);
//            }
//
//            {
//                Element nodeLabelElement = document.createElement("y:NodeLabel");
//                nodeLabelElement.setAttribute("alignment", "left");
//                nodeLabelElement.setAttribute("autoSizePolicy", "node_size");
//                nodeLabelElement.setAttribute("borderDistance", "3.0");
//                nodeLabelElement.setAttribute("configuration", "com.yworks.entityRelationship.label.attributes");
//                nodeLabelElement.setAttribute("fontFamily", "Dialog");
//                nodeLabelElement.setAttribute("fontSize", "12");
//                nodeLabelElement.setAttribute("fontStyle", "plain");
//                nodeLabelElement.setAttribute("hasBackgroundColor", "false");
//                nodeLabelElement.setAttribute("hasLineColor", "false");
//                nodeLabelElement.setAttribute("horizontalTextPosition", "center");
//                nodeLabelElement.setAttribute("iconTextGap", "4");
//                nodeLabelElement.setAttribute("modelName", "internal");
//                nodeLabelElement.setAttribute("modelPosition", "lt");
//                nodeLabelElement.setAttribute("textColor", "#000000");
//                nodeLabelElement.setAttribute("verticalTextPosition", "top");
//                nodeLabelElement.setAttribute("visible", "true");
//                StringBuffer xml = new StringBuffer();
//                xml.append("\n");
//                xml.append("\n");
//                for (int p = 0; p < lines.size(); p++) {
//                    String line = lines.get(p);
//                    if (p == lines.size() - 1) {
//                        xml.append(line);
//                    } else {
//                        xml.append(line).append("\n");
//                    }
//                }
//                nodeLabelElement.setTextContent(xml.toString());
//                genericNodeElement.appendChild(nodeLabelElement);
//            }
//
//            Element stylePropertiesElement = document.createElement("y:StyleProperties");
//            genericNodeElement.appendChild(stylePropertiesElement);
//
//            Element propertyElement = document.createElement("y:Property");
//            propertyElement.setAttribute("class", "java.lang.Boolean");
//            propertyElement.setAttribute("name", "y.view.ShadowNodePainter.SHADOW_PAINTING");
//            propertyElement.setAttribute("value", "true");
//            stylePropertiesElement.appendChild(propertyElement);
//            index++;
//        }
//
//        Map<Long, Link> links = new HashMap<>();
//        for (Table table : all.values()) {
//            long aId = identifiers.get(table.getName());
//            for (Field field : table.getFields()) {
//                if (all.containsKey(field.getReferenceTable())) {
//                    long bId = identifiers.get(field.getReferenceTable());
//                    long key = aId | bId;
//                    Link link = null;
//                    if (links.containsKey(key)) {
//                        link = links.get(key);
//                    } else {
//                        link = new Link();
//                        links.put(key, link);
//                    }
//                    link.setFromId(tableDictionary.get(table.getName()));
//                    link.setToId(tableDictionary.get(field.getReferenceTable()));
//                    link.getComments().add(table.getName() + "." + field.getName() + " = " + field.getReferenceTable() + "." + field.getReferenceField());
//                }
//            }
//        }
//
//        int edge = 0;
//        for (Link link : links.values()) {
//            String source = link.getFromId();
//            String target = link.getToId();
//
//            String linked = StringUtils.join(link.getComments(), "\n");
//            Element edgeElement = document.createElement("edge");
//            edgeElement.setAttribute("id", "e" + edge);
//            edgeElement.setAttribute("source", source);
//            edgeElement.setAttribute("target", target);
//            graphElement.appendChild(edgeElement);
//
//            Element dataD10Element = document.createElement("data");
//            dataD10Element.setAttribute("key", "d10");
//            edgeElement.appendChild(dataD10Element);
//
//            Element polyLineEdgeElement = document.createElement("y:PolyLineEdge");
//            dataD10Element.appendChild(polyLineEdgeElement);
//
//            Element pathElement = document.createElement("y:Path");
//            pathElement.setAttribute("sx", "0.0");
//            pathElement.setAttribute("sy", "0.0");
//            pathElement.setAttribute("tx", "0.0");
//            pathElement.setAttribute("ty", "0.0");
//            polyLineEdgeElement.appendChild(pathElement);
//
//            Element lineStyleElement = document.createElement("y:LineStyle");
//            lineStyleElement.setAttribute("color", "#000000");
//            lineStyleElement.setAttribute("type", "line");
//            lineStyleElement.setAttribute("width", "1.0");
//            polyLineEdgeElement.appendChild(lineStyleElement);
//
//            Element arrowsElement = document.createElement("y:Arrows");
//            arrowsElement.setAttribute("source", "none");
//            arrowsElement.setAttribute("target", "none");
//            polyLineEdgeElement.appendChild(arrowsElement);
//
//            Element edgeLabelElement = document.createElement("y:EdgeLabel");
//            edgeLabelElement.setAttribute("alignment", "center");
//            edgeLabelElement.setAttribute("configuration", "AutoFlippingLabel");
//            edgeLabelElement.setAttribute("distance", "2.0");
//            edgeLabelElement.setAttribute("fontFamily", "Dialog");
//            edgeLabelElement.setAttribute("fontSize", "12");
//            edgeLabelElement.setAttribute("fontStyle", "plain");
//            edgeLabelElement.setAttribute("hasBackgroundColor", "false");
//            edgeLabelElement.setAttribute("hasLineColor", "false");
//            edgeLabelElement.setAttribute("horizontalTextPosition", "center");
//            edgeLabelElement.setAttribute("iconTextGap", "4");
//            edgeLabelElement.setAttribute("modelName", "custom");
//            edgeLabelElement.setAttribute("preferredPlacement", "anywhere");
//            edgeLabelElement.setAttribute("ratio", "0.5");
//            edgeLabelElement.setAttribute("textColor", "#000000");
//            edgeLabelElement.setAttribute("verticalTextPosition", "bottom");
//            edgeLabelElement.setAttribute("visible", "true");
//            edgeLabelElement.setTextContent(linked);
//            polyLineEdgeElement.appendChild(edgeLabelElement);
//
//            Element labelModelElement = document.createElement("y:LabelModel");
//            edgeLabelElement.appendChild(labelModelElement);
//
//            Element rotatedDiscreteEdgeLabelModelElement = document.createElement("y:RotatedDiscreteEdgeLabelModel");
//            rotatedDiscreteEdgeLabelModelElement.setAttribute("angle", "0.0");
//            rotatedDiscreteEdgeLabelModelElement.setAttribute("autoRotationEnabled", "true");
//            rotatedDiscreteEdgeLabelModelElement.setAttribute("candidateMask", "18");
//            rotatedDiscreteEdgeLabelModelElement.setAttribute("distance", "2.0");
//            rotatedDiscreteEdgeLabelModelElement.setAttribute("positionRelativeToSegment", "false");
//            labelModelElement.appendChild(rotatedDiscreteEdgeLabelModelElement);
//
//            Element modelParameterElement = document.createElement("y:ModelParameter");
//            edgeLabelElement.appendChild(modelParameterElement);
//
//            Element rotatedDiscreteEdgeLabelModelParameterElement = document.createElement("y:RotatedDiscreteEdgeLabelModelParameter");
//            rotatedDiscreteEdgeLabelModelParameterElement.setAttribute("position", "head");
//            modelParameterElement.appendChild(rotatedDiscreteEdgeLabelModelParameterElement);
//
//            Element preferredPlacementDescriptorElement = document.createElement("y:PreferredPlacementDescriptor");
//            preferredPlacementDescriptorElement.setAttribute("angle", "0.0");
//            preferredPlacementDescriptorElement.setAttribute("angleOffsetOnRightSide", "0");
//            preferredPlacementDescriptorElement.setAttribute("angleReference", "absolute");
//            preferredPlacementDescriptorElement.setAttribute("angleRotationOnRightSide", "co");
//            preferredPlacementDescriptorElement.setAttribute("distance", "-1.0");
//            preferredPlacementDescriptorElement.setAttribute("frozen", "true");
//            preferredPlacementDescriptorElement.setAttribute("placement", "anywhere");
//            preferredPlacementDescriptorElement.setAttribute("side", "anywhere");
//            preferredPlacementDescriptorElement.setAttribute("sideReference", "relative_to_edge_flow");
//            edgeLabelElement.appendChild(preferredPlacementDescriptorElement);
//
//            Element bendStyleElement = document.createElement("y:BendStyle");
//            bendStyleElement.setAttribute("smoothed", "true");
//            polyLineEdgeElement.appendChild(bendStyleElement);
//
//            edge++;
//        }
//
//        Element dataD7Element = document.createElement("data");
//        dataD7Element.setAttribute("key", "d7");
//        graphmlElement.appendChild(dataD7Element);
//
//        Element resourcesElement = document.createElement("y:Resources");
//        dataD7Element.appendChild(resourcesElement);
//
//        // write the content into xml file
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//        DOMSource source = new DOMSource(document);
//        File outputFile = new File("target", output);
//        StreamResult result = new StreamResult(outputFile);
//        transformer.transform(source, result);
//        System.out.println("[INFO] ERD is generated to " + outputFile.getAbsolutePath());
//    }
//
//    public static String parseType(String name, String type) {
//        String commonType = "";
//        if ("tinyint(1)".equals(type) || "bit(1)".equals(type)) {
//            commonType = "boolean";
//        } else if ("int(11) unsigned".equals(type) || "smallint(4)".equals(type) || "int(4)".equals(type) || "int(2)".equals(type) || "int(3)".equals(type) || "int(15)".equals(type) || "tinyint(3)".equals(type) || "smallint(3)".equals(type) || "bigint(10)".equals(type) || "smallint(11)".equals(type) || "tinyint(2)".equals(type) || "int(20)".equals(type) || "smallint(5)".equals(type) || "smallint(2)".equals(type) || "int(10)".equals(type) || "bigint(20) unsigned".equals(type) || "tinyint(4)".equals(type) || "int(11)".equals(type) || "bigint(20)".equals(type) || "int(5)".equals(type)) {
//            commonType = "number";
//        } else if ("smallint(1)".equals(type) || "int(1)".equals(type)) {
//            if (name.contains("allow") || name.startsWith("is_") || name.startsWith("can_")) {
//                commonType = "boolean";
//            } else {
//                commonType = "number";
//            }
//        } else if ("timestamp".equals(type) || "datetime".equals(type)) {
//            commonType = "datetime";
//        } else if ("decimal(10,2)".equals(type) || "decimal(20,2)".equals(type) || "decimal(19,5)".equals(type) || "decimal(5,2)".equals(type) || "decimal(20,6)".equals(type) || "decimal(10,8) unsigned".equals(type) || "decimal(19,6)".equals(type)) {
//            commonType = "decimal";
//        } else if ("date".equals(type)) {
//            commonType = "date";
//        } else if ("time".equals(type)) {
//            commonType = "time";
//        } else if ("blob".equals(type) || "longtext".equals(type)) {
//            commonType = "binary";
//        } else if ("varchar(1)".equals(type) || "varchar(4096)".equals(type) || "varchar(128)".equals(type) || "varchar(256)".equals(type) || "varchar(11)".equals(type) || "varchar(2)".equals(type) || "varchar(4000)".equals(type) || "varchar(32)".equals(type) || "varchar(102)".equals(type) || "varchar(4)".equals(type) || "varchar(70)".equals(type) || "varchar(2000)".equals(type) || "varchar(25)".equals(type) || "smallint(6)".equals(type) || "varchar(150)".equals(type) || "varchar(300)".equals(type) || "varchar(3)".equals(type) || "varchar(250)".equals(type) || "varchar(200)".equals(type) || "text".equals(type) || "varchar(1000)".equals(type) || "varchar(10)".equals(type) || "varchar(45)".equals(type) || "varchar(50)".equals(type) || "varchar(20)".equals(type) || "varchar(255)".equals(type) || "varchar(100)".equals(type) || "varchar(500)".equals(type)) {
//            commonType = "text";
//        } else {
//            commonType = type;
//        }
//        return commonType;
//    }
//
//    public static List<Map<String, Object>> queryForFields(Table table) throws IOException {
//        List<Map<String, Object>> fields = new ArrayList<>();
//        for (Field f : table.getFields()) {
//            Map<String, Object> field = new HashMap<>();
//            field.put("field", f.getName());
//            field.put("type", f.getType());
//            field.put("key", f.getKey());
//            fields.add(field);
//        }
//        return fields;
//    }
//
//    public static Table queryForTable(Database database, String name) throws IOException {
//        for (Table table : database.getTables()) {
//            if (table.getName().equals(name)) {
//                return table;
//            }
//        }
//        return null;
//    }
//
//}
