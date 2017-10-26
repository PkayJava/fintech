package com.angkorteam.fintech.installation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Table {

    @XmlElement(name = "comment")
    private List<String> comments;

    @XmlElement(name = "field")
    private List<Field> fields;

    @XmlAttribute(name = "name")
    private String name;

    public List<Field> getFields() {
	return fields;
    }

    public void setFields(List<Field> fields) {
	this.fields = fields;
    }

    public List<String> getComments() {
	return comments;
    }

    public void setComments(List<String> comments) {
	this.comments = comments;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
