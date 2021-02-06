package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText edItem;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edItem = findViewById(R.id.edItem);
        button3 = findViewById(R.id.button3);

        getSupportActionBar().setTitle("Edit Item");

        edItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

//        when user done editing button is clicked
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//           create an intent to store result that user has modified
                Intent i = new Intent();
//           Pass the result of editing
                i.putExtra(MainActivity.KEY_ITEM_TEXT, edItem.getText().toString());
                i.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
//           set the result of intent
                setResult(RESULT_OK, i);
//            finish the activity, close the screen and go back
                finish();
            }
        });
    }
}