package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MImportDocument extends AbstractTable {

    public final Column ID;

    public final Column DOCUMENT_ID;

    public final Column IMPORT_TIME;

    public final Column END_TIME;

    public final Column ENTITY_TYPE;

    public final Column COMPLETED;

    public final Column TOTAL_RECORD;

    public final Column SUCCESS_COUNT;

    public final Column FAILURE_COUNT;

    public final Column CREATED_BY_ID;

    public static MImportDocument staticInitialize(DataContext dataContext) {
        return new MImportDocument(dataContext);
    }

    private MImportDocument(DataContext dataContext) {
        super(dataContext, "m_import_document");
        this.ID = getInternalColumnByName("id");
        this.DOCUMENT_ID = getInternalColumnByName("document_id");
        this.IMPORT_TIME = getInternalColumnByName("import_time");
        this.END_TIME = getInternalColumnByName("end_time");
        this.ENTITY_TYPE = getInternalColumnByName("entity_type");
        this.COMPLETED = getInternalColumnByName("completed");
        this.TOTAL_RECORD = getInternalColumnByName("total_records");
        this.SUCCESS_COUNT = getInternalColumnByName("success_count");
        this.FAILURE_COUNT = getInternalColumnByName("failure_count");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
    }

}
