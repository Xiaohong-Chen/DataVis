/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visualyou;

import java.time.LocalDateTime;

/**
 *
 * @author sean
 */
public class Study implements Comparable<Study>{

    private int studyId;
    private LocalDateTime recordDate;
    private double timetotal;
    private double timeEffective;
    private double finishedLevelTotal;
    private double finishedLevel;
    private double sleepTime;
    private double exerciseTime;

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }

    public double getTimetotal() {
        return timetotal;
    }

    public void setTimetotal(double timetotal) {
        this.timetotal = timetotal;
    }

    public double getTimeEffective() {
        return timeEffective;
    }

    public void setTimeEffective(double timeEffective) {
        this.timeEffective = timeEffective;
    }

    public double getFinishedLevelTotal() {
        return finishedLevelTotal;
    }

    public void setFinishedLevelTotal(double finishedLevelTotal) {
        this.finishedLevelTotal = finishedLevelTotal;
    }

    public double getFinishedLevel() {
        return finishedLevel;
    }

    public void setFinishedLevel(double finishedLevel) {
        this.finishedLevel = finishedLevel;
    }

    public double getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(double sleepTime) {
        this.sleepTime = sleepTime;
    }

    public double getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(double exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    @Override
    public int compareTo(Study study) {
        return study.studyId >= studyId ? -1 : 0;
    }
    
    

}
