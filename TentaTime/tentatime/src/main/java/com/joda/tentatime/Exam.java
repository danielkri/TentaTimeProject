package com.joda.tentatime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * This class contains all the essential information for an exam.
 *
 * @author Daniel Kristoffersson
 * @author Joseph Hejderup
 * @author jhejderup
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class Exam implements Parcelable{

    private String courseID;
    private String courseName;
    private String time;
    private String date;
    private String place;
    private final DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Constructor of an Exam object
     * @param id course ID
     * @param name course Name
     * @param time time the exam will be held
     * @param date date of the exam
     * @param place place where the exam will be held
     */
    public Exam(String id, String name, String time, String date, String place){
        this.courseID = id;
        this.courseName = name;
        this.time = time;
        this.date = date;
        this.place = place;
    }
    /**
     *
     * @param parcelData
     */
    public Exam(Parcel parcelData)
    {

        this.courseID = parcelData.readString();
        this.courseName = parcelData.readString();
        this.date = parcelData.readString();
        this.place = parcelData.readString();
        this.time = parcelData.readString();



    }
    /**
     * get course id
     * @return the course id
     */
    public String getCourseID() {
        return courseID;
    }
    /**
     * set course id
     * @param courseID new course id
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    /**
     * get course name
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }
    /**
     * set course name
     * @param courseName new course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    /**
     * get exam time
     * @return the time of the exam
     */
    public String getTime() {
        return time;
    }
    /**
     * set exam time
     * @param time new time for the exam
     */
    public void setTime(String time) {
        this.time = time;
    }
    /**
     * get the date of the exam
     * @return the date of the exam
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the day
     * @return day
     */
    public int getDay(){

        return getDateObject().getDate();


    }
    /**
     * Return the month
     * @return month
     */
    public int getMonth(){


        return getDateObject().getMonth();
    }

    /**
     * Return the year of the exam
     * @return year
     */
    public int getYear(){

        return getDateObject().getYear();
    }
    /**
     *
     * @return
     */
    public Date getDateObject(){

        Date examdate = null;

        try {
            //skall vara detta formattet "20-12-2005"
            examdate = dateformat.parse(getDate());


        } catch (ParseException e) {

            e.printStackTrace();

        }

        return examdate;

    }
    /**
     * set the date of an exam
     * @param date new date for an exam
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * get the place of an exam
     * @return the place where the exam will be held
     */
    public String getPlace() {
        return place;
    }
    /**
     * set the place of an exam
     * @param place new place for an exam to be held
     */
    public void setPlace(String place) {
        this.place = place;
    }
    /**
     *
     */
    public int describeContents() {

        return 0;
    }
    /**
     *
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseID);
        dest.writeString(courseName);
        dest.writeString(date);
        dest.writeString(place);
        dest.writeString(time);
    }
    /**
     *
     */
    public static final Creator<Exam> CREATOR = new Creator<Exam>() {

        /**
         *
         */
        public Exam createFromParcel(Parcel source) {

            return new Exam(source);
        }
        /**
         *
         */
        @Override
        public Exam[] newArray(int size) {

            return new Exam[size];
        }

    };
}
