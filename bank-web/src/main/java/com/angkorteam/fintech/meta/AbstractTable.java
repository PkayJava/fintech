package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

import java.util.Collection;
import java.util.List;

public abstract class AbstractTable implements Table {

    protected final Table delegate;

    protected AbstractTable(DataContext dataContext, String name) {
        this.delegate = dataContext.getDefaultSchema().getTableByName(name);
    }

    protected Column getInternalColumnByName(String columnName) {
        Column column = this.delegate.getColumnByName(columnName);
        if (column == null) {
            throw new IllegalArgumentException(columnName + " is not found");
        }
        return column;
    }

    @Override
    public final String getName() {
        return delegate.getName();
    }

    @Override
    public final int getColumnCount() {
        return delegate.getColumnCount();
    }

    @Override
    public final List<Column> getColumns() {
        return delegate.getColumns();
    }

    @Override
    public final Column getColumnByName(String columnName) {
        return delegate.getColumnByName(columnName);
    }

    @Override
    public final Column getColumn(int index) throws IndexOutOfBoundsException {
        return delegate.getColumn(index);
    }

    @Override
    public final Schema getSchema() {
        return delegate.getSchema();
    }

    @Override
    public final TableType getType() {
        return delegate.getType();
    }

    @Override
    public final Collection<Relationship> getRelationships() {
        return delegate.getRelationships();
    }

    @Override
    public final Collection<Relationship> getRelationships(Table otherTable) {
        return delegate.getRelationships(otherTable);
    }

    @Override
    public final int getRelationshipCount() {
        return delegate.getRelationshipCount();
    }

    @Override
    public final String getRemarks() {
        return delegate.getRemarks();
    }

    @Override
    public final List<Column> getNumberColumns() {
        return delegate.getNumberColumns();
    }

    @Override
    public final List<Column> getLiteralColumns() {
        return delegate.getLiteralColumns();
    }

    @Override
    public final List<Column> getTimeBasedColumns() {
        return delegate.getTimeBasedColumns();
    }

    @Override
    public final List<Column> getBooleanColumns() {
        return delegate.getBooleanColumns();
    }

    @Override
    public final List<Column> getIndexedColumns() {
        return delegate.getIndexedColumns();
    }

    @Override
    public final Collection<Relationship> getForeignKeyRelationships() {
        return delegate.getForeignKeyRelationships();
    }

    @Override
    public final Collection<Relationship> getPrimaryKeyRelationships() {
        return delegate.getPrimaryKeyRelationships();
    }

    @Override
    public final List<Column> getForeignKeys() {
        return delegate.getForeignKeys();
    }

    @Override
    public final List<Column> getPrimaryKeys() {
        return delegate.getPrimaryKeys();
    }

    @Override
    public final List<String> getColumnNames() {
        return delegate.getColumnNames();
    }

    @Override
    public final List<Column> getColumnsOfType(ColumnType columnType) {
        return delegate.getColumnsOfType(columnType);
    }

    @Override
    public final List<Column> getColumnsOfSuperType(SuperColumnType superColumnType) {
        return delegate.getColumnsOfSuperType(superColumnType);
    }

    @Override
    public final int compareTo(Table o) {
        return delegate.compareTo(o);
    }

    @Override
    public final String getQuote() {
        return delegate.getQuote();
    }

    @Override
    public final String getQuotedName() {
        return delegate.getQuotedName();
    }

    @Override
    public final String getQualifiedLabel() {
        return delegate.getQualifiedLabel();
    }

}
