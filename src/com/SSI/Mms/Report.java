package com.SSI.Mms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Report extends Activity {

	private GraphicalView mChart;

	private ArrayList<String> code = new ArrayList<String>();
	private ArrayList<Double> distribution = new ArrayList<Double>();
	private ArrayList<Double> datei = new ArrayList<Double>();
	private double t = 0;
	private double date = 0;

	
	private TextView data, text;
	private String color = "c10";

	int x = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);

		data = (TextView) findViewById(R.id.data);
		text = (TextView) findViewById(R.id.text);

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df.format(c.getTime());

		Bundle extras = getIntent().getExtras();
		color = extras.getString("color");
		int idc2 = getResources().getIdentifier(color, "color",
				getPackageName());
		int color2 = getResources().getColor(idc2);
		data.setTextColor(color2);

		data.setText(formattedDate);

		openChart(formattedDate);

		final ImageButton st = (ImageButton) findViewById(R.id.st);
		st.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				Calendar c1 = Calendar.getInstance();
				c1.getTime();
				x--;
				c1.add(Calendar.DATE, x);
				SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate1 = df1.format(c1.getTime());
				openChart(formattedDate1);
				data.setText(formattedDate1);

			}
		});

		final ImageButton dr = (ImageButton) findViewById(R.id.dr);
		dr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				Calendar c2 = Calendar.getInstance();
				c2.getTime();
				x++;
				c2.add(Calendar.DATE, x);
				SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate2 = df2.format(c2.getTime());
				openChart(formattedDate2);
				data.setText(formattedDate2);

			}
		});
		// Ploting the chart

		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.action_bar, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		// You customization
		final int actionBarColor = getResources().getColor(R.color.action_bar);
		actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

		ImageButton set = (ImageButton) findViewById(R.id.settings);
		set.setVisibility(View.GONE);

		final TextView Title = (TextView) findViewById(R.id.title);
		Title.setText(R.string.rep);

		Title.setGravity(Gravity.CENTER_VERTICAL);

		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				finish();
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);

			}
		});
	}

	private void openChart(String data) {

		// Pie Chart Slice Names

		// Pie Chart Slice Values

		distribution.clear();
		code.clear();
		LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
		chartContainer.removeAllViews();
		int s = 0;

		BufferedReader br = null;

		try {
			int k = 0;
			String fpath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Btw/user/"

			+ "history/" + data + ".btw";

			br = new BufferedReader(new FileReader(fpath));
			String line = "";
			while ((line = br.readLine()) != null) {

				double d = 0;
				String items[] = line.split(" ");
				k = items.length;
				if (code.contains(items[k - 5])) {
					int index = code.indexOf(items[k - 5]);
					if (items[1].contains(getResources().getString(R.string.h)
							+ "")) {

						d = d + distribution.get(index)
								+ Double.parseDouble(items[0]) * 3600;
						if (items[3].contains(getResources().getString(
								R.string.min)
								+ "")) {
							d = d + +Double.parseDouble(items[2]) * 60;

							if (items[5].contains(getResources().getString(
									R.string.sec)
									+ "")) {
								d = d + +Double.parseDouble(items[4]);
							}
						}
						if (items[3].contains(getResources().getString(
								R.string.sec)
								+ "")) {
							d = d + Double.parseDouble(items[2]);
						}

					}

					if (items[1].contains(getResources()
							.getString(R.string.min) + "")) {
						d = d
								+ (distribution.get(index) + Double
										.parseDouble(items[0]) * 60);

						if (items[3].contains(getResources().getString(
								R.string.sec)
								+ "")) {
							d = d + +Double.parseDouble(items[2]);
						}
					}

					if (items[1].contains(getResources()
							.getString(R.string.sec) + "")) {
						d = d
								+ (distribution.get(index) + Double
										.parseDouble(items[0]));
					}

					distribution.set(index, d);
				} else {
					code.add(items[k - 5]);

					if (items[1].contains(getResources().getString(R.string.h)
							+ "")) {

						d = d + Double.parseDouble(items[0]) * 3600;
						if (items[3].contains(getResources().getString(
								R.string.min)
								+ "")) {
							d = d + Double.parseDouble(items[2]) * 60;

							if (items[5].contains(getResources().getString(
									R.string.sec)
									+ "")) {
								d = d + Double.parseDouble(items[4]);
							}
						}
						if (items[3].contains(getResources().getString(
								R.string.sec)
								+ "")) {
							d = d + Double.parseDouble(items[2]);
						}

					}

					if (items[1].contains(getResources()
							.getString(R.string.min) + "")) {
						d = d + Double.parseDouble(items[0]) * 60;

						if (items[3].contains(getResources().getString(
								R.string.sec)
								+ "")) {
							d = d + Double.parseDouble(items[2]);
						}
					}

					if (items[1].contains(getResources()
							.getString(R.string.sec) + "")) {
						d = d + Double.parseDouble(items[0]);
					}
					distribution.add(d);

				}
				s = 1;

			}

		} catch (IOException e) {
			e.printStackTrace();

		}

		if (s == 1) {
			t = 0;
			for (int k = 0; k < distribution.size(); k++) {
				t += distribution.get(k);
			}
			text.setHint("");
			// Color of each Pie Chart Slices
			final int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN,
					Color.CYAN, Color.RED, Color.YELLOW, Color.CYAN,
					Color.MAGENTA, Color.BLACK, Color.LTGRAY, Color.DKGRAY,
					R.color.c1, R.color.c2, R.color.c3, R.color.c4, R.color.c5,
					R.color.c18, R.color.c19, R.color.c20 };

			// Instantiating CategorySeries to plot Pie Chart
			CategorySeries distributionSeries = new CategorySeries(
					" Daily report");

			for (int i = 0; i < distribution.size(); i++) {
				// Adding a slice with its values and name to the Pie Chart
				distributionSeries.add(code.get(i), distribution.get(i));
			}

			// Instantiating a renderer for the Pie Chart
			DefaultRenderer defaultRenderer = new DefaultRenderer();
			for (int i = 0; i < distribution.size(); i++) {

				// Instantiating a render for the slice
				SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
				seriesRenderer.setColor(colors[i]);
				seriesRenderer.setDisplayChartValues(true);

				// Adding the renderer of a slice to the renderer of the pie
				// chart
				defaultRenderer.addSeriesRenderer(seriesRenderer);
			}
			defaultRenderer.setPanEnabled(false);
			defaultRenderer.setZoomEnabled(false);

			defaultRenderer.setLabelsColor(Color.BLUE);
			defaultRenderer.setChartTitleTextSize(20);
			defaultRenderer.setZoomButtonsVisible(true);

			// Getting a reference to view group linear layout chart_container

			// Getting PieChartView to add to the custom layout

			mChart = ChartFactory.getPieChartView(getBaseContext(),
					distributionSeries, defaultRenderer);

			defaultRenderer.setClickEnabled(true);//
			defaultRenderer.setSelectableBuffer(10);
			mChart.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					SeriesSelection seriesSelection = mChart
							.getCurrentSeriesAndPoint();
					if (seriesSelection != null) {

						// Getting the name of the clicked slice
						int seriesIndex = seriesSelection.getPointIndex();
						String selectedSeries = "";
						selectedSeries = code.get(seriesIndex);
						double tf = (100 * distribution.get(seriesIndex)) / t;

						

						DecimalFormat dFormat = new DecimalFormat("#.#");

						barchart(code.get(seriesIndex), colors[seriesIndex]);
						// Displaying the message
						Toast.makeText(
								getBaseContext(),
								selectedSeries + " : " + dFormat.format(tf)
										+ " % ", Toast.LENGTH_SHORT).show();
					}
				}
			});

			// Adding the pie chart to the custom layout
			chartContainer.removeAllViews();
			chartContainer.addView(mChart);
		} else {
			chartContainer.removeAllViews();

			text.setHint(R.string.norep);
		}

	}

	@SuppressLint("ResourceAsColor")
	public void barchart(String act, int color) {
		// Bar 1

		ArrayList<String> data = new ArrayList<String>();
		double max = 0;
		int x = 0, hms = 4;
		String dass = "";
		datei.clear();

		File dir = new File(Environment.getExternalStorageDirectory()
				+ "/Btw/user/" + "history/");
		if (dir.isDirectory()) {
			String[] children = dir.list();

			for (int i = 0; i < children.length; i++) {

				BufferedReader br = null;

				try {

					String fpath = Environment.getExternalStorageDirectory()
							.getAbsolutePath()
							+ "/Btw/user/"
							+ "history/"
							+ children[i];

					br = new BufferedReader(new FileReader(fpath));
					String line = "";
					double d = 0;
					while ((line = br.readLine()) != null) {

						String items[] = line.split(" ");

						if (act.equals(items[items.length - 5])) {

							if (items[1].contains(getResources().getString(
									R.string.h)
									+ "")) {

								d = d + Double.parseDouble(items[0]) * 3600;
								if (items[3].contains(getResources().getString(
										R.string.min)
										+ "")) {
									d = d + Double.parseDouble(items[2]) * 60;

									if (items[5].contains(getResources()
											.getString(R.string.sec) + "")) {
										d = d + Double.parseDouble(items[4]);
									}
								}
								if (items[3].contains(getResources().getString(
										R.string.sec)
										+ "")) {
									d = d + Double.parseDouble(items[2]);
								}

							}

							if (items[1].contains(getResources().getString(
									R.string.min)
									+ "")) {
								d = d + Double.parseDouble(items[0]) * 60;

								if (items[3].contains(getResources().getString(
										R.string.sec)
										+ "")) {
									d = d + Double.parseDouble(items[2]);
								}
							}

							if (items[1].contains(getResources().getString(
									R.string.sec)
									+ "")) {
								d = d + Double.parseDouble(items[0]);
							}

							date += (int) d;
							d = 0;

							if (items[1].contains(getResources().getString(
									R.string.h)
									+ "")) {
								hms = 2;

								dass = getResources().getString(R.string.hs)
										+ "";
							} else if (items[1].contains(getResources()
									.getString(R.string.min) + "")
									&& hms >= 3) {
								hms = 3;
								dass = getResources().getString(R.string.mins)
										+ "";

							} else if (items[1].contains(getResources()
									.getString(R.string.sec) + "")
									&& hms == 4) {
								dass = getResources().getString(R.string.secs)
										+ "";
								hms = 4;

							}

						}

					}

				} catch (IOException e) {
					e.printStackTrace();

				}

				String d = children[i].substring(0, children[i].length() - 9);
				datei.add(date);
				data.add(d);

				x++;
				date = 0;

			}

		}

		max = 0;
		CategorySeries series = new CategorySeries(act);

		if (dass.equals(getResources().getString(R.string.hs) + "")) {

			for (int i = 0; i < datei.size(); i++) {

				series.add(datei.get(i) / 3600);
				if (max < (datei.get(i) / 3600)) {
					max = (datei.get(i) / 3600);
				}

			}
		}

		if (dass.equals(getResources().getString(R.string.mins) + "")) {

			for (int i = 0; i < datei.size(); i++) {

				series.add(datei.get(i) / 60);

				if (max < (datei.get(i) / 60)) {
					max = (datei.get(i) / 60);
				}

			}
		}

		if (dass.equals(getResources().getString(R.string.secs) + "")) {

			for (int i = 0; i < datei.size(); i++) {
				series.add(datei.get(i));

				if (max < datei.get(i)) {
					max = datei.get(i);
				}

			}
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(13);
		renderer.setLegendTextSize(15);
		renderer.setBarSpacing(1);
		renderer.setXLabels(0);

		for (int i = 0; i < data.size(); i++) {
			renderer.addXTextLabel(i + 1, data.get(i));
		}

		renderer.setXTitle(getResources().getString(R.string.date) + "");

		renderer.setYLabelsColor(0, Color.RED);
		renderer.setYTitle(dass);

		renderer.setLabelsColor(color);
		renderer.setPanEnabled(true, false);
		renderer.setZoomEnabled(false, false);
		renderer.setYAxisMin(0);
		renderer.setXAxisMin(0.5);
		if (x > 10) {
			x = x - 4;
		}
		renderer.setXAxisMax(x);
		max = max + 1;
		renderer.setYAxisMax(max);

		renderer.setXLabelsColor(Color.RED);
		renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
		renderer.setBackgroundColor(Color.TRANSPARENT);

		renderer.setMargins(new int[] { 40, 40, 15, 10 });
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();

		r.setColor(color);

		renderer.addSeriesRenderer(r);

		// Customize bar 1

		// Customize bar 2

		Intent intent = ChartFactory.getBarChartIntent(getBaseContext(),
				dataset, renderer, Type.DEFAULT);
		startActivity(intent);
	}

}