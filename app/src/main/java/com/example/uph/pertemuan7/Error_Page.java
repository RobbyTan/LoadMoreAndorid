package com.example.uph.pertemuan7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Error_Page extends AppCompatActivity {


    private ImageView retry;
    private TextView txtMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error__page);
        retry = (ImageView) findViewById(R.id.retry);
        txtMessage = (TextView) findViewById(R.id.txt_message);
        txtMessage.setText(getIntent().getStringExtra("ERROR_INFO"));
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Error_Page.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
