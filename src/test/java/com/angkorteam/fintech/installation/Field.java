package com.angkorteam.fintech.installation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "referenceTable")
    private String referenceTable;

    @XmlAttribute(name = "referenceField")
    private String referenceField;

    @XmlAttribute(name = "key")
    private String key;

    @XmlAttribute(name = "type")
    private String type;

    @XmlElement(name = "comment")
    private List<String> comments;

    public boolean isLink() {
        return this.referenceTable != null && !"".equals(this.referenceTable) && this.referenceField != null && !"".equals(this.referenceField);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReferenceField() {
        return referenceField;
    }

    public void setReferenceField(String referenceField) {
        this.referenceField = referenceField;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

}
