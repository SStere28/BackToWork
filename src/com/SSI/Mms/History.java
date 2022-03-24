package com.SSI.Mms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class History extends Activity {

	private LinearLayout today;
	private TextView data;

	int x = 0;
	int blbl = 0;
	int lblb = 0;
	final Context context = this;
	String color = "c10";

	private TextView start, finish, titlu, label;
	private ArrayList<String> linie = new ArrayList<String>();
	private ArrayList<String> del = new ArrayList<String>();
	private String path = "";

	@Override
	@SuppressLint({ "InflateParams", "NewApi" })
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);

		data = (TextView) findViewById(R.id.data);
		today = (LinearLayout) findViewById(R.id.today);

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

		final ImageButton set = (ImageButton) findViewById(R.id.settings);
		set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
              PopupWindow popupwindow_obj = popupDisplay2();
				
				popupwindow_obj.showAsDropDown(set);
				
			}
		});

		final TextView Title = (TextView) findViewById(R.id.title);
		Title.setText(getResources().getString(R.string.today));

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

		Bundle extras = getIntent().getExtras();
		String value = extras.getString("data");
		

		if (extras != null && value != null) {

			BufferedReader br = null;

			try {
				linie.clear();
				del.clear();
				String fpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/"

				+ "history/" + value + ".btw";
				path = fpath + "";
				data.setText(value);
				br = new BufferedReader(new FileReader(fpath));
				String line = "";

				while ((line = br.readLine()) != null) {

					linie.add(line);
				}

			} catch (IOException e) {
				e.printStackTrace();

			}

		} else {

			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = df.format(c.getTime());
			linie.clear();
			del.clear();
			String fpath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Btw/user/"

			+ "history/" + formattedDate + ".btw";
			BufferedReader br = null;
			path = fpath;

			try {

				data.setText(formattedDate);
				br = new BufferedReader(new FileReader(fpath));
				String line = "";

				while ((line = br.readLine()) != null) {

					linie.add(line);
				}

			} catch (IOException e) {
				e.printStackTrace();

			}

		}
		today = (LinearLayout) findViewById(R.id.today);
		final Button b[] = new Button[25];
		for (int i = 0; i < linie.size(); i++) {

			final String lim[] = linie.get(i).split(" ");

			int s = 0;
			for (int i1 = 0; i1 < blbl; i1++) {

				if (b[i1].getText().toString().equals(lim[lim.length - 2])) {
					s = 1;

				}

			}
			if (s == 0) {

				b[blbl] = new Button(getApplicationContext());
				String name = lim[lim.length - 1] + "";
				int id = getResources().getIdentifier(name, "drawable",
						getPackageName());
				Drawable drawable = getResources().getDrawable(id);
				b[blbl].setText(lim[lim.length - 2] + "");
				String namec = lim[lim.length - 1] + "";
				int idc = getResources().getIdentifier(namec, "color",
						getPackageName());
				int color = getResources().getColor(idc);
				b[blbl].setTextColor(color);
				b[blbl].setBackground(drawable);
				b[blbl].setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, 65));
				

				today.addView(b[blbl]);
				blbl++;
				lblb = 1;

				for (int k = 0; k < linie.size(); k++) {

					final String[] lim2 = linie.get(k).split(" ");
					final String dels = linie.get(k);
					String ass = "";
					for (int i1 = 0; i1 < lim2.length - 4; i1++) {

						String con = lim2[i1] + " ";
						ass = ass.concat(con);
					}

					if (lim[lim.length - 1].equals(lim2[lim2.length - 1])
							&& lim[lim.length - 2]
									.equals(lim2[lim2.length - 2])) {

						final TextView tv = new TextView(
								getApplicationContext());
						tv.setGravity(Gravity.CENTER);
						LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
						llp.setMargins(0, 5, 0, 5);

						tv.setTextColor(color);
						tv.setLayoutParams(llp);
						tv.setText(ass + " ");
						tv.setTextSize(15);

						today.addView(tv);

						tv.setOnClickListener(new OnClickListener() {

							@SuppressLint({ "CutPasteId", "ShowToast" })
							@Override
							public void onClick(View v) {
								
							
								PopupWindow popupwindow_obj = popupDisplayonclick(lim2);
								popupwindow_obj.showAtLocation(tv,
										Gravity.CENTER, 0, 0);

							}
						});

						tv.setOnLongClickListener(new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								

								PopupWindow popupwindow_obj = popupDisplaypressclick(
										dels, tv);
								popupwindow_obj.showAtLocation(tv,
										Gravity.CENTER, 0, 0);

								return true;

							}
						});

					}
				}
			}

		}

		ImageButton st = (ImageButton) findViewById(R.id.st);
		st.setOnClickListener(new OnClickListener() {

			@Override
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				
				Calendar c = Calendar.getInstance();
				c.getTime();
				x--;
				c.add(Calendar.DATE, x);
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate = df.format(c.getTime());

				data.setText(formattedDate);
				BufferedReader br = null;
				blbl = 0;
				linie.clear();
				today.removeAllViews();
				String fpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/"

				+ "history/" + formattedDate + ".btw";
				path = fpath;

				try {

					File m = new File(fpath);
					br = new BufferedReader(new FileReader(fpath));

					if (m.exists()) {

						String line = "";
						while ((line = br.readLine()) != null) {

							linie.add(line);

						}
					}

				} catch (IOException e) {
					e.printStackTrace();

					today.removeAllViews();

				}

				today = (LinearLayout) findViewById(R.id.today);
				final Button b[] = new Button[25];
				blbl = 0;
				for (int i = 0; i < linie.size(); i++) {

					final String lim[] = linie.get(i).split(" ");

					int s = 0;
					for (int i1 = 0; i1 < blbl; i1++) {

						if (b[i1].getText().toString()
								.equals(lim[lim.length - 2])) {
							s = 1;

						}

					}
					if (s == 0) {

						b[blbl] = new Button(getApplicationContext());
						String name = lim[lim.length - 1] + "";
						int id = getResources().getIdentifier(name, "drawable",
								getPackageName());
						Drawable drawable = getResources().getDrawable(id);
						b[blbl].setText(lim[lim.length - 2] + "");
						String namec = lim[lim.length - 1] + "";
						int idc = getResources().getIdentifier(namec, "color",
								getPackageName());
						int color = getResources().getColor(idc);
						b[blbl].setTextColor(color);
						b[blbl].setBackground(drawable);
						b[blbl].setLayoutParams(new LayoutParams(
								LayoutParams.MATCH_PARENT, 65));
						

						today.addView(b[blbl]);
						blbl++;
						lblb = 1;

						for (int k = 0; k < linie.size(); k++) {

							final String[] lim2 = linie.get(k).split(" ");
							final String dels = linie.get(k);
							String ass = "";
							for (int i1 = 0; i1 < lim2.length - 4; i1++) {

								String con = lim2[i1] + " ";
								ass = ass.concat(con);
							}

							if (lim[lim.length - 1]
									.equals(lim2[lim2.length - 1])
									&& lim[lim.length - 2]
											.equals(lim2[lim2.length - 2])) {

								final TextView tv = new TextView(
										getApplicationContext());
								tv.setGravity(Gravity.CENTER);
								LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										LayoutParams.WRAP_CONTENT);
								llp.setMargins(0, 5, 0, 5);

								tv.setTextColor(color);
								tv.setLayoutParams(llp);
								tv.setText(ass + " ");
								tv.setTextSize(15);

								today.addView(tv);

								tv.setOnClickListener(new OnClickListener() {

									@SuppressLint({ "CutPasteId", "ShowToast" })
									@Override
									public void onClick(View v) {
										
										PopupWindow popupwindow_obj = popupDisplayonclick(lim2);
										popupwindow_obj.showAtLocation(tv,
												Gravity.CENTER, 0, 0);

									}
								});

								tv.setOnLongClickListener(new OnLongClickListener() {

									@Override
									public boolean onLongClick(View v) {
										

										PopupWindow popupwindow_obj = popupDisplaypressclick(
												dels, tv);
										popupwindow_obj.showAtLocation(tv,
												Gravity.CENTER, 0, 0);

										return true;

									}
								});
							}
						}
					}

				}

			}
		});

		ImageButton dr = (ImageButton) findViewById(R.id.dr);
		dr.setOnClickListener(new OnClickListener() {

			@Override
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				
				Calendar c = Calendar.getInstance();
				c.getTime();
				x++;
				c.add(Calendar.DATE, x);
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate = df.format(c.getTime());
				linie.clear();
				data.setText(formattedDate);
				BufferedReader br = null;
				today.removeAllViews();
				lblb = 0;
				try {

					String fpath = Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/Btw/user/"

					+ "history/" + formattedDate + ".btw";
					path = fpath;

					File m = new File(fpath);
					br = new BufferedReader(new FileReader(fpath));

					if (m.exists()) {
						String line = "";
						while ((line = br.readLine()) != null) {

							linie.add(line);

						}

					}

				} catch (IOException e) {
					e.printStackTrace();

					today.removeAllViews();

				}

				today = (LinearLayout) findViewById(R.id.today);
				blbl = 0;
				final Button b[] = new Button[25];
				for (int i = 0; i < linie.size(); i++) {

					final String lim[] = linie.get(i).split(" ");

					int s = 0;
					for (int i1 = 0; i1 < blbl; i1++) {

						if (b[i1].getText().toString()
								.equals(lim[lim.length - 2])) {
							s = 1;

						}

					}
					if (s == 0) {

						b[blbl] = new Button(getApplicationContext());
						String name = lim[lim.length - 1] + "";
						int id = getResources().getIdentifier(name, "drawable",
								getPackageName());
						Drawable drawable = getResources().getDrawable(id);
						b[blbl].setText(lim[lim.length - 2] + "");
						String namec = lim[lim.length - 1] + "";
						int idc = getResources().getIdentifier(namec, "color",
								getPackageName());
						int color = getResources().getColor(idc);
						b[blbl].setTextColor(color);
						b[blbl].setBackground(drawable);
						b[blbl].setLayoutParams(new LayoutParams(
								LayoutParams.MATCH_PARENT, 65));
						

						today.addView(b[blbl]);
						blbl++;

						for (int k = 0; k < linie.size(); k++) {

							final String[] lim2 = linie.get(k).split(" ");
							final String dels = linie.get(k);
							String ass = "";
							for (int i1 = 0; i1 < lim2.length - 4; i1++) {

								String con = lim2[i1] + " ";
								ass = ass.concat(con);
							}

							if (lim[lim.length - 1]
									.equals(lim2[lim2.length - 1])
									&& lim[lim.length - 2]
											.equals(lim2[lim2.length - 2])) {

								final TextView tv = new TextView(
										getApplicationContext());
								tv.setGravity(Gravity.CENTER);
								LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										LayoutParams.WRAP_CONTENT);
								llp.setMargins(0, 5, 0, 5);

								tv.setTextColor(color);
								tv.setLayoutParams(llp);
								tv.setText(ass + " ");
								tv.setTextSize(15);

								today.addView(tv);

								tv.setOnClickListener(new OnClickListener() {

									@SuppressLint({ "CutPasteId", "ShowToast" })
									@Override
									public void onClick(View v) {
										
										

										PopupWindow popupwindow_obj = popupDisplayonclick(lim2);
										popupwindow_obj.showAtLocation(tv,
												Gravity.CENTER, 0, 0);

									}
								});

								tv.setOnLongClickListener(new OnLongClickListener() {

									@Override
									public boolean onLongClick(View v) {
									

										PopupWindow popupwindow_obj = popupDisplaypressclick(
												dels, tv);
										popupwindow_obj.showAtLocation(tv,
												Gravity.CENTER, 0, 0);

										return true;

									}
								});

							}
						}
					}

				}
			}
		});

	}

	public PopupWindow popupDisplayonclick(String[] lim2) {

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_h_extra, null);

		start = (TextView) view.findViewById(R.id.start);
		finish = (TextView) view.findViewById(R.id.finish);
		titlu = (TextView) view.findViewById(R.id.titlu);
		label = (TextView) view.findViewById(R.id.label);

		titlu.setText(lim2[lim2.length - 5]);
		start.setText(lim2[lim2.length - 3]);
		finish.setText(lim2[lim2.length - 4]);
		label.setText(lim2[lim2.length - 2]);
		// set dialog message

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				popupWindow2.dismiss();

			}
		});

		popupWindow2.setFocusable(true);

		popupWindow2.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow2.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow2.setBackgroundDrawable(new ColorDrawable(
				android.graphics.Color.WHITE));
		popupWindow2.setContentView(view);

		return popupWindow2;
	}

	public PopupWindow popupDisplaypressclick(final String dels,
			final TextView tv) {

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_delete, null);

		del.clear();
		TextView delete = (TextView) view.findViewById(R.id.delete);
		TextView textView1 = (TextView) view.findViewById(R.id.textView1);
		String tvs = tv.getText() + "";
		String tvs2 = "";
		String[] tvv = tvs.split(" ");
		textView1.setText(getResources().getString(R.string.dela) + " "
				+ tvv[tvv.length - 1] + " ?");
		for (int i = 0; i < (tvv.length - 1); i++) {
			tvs2 = tvs2 + " " + tvv[i];
		}
		delete.setText(tvs2);

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				BufferedReader br = null;
				try {
					File m = new File(path);
					br = new BufferedReader(new FileReader(path));

					if (m.exists()) {
						String line = "";
						while ((line = br.readLine()) != null) {

							if (dels.equals(line)) {

							} else {
								del.add(line);
							}

						}

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				File dir1 = new File(path);
				dir1.delete();

				if (del.size() > 0) {
					File dir = new File(path);

					
					BufferedWriter buf;
					try {
						buf = new BufferedWriter(new FileWriter(dir, true));
						for (int k = 0; k < del.size(); k++) {
							if (k != 0) {

								buf.append("\r\n");
							}
							buf.append(del.get(k));

						}

						buf.close();

					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				tv.setVisibility(View.GONE);

				popupWindow2.dismiss();

			}
		});

		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				popupWindow2.dismiss();

			}
		});

		popupWindow2.setFocusable(true);

		popupWindow2.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow2.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow2.setBackgroundDrawable(new ColorDrawable(
				android.graphics.Color.WHITE));
		popupWindow2.setContentView(view);

		return popupWindow2;
	}
	
	public PopupWindow popupDisplay2() {

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_settings_history, null);

		Button ch = (Button) view.findViewById(R.id.ch);
		ch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				Calendar c = Calendar.getInstance();
				System.out.println("Current time => " + c.getTime());

				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				final String formattedDate = df.format(c.getTime());
				String fpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/"

				+ "history/" + formattedDate + ".btw";

				File file = new File(fpath);
				file.delete();
				popupWindow2.dismiss();
				
				Toast.makeText(getBaseContext(),
						getResources().getString(R.string.saved), Toast.LENGTH_SHORT)
						.show();
			}
		});
		final Button cah = (Button) view.findViewById(R.id.cah);
		cah.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				popupWindow2.dismiss();
				PopupWindow popupwindow_obj = popupDisplaycah();
				popupwindow_obj.showAtLocation(cah, Gravity.CENTER, 0, 0);
			}
		});

		popupWindow2.setFocusable(true);

		popupWindow2.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow2.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow2.setBackgroundDrawable(new ColorDrawable(
				android.graphics.Color.WHITE));
		popupWindow2.setContentView(view);

		return popupWindow2;
	}
	
	public PopupWindow popupDisplaycah() {

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_clear_all_history, null);

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				String fpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/";

				File dir = new File(Environment.getExternalStorageDirectory()
						+ "/Btw/user/" + "history/");
				if (dir.isDirectory()) {
					String[] children = dir.list();
					for (int i = 0; i < children.length; i++) {
						new File(dir, children[i]).delete();
					}
				}
				File file = new File(fpath);
				file.delete();
				popupWindow2.dismiss();

			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				popupWindow2.dismiss();

			}
		});

		popupWindow2.setFocusable(true);

		popupWindow2.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow2.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow2.setBackgroundDrawable(new ColorDrawable(
				android.graphics.Color.WHITE));
		popupWindow2.setContentView(view);

		return popupWindow2;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

}
