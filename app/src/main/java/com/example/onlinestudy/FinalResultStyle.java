package com.example.onlinestudy;

public class FinalResultStyle {
    String subjectColumn,degreeColumn,estimateColumn;
    FinalResultStyle( String subjectColumn,String degreeColumn,String estimateColumn){this.subjectColumn=subjectColumn;this.degreeColumn=degreeColumn;this.estimateColumn=estimateColumn;}
    public String getSubjectColumn(){return subjectColumn;}
    public String getDegreeColumn(){return degreeColumn;}
    public String getEstimateColumn(){return estimateColumn;}

    public void setSubjectColumn(String subjectColumn){this.subjectColumn=subjectColumn;}
    public void setDegreeColumn(String degreeColumn){this.degreeColumn=degreeColumn;}
    public void setEstimateColumn(String estimateColumn){this.estimateColumn=estimateColumn;}
}
