package com.ted.torch;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main extends Activity {
	SensorManager sensorManager;
	private static final int SENSOR_SHAKE = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		try {
			Log.i("tag", "~~~~~~~~~~~�������~~~~~~~~~~~");
			camera = Camera.open();
			camera.startPreview();
			Parameters parameters = camera.getParameters();
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
         open  =(Button) findViewById(R.id.open);
		
	open.setOnClickListener(new  OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			try {
				Log.i("tag", "~~~~~~~~~~~�������~~~~~~~~~~~");
				camera = Camera.open();
				camera.startPreview();
				Parameters parameters = camera.getParameters();
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tv.setText("�Ѿ���");
			open.setText("      ");
		}
	});	
	}

	
	
	TextView tv;
	Button open ;
	
	
	@Override
	protected void onResume() {
		super.onResume();

		if (sensorManager != null) {// ע�������
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
			// ��һ��������Listener���ڶ������������ô��������ͣ�����������ֵ��ȡ��������Ϣ��Ƶ��
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (sensorManager != null) {// ȡ��������
			sensorManager.unregisterListener(sensorEventListener);
		}
	}

	/**
	 * ������Ӧ����
	 */

	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// ��������Ϣ�ı�ʱִ�и÷���
			float[] values = event.values;
			float x = values[0];
			float y = values[1];
			float z = values[2];

			System.out.println(x + y + z);

			if (x + y + z >= 20) {

				Message msg = new Message();
				msg.what = SENSOR_SHAKE;
				handler1.sendMessage(msg);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	/**
	 * ����ִ��
	 */
	 Camera  camera;
	Handler handler1 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:
				try {
					Log.i("tag", "~~~~~~~~~~~�ر������~~~~~~~~~~~");
					Parameters parameters = camera.getParameters();
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
					camera.setParameters(parameters);
					camera.release();
					camera = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			tv = (TextView) findViewById(R.id.textView1);
				tv.setText("�Ѿ��ر�");
				open.setText("�ٴδ�");
			}

		}

	};
	
	
	
	
	

}
