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
			Log.i("tag", "~~~~~~~~~~~打开闪光灯~~~~~~~~~~~");
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
				Log.i("tag", "~~~~~~~~~~~打开闪光灯~~~~~~~~~~~");
				camera = Camera.open();
				camera.startPreview();
				Parameters parameters = camera.getParameters();
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tv.setText("已经打开");
			open.setText("      ");
		}
	});	
	}

	
	
	TextView tv;
	Button open ;
	
	
	@Override
	protected void onResume() {
		super.onResume();

		if (sensorManager != null) {// 注册监听器
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (sensorManager != null) {// 取消监听器
			sensorManager.unregisterListener(sensorEventListener);
		}
	}

	/**
	 * 重力感应监听
	 */

	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// 传感器信息改变时执行该方法
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
	 * 动作执行
	 */
	 Camera  camera;
	Handler handler1 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SENSOR_SHAKE:
				try {
					Log.i("tag", "~~~~~~~~~~~关闭闪光灯~~~~~~~~~~~");
					Parameters parameters = camera.getParameters();
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
					camera.setParameters(parameters);
					camera.release();
					camera = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			tv = (TextView) findViewById(R.id.textView1);
				tv.setText("已经关闭");
				open.setText("再次打开");
			}

		}

	};
	
	
	
	
	

}
