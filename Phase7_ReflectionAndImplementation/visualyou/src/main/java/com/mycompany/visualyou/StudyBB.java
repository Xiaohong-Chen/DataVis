/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visualyou;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author sean
 */
public class StudyBB extends BackingBeanUtils implements Serializable {

    private Study study;

    private ArrayList<Study> studyList;

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public ArrayList<Study> getStudyList() {
        return studyList;
    }

    public void setStudyList(ArrayList<Study> studyList) {
        this.studyList = studyList;
    }

    @PostConstruct
    public void initBean() {
        
       
        study = new Study();
        StudyIntegrator si = new StudyIntegrator();
        
        studyList = si.getStudyList();

        createChartStudySleepModel();
        createChartStudyExerciseModel();
        createBubbleModels();
        createDonutModels();

    }

    //************************************************************//
    //**********************Study and Sleep***********************//
    //************************************************************//
    private LineChartModel chartStudySleepModel;

    private void createChartStudySleepModel() {

        chartStudySleepModel = new LineChartModel();

        BarChartSeries series1 = new BarChartSeries();
        series1.setLabel("Study Score");

        for (Study s : studyList) {
            series1.set(s.getRecordDate().format(DateTimeFormatter.BASIC_ISO_DATE), calculateStudyScore(s));
            System.out.println(s.getRecordDate().format(DateTimeFormatter.BASIC_ISO_DATE));
            
        }

        LineChartSeries series2 = new LineChartSeries();

        series2.setLabel("Sleep Time");
        series2.setYaxis(AxisType.Y2);
        for (Study s : studyList) {
            series2.set(s.getRecordDate().format(DateTimeFormatter.BASIC_ISO_DATE), s.getSleepTime());
        }

        chartStudySleepModel.addSeries(series1);
        chartStudySleepModel.addSeries(series2);

        chartStudySleepModel.setMouseoverHighlight(false);

        chartStudySleepModel.getAxes().put(AxisType.X, new CategoryAxis(""));

        Axis yAxis = chartStudySleepModel.getAxis(AxisType.Y);
        yAxis.setLabel("Sleep Time");

        Axis y2Axis = new LinearAxis("Study Score");

        chartStudySleepModel.getAxes().put(AxisType.Y2, y2Axis);

    }

    public LineChartModel getChartStudySleepModel() {
        return chartStudySleepModel;
    }

    //************************************************************//
    //**********************Study and Exercise********************//
    //************************************************************//
    private LineChartModel chartStudyExerciseModel;

    private void createChartStudyExerciseModel() {

        chartStudyExerciseModel = new LineChartModel();

        BarChartSeries series1 = new BarChartSeries();
        series1.setLabel("Study Score");

        for (Study s : studyList) {
            series1.set(s.getRecordDate().format(DateTimeFormatter.BASIC_ISO_DATE), calculateStudyScore(s));
        }

        LineChartSeries series2 = new LineChartSeries();

        series2.setLabel("Exercise Time");
        for (Study s : studyList) {
            series2.set(s.getRecordDate().format(DateTimeFormatter.BASIC_ISO_DATE), s.getExerciseTime());
        }
        series2.setYaxis(AxisType.Y2);

        chartStudyExerciseModel.addSeries(series1);
        chartStudyExerciseModel.addSeries(series2);

        chartStudyExerciseModel.setMouseoverHighlight(false);

        chartStudyExerciseModel.getAxes().put(AxisType.X, new CategoryAxis(""));

        Axis yAxis = chartStudyExerciseModel.getAxis(AxisType.Y);
        yAxis.setLabel("Exercise Time");

        Axis y2Axis = new LinearAxis("Study Score");

        chartStudyExerciseModel.getAxes().put(AxisType.Y2, y2Axis);

    }

    public LineChartModel getChartStudyExerciseModel() {
        return chartStudyExerciseModel;
    }

    private BubbleChartModel bubbleModel;

    private void createBubbleModels() {
        bubbleModel = initBubbleModel(studyList);
        bubbleModel.getAxis(AxisType.X).setLabel("Date");
        Axis yAxis = bubbleModel.getAxis(AxisType.Y);
        yAxis.setLabel("Study Score");

    }

    private BubbleChartModel initBubbleModel(ArrayList<Study> study) {
        BubbleChartModel model = new BubbleChartModel();

        for (Study s : study) {
            String name = s.getRecordDate().format(DateTimeFormatter.BASIC_ISO_DATE);
            double xid = s.getStudyId();
            double ystudyScore = calculateStudyScore(s.getTimeEffective(), s.getFinishedLevel(), s.getTimetotal(), s.getFinishedLevelTotal(), 0.7, 0.3);
            double rsleepTime = s.getSleepTime();
            model.add(new BubbleChartSeries(name, rsleepTime, ystudyScore, rsleepTime));
        }

        return model;
    }

    public BubbleChartModel getBubbleModel() {
        return bubbleModel;
    }

    /**
     * Calculate StudyScore
     *
     * @param t effectiveTime
     * @param l finishedLevel
     * @param tt effectiveTimeTotal
     * @param tl finishedLevelTotal
     * @param tw effectiveTimeWeight
     * @param lw finishedLevelWeight
     * @return study score
     */
    private double calculateStudyScore(double t, double l, double tt, double lt, double tw, double lw) {
        return t / tt * tw + l / lt * lw;
    }

    public double calculateStudyScore(Study s) {
        return calculateStudyScore(s.getTimeEffective(), s.getFinishedLevel(), s.getTimetotal(), s.getFinishedLevelTotal(), 0.7, 0.3);
    }

    public String formatDouble(double d) {
        DecimalFormat fmt = new DecimalFormat("##0.0");
        return fmt.format(d);
    }

    public String insertdata() {
        StudyIntegrator si = new StudyIntegrator();
        si.insertStudy(study);

        return "guide";
    }

    private DonutChartModel donutModel;
    private DonutChartModel donutModelsleep;

    private void createDonutModels() {

        donutModel = initDonutModel();
        donutModel.setLegendPosition("s");
        donutModel.setLegendRows(1);

        donutModel.setSliceMargin(2);
        donutModel.setShowDataLabels(true);
        donutModel.setDataFormat("value");
        donutModel.setShadow(false);
        
        donutModelsleep = initDonutModelSleep();
        donutModelsleep.setLegendPosition("s");
        donutModelsleep.setLegendRows(1);

        donutModelsleep.setSliceMargin(2);
        donutModelsleep.setShowDataLabels(true);
        donutModelsleep.setDataFormat("value");
        donutModelsleep.setShadow(false);
    }
    
    private DonutChartModel initDonutModel() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<>();
        circle1.put(" Effective ", getStudyScoreAve());
        circle1.put(" Ineffective ", 100-getStudyScoreAve());
        
        model.addCircle(circle1);

        return model;
    }

    private DonutChartModel initDonutModelSleep() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle1 = new LinkedHashMap<>();
        circle1.put(" Sleep", getSleepAve());
        circle1.put(" Wake", 24-getSleepAve());
        
        model.addCircle(circle1);

        return model;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }
    public DonutChartModel getDonutModelSleep() {
        return donutModelsleep;
    }

    public double getStudyScoreAve() {
        int count = 0;
        double sum = 0.0;

        for (Study s : studyList) {
            sum = sum + calculateStudyScore(s);
            count++;
        }
        System.out.println(sum / count *100);
        return sum / count *100;

    }
    
     public int getSleepAve() {
        int count = 0;
        double sum = 0.0;

        for (Study s : studyList) {
            sum = sum + s.getSleepTime();
            count++;
        }
        System.out.println(sum / count);
        return (int)(sum / count);

    }
}
