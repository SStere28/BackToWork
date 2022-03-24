package com.SSI.Mms;


import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

@SuppressLint({ "ShowToast", "InflateParams" })
public class MainActivity extends Activity {

	int k = 0;
	final Context context = this;
	private ProgressBar pr;
	String titlu = "User";
	String color = "c10";
	private Locale myLocale;
	String lang = "en";
	 private static final String PREFS_NAME = "B.T.W.";
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint({ "NewApi", "InflateParams" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pr = (ProgressBar) findViewById(R.id.progressBar);

		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
			return;
		}

		
	        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	        changeLang(prefs.getString("lang", "en"));

	        if(!prefs.getBoolean("isFirst", false)){
	            addShortcut();
	            SharedPreferences.Editor editor = prefs.edit();
	            editor.putBoolean("isFirst", true);
	            editor.commit();

	        }
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent in = new Intent(getApplicationContext(), Meniu.class);
				startActivity(in);

				pr.animate();
				overridePendingTransition(R.anim.abc_fade_in,
						R.anim.abc_fade_out);

			}
		}, 3000);

	}

	public void changeLang(String lang) {
		if (lang.equalsIgnoreCase(""))
			return;
		myLocale = new Locale(lang);

		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

	}
	private void addShortcut() {

        Intent shortcutIntent = new Intent(getApplicationContext(),MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, this.getResources().getString(R.string.app_name));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                R.drawable.logo));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }
}
