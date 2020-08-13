package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.DOCUMENT_ID = this.delegate.getColumnByName("document_id");
        this.IMPORT_TIME = this.delegate.getColumnByName("import_time");
        this.END_TIME = this.delegate.getColumnByName("end_time");
        this.ENTITY_TYPE = this.delegate.getColumnByName("entity_type");
        this.COMPLETED = this.delegate.getColumnByName("completed");
        this.TOTAL_RECORD = this.delegate.getColumnByName("total_records");
        this.SUCCESS_COUNT = this.delegate.getColumnByName("success_count");
        this.FAILURE_COUNT = this.delegate.getColumnByName("failure_count");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
    }

}
