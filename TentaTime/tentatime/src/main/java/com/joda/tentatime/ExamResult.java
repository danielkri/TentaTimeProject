package com.joda.tentatime;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by daniel on 8/7/13.
 *
 * @author Daniel Kristoffersson
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class ExamResult implements Serializable {

    private static final long serialVersionUID = 1L;

    //May need changing to date or int
    @SerializedName("ac_year")
    private String acYear;

    ////May need changing to int
    private String beginsAt;

    //May need changing to date or int
    private ArrayList<String> changes;

    private String code;

    private String element;

    //May need changing to date or int
    @SerializedName("exam_date")
    private String examDate;

    //May need changing to date or int
    @SerializedName("last_date_reg")
    private String lastDateReg;

    private String length;

    private String location;

    private String name;

    private String url;

    public String getAcYear() {
        return acYear;
    }

    public void setAcYear(String acYear) {
        this.acYear = acYear;
    }

    public String getBeginsAt() {
        return beginsAt;
    }

    public void setBeginsAt(String beginsAt) {
        this.beginsAt = beginsAt;
    }

    public ArrayList<String> getChanges() {
        return changes;
    }

    public void setChanges(ArrayList<String> changes) {
        this.changes = changes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getLastDateReg() {
        return lastDateReg;
    }

    public void setLastDateReg(String lastDateReg) {
        this.lastDateReg = lastDateReg;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
