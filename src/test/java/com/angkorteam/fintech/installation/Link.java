package com.angkorteam.fintech.installation;

import java.util.ArrayList;
import java.util.List;

public class Link {

    private List<String> comments = new ArrayList<>();

    private String fromId;

    private String toId;

    public List<String> getComments() {
	return comments;
    }

    public void setComments(List<String> comments) {
	this.comments = comments;
    }

    public String getFromId() {
	return fromId;
    }

    public void setFromId(String fromId) {
	this.fromId = fromId;
    }

    public String getToId() {
	return toId;
    }

    public void setToId(String toId) {
	this.toId = toId;
    }

}
