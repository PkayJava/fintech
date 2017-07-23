package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class DataTableHelper {

    private static final String DATATABLE_URL = "/fineract-provider/api/v1/datatables";

    public static JsonNode create(JsonNode dataTable) throws UnirestException {
        return Helper.performServerPost(DATATABLE_URL, dataTable);
    }

    public static JsonNode delete(final String datatableName) throws UnirestException {
        return Helper.performServerDelete(DATATABLE_URL + "/" + datatableName);
    }

    public static JsonNode deleteEntry(final String datatableName, final Integer apptableId) throws UnirestException {
        final String deleteEntryUrl = DATATABLE_URL + "/" + datatableName + "/" + apptableId + "?genericResultSet=true";
        return Helper.performServerDelete(deleteEntryUrl);
    }

}
