package com.techno.googlemap.Screens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.techno.googlemap.R;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        findViewById(R.id.btnMarker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SelectionActivity.this,CustomMarker.class);

            }
        });
        findViewById(R.id.btnCluster).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SelectionActivity.this,ClusterActivity.class);
            }
        });
        findViewById(R.id.btnDrag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SelectionActivity.this,DragPinActivity.class);
            }
        });

    }

    public void startActivity(Context context, Class openClass) {
        try {
            Intent intent = new Intent(context, openClass);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
