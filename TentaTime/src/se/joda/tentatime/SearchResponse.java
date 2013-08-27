package se.joda.tentatime;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniel on 8/7/13.
 *
 * @author Daniel Kristoffersson
 *
 * Copyright (c) Joseph Hejderup & Daniel Kristoffersson, All rights reserved.
 * See License.txt in the project root for license information.
 */
public class SearchResponse {
    @SerializedName("exams")
    private List<ExamResult> results;

    public List<ExamResult> getResults() {
        return results;
    }

    public void setResults(List<ExamResult> results) {
        this.results = results;
    }
}
