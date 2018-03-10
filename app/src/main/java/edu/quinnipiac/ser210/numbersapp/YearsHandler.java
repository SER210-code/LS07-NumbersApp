package edu.quinnipiac.ser210.numbersapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by relkharboutly on 1/6/2016.
 */
public class YearsHandler {

    private static final int START_YEAR = 1950;
    private static final int END_YEAR= 2010;
    public static final String YEAR_FACT = "YEAR_FACT";
    final public static String [] years = new String[END_YEAR - START_YEAR +1];;

    public YearsHandler(){

    //populate the array
        int i = 0;
        for (int yr = START_YEAR; yr <= END_YEAR; yr++ )
            years[i++] = Integer.toString(yr);
    }

    public String getYearFact(String yearFactJsonStr) throws JSONException {
        JSONObject yearFactJSONObj = new JSONObject(yearFactJsonStr);
        return yearFactJSONObj.getString("text");
    }

}
