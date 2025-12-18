package renwu;

import java.io.Serializable;

public class ExperimentReport implements Serializable {
    private String reportTitle;
    private String experimentNumber;
    private String experimentDate;
    private String experimentPurpose;
    private String experimentPrinciple;
    private String experimentSteps;
    private String experimentResult;
    private String experimentAnalysis;
    private String conclusion;

    public ExperimentReport() {
        this.reportTitle = "";
        this.experimentNumber = "";
        this.experimentDate = "";
        this.experimentPurpose = "";
        this.experimentPrinciple = "";
        this.experimentSteps = "";
        this.experimentResult = "";
        this.experimentAnalysis = "";
        this.conclusion = "";
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getExperimentNumber() {
        return experimentNumber;
    }

    public void setExperimentNumber(String experimentNumber) {
        this.experimentNumber = experimentNumber;
    }

    public String getExperimentDate() {
        return experimentDate;
    }

    public void setExperimentDate(String experimentDate) {
        this.experimentDate = experimentDate;
    }

    public String getExperimentPurpose() {
        return experimentPurpose;
    }

    public void setExperimentPurpose(String experimentPurpose) {
        this.experimentPurpose = experimentPurpose;
    }

    public String getExperimentPrinciple() {
        return experimentPrinciple;
    }

    public void setExperimentPrinciple(String experimentPrinciple) {
        this.experimentPrinciple = experimentPrinciple;
    }

    public String getExperimentSteps() {
        return experimentSteps;
    }

    public void setExperimentSteps(String experimentSteps) {
        this.experimentSteps = experimentSteps;
    }

    public String getExperimentResult() {
        return experimentResult;
    }

    public void setExperimentResult(String experimentResult) {
        this.experimentResult = experimentResult;
    }

    public String getExperimentAnalysis() {
        return experimentAnalysis;
    }

    public void setExperimentAnalysis(String experimentAnalysis) {
        this.experimentAnalysis = experimentAnalysis;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        return reportTitle.isEmpty() ? "未命名报告" : reportTitle;
    }
}
