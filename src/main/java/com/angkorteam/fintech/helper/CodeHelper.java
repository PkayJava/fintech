package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class CodeHelper {

    public static final String CODE_ID_ATTRIBUTE_NAME = "id";
    public static final String RESPONSE_ID_ATTRIBUTE_NAME = "resourceId";
    public static final String SUBRESPONSE_ID_ATTRIBUTE_NAME = "subResourceId";
    public static final String CODE_NAME_ATTRIBUTE_NAME = "name";
    public static final String CODE_SYSTEM_DEFINED_ATTRIBUTE_NAME = "systemDefined";
    public static final String CODE_URL = "/fineract-provider/api/v1/codes";

    public static final String CODE_VALUE_ID_ATTRIBUTE_NAME = "id";
    public static final String CODE_VALUE_NAME_ATTRIBUTE_NAME = "name";
    public static final String CODE_VALUE_DESCRIPTION_ATTRIBUTE_NAME = "description";
    public static final String CODE_VALUE_POSITION_ATTRIBUTE_NAME = "position";
    public static final String CODE_VALUE_URL = "/fineract-provider/api/v1/codes/[codeId]/codevalues";

    public static JsonNode create(final String name) throws UnirestException {
        JsonNode node = new com.angkorteam.fintech.dto.JsonNode();
        node.getObject().put("name", name);
        return Helper.performServerPost(CODE_URL, node);
    }

    public static JsonNode createValue(JsonNode codeValue) throws UnirestException {
        String codeId = (String) codeValue.getObject().remove("codeId");
        return Helper.performServerPost(CODE_URL + "/" + codeId + "/codevalues", codeValue);
    }

    public static JsonNode updateValue(JsonNode codeValue) throws UnirestException {
        String codeId = (String) codeValue.getObject().remove("codeId");
        String id = (String) codeValue.getObject().remove("id");
        return Helper.performServerPut(CODE_URL + "/" + codeId + "/codevalues/" + id, codeValue);
    }


}
