/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.visualyou;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author sean
 */
public class StudyIntegrator extends BackingBeanUtils implements Serializable {

    public ArrayList<Study> getStudyList() {

        String query = "SELECT studyid, recorddate, timetotal, timeeffective, finishedleveltotal, \n"
                + "       finishedlevel, sleeptime, exercisetime\n"
                + "  FROM public.study;";

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Study> studyList = new ArrayList<>();
        Study study;

        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                study = generateStudy(rs);
                System.out.println(study.getStudyId());
                studyList.add(study);
            }
            Collections.sort(studyList);

        } catch (SQLException ex) {
            System.out.println(ex);

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {/* ignored */ }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    /* ignored */ }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }
        } // close finally

        return studyList;
    }

    private Study generateStudy(ResultSet rs) throws SQLException {

        Study s = new Study();

        s.setStudyId(rs.getInt("studyid"));
        s.setRecordDate(rs.getTimestamp("recorddate").toLocalDateTime());
        s.setTimetotal(rs.getDouble("timetotal"));
        s.setTimeEffective(rs.getDouble("timeeffective"));
        s.setFinishedLevelTotal(rs.getDouble("finishedleveltotal"));
        s.setFinishedLevel(rs.getDouble("finishedlevel"));
        s.setSleepTime(rs.getDouble("sleeptime"));
        s.setExerciseTime(rs.getDouble("exercisetime"));

        return s;

    }

    public void insertStudy(Study s) {

        String query = "INSERT INTO public.study(\n"
                + "            recorddate, timetotal, timeeffective, finishedleveltotal, \n"
                + "            finishedlevel, sleeptime, exercisetime, userid)\n"
                + "    VALUES (now(), ?, ?, ?, \n"
                + "            ?, ?, ?, ?);";

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, 3);
            stmt.setDouble(2, s.getTimeEffective());
            stmt.setDouble(3, 5);
            stmt.setDouble(4, s.getFinishedLevel());
            stmt.setDouble(5, s.getSleepTime());
            stmt.setDouble(6, s.getExerciseTime());
            stmt.setDouble(7, 3);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.toString());

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {/* ignored */ }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }
        } // close finally
    }

}
