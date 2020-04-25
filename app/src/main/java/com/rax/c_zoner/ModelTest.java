package com.rax.c_zoner;

public class ModelTest {

    private String day;

    private Integer totalSamplesTested;

    private Integer totalIndividualsTested;

    private Integer totalPositiveCases;

    private String source;

    public ModelTest(String day, Integer totalSamplesTested, Integer totalIndividualsTested, Integer totalPositiveCases, String source) {
        this.day = day;
        this.totalSamplesTested = totalSamplesTested;
        this.totalIndividualsTested = totalIndividualsTested;
        this.totalPositiveCases = totalPositiveCases;
        this.source = source;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getTotalSamplesTested() {
        return totalSamplesTested;
    }

    public void setTotalSamplesTested(Integer totalSamplesTested) {
        this.totalSamplesTested = totalSamplesTested;
    }

    public Integer getTotalIndividualsTested() {
        return totalIndividualsTested;
    }

    public void setTotalIndividualsTested(Integer totalIndividualsTested) {
        this.totalIndividualsTested = totalIndividualsTested;
    }

    public Integer getTotalPositiveCases() {
        return totalPositiveCases;
    }

    public void setTotalPositiveCases(Integer totalPositiveCases) {
        this.totalPositiveCases = totalPositiveCases;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
