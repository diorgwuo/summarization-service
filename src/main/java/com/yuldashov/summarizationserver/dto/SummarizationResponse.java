package com.yuldashov.summarizationserver.dto;

public class SummarizationResponse {
    private String requestId;
    private String report;

    public SummarizationResponse() {
    }

    public SummarizationResponse(String requestId, String report) {
        this.report = report;
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
