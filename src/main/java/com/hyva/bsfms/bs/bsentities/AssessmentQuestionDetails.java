package com.hyva.bsfms.bs.bsentities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class AssessmentQuestionDetails {
    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    private Long assessmentDetailsId;
    private String question;
    private String maxMarks;
    @Column(columnDefinition="text")
    private String answer;
    private String questionType;
    private String instrument;
    private String subComponent;
    private String componentNm;
    private String remarks;
    private String comments;
    private String recommendation;
    @OneToOne
    private AssessmentQuestions assessmentQuestions;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getSubComponent() {
        return subComponent;
    }

    public void setSubComponent(String subComponent) {
        this.subComponent = subComponent;
    }

    public String getComponentNm() {
        return componentNm;
    }

    public void setComponentNm(String componentNm) {
        this.componentNm = componentNm;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public AssessmentQuestions getAssessmentQuestions() {
        return assessmentQuestions;
    }

    public void setAssessmentQuestions(AssessmentQuestions assessmentQuestions) {
        this.assessmentQuestions = assessmentQuestions;
    }

    public Long getAssessmentDetailsId() {
        return assessmentDetailsId;
    }

    public void setAssessmentDetailsId(Long assessmentDetailsId) {
        this.assessmentDetailsId = assessmentDetailsId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
