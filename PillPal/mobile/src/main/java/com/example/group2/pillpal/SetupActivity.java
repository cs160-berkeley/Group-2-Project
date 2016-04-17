package com.example.group2.pillpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);

        ImageButton setupButton = (ImageButton) findViewById(R.id.setup_button);
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailedStats = new Intent(view.getContext(), NavigationDrawer.class);
                startActivity(detailedStats);
            }
        });
    }
}
