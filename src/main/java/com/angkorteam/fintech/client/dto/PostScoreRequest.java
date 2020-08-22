package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostScoreRequest {

    private long id;

    private long userId;

    private String username;

    private long clientId;

    private long surveyId;

    private String surveyName;

    private List<ScorecardValue> scorecardValues = new ArrayList<>();

    public PostScoreRequest() {
    }

    private PostScoreRequest(final long id, final long userId, final String username, final long surveyId, final String surveyName,
                             final long clientId) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.scorecardValues = new ArrayList<>();
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.username = username;
    }

    public static PostScoreRequest instance(final long id, final long userId, final String username, final long surveyId,
                                            final String surveyName, final long clientId) {
        return new PostScoreRequest(id, userId, username, surveyId, surveyName, clientId);
    }

    public long getUserId() {
        return userId;
    }

    public long getClientId() {
        return clientId;
    }

    public List<ScorecardValue> getScorecardValues() {
        return scorecardValues;
    }

    public void setScorecardValues(List<ScorecardValue> scorecardValues) {
        if (this.scorecardValues == null) {
            this.scorecardValues = new ArrayList<>();
        }
        this.scorecardValues.addAll(scorecardValues);
    }

    public String getUsername() {
        return this.username;
    }

    public long getSurveyId() {
        return this.surveyId;
    }

    public String getSurveyName() {
        return this.surveyName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public static class ScorecardValue {

        private long questionId;

        private long responseId;

        private int value;

        private Date createdOn;

        public ScorecardValue() {
        }

        private ScorecardValue(final long questionId, final long responseId, final int value, final Date createdOn) {
            this.questionId = questionId;
            this.responseId = responseId;
            this.value = value;
            this.createdOn = createdOn;
        }

        public static ScorecardValue instance(final long questionId, final long responseId, final int value, final Date createdOn) {
            return new ScorecardValue(questionId, responseId, value, createdOn);
        }

        public long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }

        public long getResponseId() {
            return responseId;
        }

        public void setResponseId(long responseId) {
            this.responseId = responseId;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Date getCreatedOn() {
            return this.createdOn;
        }

        public void setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
        }

    }


}
