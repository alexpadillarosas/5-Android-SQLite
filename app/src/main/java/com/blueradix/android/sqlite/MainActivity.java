package com.blueradix.android.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;


import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText nameEditText;
    EditText descriptionEditText;
    SeekBar scarinessSeekBar;
    EditText idEditText;
    Integer scarinessLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        //once your database is created, you can find it using Device File Explorer
        //go to: data/data/app_name/databases there you will find your databases

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        scarinessSeekBar = findViewById(R.id.scarinessSeekBar);
        idEditText = findViewById(R.id.idEditText);

        scarinessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                scarinessLevel = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(view);
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(view);
            }
        });

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(view);
            }
        });

        Button viewAllButton = findViewById(R.id.viewAllButton);
        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAll(view);
            }
        });

    }

    private void add(View view) {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        boolean result = databaseHelper.insert(name, description, scarinessLevel);
        //we have to import the material design to use snackbar
        //import com.google.android.material.snackbar.Snackbar;
        if (result)
            Snackbar.make(view, "Your monster was added ", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(view, "Error, we couldn't add the monster ", Snackbar.LENGTH_SHORT).show();
    }

    private void delete(View view) {
        String id = idEditText.getText().toString();
        boolean result = databaseHelper.delete(Integer.valueOf(id));
        if (result)
            Snackbar.make(view, "Monster id " + id + " was deleted ", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(view, "Error, Monster id " + id + " was not deleted ", Snackbar.LENGTH_SHORT).show();
    }


    private void update(View view) {
        String id = idEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        boolean result = databaseHelper.update(Integer.valueOf(id), name, description, scarinessLevel);
        if(result)
            Snackbar.make(view, "Monster id " + id + " was updated ", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(view, "Monster id " + id + " was not updated ", Snackbar.LENGTH_SHORT).show();
    }


    private void viewAll(View view) {
        Cursor cursor = databaseHelper.getAll();

        if(cursor.getCount()==0){
            cursor.close();
            showMessage("Records", "Nothing found");
        }else{
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                buffer.append("Id: " + cursor.getString(0) + "\n");
                buffer.append("Name: " + cursor.getString(1) + "\n");
                buffer.append("description: " + cursor.getString(2) + "\n");
                buffer.append("scariness: " + cursor.getString(3) + "\n");
                buffer.append("image: " + cursor.getString(4) + "\n");
                buffer.append("_____________________________" + "\n");
            }

            cursor.close();
            showMessage("Data", buffer.toString());
        }
    }

    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.show();
    }

}