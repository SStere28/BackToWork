package com.SSI.Mms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "ResourceAsColor", "NewApi" })
public class Add extends Activity {

	private EditText text;
	private Button  labelb;
	private String logon = "work";
	private CheckBox cb;

	private LinearLayout labelf;
	private String color = "c10";
	private EditText labelt;
	int f = 0;
	private ArrayList<String> t = new ArrayList<String>();
	private ArrayList<String> c = new ArrayList<String>();

	@SuppressLint({ "InflateParams", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);

		text = (EditText) findViewById(R.id.text);
		labelt = (EditText) findViewById(R.id.labelt);
		cb = (CheckBox) findViewById(R.id.cb);
		labelb = (Button) findViewById(R.id.labelb);

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
		

		labelb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);

				int height = size.y;

				PopupWindow popupwindow_obj = popupDisplaylabel();
				popupwindow_obj.setHeight(height - 220);

				popupwindow_obj.showAtLocation(labelb, Gravity.CENTER, 0, 0);

			}
		});
		labelf = (LinearLayout) findViewById(R.id.labelf);

		if (f == 0) {

			BufferedReader br = null;

			try {

				String fpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/"

				+ "settings/save.btw";
				File m = new File(fpath);
				br = new BufferedReader(new FileReader(fpath));

				if (m.exists()) {

					String line = "";
					while ((line = br.readLine()) != null) {

						final String vl[] = line.split(" ");

						if (t.size() == 0 && c.size() == 0) {

							if (vl.length == 4) {

								final Button b = new Button(
										getApplicationContext());
								String name = vl[3] + "";
								int id = getResources().getIdentifier(name,
										"drawable", getPackageName());
								Drawable drawable = getResources().getDrawable(
										id);
								String namec = vl[3] + "";
								int idc = getResources().getIdentifier(namec,
										"color", getPackageName());
								int color2 = getResources().getColor(idc);
								b.setText(vl[2] + "");
								b.setTextColor(color2);
								b.setBackground(drawable);
								b.setLayoutParams(new LayoutParams(
										LayoutParams.MATCH_PARENT, 65));

								b.setOnClickListener(new OnClickListener() {

									
									public void onClick(View v) {

										labelt.setText(vl[2] + "");
										color = vl[3];

									}
								});

								labelf.addView(b);
								t.add(vl[2]);
								c.add(vl[3]);
							}
						} else if (t.contains(vl[2]) && c.contains(vl[3])
								&& c.indexOf(vl[3]) == t.indexOf(vl[2])) {

						} else {

							if (vl.length == 4) {

								final Button b = new Button(
										getApplicationContext());
								String name = vl[3] + "";
								int id = getResources().getIdentifier(name,
										"drawable", getPackageName());
								Drawable drawable = getResources().getDrawable(
										id);
								String namec = vl[3] + "";
								int idc = getResources().getIdentifier(namec,
										"color", getPackageName());
								int color2 = getResources().getColor(idc);
								b.setText(vl[2] + "");
								b.setTextColor(color2);

								b.setBackground(drawable);
								b.setLayoutParams(new LayoutParams(
										LayoutParams.MATCH_PARENT, 65));

								b.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										

										labelt.setText(vl[2] + "");
										color = vl[3];

									}
								});

								labelf.addView(b);
								t.add(vl[2]);
								c.add(vl[3]);
							}

						}

					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			f = 1;
		}

		final Button create = (Button) findViewById(R.id.create);
		create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (!cb.isChecked()) {
					String path = Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/Btw/user/" + "settings/";

					File dir = new File(path);
					if (!dir.exists()) {
						dir.mkdirs();

						File file = new File(dir, "other.btw");

						
						BufferedWriter buf;
						try {
							buf = new BufferedWriter(new FileWriter(file, true));
							if (text.getText().length() != 0
									&& labelt.getText().length() != 0) {

								String text1 = text.getText().toString()
										.replace(" ", "_");
								String text2 = labelt.getText().toString()
										.replace(" ", "_");
								buf.append(text1 + " " + logon + " " + text2
										+ " " + color);
								text.setText("");
								labelt.setText("");
								labelb.setBackgroundResource(android.R.drawable.btn_default);
								
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(
												R.string.created),
										Toast.LENGTH_SHORT).show();

								buf.close();
							} else {
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(R.string.taal),
										Toast.LENGTH_SHORT).show();

							}

						} catch (IOException e) {
							
						
							e.printStackTrace();
						}

					} else {

						File file = new File(dir, "other.btw");
						if (!file.exists()) {

							

							BufferedWriter buf;
							try {
								buf = new BufferedWriter(new FileWriter(file,
										true));
								if (text.getText().length() != 0
										&& labelt.getText().length() != 0) {

									String text1 = text.getText().toString()
											.replace(" ", "_");

									String text2 = labelt.getText().toString()
											.replace(" ", "_");
									buf.append(text1 + " " + logon + " "
											+ text2 + " " + color);
									text.setText("");
									labelt.setText("");
									
									labelb.setBackgroundResource(android.R.drawable.btn_default);
									
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.created),
											Toast.LENGTH_SHORT).show();

									buf.close();
								} else {
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.taal),
											Toast.LENGTH_SHORT).show();

								}
							} catch (IOException e) {
								
								
								e.printStackTrace();
							}

						} else {

							BufferedWriter buf;
							try {
								buf = new BufferedWriter(new FileWriter(file,
										true));
								if (text.getText().length() != 0
										&& labelt.getText().length() != 0) {

									buf.append("\r\n");
									String text1 = text.getText().toString()
											.replace(" ", "_");
									String text2 = labelt.getText().toString()
											.replace(" ", "_");
									buf.append(text1 + " " + logon + " "
											+ text2 + " " + color);
									text.setText("");
								
									labelb.setBackgroundResource(android.R.drawable.btn_default);
							
									text.setText("");
									labelt.setText("");
									buf.close();
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.created),
											Toast.LENGTH_SHORT).show();

									buf.close();
								} else {
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.taal),
											Toast.LENGTH_SHORT).show();

								}
							} catch (IOException e) {
								
								
								e.printStackTrace();
							}
						}

					}

				} else if (cb.isChecked()) {

					String path = Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/Btw/user/" + "settings/";

					File dir = new File(path);
					if (!dir.exists()) {
						dir.mkdirs();

						File file = new File(dir, "save.btw");

						
						BufferedWriter buf;
						try {
							buf = new BufferedWriter(new FileWriter(file, true));
							if (text.getText().length() != 0
									&& labelt.getText().length() != 0) {

								String text1 = text.getText().toString()
										.replace(" ", "_");
								String text2 = labelt.getText().toString()
										.replace(" ", "_");
								buf.append(text1 + " " + logon + " " + text2
										+ " " + color);
								text.setText("");
							
								labelb.setBackgroundResource(android.R.drawable.btn_default);
							
								labelt.setText("");
								cb.setChecked(false);
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(
												R.string.created),
										Toast.LENGTH_SHORT).show();

								buf.close();
							} else {
								Toast.makeText(
										getApplicationContext(),
										getResources().getString(R.string.taal),
										Toast.LENGTH_SHORT).show();

							}

						} catch (IOException e) {
							
							
							e.printStackTrace();
						}

					} else {

						File file = new File(dir, "save.btw");
						if (!file.exists()) {

							BufferedWriter buf;
							try {
								buf = new BufferedWriter(new FileWriter(file,
										true));
								if (text.getText().length() != 0
										&& labelt.getText().length() != 0) {

									String text1 = text.getText().toString()
											.replace(" ", "_");
									String text2 = labelt.getText().toString()
											.replace(" ", "_");
									buf.append(text1 + " " + logon + " "
											+ text2 + " " + color);
									text.setText("");
									labelt.setText("");
									
									labelb.setBackgroundResource(android.R.drawable.btn_default);
									
									cb.setChecked(false);
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.created),
											Toast.LENGTH_SHORT).show();

									buf.close();
								} else {
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.taal),
											Toast.LENGTH_SHORT).show();

								}
							} catch (IOException e) {
								
								// catch block
								e.printStackTrace();
							}

						} else {

							BufferedWriter buf;
							try {
								buf = new BufferedWriter(new FileWriter(file,
										true));
								if (text.getText().length() != 0
										&& labelt.getText().length() != 0) {

									buf.append("\r\n");
									String text1 = text.getText().toString()
											.replace(" ", "_");
									String text2 = labelt.getText().toString()
											.replace(" ", "_");
									buf.append(text1 + " " + logon + " "
											+ text2 + " " + color);
									labelb.setBackgroundResource(android.R.drawable.btn_default);
									
									text.setText("");
									labelt.setText("");
									cb.setChecked(false);
									buf.close();
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.created),
											Toast.LENGTH_SHORT).show();

									buf.close();
								} else {
									Toast.makeText(
											getApplicationContext(),
											getResources().getString(
													R.string.taal),
											Toast.LENGTH_SHORT).show();

								}

							} catch (IOException e) {
							
							
								e.printStackTrace();
							}
						}

					}
				}
			}

		});

		final TextView Title = (TextView) findViewById(R.id.title);
		Title.setText(R.string.othert);

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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);

	}

	

	@SuppressLint("ResourceAsColor")
	public PopupWindow popupDisplaylabel() {

		final PopupWindow popupWindow = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_label, null);

		RelativeLayout c1 = (RelativeLayout) view.findViewById(R.id.c1);
		c1.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				

				labelb.setBackgroundColor(getResources().getColor(R.color.c1));
				color = "c1";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c2 = (RelativeLayout) view.findViewById(R.id.c2);
		c2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				labelb.setBackgroundColor(getResources().getColor(R.color.c2));
				color = "c2";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c3 = (RelativeLayout) view.findViewById(R.id.c3);
		c3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c3));
				color = "c3";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c4 = (RelativeLayout) view.findViewById(R.id.c4);
		c4.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c4));
				color = "c4";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c5 = (RelativeLayout) view.findViewById(R.id.c5);
		c5.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c5));
				color = "c5";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c6 = (RelativeLayout) view.findViewById(R.id.c6);
		c6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c6));
				color = "c6";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c7 = (RelativeLayout) view.findViewById(R.id.c7);
		c7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c7));
				color = "c7";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c8 = (RelativeLayout) view.findViewById(R.id.c8);
		c8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c8));
				color = "c8";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c9 = (RelativeLayout) view.findViewById(R.id.c9);
		c9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c9));
				color = "c9";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c10 = (RelativeLayout) view.findViewById(R.id.c10);
		c10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c10));
				color = "c10";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c11 = (RelativeLayout) view.findViewById(R.id.c11);
		c11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c11));
				color = "c11";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c12 = (RelativeLayout) view.findViewById(R.id.c12);
		c12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c12));
				color = "c12";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c13 = (RelativeLayout) view.findViewById(R.id.c13);
		c13.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c13));
				color = "c13";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c14 = (RelativeLayout) view.findViewById(R.id.c14);
		c14.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c14));
				color = "c14";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c15 = (RelativeLayout) view.findViewById(R.id.c15);
		c15.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c15));
				color = "c15";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c16 = (RelativeLayout) view.findViewById(R.id.c16);
		c16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c16));
				color = "c16";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c17 = (RelativeLayout) view.findViewById(R.id.c17);
		c17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c17));
				color = "c17";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c18 = (RelativeLayout) view.findViewById(R.id.c18);
		c18.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c18));
				color = "c18";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c19 = (RelativeLayout) view.findViewById(R.id.c19);
		c19.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c19));
				color = "c19";
				popupWindow.dismiss();

			}
		});
		RelativeLayout c20 = (RelativeLayout) view.findViewById(R.id.c20);
		c20.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
			

				labelb.setBackgroundColor(getResources().getColor(R.color.c20));

				color = "c20";
				popupWindow.dismiss();

			}
		});
		popupWindow.setFocusable(true);
		popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(
				android.graphics.Color.WHITE));
		popupWindow.setContentView(view);

		return popupWindow;
	}
}
