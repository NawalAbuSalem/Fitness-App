package com.nns.youcan.Views.home.DashboardFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.nns.youcan.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonthViewFragment extends Fragment {
    private View monthLayout;
    private LineChart lineChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        monthLayout=inflater.inflate(R.layout.month_view_fragment,container,false);
        lineChart = monthLayout.findViewById(R.id.line_chart);
        List<Entry> entries = prepareDataEntries();
        LineDataSet lineDataSet = prepareLineDataSet(entries);
        List<String> labels = prepareLabels();
        prepareChartAxis(labels, entries);
        LineData lineData = prepareLineData(lineDataSet);
        setLineChartProperties();
        setData(lineData, "");
        return monthLayout;
    }

    /**
     * @return
     */
    private List<String> prepareLabels() {
        return Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    }

    /**
     * @return
     */
    private List<Entry> prepareDataEntries() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1f, 3f));
        entries.add(new Entry(2f, 10f));
        entries.add(new Entry(3f, 7f));
        entries.add(new Entry(4f, 14f));
        entries.add(new Entry(5f, 16f));
        entries.add(new Entry(6f, 20f));
        entries.add(new Entry(7f, 18f));
        entries.add(new Entry(8f, 25f));
        entries.add(new Entry(9f, 30f));
        entries.add(new Entry(10f, 35f));
        entries.add(new Entry(11f, 30f));
        entries.add(new Entry(12f, 25));
        return entries;
    }

    /**
     * @param entries
     */
    private LineDataSet prepareLineDataSet(List<Entry> entries) {

        LineDataSet lineDataSet = new LineDataSet(entries, "Activity");

        // To change line color
        lineDataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorChartLine));

        // To change line width
        lineDataSet.setLineWidth(5);

        // To remove the chicle from the graph
        lineDataSet.setDrawCircles(false);

        // To remove text value over the line
        lineDataSet.setDrawValues(false);

        // To change circles style
        lineDataSet.setCircleHoleColor(Color.GREEN);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setCircleRadius(10f);

        // To make the smooth line as the graph is adapt change so smooth curve
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // To enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.setCubicIntensity(0.1f);

        // To fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.BLACK);

        // Set the transparency
        lineDataSet.setFillAlpha(80);

        // Set the Gradient then the above draw fill color will be replace
//        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.chart_fill);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.color.colorChartFill));

        return lineDataSet;

    }

    /**
     * @param labels
     * @param entries
     */
    private void prepareChartAxis(final List<String> labels, final List<Entry> entries) {

        XAxis xAxis = lineChart.getXAxis();

        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

//        xAxis.setAxisMinimum(0);
//        xAxis.setAxisMaximum(365); // because there are 365 days!

        xAxis.setLabelCount(12); // X value count

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1

        // Customizing x axis value
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
//                int division = (entries.size() / labels.size());
//                int monthIndex = (int) Math.floor(value / division);
                int monthIndex = (int) value - 1;
                return labels.get(monthIndex);
            }
        };

        xAxis.setValueFormatter(formatter);


        // Controlling right side of y axis
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        // Controlling left side of y axis
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setEnabled(false);
        yAxisLeft.setGranularity(1f);

    }


    /**
     * @param lineDataSet
     * @return
     */
    private LineData prepareLineData(LineDataSet lineDataSet) {
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);
        return lineData;
    }

    /**
     *
     */
    private void setData(LineData lineData, String desc) {
        Description description = new Description();
        description.setText(desc);
        lineChart.setDescription(description);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }



    /**
     * Change line chart properties
     */
    private void setLineChartProperties() {
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setMaxHighlightDistance(200);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
//        lineChart.setViewPortOffsets(0, 0, 0, 0);
        // Set legend disable or enable to hide {the left down corner name of graph}
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
    }

}
