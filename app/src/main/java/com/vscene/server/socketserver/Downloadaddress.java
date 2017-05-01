package com.vscene.server.socketserver;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.vscene.server.server.R;

public class Downloadaddress extends Activity {

	private TextView addresstxtView = null;
	private String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address);
		addresstxtView = (TextView) findViewById(R.id.addresstxtView);
		Bundle bundle = getIntent().getExtras();
		address = bundle.getString("name");
		addresstxtView.setText(address);
	}

}
