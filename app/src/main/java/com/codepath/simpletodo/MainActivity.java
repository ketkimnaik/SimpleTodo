package com.codepath.simpletodo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT = "item text";
    public static final String KEY_ITEM_POSITION = "item position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button buttonAdd;
    EditText editTextTextPersonName;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        rvItems = findViewById(R.id.rvItems);

//        editTextTextPersonName.setText("I am doing this from Java");

        loadItems();
//        items = new ArrayList<>();
//        items.add("Milk");
//        items.add("Egg");
//        items.add("Bread");

        ItemsAdapter.OnLongClickListener  onLongClickListener =  new ItemsAdapter.OnLongClickListener() {
            @Override
            public void OnItemLongClicked(int position) {
//                delete item from list
                items.remove(position);

//                notify the adapter
                itemsAdapter.notifyItemRemoved(position);

                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };

        ItemsAdapter.OnClickListner onClickListner = new ItemsAdapter.OnClickListner() {
            @Override
            public void OnItemClicked(int position) {
                Log.d("MainActivity", "Single click at position" + position);
//              create the new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
//              pass the data being edited
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
//              Display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };



        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListner);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editTextTextPersonName.getText().toString();

                //Add item to the model
                items.add(todoItem);

                //notify the adapter
                itemsAdapter.notifyItemInserted(items.size()-1);

                editTextTextPersonName.setText("");

                //pop up window to show that item added successfully
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });


    }
//handle result of edit activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
//      retrieve the edited text value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
    //      extract the original position of the edited item from position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

    //      update the model at right position with new item text
            items.set(position, itemText);
    //      notify the adapter
            itemsAdapter.notifyItemChanged(position);
    //      persist the changes
            saveItems();

            Toast.makeText(getApplicationContext(), "Item is updated", Toast.LENGTH_SHORT).show();

        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

//    load items by reading every line of the dat file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading item", e);
            items = new ArrayList<>();
        }
    }

//    this function saves items by writing them into data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing item", e);
        }
    }

}