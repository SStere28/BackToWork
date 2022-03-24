package com.SSI.Mms;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

public class PieGraph {

	public Intent getIntent(final Context context) {

		int[] values = { 1, 2, 3, 4, 5 };
		CategorySeries series = new CategorySeries("Pie Graph");
		int k = 0;
		for (int value : values) {
			series.add("Section " + ++k, value);
		}

		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA,
				Color.YELLOW, Color.CYAN };

		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setClickEnabled(true);
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitle("Pie Chart Demo");
		renderer.setChartTitleTextSize(7);
		renderer.setZoomButtonsVisible(true);

		Intent intent = ChartFactory.getPieChartIntent(context, series,
				renderer, "Pie");
		final GraphicalView mChartView = ChartFactory.getPieChartView(context,
				series, renderer);
		renderer.setClickEnabled(true);
		renderer.setSelectableBuffer(100);
		mChartView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SeriesSelection seriesSelection = mChartView
						.getCurrentSeriesAndPoint();
				if (seriesSelection == null) {
					Toast.makeText(context, "No chart element was clicked",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(
							context,
							"Chart element data point index "
									+ seriesSelection.getPointIndex()
									+ " was clicked" + " point value="
									+ seriesSelection.getValue(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		return intent;
	}
}
