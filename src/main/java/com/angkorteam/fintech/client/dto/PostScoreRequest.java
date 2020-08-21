package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostScoreRequest {

    private Long id;

    private Long userId;

    private String username;

    private Long clientId;

    private Long surveyId;

    private String surveyName;

    private List<ScorecardValue> scorecardValues;

    public PostScoreRequest() {
    }

    private PostScoreRequest(final Long id, final Long userId, final String username, final Long surveyId, final String surveyName,
                             final Long clientId) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.scorecardValues = new ArrayList<>();
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.username = username;
    }

    public static PostScoreRequest instance(final Long id, final Long userId, final String username, final Long surveyId,
                                            final String surveyName, final Long clientId) {
        return new PostScoreRequest(id, userId, username, surveyId, surveyName, clientId);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getClientId() {
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

    public Long getSurveyId() {
        return this.surveyId;
    }

    public String getSurveyName() {
        return this.surveyName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public static class ScorecardValue {

        private Long questionId;

        private Long responseId;

        private Integer value;

        private Date createdOn;

        public ScorecardValue() {
        }

        private ScorecardValue(final Long questionId, final Long responseId, final Integer value, final Date createdOn) {
            this.questionId = questionId;
            this.responseId = responseId;
            this.value = value;
            this.createdOn = createdOn;
        }

        public static ScorecardValue instance(final Long questionId, final Long responseId, final Integer value, final Date createdOn) {
            return new ScorecardValue(questionId, responseId, value, createdOn);
        }

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public Long getResponseId() {
            return responseId;
        }

        public void setResponseId(Long responseId) {
            this.responseId = responseId;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
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
