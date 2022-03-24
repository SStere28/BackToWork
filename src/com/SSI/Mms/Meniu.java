package com.SSI.Mms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint({ "InflateParams", "CutPasteId", "NewApi", "Wakelock",
		"SimpleDateFormat" })
public class Meniu extends Activity implements OnClickListener {

	private static final int TIME_INTERVAL = 1500; // # milliseconds, desired
													// time passed between two
													// back presses.
	private static final String PREFS_NAME = "B.T.W.";
	private long mBackPressed;
	
	int contor = 1, contor1 = 1, contor2 = 1, contor3 = 1;
	long timeWhenStopped = 0;
	final Context context = this;
	public TextView data, categ;
	private ImageView img;
	private String hiss = "";
	int hours = 0;
	int minutes = 0;
	int seconds = 0;
	int i = 0;
	int rel = 0;
	int j = 0;
	int ff = 0;
	int a = 0;
	int b = 0, cs = 0;
	String labelt = "", labelc = "";
	String color = "c10";
	int psr = 0;
	int eni=0, dei=0, roi=0, rui=0, ch=0;

	ArrayList<Integer> r = new ArrayList<Integer>();

	ArrayList<Integer> s = new ArrayList<Integer>();
	public LinearLayout category;
	int catc = 0;
	Button stop;
	int k = 0;
	int stopp = 0;
	private CheckBox alarm, pausec, stopc;
	private volatile boolean mIsStopped = false;
	private TimePicker t1;
	RotateAnimation rotateAnimation;
	private LinearLayout otherl;
	private Button st;
	int imgk = 0;
	int time = 0;
	int ma = 0, ha = 0, mp = 0, hp = 0, ms = 0, hs = 0;
	private TextView txtCurrentTime;
	String time1 = null, time2 = null;
	int kkk = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.meniu);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		final PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "Tag");

		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
		}
		final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		st = (Button) findViewById(R.id.st);
		stop = (Button) findViewById(R.id.sto);
		img = (ImageView) findViewById(R.id.img1);
		data = (TextView) findViewById(R.id.data);
		txtCurrentTime = (TextView) findViewById(R.id.txt);
		
		categ = (TextView) findViewById(R.id.categ);

		
		

		 Calendar c = Calendar.getInstance();

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		final String formattedDate = df.format(c.getTime());

		data.setText(formattedDate);

		category = (LinearLayout) findViewById(R.id.category);

		category.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				PopupWindow popupwindow_obj = popupDisplay();
				popupwindow_obj.showAtLocation(category, Gravity.CENTER, 0, 0);
				
				catc = 1;

			}
		});

		stop.setClickable(false);

		st.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				if (kkk == 0) {
					Calendar c = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					time1 = sdf.format(c.getTime());

					kkk = 1;
				}

				if (catc == 1
						&& !categ
								.getText()
								.toString()
								.equals(getResources().getString(
										R.string.selcategory))) {
					st.setClickable(false);
					Thread myThread = null;
					k = 1;
					Runnable runnable = new CountDownRunner();
					myThread = new Thread(runnable);
					myThread.start();
					rel = 1;
					wl.acquire();
					
					category.setClickable(false);
					stop.setClickable(true);
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.scf),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		final Button pause = (Button) findViewById(R.id.pause);
		pause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				st.setClickable(true);
				setStopped(true);
				

			}
		});

		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
			

				st.setClickable(true);

				if (k == 1) {
					setStopped(true);

					PopupWindow popupwindow_obj = popupDisplaystop();
					popupwindow_obj.showAtLocation(category, Gravity.CENTER, 0,
							0);

					if (rel == 1) {
						wl.release();
						rel = 0;
					}
					Intent intent = new Intent(getApplicationContext(),
							AlarmReceiverActivity.class);
					PendingIntent pendingIntent = PendingIntent.getActivity(
							context, 12345, intent,
							PendingIntent.FLAG_CANCEL_CURRENT);
					AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
					am.cancel(pendingIntent);
					

				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.sf),
							Toast.LENGTH_SHORT).show();

				}

			}

		});

		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.action_bar, null);

		// Set up your ActionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		// You customization
		final int actionBarColor = getResources().getColor(R.color.action_bar);
		actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

		final ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setVisibility(View.GONE);

		final Animation out = new AlphaAnimation(1.0f, 0.0f);
		out.setDuration(3000);
		final Animation in = new AlphaAnimation(0.0f, 1.0f);
		in.setDuration(3000);

		final TextView actionBarTitle = (TextView) findViewById(R.id.title);
		actionBarTitle.setAnimation(out);
		actionBarTitle.setText(R.string.wel);
		actionBarTitle.setTextColor(Color.BLACK);
		actionBarTitle.setTextSize(16);
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				actionBarTitle.setAnimation(in);
				actionBarTitle.setText("   " +prefs.getString("Titlu", "User"));
			}
		}, 3000);

		ImageButton set = (ImageButton) findViewById(R.id.settings);
		set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				PopupWindow popupwindow_obj = popupSettings();
				popupwindow_obj.showAtLocation(category, Gravity.CENTER, 0, 0);
				

			}
		});

		Button today = (Button) findViewById(R.id.today);
		today.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				Intent h = new Intent(v.getContext(), History.class);
				h.putExtra("data", formattedDate);
				startActivityForResult(h, 0);

			}
		});
		Button custom = (Button) findViewById(R.id.custom);
		custom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				PopupWindow popupwindow_obj = popupDisplaycustom();
				popupwindow_obj.showAtLocation(category, Gravity.CENTER, 0, 0);

			}
		});

		final TextView btn = (TextView) findViewById(R.id.title);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {

				PopupWindow popupwindow_obj = popupDisplay2();
				
				popupwindow_obj.showAsDropDown(btn);
				

			}
		});

	}

	@Override
	public void onBackPressed() {
		if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
			String fpath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Btw/user/"

			+ "settings/" + "other.btw";

			File file = new File(fpath);
			file.delete();
			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("EXIT", true);
			startActivity(intent);
			finish();
			return;
		} else {
			Toast.makeText(getBaseContext(),
					getResources().getString(R.string.exit), Toast.LENGTH_SHORT)
					.show();
		}

		mBackPressed = System.currentTimeMillis();
	}

	public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(Meniu.this);
		// Setting Dialog Title
		alertDialog.setTitle("Leave application?");
		// Setting Dialog Message
		alertDialog
				.setMessage("Are you sure you want to leave the application?");
		// Setting Icon to Dialog

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getApplicationContext(),
								MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("EXIT", true);
						startActivity(intent);
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});
		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onClick(View arg0) {
		

	}

	public void doWork() {

		runOnUiThread(new Runnable() {
			@SuppressWarnings("resource")
			@Override
			public void run() {
				try {
					String m, h, s;

					if (seconds < 10) {
						s = "0" + seconds;
					} else {
						s = "" + seconds;
					}
					if (minutes < 10) {
						m = "0" + minutes;
					} else {
						m = "" + minutes;
					}
					if (hours < 10) {
						h = "0" + hours;
					} else {
						h = "" + hours;
					}

					String curTime = h + ":" + m + ":" + s;
					txtCurrentTime.setText(curTime);

					img = (ImageView) findViewById(R.id.img1);
					rotateAnimation = new RotateAnimation((seconds - 1) * 6,
							seconds * 6, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					seconds++;
					if (seconds == 60) {
						seconds = 0;
						minutes++;
					}
					if (minutes == 60) {
						minutes = 0;
						hours++;
					}
					rotateAnimation.setInterpolator(new LinearInterpolator());
					rotateAnimation.setDuration(1000);
					rotateAnimation.setFillAfter(true);

					int timea = ma + ha * 60;
					int timep = mp + hp * 60;
					int times = ms + hs * 60;
					int time22 = minutes + hours * 60;

					if (time22 == timea && timea > 0) {

						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.SECOND, 1);
						st.setClickable(true);
						setStopped(true);
						a = 0;
						// Create a new PendingIntent and add it to the
						// AlarmManager
						Intent intent = new Intent(getApplicationContext(),
								AlarmReceiverActivity.class);
						PendingIntent pendingIntent = PendingIntent
								.getActivity(context, 12345, intent,
										PendingIntent.FLAG_CANCEL_CURRENT);
						AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
						am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
								pendingIntent);

						ma = 0;
						ha = 0;
					}

					if (time22 == timep && timep > 0) {

						st.setClickable(true);
						setStopped(true);

						mp = 0;
						hp = 0;
						b = 0;
					}

					if (time22 == times && times > 0) {
						Calendar c = Calendar.getInstance();
						SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						final String formattedDate = df.format(c.getTime());
						setStopped(true);

						ms = 0;
						hs = 0;
						cs = 0;
						Intent intent = new Intent(getApplicationContext(),
								AlarmReceiverActivity.class);
						PendingIntent pendingIntent = PendingIntent
								.getActivity(context, 12345, intent,
										PendingIntent.FLAG_CANCEL_CURRENT);
						AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
						am.cancel(pendingIntent);
						String path = Environment.getExternalStorageDirectory()
								.getAbsolutePath() + "/Btw/user/"

						+ "history/";

						File dir = new File(path);
						if (!dir.exists()) {
							dir.mkdirs();

							File file = new File(dir, formattedDate + ".btw");

							
							BufferedWriter buf;
							try {
								buf = new BufferedWriter(new FileWriter(file,
										true));
								String m1 = "";
								String h1 = "";
								String s1 = "";

								if (minutes > 0) {
									if (minutes == 1) {
										m1 = minutes + " minute ";
									} else {
										m1 = minutes + " minutes ";
									}
								}
								if (hours > 0) {
									if (hours == 1) {
										h1 = hours + " hour ";
									} else {
										h1 = hours + " hours ";
									}
								}
								if (seconds > 0) {
									if (seconds == 1) {
										s1 = seconds + " second ";
									} else {
										s1 = seconds + " seconds ";
									}
								}
								time2 = DateFormat.getDateTimeInstance()
										.format(new Date());
								String time1p[] = time1.split(" ");
								String time2p[] = time2.split(" ");

								String all = h1 + m1 + s1 + hiss + " "
										+ time1p[1] + " " + time2p[1] + " "
										+ labelt + " " + labelc;
								;
								all.trim();
								buf.append(all);
								kkk = 0;
								buf.close();

							} catch (IOException e) {
								
								// block
								e.printStackTrace();
							}

						} else {

							File file = new File(dir, formattedDate + ".btw");
							if (!file.exists()) {

								try {
									@SuppressWarnings("unused")
									FileOutputStream fOut = new FileOutputStream(
											file);
								} catch (FileNotFoundException e) {
									
									// catch block
									e.printStackTrace();
								}

								BufferedWriter buf;
								try {
									buf = new BufferedWriter(new FileWriter(
											file, true));
									String m1 = "";
									String h1 = "";
									String s1 = "";

									if (minutes > 0) {
										if (minutes == 1) {
											m1 = minutes + " minute ";
										} else {
											m1 = minutes + " minutes ";
										}
									}
									if (hours > 0) {
										if (hours == 1) {
											h1 = hours + " hour ";
										} else {
											h1 = hours + " hours ";
										}
									}
									if (seconds > 0) {
										if (seconds == 1) {
											s1 = seconds + " second ";
										} else {
											s1 = seconds + " seconds ";
										}
									}

									time2 = DateFormat.getDateTimeInstance()
											.format(new Date());
									String time1p[] = time1.split(" ");
									String time2p[] = time2.split(" ");

									String all = h1 + m1 + s1 + hiss + " "
											+ time1p[1] + " " + time2p[1] + " "
											+ labelt + " " + labelc;
									;
									all.trim();
									buf.append(all);
									kkk = 0;
									buf.close();
								} catch (IOException e) {
									
									// catch block
									e.printStackTrace();
								}

							} else {

								BufferedWriter buf;
								try {
									buf = new BufferedWriter(new FileWriter(
											file, true));
									String m1 = "";
									String h1 = "";
									String s1 = "";

									if (minutes > 0) {
										if (minutes == 1) {
											m1 = minutes
													+ " "
													+ getResources().getString(
															R.string.min) + " ";
										} else {
											m1 = minutes
													+ " "
													+ getResources().getString(
															R.string.mins)
													+ " ";
										}
									}
									if (hours > 0) {
										if (hours == 1) {
											h1 = hours
													+ " "
													+ getResources().getString(
															R.string.h) + " ";
										} else {
											h1 = hours
													+ " "
													+ getResources().getString(
															R.string.hs) + " ";
										}
									}
									if (seconds > 0) {
										if (seconds == 1) {
											s1 = seconds
													+ " "
													+ getResources().getString(
															R.string.sec) + " ";
										} else {
											s1 = seconds
													+ " "
													+ getResources().getString(
															R.string.secs)
													+ " ";
										}
									}
									buf.append("\r\n");
									time2 = DateFormat.getDateTimeInstance()
											.format(new Date());
									String time1p[] = time1.split(" ");
									String time2p[] = time2.split(" ");

									String all = h1 + m1 + s1 + hiss + " "
											+ time1p[1] + " " + time2p[1] + " "
											+ labelt + " " + labelc;
									all.trim();
									buf.append(all);
									kkk = 0;
									buf.close();
								} catch (IOException e) {
									
									// catch block
									e.printStackTrace();
								}
							}

						}

						st.setClickable(true);
						stop.setClickable(false);
						category.setClickable(true);
						stopp = 1;

						if (stopp == 1) {

							
							

							Toast.makeText(getApplicationContext(),
									getResources().getString(R.string.works),
									Toast.LENGTH_SHORT).show();

							setStopped(true);
							rotateAnimation.cancel();
							rotateAnimation.reset();
							img.clearAnimation();
							seconds = 0;
							minutes = 0;
							hours = 0;
							stopp = 0;
							k = 0;
						}

						categ.setText(R.string.selcategory);
						
					}

					img.startAnimation(rotateAnimation);

				} catch (Exception e) {

				}
			}
		});

	}

	public void stop() {
		setStopped(true);
	}

	private void setStopped(boolean isStop) {
		if (mIsStopped != isStop)
			mIsStopped = isStop;
	}

	public boolean isStopped() {
		return mIsStopped;
	}

	class CountDownRunner implements Runnable {
		// @Override

		// schedule for every 30 seconds

		@Override
		public void run() {
			setStopped(false);
			while (!Thread.currentThread().isInterrupted() && !mIsStopped) {

				doWork();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}

			}
		}

	}

	
	public PopupWindow popupDisplaycustom() {

		final PopupWindow popupWindow = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_custom, null);

		alarm = (CheckBox) view.findViewById(R.id.alarm);

		t1 = (TimePicker) view.findViewById(R.id.t1);

		t1.setIs24HourView(true);

		t1.setCurrentHour(00);
		t1.setCurrentMinute(00);

		alarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				if (((CheckBox) v).isChecked()) {
					a = 1;

				} else {
					a = 0;

				}
			}
		});
		pausec = (CheckBox) view.findViewById(R.id.pausec);

		pausec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (((CheckBox) v).isChecked()) {

					b = 1;

				} else {

					b = 0;
				}
			}
		});

		stopc = (CheckBox) view.findViewById(R.id.stopc);

		stopc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				if (((CheckBox) v).isChecked()) {

					cs = 1;

				} else {

					cs = 0;
				}
			}
		});
		// set dialog message
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				if (a == 1) {
					ma = t1.getCurrentMinute();
					ha = t1.getCurrentHour();
					a = 0;
				}

				if (b == 1) {
					mp = t1.getCurrentMinute();
					hp = t1.getCurrentHour();
					b = 0;
				}
				ff = 1;

				if (cs == 1) {
					ms = t1.getCurrentMinute();
					hs = t1.getCurrentHour();
					cs = 0;
				}

				popupWindow.dismiss();
			}
		});

		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
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

	@SuppressWarnings({ "resource" })
	public PopupWindow popupDisplay() {

		final PopupWindow popupWindow = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_select_category, null);

		Button other = (Button) view.findViewById(R.id.other);
		other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				popupWindow.dismiss();
				Intent h = new Intent(v.getContext(), Add.class);
				startActivityForResult(h, 0);
			}
		});

		LinearLayout work = (LinearLayout) view.findViewById(R.id.work);
		work.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				categ.setText(R.string.work);
				
				popupWindow.dismiss();
				hiss = "Work";
				labelt = "Work";
				labelc = "c10";
			}
		});

		LinearLayout read = (LinearLayout) view.findViewById(R.id.read);
		read.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				categ.setText(R.string.read);
				
				popupWindow.dismiss();
				hiss = "Read";
				labelt = "Read";
				labelc = "c5";
			}
		});

		LinearLayout run = (LinearLayout) view.findViewById(R.id.run);
		run.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				categ.setText(R.string.run);
			
				popupWindow.dismiss();
				hiss = "Run";
				labelt = "Run";
				labelc = "c3";
			}
		});

		otherl = (LinearLayout) view.findViewById(R.id.otherl);
		BufferedReader br = null;

		try {

			i = -1;

			String fpath = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Btw/user/" + "settings/other.btw";
			File m = new File(fpath);
			br = new BufferedReader(new FileReader(fpath));

			if (m.exists()) {

				String line = "";
				while ((line = br.readLine()) != null) {

					i++;
					String v[] = line.split(" ");

					LinearLayout l = new LinearLayout(this);
					l.setOrientation(LinearLayout.HORIZONTAL);

					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);

					layoutParams.setMargins(0, 0, 0, 7);

					final TextView tv = new TextView(getApplicationContext());
					tv.setText(v[0] + "");
					tv.setTextColor(Color.BLACK);
					tv.setTextSize(20);

					final TextView tvlt = new TextView(getApplicationContext());
					tvlt.setText(v[2] + "");
					tvlt.setVisibility(View.GONE);

					final TextView tvlc = new TextView(getApplicationContext());
					tvlc.setText(v[3] + "");
					tvlc.setVisibility(View.GONE);

					

					l.addView(tv);
					l.addView(tvlt);
					l.addView(tvlc);

					l.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							
							categ.setText(tv.getText() + "");
							
							popupWindow.dismiss();
							hiss = tv.getText() + "";
							labelt = tvlt.getText() + "";
							labelc = tvlc.getText() + "";
						}
					});

					otherl.addView(l, layoutParams);

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		otherl = (LinearLayout) view.findViewById(R.id.otherl);
		BufferedReader br2 = null;

		try {

			j = -1;

			String fpath2 = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Btw/user/" + "settings/save.btw";
			File m2 = new File(fpath2);
			br2 = new BufferedReader(new FileReader(fpath2));

			if (m2.exists()) {

				String line = "";
				while ((line = br2.readLine()) != null) {
					j++;

					String v[] = line.split(" ");

					LinearLayout l = new LinearLayout(this);
					l.setOrientation(LinearLayout.HORIZONTAL);

					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);

					layoutParams.setMargins(0, 0, 0, 7);

					final TextView tv = new TextView(getApplicationContext());
					tv.setText(v[0] + "");
					tv.setTextColor(Color.BLACK);
					tv.setTextSize(20);

					final TextView tvlt = new TextView(getApplicationContext());
					tvlt.setText(v[2] + "");
					tvlt.setVisibility(View.GONE);

					final TextView tvlc = new TextView(getApplicationContext());
					tvlc.setText(v[3] + "");
					tvlc.setVisibility(View.GONE);

					
					
					l.addView(tv);
					l.addView(tvlt);
					l.addView(tvlc);

					l.setOnClickListener(new OnClickListener() {

						@SuppressLint("NewApi")
						@Override
						public void onClick(View v) {
							
							categ.setText(tv.getText() + "");
						
							popupWindow.dismiss();
							hiss = tv.getText() + "";
							labelt = tvlt.getText() + "";
							labelc = tvlc.getText() + "";
						}
					});

					otherl.addView(l, layoutParams);

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		popupWindow.setFocusable(true);

		popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(
				android.graphics.Color.WHITE));
		popupWindow.setContentView(view);

		return popupWindow;
	}

	public PopupWindow popupDisplay2() {

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_title, null);

		Button h = (Button) view.findViewById(R.id.h);
		h.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				popupWindow2.dismiss();
				Intent h = new Intent(v.getContext(), History_Calendar.class);
				h.putExtra("color", color);
				startActivityForResult(h, 0);
			}
		});
		Button r = (Button) view.findViewById(R.id.r);
		r.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				popupWindow2.dismiss();
				Intent h = new Intent(v.getContext(), Report.class);
				h.putExtra("color", color);
				startActivityForResult(h, 0);
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

	public PopupWindow popupDisplaystop() {

		Calendar c = Calendar.getInstance();

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		final String formattedDate = df.format(c.getTime());

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_adv, null);

		TextView act = (TextView) view.findViewById(R.id.activia);
		act.setText(categ.getText() + "");

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				String path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/"

				+ "history/";

				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();

					File file = new File(dir, formattedDate + ".btw");

					
					BufferedWriter buf;
					try {
						buf = new BufferedWriter(new FileWriter(file, true));

						String m = "";
						String h = "";
						String s = "";

						if (minutes > 0) {
							if (minutes == 1) {
								m = minutes
										+ " "
										+ getResources()
												.getString(R.string.min) + " ";
							} else {
								m = minutes
										+ " "
										+ getResources().getString(
												R.string.mins) + " ";
							}
						}
						if (hours > 0) {
							if (hours == 1) {
								h = hours + " "
										+ getResources().getString(R.string.h)
										+ " ";
							} else {
								h = hours + " "
										+ getResources().getString(R.string.hs)
										+ " ";
							}
						}
						if (seconds > 0) {
							if (seconds == 1) {
								s = seconds
										+ " "
										+ getResources()
												.getString(R.string.sec) + " ";
							} else {
								s = seconds
										+ " "
										+ getResources().getString(
												R.string.secs) + " ";
							}
						}

						Calendar c = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						time2 = sdf.format(c.getTime());

						String all = h + m + s + hiss + " " + time1 + " "
								+ time2 + " " + labelt + " " + labelc;

						all.trim();
						buf.append(all);
						kkk = 0;
						buf.close();

					} catch (IOException e) {
					
						// block
						e.printStackTrace();
					}

				} else {

					File file = new File(dir, formattedDate + ".btw");
					if (!file.exists()) {

						

						BufferedWriter buf;
						try {
							buf = new BufferedWriter(new FileWriter(file, true));
							String m = "";
							String h = "";
							String s = "";

							if (minutes > 0) {
								if (minutes == 1) {
									m = minutes
											+ " "
											+ getResources().getString(
													R.string.min) + " ";
								} else {
									m = minutes
											+ " "
											+ getResources().getString(
													R.string.mins) + " ";
								}
							}
							if (hours > 0) {
								if (hours == 1) {
									h = hours
											+ " "
											+ getResources().getString(
													R.string.h) + " ";
								} else {
									h = hours
											+ " "
											+ getResources().getString(
													R.string.hs) + " ";
								}
							}
							if (seconds > 0) {
								if (seconds == 1) {
									s = seconds
											+ " "
											+ getResources().getString(
													R.string.sec) + " ";
								} else {
									s = seconds
											+ " "
											+ getResources().getString(
													R.string.secs) + " ";
								}
							}
							Calendar c = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"HH:mm:ss");
							time2 = sdf.format(c.getTime());

							String all = h + m + s + hiss + " " + time1 + " "
									+ time2 + " " + labelt + " " + labelc;

							all.trim();
							buf.append(all);
							kkk = 0;
							buf.close();
						} catch (IOException e) {
							
							// catch block
							e.printStackTrace();
						}

					} else {

						BufferedWriter buf;
						try {
							buf = new BufferedWriter(new FileWriter(file, true));

							String m = "";
							String h = "";
							String s = "";
							if (minutes > 0) {
								if (minutes == 1) {
									m = minutes
											+ " "
											+ getResources().getString(
													R.string.min) + " ";
								} else {
									m = minutes
											+ " "
											+ getResources().getString(
													R.string.mins) + " ";
								}
							}
							if (hours > 0) {
								if (hours == 1) {
									h = hours
											+ " "
											+ getResources().getString(
													R.string.h) + " ";
								} else {
									h = hours
											+ " "
											+ getResources().getString(
													R.string.hs) + " ";
								}
							}
							if (seconds > 0) {
								if (seconds == 1) {
									s = seconds
											+ " "
											+ getResources().getString(
													R.string.sec) + " ";
								} else {
									s = seconds
											+ " "
											+ getResources().getString(
													R.string.secs) + " ";
								}
							}
							buf.append("\r\n");
							Calendar c = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"HH:mm:ss");
							time2 = sdf.format(c.getTime());

							String all = h + m + s + hiss + " " + time1 + " "
									+ time2 + " " + labelt + " " + labelc;

							all.trim();
							buf.append(all);
							buf.close();
							kkk = 0;
							buf.close();
						} catch (IOException e) {
							
							// catch block
							e.printStackTrace();
						}
					}

				}

				st.setClickable(true);
				stop.setClickable(false);
				category.setClickable(true);
				stopp = 1;

				if (stopp == 1) {

					String m = "";
					String h = "";
					String s = "";

					if (minutes > 0) {
						if (minutes == 1) {
							m = minutes + " "
									+ getResources().getString(R.string.min)
									+ " ";
						} else {
							m = minutes + " "
									+ getResources().getString(R.string.mins)
									+ " ";
						}
					}
					if (hours > 0) {
						if (hours == 1) {
							h = hours + " "
									+ getResources().getString(R.string.h)
									+ " ";
						} else {
							h = hours + " "
									+ getResources().getString(R.string.hs)
									+ " ";
						}
					}
					if (seconds > 0) {
						if (seconds == 1) {
							s = seconds + " "
									+ getResources().getString(R.string.sec)
									+ " ";
						} else {
							s = seconds + " "
									+ getResources().getString(R.string.secs)
									+ " ";
						}
					}

					Toast.makeText(
							getApplicationContext(),
							getResources().getString(R.string.works) + " " + h
									+ " " + m + " " + s + " ",
							Toast.LENGTH_SHORT).show();

					setStopped(true);
					rotateAnimation.cancel();
					rotateAnimation.reset();
					img.clearAnimation();
					seconds = 0;
					minutes = 0;
					hours = 0;
					stopp = 0;
					k = 0;
					kkk = 0;
				}
				catc = 0;

				categ.setText(R.string.selcategory);
			

				popupWindow2.dismiss();

			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				st.setClickable(false);
				Thread myThread = null;
				k = 1;
				Runnable runnable = new CountDownRunner();
				myThread = new Thread(runnable);
				myThread.start();

				category.setClickable(false);
				stop.setClickable(true);
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
	
	
	public PopupWindow popupSettings() {

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_settings_meniu, null);

		
		final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		final EditText titlu=(EditText) view.findViewById(R.id.editText1);
		titlu.setText(prefs.getString("Titlu", "User"));
		
	
		
		final ImageButton en = (ImageButton) view.findViewById(R.id.imageButton1);
		final ImageButton de = (ImageButton) view.findViewById(R.id.imageButton2);
		final ImageButton ro = (ImageButton) view.findViewById(R.id.imageButton3);
		final ImageButton ru = (ImageButton) view.findViewById(R.id.imageButton4);
		
		
	   if(prefs.getString("lang", "en").equals("en")){
		   en.setBackgroundResource(R.drawable.en2);
		   eni=1;
		}else if(prefs.getString("lang", "en").equals("de")){
			de.setBackgroundResource(R.drawable.de2);
			dei=1;
		}else if(prefs.getString("lang", "en").equals("ro")){
			ro.setBackgroundResource(R.drawable.ro2);
			roi=1;
		}else if(prefs.getString("lang", "en").equals("ru")){
			ru.setBackgroundResource(R.drawable.ru2);
			rui=1;
		}
		
		en.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				if(eni==0){
				eni=1;
				dei=0;
				roi=0;
				rui=0;
				en.setBackgroundResource(R.drawable.en2);
				de.setBackgroundResource(R.drawable.de);
				ro.setBackgroundResource(R.drawable.ro);
				ru.setBackgroundResource(R.drawable.ru);
				ch=1;
				}else{
				eni=0;
				ch=0;
				en.setBackgroundResource(R.drawable.en);
				}

			}
		});
		
	    
		de.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				if(dei==0){
					eni=0;
					dei=1;
					roi=0;
					rui=0;
					en.setBackgroundResource(R.drawable.en);
					de.setBackgroundResource(R.drawable.de2);
					ro.setBackgroundResource(R.drawable.ro);
					ru.setBackgroundResource(R.drawable.ru);
					ch=1;
					}else{
					dei=0;
					de.setBackgroundResource(R.drawable.de);
					ch=0;
					}

			}
		});
		
		ro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				if(roi==0){
					eni=0;
					dei=0;
					roi=1;
					rui=0;
					en.setBackgroundResource(R.drawable.en);
					de.setBackgroundResource(R.drawable.de);
					ro.setBackgroundResource(R.drawable.ro2);
					ru.setBackgroundResource(R.drawable.ru);
					ch=1;
					}else{
					roi=0;
					ro.setBackgroundResource(R.drawable.ro);
					ch=0;
					}

			}
		});
		
		ru.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				if(rui==0){
					eni=0;
					dei=0;
					roi=0;
					rui=1;
					en.setBackgroundResource(R.drawable.en);
					de.setBackgroundResource(R.drawable.de);
					ro.setBackgroundResource(R.drawable.ro);
					ru.setBackgroundResource(R.drawable.ru2);
					ch=1;
					}else{
					rui=0;
					ru.setBackgroundResource(R.drawable.ru);
					ch=0;
					}

			}
		});
		
		
		final Button cla = (Button) view.findViewById(R.id.button1);
		cla.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				popupWindow2.dismiss();
				PopupWindow popupwindow_obj = popupDisplayca();
				popupwindow_obj.showAtLocation(cla, Gravity.CENTER, 0, 0);

			}

		});
		
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				if(!titlu.getText().toString().equals(prefs.getString("Titlu", "User")) || ch==1){
					
					SharedPreferences.Editor editor = prefs.edit();
					if(eni==1){
						editor.putString("lang", "en");
					}else if(dei==1){
						editor.putString("lang", "de");
					}else if(roi==1){
						editor.putString("lang", "ro");
					}else if(rui==1){
						editor.putString("lang", "ru");
					}
					if(!titlu.getText().toString().equals(prefs.getString("Titlu", "User"))){
		            editor.putString("Titlu", titlu.getText().toString());
		            }
		            editor.commit();
		            
		            Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.saved) + " ",
							Toast.LENGTH_SHORT).show();

					Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					finish();
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
				}
				

				
          InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
          imm.hideSoftInputFromWindow(titlu.getWindowToken(), 0);
				
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
	
	
	public PopupWindow popupDisplayca() {

		final PopupWindow popupWindow2 = new PopupWindow(this);

		// inflate your layout or dynamically add view
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.popup_clear_activities, null);

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				String fpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/"

				+ "settings/" + "save.btw";

				File file = new File(fpath);
				file.delete();

				String fpath2 = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/Btw/user/"

				+ "settings/" + "other.btw";

				File file2 = new File(fpath2);
				file2.delete();

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
	public void onResume() {
		super.onResume(); // Always call the superclass method first

		// Get the Camera instance as the activity achieves full user focus

	}

	@Override
	public void onPause() {
		super.onPause(); // Always call the superclass method first

	}

	@Override
	protected void onStop() {
		super.onStop(); // Always call the superclass method first

	}

	@Override
	protected void onRestart() {
		super.onRestart(); // Always call the superclass method first

	}

}
