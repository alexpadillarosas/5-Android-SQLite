package com.blueradix.android.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blueradix.android.sqlite.entities.Monster;
import com.blueradix.android.sqlite.services.DataService;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataService monsterDataService;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private SeekBar scarinessSeekBar;
    private EditText idEditText;
    private Integer scarinessLevel = 0;
    private Monster monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://developer.android.com/topic/libraries/view-binding?utm_medium=studio-assistant-stable&utm_source=android-studio-3-6
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monsterDataService = new DataService();
        monsterDataService.init(this);
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

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear(view);
            }
        });

    }

    private void clear(View view) {
        nameEditText.getText().clear();
        descriptionEditText.getText().clear();
        scarinessSeekBar.setProgress(0, true);
        nameEditText.requestFocus();
        idEditText.getText().clear();
    }

    private void add(View view) {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        if(name.trim().isEmpty()) {
            Snackbar.make(view, "The Monster's name cannot be empty ", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(description.trim().isEmpty()) {
            Snackbar.make(view, "The Monster's description cannot be empty ", Snackbar.LENGTH_SHORT).show();
            return;
        }
        monster = new Monster(null, name, description, scarinessLevel, null, 0, 0);

        //we have to import the material design to use snackbar
        //import com.google.android.material.snackbar.Snackbar;
        Long result = monsterDataService.add(monster);
        if (result > 0)
            Snackbar.make(view, "Your monster was added ", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(view, "Error, we couldn't add the monster ", Snackbar.LENGTH_SHORT).show();
    }

    private void delete(View view) {
        String id = idEditText.getText().toString();
        if (isMonsterIdEmpty(view, id))
            return;
        monster = new Monster();
        monster.setId(Long.valueOf(id));
        boolean result = monsterDataService.delete(monster);
        if (result)
            Snackbar.make(view, "Monster id " + id + " was deleted ", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(view, "Error, Monster id " + id + " was not deleted ", Snackbar.LENGTH_SHORT).show();
    }

    private boolean isMonsterIdEmpty(View view, String id) {
        //clean trailing and leading empty spaces and then check if the size is not 0
        if(id.trim().isEmpty()){
            Snackbar.make(view, "You must input the Monster's Id", Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void update(View view) {
        String id = idEditText.getText().toString();

        if (isMonsterIdEmpty(view, id))
            return;

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        monster = new Monster();
        monster.setId(Long.valueOf(id));
        monster.setName(name);
        monster.setDescription(description);
        monster.setScariness(scarinessLevel);

        boolean result = monsterDataService.update(monster);
        if (result)
            Snackbar.make(view, "Monster id " + id + " was updated ", Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(view, "Monster id " + id + " was not updated ", Snackbar.LENGTH_SHORT).show();
    }


    private void viewAll(View view) {
        List<Monster> monsters = monsterDataService.getMonsters();
        String text = "";

        if (monsters.size() > 0) {
            for( Monster monster : monsters){
                text = text.concat(monster.toString());
            }
            showMessage("Data", text);
        } else {
            showMessage("Records", "Nothing found");
        }
    }


    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.show();
    }

}
