package com.joda.tentatime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * This class validates all the input necessary for an exam to be created.
 * @author Daniel Kristoffersson
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class InputUtility {

    /**
     * Validates if the course id has the correct format
     * @param id
     * @return true or false
     */
    public static boolean validateCourseID(String id) {

        //The course id needs to be 6 characters 3 letters, 3 digits
        //The course id cant be null
        if(id != null && id.length() == 6) {
            //Separates the chars from the integers in the courseId string
            String sub1 = id.substring(0, 3);
            String sub2 = id.substring(3, 6);

            if(sub1.matches("^[a-zA-Z]+$")) {
                if(sub2.matches("^[0-9]+$")) {
                    //courseId is valid
                    return true;
                    //Does not contain 3 integer at the end of the courseId
                } else {
                    return false;
                }
                //Does not contain 3 letters in the beginning of the courseId
            } else {
                return false;
            }
            //corseId is invalid
        } else {
            return false;
        }
    }
    /**
     * Validates if the name of the course has the correct format
     * @param name
     * @return true or false
     */
    public static boolean validateName(String name) {
        //name can't be empty and it can't start with a symbol
        if(!isEmpty(name) && name != null) {
            if(name.substring(0, 1).matches("^[a-zA-Z]+$")) {
                //name needs to be between 3 and 40 characters (can't be empty)
                if(name.length() >= 3 && name.length() <= 40) {
                    //valid name
                    return true;
                } else {
                    //invalid name
                    return false;
                }
            } else {
                //invalid name
                return false;
            }
        } else {
            //invalid name
            return false;
        }
    }
    /**
     * Validates if the Time is has the correct format HH:MM and correct time digits
     * @param time
     * @return true or false
     */
    public static boolean validateTime(String time) {
        //time field cant be empty
        if(!isEmpty(time) && time != null) {
            //Right format is HH:MM
            if(time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                //Valid time
                return true;
            } else {
                //invalid time
                return false;
            }
        } else {
            //invalid time
            return false;
        }
    }
    /**
     * Validates if date has the correct format dd/mm/yyyy and is not outdated e.g Old date
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean validateDate(String date) throws ParseException {

        //Get current date in correct format
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        //Get current date in the string form of "dd/MM/yyyy"
        String dateToday = curFormater.format(new Date());


        //date cant be empty or null
        if(!isEmpty(date) && date != null) {
            //correct format is dd-mm-yyyy
            if(date.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((20|21)\\d\\d)")) {

                //Is the date entered too old?
                //Todays date
                Date today = curFormater.parse(dateToday);
                //Date to compare with
                Date dateObj = curFormater.parse(date);
                //Comparing
                int result = today.compareTo(dateObj);

                if(result < 0){


                    //if no then its a valid date
                    return true;
                } else {
                    //invalid date, its too old
                    return false;
                }
            } else {
                //invalid date
                return false;
            }
        } else {
            //invalid date
            return false;
        }
    }
    /**
     * Validates if the place string has the correct format, check req. for more info
     * this method has been depricated/removed, a miss in the requirements
     * @param place
     * @return true or false
     */
    public static boolean validatePlace(String place) {
        //Check if place is empty or null
        if(!isEmpty(place) && place != null) {
            //place can't start with a symbol
            if(place.substring(0,1).matches("^[a-zA-Z]+$")) {
                //maximum letter of place is 12
                if(place.length() <= 25) {

                    StringBuffer digits = new StringBuffer();
                    StringBuffer letters = new StringBuffer();
                    char[] chrs = place.toCharArray();
                    for(char c: chrs) {
                        if(((int)c >= 48) && ((int)c <= 57)) {
                            digits.append(c);
                        }else {
                            letters.append(c);
                        }
                    }

                    //letters needs to be between 2-10 letters
                    if(letters.length() >= 2 && letters.length() <= 10) {
                        //digits needs to be between 1-5
                        if(digits.length() >= 0 && digits.length() <= 5) {
                            return true;
                        } else {
                            //invalid place
                            return false;
                        }
                    } else {
                        //invalid place
                        return false;
                    }

                } else {
                    //invalid place
                    return false;
                }
            } else {
                //invalid place
                return false;
            }
        } else {
            //invalid place
            return false;
        }
    }
    /**
     * Private method to check if a string is empty
     */
    private static Boolean isEmpty(String check) {
        return check.length() == 0;
    }

    /**
     * This method will check if the phone is connected to the Internet
     * @return true or false
     */
    public static boolean connectedToTheInternet(Activity context) {
        ConnectivityManager conMan = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /**
     * This method will convert the first letter to a capital letter
     * @param data The string to be converted
     * @return string with the first letter as capital
     */
    public static String convertFirstLetterToCapital(String data){


        //Convert the first letter to a capital letter
        return String.format( "%s%s",
                Character.toUpperCase(data.charAt(0)),
                data.substring(1) );


    }
    /**
     * Converts the 3 first letters of course Id to uppercase
     * ex. input abc123 output ABC123
     * @return data if  returns the new String with uppercase else
     * return the original input String.
     */
    public static String convertIdToUpperCase(String data) {

        //Divide the string into to pieces
        //One with letters to be converted into uppercase
        String sub1 = data.substring(0, 3);
        //One with digits
        String sub2 = data.substring(3, 6);

        //Control that the letters realy are lowercase
        if(data.length() == 6 && sub1.matches("^[a-z]+$"))
            //Convert the letters to uppercase and concatenate the two
            //strings, letters and digits
            //Then return the new string
            return data = sub1.toUpperCase().concat(sub2);
        else
            //Failed, return the original string
            return data;
    }

}