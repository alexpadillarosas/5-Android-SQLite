package com.blueradix.android.sqlite.services;

import android.content.Context;

import com.blueradix.android.sqlite.database.MonsterDatabaseHelper;
import com.blueradix.android.sqlite.entities.Monster;

import java.util.List;

public class DataService {

    private MonsterDatabaseHelper sqlite;

    public void connect(){

    }

    public void disconnect(){

    }

    public void init(Context context){
        sqlite = sqlite.getInstance(context);
    }

    public Long add(Monster monster){
        return sqlite.insert(monster.getName(), monster.getDescription(), monster.getScariness());
    }

    public boolean delete(Monster monster){
        return sqlite.delete(monster.getId());
    }

    public boolean update(Monster monster){
        return sqlite.update(monster.getId(), monster.getName(), monster.getDescription(), monster.getScariness());
    }

    public List<Monster> getAll(){
        List<Monster> monsters = sqlite.getAllMonsters();
        return monsters;
    }

    public Monster getMonster(Long id){
        //TODO implement getting a monster from a database given its primary key
        return null;
    }

    public boolean rateMonster(Long id, Integer stars){
        //TODO implement a mechanism to rate monsters.
        return false;
    }
}
