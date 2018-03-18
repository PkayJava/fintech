package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class CodeHelper {

    public static final String CODE_ID_ATTRIBUTE_NAME = "id";
    public static final String RESPONSE_ID_ATTRIBUTE_NAME = "resourceId";
    public static final String SUBRESPONSE_ID_ATTRIBUTE_NAME = "subResourceId";
    public static final String CODE_NAME_ATTRIBUTE_NAME = "name";
    public static final String CODE_SYSTEM_DEFINED_ATTRIBUTE_NAME = "systemDefined";

    public static final String CODE_VALUE_ID_ATTRIBUTE_NAME = "id";
    public static final String CODE_VALUE_NAME_ATTRIBUTE_NAME = "name";
    public static final String CODE_VALUE_DESCRIPTION_ATTRIBUTE_NAME = "description";
    public static final String CODE_VALUE_POSITION_ATTRIBUTE_NAME = "position";

    public static JsonNode create(IMifos session, String name) {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("name", name);
        return Helper.performServerPost(session, "/api/v1/codes", node);
    }

    public static JsonNode createValue(IMifos session, JsonNode object) {
        String codeId = (String) object.getObject().remove("codeId");
        return Helper.performServerPost(session, "/api/v1/codes" + "/" + codeId + "/codevalues", object);
    }

    public static JsonNode updateValue(IMifos session, JsonNode object) {
        String codeId = (String) object.getObject().remove("codeId");
        String id = (String) object.getObject().remove("id");
        return Helper.performServerPut(session, "/api/v1/codes" + "/" + codeId + "/codevalues/" + id, object);
    }

}
