package com.example.onlineexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class UpdataLevelActivity extends AppCompatActivity {
        EditText  editText;
        Button update;
        ArrayList<Level> array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_level);
        editText=findViewById(R.id.edit_level_update);
        update=findViewById(R.id.btn_level_update);

        final Intent intent=getIntent();
        final String name =intent.getStringExtra("name");
        editText.setText(name);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
