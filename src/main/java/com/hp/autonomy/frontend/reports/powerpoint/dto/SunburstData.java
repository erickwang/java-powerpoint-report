/*
 * Copyright 2017-2018 Micro Focus International plc.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.frontend.reports.powerpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to represent a one-level Sunburst chart
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SunburstData implements ComposableElement {
    /** List of categories. */
    private String[] categories;

    /** List of values for each category, should be the same length as the category array. */
    private double[] values;

    /** List of fill colors for each category, e.g. '#FF0000'; will default to the template if null. */
    private String[] colors;

    /** List of stroke colors for each category, e.g. '#FF0000'; will default to the template if null. */
    private String[] strokeColors;

    /**
     * List of category indices which should be shown in the legend, all categories will be shown if null.
     * Note this only works in PowerPoint, and not Open Office.
     * */
    private int[] showInLegend;

    /** Optional title to put on the chart. */
    private String title;

    public boolean validateInput() {
        return categories != null && values != null && categories.length == values.length;
    }
}