package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class DataTableHelper {

    public static JsonNode create(IMifos session, JsonNode object) {
        return Helper.performServerPost(session, "/api/v1/datatables", object);
    }

    public static JsonNode delete(IMifos session, String datatableName) {
        return Helper.performServerDelete(session, "/api/v1/datatables/" + datatableName);
    }

    public static JsonNode deleteEntry(IMifos session, String datatableName, final Long apptableId) {
        return Helper.performServerDelete(session, "/api/v1/datatables/" + datatableName + "/" + apptableId + "?genericResultSet=true");
    }

}
