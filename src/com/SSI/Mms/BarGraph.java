package com.SSI.Mms;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarGraph {

	public Intent getIntent(Context context) {
		// Bar 1
		int[] y = { 124, 135, 143, 156, 134, 123, 142 };
		CategorySeries series = new CategorySeries("Graph");

		for (int i = 0; i < y.length; i++) {
			series.add(y[i]);

		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setBarSpacing(0.5);
		renderer.setXLabels(0);
		renderer.setClickEnabled(true);
		renderer.addXTextLabel(1, "Mon");
		renderer.addXTextLabel(2, "Tue");
		renderer.addXTextLabel(3, "Wed");
		renderer.addXTextLabel(4, "Thu");
		renderer.addXTextLabel(5, "Fri");
		renderer.addXTextLabel(6, "Sat");
		renderer.addXTextLabel(7, "Sun");
		renderer.setChartTitle("Bar Chart");
		renderer.setXTitle("Week Days");
		renderer.setYTitle("Hits");
		renderer.setGridColor(Color.GREEN);

		renderer.setPanEnabled(false, false);
		renderer.setZoomEnabled(false, false);
		renderer.setYAxisMin(0);
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(8);
		renderer.setYAxisMax(200);

		renderer.setMargins(new int[] { 40, 40, 15, 10 });
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();

		r.setColor(Color.RED);

		renderer.addSeriesRenderer(r);

		// Customize bar 1

		// Customize bar 2

		Intent intent = ChartFactory.getBarChartIntent(context, dataset,
				renderer, Type.DEFAULT);
		return intent;
	}

}
