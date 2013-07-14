package com.google.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {
	private TextView result_textView;
	private Button button_restart;
	private Button button_return;
	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		webview = (WebView) findViewById(R.id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		button_restart = (Button) findViewById(R.id.button_restart);
		button_restart.setOnClickListener(button_listener_restart);
		button_return = (Button) findViewById(R.id.button_return);
		button_return.setOnClickListener(button_listener_return);
		result_textView = (TextView) findViewById(R.id.result_view);
		processIntent();

		MyApplication.getInstance().addActivity(this);
	}

	void processIntent() {
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			String name = bundle.getString("name");
			result_textView.setText(name);
			String hardware = android.os.Build.MODEL;
			String os = "android " + android.os.Build.VERSION.RELEASE;

			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			String screenSize = dm.widthPixels + "X" + dm.heightPixels;

			String contextService = Context.LOCATION_SERVICE;
			// ͨ��ϵͳ����ȡ��LocationManager����
			LocationManager loctionManager = (LocationManager) getSystemService(contextService);

			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);// �߾���
			criteria.setAltitudeRequired(false);// ��Ҫ�󺣰�
			criteria.setBearingRequired(false);// ��Ҫ��λ
			criteria.setCostAllowed(true);// �����л���
			criteria.setPowerRequirement(Criteria.POWER_LOW);// �͹���
			// �ӿ��õ�λ���ṩ���У�ƥ�����ϱ�׼������ṩ��
			String provider = loctionManager.getBestProvider(criteria, true);
			// ������һ�α仯��λ��
			Location location = loctionManager.getLastKnownLocation(provider);

			String loc = "(" + location.getLatitude() + ","
					+ location.getLongitude() + ")";

			Log.d("android", os);
			Log.d("android", hardware);
			Log.d("android", screenSize);
			Log.d("android", loc);

			String url = "http://m1.ampthon.com/m1.php?tag_id=" + name + "&os="
					+ os + "&hardware=" + hardware + "&loc=" + loc
					+ "&screen_size=" + screenSize;

			try {
				webview.loadUrl(url);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	private Button.OnClickListener button_listener_restart = new Button.OnClickListener() { // ��Ա��ť��������
		@Override
		public void onClick(View v) {
			// ��ť�¼�
			// TODO Auto-generated method stub

			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			ResultActivity.this.startActivityForResult(intent, 10);

		}
	};
	private Button.OnClickListener button_listener_return = new Button.OnClickListener() { // ��Ա��ť��������
		@Override
		public void onClick(View v) {
			// ��ť�¼�
			// TODO Auto-generated method stub
			ResultActivity.this.finish();

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String contents = data.getStringExtra("SCAN_RESULT");
		result_textView.setText(contents);
		String info = android.os.Build.PRODUCT;
		String cpu = android.os.Build.CPU_ABI;
		String brand = android.os.Build.BRAND;
	};

	/*
	 * @Override protected void onDestroy() { // TODO Auto-generated method stub
	 * super.onDestroy(); }
	 */
}
