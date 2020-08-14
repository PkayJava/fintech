package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class TopicSubscriber extends AbstractTable {

    public final Column ID;

    public final Column TOPIC_ID;

    public final Column USER_ID;

    public final Column SUBSCRIPTION_DATE;

    public static TopicSubscriber staticInitialize(DataContext dataContext) {
        return new TopicSubscriber(dataContext);
    }

    private TopicSubscriber(DataContext dataContext) {
        super(dataContext, "topic_subscriber");
        this.ID = getInternalColumnByName("id");
        this.TOPIC_ID = getInternalColumnByName("topic_id");
        this.USER_ID = getInternalColumnByName("user_id");
        this.SUBSCRIPTION_DATE = getInternalColumnByName("subscription_date");
    }

}
