package com.angkorteam.fintech.installation;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.angkorteam.fintech.Constants;
import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ERDData {

    private static final int CHAR_WIDTH = 8;
    private static final BigDecimal LINE_HEIGHT = new BigDecimal(13.96875d);
    private static final BigDecimal TOP_PADDING = new BigDecimal(4d);

    @Test
    public void initERDData() throws Exception {

        File fintechFile = new File(FileUtils.getUserDirectory(), ".xml/fintech.properties.xml");

        Properties properties = new Properties();
        try (FileInputStream inputStream = FileUtils.openInputStream(fintechFile)) {
            properties.loadFromXML(inputStream);
        }
        BasicDataSource platformDataSource = new BasicDataSource();
        platformDataSource.setUsername(properties.getProperty("app.jdbc.username"));
        platformDataSource.setPassword(properties.getProperty("app.jdbc.password"));
        platformDataSource.setUrl(properties.getProperty("app.jdbc.url"));
        platformDataSource.setDriverClassName(properties.getProperty("app.jdbc.driver"));

        String mifosUrl = properties.getProperty("mifos.url");

        MifosDataSourceManager dataSourceManager = new MifosDataSourceManager();
        dataSourceManager.setDelegate(platformDataSource);
        dataSourceManager.setMifosUrl(mifosUrl);
        dataSourceManager.afterPropertiesSet();

        DataSource dataSource = dataSourceManager.getDataSource(Constants.AID);

        Map<String, String> tableDictionary = new HashMap<>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> tables = queryForTables(jdbcTemplate);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.newDocument();

        Element graphmlElement = document.createElement("graphml");
        graphmlElement.setAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
        graphmlElement.setAttribute("xmlns:java", "http://www.yworks.com/xml/yfiles-common/1.0/java");
        graphmlElement.setAttribute("xmlns:sys", "http://www.yworks.com/xml/yfiles-common/markup/primitives/2.0");
        graphmlElement.setAttribute("xmlns:x", "http://www.yworks.com/xml/yfiles-common/markup/2.0");
        graphmlElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        graphmlElement.setAttribute("xmlns:y", "http://www.yworks.com/xml/graphml");
        graphmlElement.setAttribute("xmlns:yed", "http://www.yworks.com/xml/yed/3");
        graphmlElement.setAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd");
        document.appendChild(graphmlElement);
        Comment comment = document.createComment("Created by yEd 3.17.1");
        graphmlElement.appendChild(comment);
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("attr.name", "Description");
            keyElement.setAttribute("attr.type", "string");
            keyElement.setAttribute("for", "graph");
            keyElement.setAttribute("id", "d0");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("yfiles.type", "portgraphics");
            keyElement.setAttribute("for", "port");
            keyElement.setAttribute("id", "d1");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("yfiles.type", "portgeometry");
            keyElement.setAttribute("for", "port");
            keyElement.setAttribute("id", "d2");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("yfiles.type", "portuserdata");
            keyElement.setAttribute("for", "port");
            keyElement.setAttribute("id", "d3");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("attr.type", "string");
            keyElement.setAttribute("for", "node");
            keyElement.setAttribute("id", "d4");
            keyElement.setAttribute("attr.name", "url");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("attr.type", "string");
            keyElement.setAttribute("for", "node");
            keyElement.setAttribute("id", "d5");
            keyElement.setAttribute("attr.name", "description");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("yfiles.type", "nodegraphics");
            keyElement.setAttribute("for", "node");
            keyElement.setAttribute("id", "d6");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("yfiles.type", "resources");
            keyElement.setAttribute("for", "graphml");
            keyElement.setAttribute("id", "d7");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("attr.type", "string");
            keyElement.setAttribute("for", "edge");
            keyElement.setAttribute("id", "d8");
            keyElement.setAttribute("attr.name", "url");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("attr.type", "string");
            keyElement.setAttribute("for", "edge");
            keyElement.setAttribute("id", "d9");
            keyElement.setAttribute("attr.name", "description");
            graphmlElement.appendChild(keyElement);
        }
        {
            Element keyElement = document.createElement("key");
            keyElement.setAttribute("yfiles.type", "edgegraphics");
            keyElement.setAttribute("for", "edge");
            keyElement.setAttribute("id", "d10");
            graphmlElement.appendChild(keyElement);
        }

        Element graphElement = document.createElement("graph");
        graphElement.setAttribute("edgedefault", "directed");
        graphElement.setAttribute("id", "G");
        graphmlElement.appendChild(graphElement);

        Element dataD0Element = document.createElement("data");
        dataD0Element.setAttribute("key", "d0");
        graphElement.appendChild(dataD0Element);

        List<String> process = new ArrayList<>();
        List<String> filters = new ArrayList<>();
        boolean hasFilter = false;
        String temps = System.getProperty("tables");
        temps = "m_office";
        if (temps != null && !"".equals(temps)) {
            hasFilter = true;
            String temp = (String) temps;
            for (String filter : temp.split(",")) {
                filter = StringUtils.trimToEmpty(filter);
                if (!"".equals(filter)) {
                    if (!filters.contains(filter)) {
                        filters.add(filter);
                    }
                }
            }
        }

        String json = FileUtils.readFileToString(new File("src/main/resources/erd.json"), "UTF-8");
        Gson gson = new Gson();

        Map<String, List<ErdVO>> erds = gson.fromJson(json, new TypeToken<Map<String, List<ErdVO>>>() {
        }.getType());

        if (hasFilter) {
            for (Entry<String, List<ErdVO>> erd : erds.entrySet()) {
                if (filters.contains(erd.getKey())) {
                    if (!process.contains(erd.getKey())) {
                        process.add(erd.getKey());
                    }
                    for (ErdVO vo : erd.getValue()) {
                        if (!process.contains(vo.getReferenceTo().getTableName())) {
                            process.add(vo.getReferenceTo().getTableName());
                        }
                    }
                } else {
                    for (ErdVO vo : erd.getValue()) {
                        if (filters.contains(vo.getReferenceTo().getTableName())) {
                            if (!process.contains(erd.getKey())) {
                                process.add(erd.getKey());
                            }
                        }
                    }
                }
            }
            for (String f : filters) {
                if (!process.contains(f)) {
                    process.add(f);
                }
            }
        } else {
            for (Entry<String, List<ErdVO>> erd : erds.entrySet()) {
                if (!process.contains(erd.getKey())) {
                    process.add(erd.getKey());
                }
                for (ErdVO vo : erd.getValue()) {
                    if (!process.contains(vo.getReferenceTo().getTableName())) {
                        process.add(vo.getReferenceTo().getTableName());
                    }
                }
            }
        }

        int index = 0;

        for (String table : tables) {
            if (hasFilter && !process.contains(table)) {
                continue;
            }
            List<Map<String, Object>> fields = queryForFields(jdbcTemplate, table);
            int maxWidth = 0;
            maxWidth = Math.max(maxWidth, table.length() * CHAR_WIDTH);
            List<String> lines = new LinkedList<>();
            for (Map<String, Object> field : fields) {
                String name = (String) field.get("field");
                String type = (String) field.get("type");
                String key = (String) field.get("key");
                String commonType = parseType(name, type);
                String prefix = "  -";
                String suffix = "";
                if ("PRI".equals(key)) {
                    prefix = " *";
                    suffix = "(PK)";
                }
                if ("UNI".equals(key)) {
                    prefix = "  -";
                    suffix = "(UQ)";
                }
                String line = prefix + " " + name + suffix + " :: " + commonType;
                maxWidth = Math.max(maxWidth, (line.length() * CHAR_WIDTH) + 20);
                lines.add(line);
            }

            BigDecimal height = LINE_HEIGHT.multiply(new BigDecimal(fields.size() + 1)).add(TOP_PADDING);
            tableDictionary.put(table, "n" + index);
            Element nodeElement = document.createElement("node");
            nodeElement.setAttribute("id", "n" + index);
            graphElement.appendChild(nodeElement);

            Element dataD6Element = document.createElement("data");
            dataD6Element.setAttribute("key", "d6");
            nodeElement.appendChild(dataD6Element);

            Element genericNodeElement = document.createElement("y:GenericNode");
            genericNodeElement.setAttribute("configuration", "com.yworks.entityRelationship.big_entity");
            dataD6Element.appendChild(genericNodeElement);

            Element geometryElement = document.createElement("y:Geometry");
            geometryElement.setAttribute("height", String.valueOf(height.add(new BigDecimal(26))));
            geometryElement.setAttribute("width", String.valueOf(maxWidth));
            genericNodeElement.appendChild(geometryElement);

            Element fillElement = document.createElement("y:Fill");
            fillElement.setAttribute("color", "#E8EEF7");
            fillElement.setAttribute("color2", "#B7C9E3");
            fillElement.setAttribute("transparent", "false");
            genericNodeElement.appendChild(fillElement);

            Element borderStyleElement = document.createElement("y:BorderStyle");
            borderStyleElement.setAttribute("color", "#000000");
            borderStyleElement.setAttribute("type", "line");
            borderStyleElement.setAttribute("width", "1.0");
            genericNodeElement.appendChild(borderStyleElement);

            {
                Element nodeLabelElement = document.createElement("y:NodeLabel");
                nodeLabelElement.setAttribute("alignment", "center");
                nodeLabelElement.setAttribute("autoSizePolicy", "content");
                nodeLabelElement.setAttribute("backgroundColor", "#B7C9E3");
                nodeLabelElement.setAttribute("configuration", "com.yworks.entityRelationship.label.name");
                nodeLabelElement.setAttribute("fontFamily", "Dialog");
                nodeLabelElement.setAttribute("fontSize", "12");
                nodeLabelElement.setAttribute("fontStyle", "plain");
                nodeLabelElement.setAttribute("hasLineColor", "false");
                nodeLabelElement.setAttribute("horizontalTextPosition", "center");
                nodeLabelElement.setAttribute("iconTextGap", "4");
                nodeLabelElement.setAttribute("modelName", "internal");
                nodeLabelElement.setAttribute("modelPosition", "t");
                nodeLabelElement.setAttribute("textColor", "#000000");
                nodeLabelElement.setAttribute("verticalTextPosition", "bottom");
                nodeLabelElement.setAttribute("visible", "true");
                nodeLabelElement.setTextContent(table);
                genericNodeElement.appendChild(nodeLabelElement);
            }

            {
                Element nodeLabelElement = document.createElement("y:NodeLabel");
                nodeLabelElement.setAttribute("alignment", "left");
                nodeLabelElement.setAttribute("autoSizePolicy", "node_size");
                nodeLabelElement.setAttribute("borderDistance", "3.0");
                nodeLabelElement.setAttribute("configuration", "com.yworks.entityRelationship.label.attributes");
                nodeLabelElement.setAttribute("fontFamily", "Dialog");
                nodeLabelElement.setAttribute("fontSize", "12");
                nodeLabelElement.setAttribute("fontStyle", "plain");
                nodeLabelElement.setAttribute("hasBackgroundColor", "false");
                nodeLabelElement.setAttribute("hasLineColor", "false");
                nodeLabelElement.setAttribute("horizontalTextPosition", "center");
                nodeLabelElement.setAttribute("iconTextGap", "4");
                nodeLabelElement.setAttribute("modelName", "internal");
                nodeLabelElement.setAttribute("modelPosition", "lt");
                nodeLabelElement.setAttribute("textColor", "#000000");
                nodeLabelElement.setAttribute("verticalTextPosition", "top");
                nodeLabelElement.setAttribute("visible", "true");
                StringBuffer xml = new StringBuffer();
                xml.append("\n");
                xml.append("\n");
                for (int p = 0; p < lines.size(); p++) {
                    String line = lines.get(p);
                    if (p == lines.size() - 1) {
                        xml.append(line);
                    } else {
                        xml.append(line).append("\n");
                    }
                }
                nodeLabelElement.setTextContent(xml.toString());
                genericNodeElement.appendChild(nodeLabelElement);
            }

            Element stylePropertiesElement = document.createElement("y:StyleProperties");
            genericNodeElement.appendChild(stylePropertiesElement);

            Element propertyElement = document.createElement("y:Property");
            propertyElement.setAttribute("class", "java.lang.Boolean");
            propertyElement.setAttribute("name", "y.view.ShadowNodePainter.SHADOW_PAINTING");
            propertyElement.setAttribute("value", "true");
            stylePropertiesElement.appendChild(propertyElement);
            index++;
        }

        int edge = 0;
        for (Entry<String, List<ErdVO>> erd : erds.entrySet()) {
            List<ErdVO> values = erd.getValue();
            String sourceTable = erd.getKey();
            for (ErdVO value : values) {
                String sourceField = "";
                if (value.getFieldName().size() == 1) {
                    sourceField = value.getFieldName().get(0);
                } else {
                    sourceField = "(" + StringUtils.join(value.getFieldName(), "/") + ")";
                }
                String source = tableDictionary.get(sourceTable);

                String targetTable = value.getReferenceTo().getTableName();
                String targetField = value.getReferenceTo().getFieldName();
                String target = tableDictionary.get(targetTable);

                if (hasFilter) {
                    if (target == null || source == null) {
                        continue;
                    }
                } else {
                    if (target == null) {
                        throw new RuntimeException("could not find " + targetTable + " table in data dictionary");
                    }
                    if (source == null) {
                        throw new RuntimeException("could not find " + sourceTable + " table in data dictionary");

                    }
                }
                String linked = sourceTable + "." + sourceField + " <=> " + targetTable + "." + targetField;
                Element edgeElement = document.createElement("edge");
                edgeElement.setAttribute("id", "e" + edge);
                edgeElement.setAttribute("source", source);
                edgeElement.setAttribute("target", target);
                graphElement.appendChild(edgeElement);

                Element dataD10Element = document.createElement("data");
                dataD10Element.setAttribute("key", "d10");
                edgeElement.appendChild(dataD10Element);

                Element polyLineEdgeElement = document.createElement("y:PolyLineEdge");
                dataD10Element.appendChild(polyLineEdgeElement);

                Element pathElement = document.createElement("y:Path");
                pathElement.setAttribute("sx", "0.0");
                pathElement.setAttribute("sy", "0.0");
                pathElement.setAttribute("tx", "0.0");
                pathElement.setAttribute("ty", "0.0");
                polyLineEdgeElement.appendChild(pathElement);

                Element lineStyleElement = document.createElement("y:LineStyle");
                lineStyleElement.setAttribute("color", "#000000");
                lineStyleElement.setAttribute("type", "line");
                lineStyleElement.setAttribute("width", "1.0");
                polyLineEdgeElement.appendChild(lineStyleElement);

                Element arrowsElement = document.createElement("y:Arrows");
                arrowsElement.setAttribute("source", "none");
                arrowsElement.setAttribute("target", "none");
                polyLineEdgeElement.appendChild(arrowsElement);

                Element edgeLabelElement = document.createElement("y:EdgeLabel");
                edgeLabelElement.setAttribute("alignment", "center");
                edgeLabelElement.setAttribute("configuration", "AutoFlippingLabel");
                edgeLabelElement.setAttribute("distance", "2.0");
                edgeLabelElement.setAttribute("fontFamily", "Dialog");
                edgeLabelElement.setAttribute("fontSize", "12");
                edgeLabelElement.setAttribute("fontStyle", "plain");
                edgeLabelElement.setAttribute("hasBackgroundColor", "false");
                edgeLabelElement.setAttribute("hasLineColor", "false");
                edgeLabelElement.setAttribute("horizontalTextPosition", "center");
                edgeLabelElement.setAttribute("iconTextGap", "4");
                edgeLabelElement.setAttribute("modelName", "custom");
                edgeLabelElement.setAttribute("preferredPlacement", "anywhere");
                edgeLabelElement.setAttribute("ratio", "0.5");
                edgeLabelElement.setAttribute("textColor", "#000000");
                edgeLabelElement.setAttribute("verticalTextPosition", "bottom");
                edgeLabelElement.setAttribute("visible", "true");
                edgeLabelElement.setTextContent(linked);
                polyLineEdgeElement.appendChild(edgeLabelElement);

                Element labelModelElement = document.createElement("y:LabelModel");
                edgeLabelElement.appendChild(labelModelElement);

                Element rotatedDiscreteEdgeLabelModelElement = document.createElement("y:RotatedDiscreteEdgeLabelModel");
                rotatedDiscreteEdgeLabelModelElement.setAttribute("angle", "0.0");
                rotatedDiscreteEdgeLabelModelElement.setAttribute("autoRotationEnabled", "true");
                rotatedDiscreteEdgeLabelModelElement.setAttribute("candidateMask", "18");
                rotatedDiscreteEdgeLabelModelElement.setAttribute("distance", "2.0");
                rotatedDiscreteEdgeLabelModelElement.setAttribute("positionRelativeToSegment", "false");
                labelModelElement.appendChild(rotatedDiscreteEdgeLabelModelElement);

                Element modelParameterElement = document.createElement("y:ModelParameter");
                edgeLabelElement.appendChild(modelParameterElement);

                Element rotatedDiscreteEdgeLabelModelParameterElement = document.createElement("y:RotatedDiscreteEdgeLabelModelParameter");
                rotatedDiscreteEdgeLabelModelParameterElement.setAttribute("position", "head");
                modelParameterElement.appendChild(rotatedDiscreteEdgeLabelModelParameterElement);

                Element preferredPlacementDescriptorElement = document.createElement("y:PreferredPlacementDescriptor");
                preferredPlacementDescriptorElement.setAttribute("angle", "0.0");
                preferredPlacementDescriptorElement.setAttribute("angleOffsetOnRightSide", "0");
                preferredPlacementDescriptorElement.setAttribute("angleReference", "absolute");
                preferredPlacementDescriptorElement.setAttribute("angleRotationOnRightSide", "co");
                preferredPlacementDescriptorElement.setAttribute("distance", "-1.0");
                preferredPlacementDescriptorElement.setAttribute("frozen", "true");
                preferredPlacementDescriptorElement.setAttribute("placement", "anywhere");
                preferredPlacementDescriptorElement.setAttribute("side", "anywhere");
                preferredPlacementDescriptorElement.setAttribute("sideReference", "relative_to_edge_flow");
                edgeLabelElement.appendChild(preferredPlacementDescriptorElement);

                Element bendStyleElement = document.createElement("y:BendStyle");
                bendStyleElement.setAttribute("smoothed", "true");
                polyLineEdgeElement.appendChild(bendStyleElement);

                edge++;
            }
        }

        Element dataD7Element = document.createElement("data");
        dataD7Element.setAttribute("key", "d7");
        graphmlElement.appendChild(dataD7Element);

        Element resourcesElement = document.createElement("y:Resources");
        dataD7Element.appendChild(resourcesElement);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(document);
        File outputFile = new File("target/erd.graphml");
        StreamResult result = new StreamResult(outputFile);
        transformer.transform(source, result);
        System.out.println("[INFO] ERD is generated to " + outputFile.getAbsolutePath());
    }

    public String parseType(String name, String type) {
        String commonType = "";
        if ("tinyint(1)".equals(type) || "bit(1)".equals(type)) {
            commonType = "boolean";
        } else if ("int(11) unsigned".equals(type) || "smallint(4)".equals(type) || "int(4)".equals(type) || "int(2)".equals(type) || "int(3)".equals(type) || "int(15)".equals(type) || "tinyint(3)".equals(type) || "smallint(3)".equals(type) || "bigint(10)".equals(type) || "smallint(11)".equals(type) || "tinyint(2)".equals(type) || "int(20)".equals(type) || "smallint(5)".equals(type) || "smallint(2)".equals(type) || "int(10)".equals(type) || "bigint(20) unsigned".equals(type) || "tinyint(4)".equals(type) || "int(11)".equals(type) || "bigint(20)".equals(type) || "int(5)".equals(type)) {
            commonType = "number";
        } else if ("smallint(1)".equals(type) || "int(1)".equals(type)) {
            if (name.contains("allow") || name.startsWith("is_") || name.startsWith("can_")) {
                commonType = "boolean";
            } else {
                commonType = "number";
            }
        } else if ("timestamp".equals(type) || "datetime".equals(type)) {
            commonType = "datetime";
        } else if ("decimal(10,2)".equals(type) || "decimal(20,2)".equals(type) || "decimal(19,5)".equals(type) || "decimal(5,2)".equals(type) || "decimal(20,6)".equals(type) || "decimal(10,8) unsigned".equals(type) || "decimal(19,6)".equals(type)) {
            commonType = "decimal";
        } else if ("date".equals(type)) {
            commonType = "date";
        } else if ("time".equals(type)) {
            commonType = "time";
        } else if ("blob".equals(type) || "longtext".equals(type)) {
            commonType = "binary";
        } else if ("varchar(1)".equals(type) || "varchar(4096)".equals(type) || "varchar(128)".equals(type) || "varchar(256)".equals(type) || "varchar(11)".equals(type) || "varchar(2)".equals(type) || "varchar(4000)".equals(type) || "varchar(32)".equals(type) || "varchar(102)".equals(type) || "varchar(4)".equals(type) || "varchar(70)".equals(type) || "varchar(2000)".equals(type) || "varchar(25)".equals(type) || "smallint(6)".equals(type) || "varchar(150)".equals(type) || "varchar(300)".equals(type) || "varchar(3)".equals(type) || "varchar(250)".equals(type) || "varchar(200)".equals(type) || "text".equals(type) || "varchar(1000)".equals(type) || "varchar(10)".equals(type) || "varchar(45)".equals(type) || "varchar(50)".equals(type) || "varchar(20)".equals(type) || "varchar(255)".equals(type) || "varchar(100)".equals(type) || "varchar(500)".equals(type)) {
            commonType = "text";
        } else {
            commonType = type;
        }
        return commonType;
    }

    public List<Map<String, Object>> queryForFields(JdbcTemplate jdbcTemplate, String table) {
        return jdbcTemplate.queryForList("DESC `" + table + "`");
    }

    public List<String> queryForTables(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList("show tables", String.class);
    }

}
