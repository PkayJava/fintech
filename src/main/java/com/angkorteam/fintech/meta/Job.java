package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class Job extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column DISPLAY_NAME;

    public final Column CRON_EXPRESSION;

    public final Column CREATE_TIME;

    public final Column TASK_PRIORITY;

    public final Column GROUP_NAME;

    public final Column PREVIOUS_RUN_START_TIME;

    public final Column NEXT_RUN_TIME;

    public final Column JOB_KEY;

    public final Column INITIALIZING_ERROR_LOG;

    public final Column ACTIVE;

    public final Column CURRENTLY_RUNNING;

    public final Column UPDATE_ALLOWED;

    public final Column SCHEDULER_GROUP;

    public final Column MISFIRED;

    public static Job staticInitialize(DataContext dataContext) {
        return new Job(dataContext);
    }

    private Job(DataContext dataContext) {
        super(dataContext, "job");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.DISPLAY_NAME = this.delegate.getColumnByName("display_name");
        this.CRON_EXPRESSION = this.delegate.getColumnByName("cron_expression");
        this.CREATE_TIME = this.delegate.getColumnByName("create_time");
        this.TASK_PRIORITY = this.delegate.getColumnByName("task_priority");
        this.GROUP_NAME = this.delegate.getColumnByName("group_name");
        this.PREVIOUS_RUN_START_TIME = this.delegate.getColumnByName("previous_run_start_time");
        this.NEXT_RUN_TIME = this.delegate.getColumnByName("next_run_time");
        this.JOB_KEY = this.delegate.getColumnByName("job_key");
        this.INITIALIZING_ERROR_LOG = this.delegate.getColumnByName("initializing_errorlog");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.CURRENTLY_RUNNING = this.delegate.getColumnByName("currently_running");
        this.UPDATE_ALLOWED = this.delegate.getColumnByName("updates_allowed");
        this.SCHEDULER_GROUP = this.delegate.getColumnByName("scheduler_group");
        this.MISFIRED = this.delegate.getColumnByName("is_misfired");
    }

}
