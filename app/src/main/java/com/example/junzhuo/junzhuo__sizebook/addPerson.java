package com.example.junzhuo.junzhuo__sizebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class addPerson extends AppCompatActivity {

    private static final String FILENAME = "Person_db.sav";
    private EditText name, date, neck, bust, chest, waist, hip, inseam, comment;
    private Person person;
    private Button button;

    private ArrayList<Person> PersonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        neck = (EditText) findViewById(R.id.neck);
        bust = (EditText) findViewById(R.id.bust);
        chest = (EditText) findViewById(R.id.chest);
        waist = (EditText) findViewById(R.id.waist);
        hip = (EditText) findViewById(R.id.hip);
        inseam = (EditText) findViewById(R.id.inseam);
        comment = (EditText) findViewById(R.id.comment);

        button =  (Button) findViewById(R.id.save);

        //load db
        readDB();

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        setResult(1, intent);

        if (message.length() != 0) {
            String[] parts = message.split(":");
            if (parts[0].equals("view")) {
                viewDate(parts[1], true);
            } else {
                viewDate(parts[1], false);
            }
        }

    }

    public void viewDate(String person_name, boolean lock) {
        Person p;
        int index = 0;
        for (int counter = 0; counter < PersonList.size(); counter++) {
            p = PersonList.get(counter);
            if (p.getName().equals(person_name)) {
                //person = p;
                index = counter;
                break;
            }
        }

        person = PersonList.get(index);
        name.setText(person.getName());
        date.setText(person.getDate());
        neck.setText(person.getNeck());
        bust.setText(person.getBust());
        chest.setText(person.getChest());
        waist.setText(person.getWaist());
        hip.setText(person.getHip());
        inseam.setText(person.getInseam());
        comment.setText(person.getComment());

        name.setFocusable(false);

        if (lock){
            //if lock is true in view mode.
            date.setFocusable(false);
            neck.setFocusable(false);
            bust.setFocusable(false);
            waist.setFocusable(false);
            hip.setFocusable(false);
            chest.setFocusable(false);
            inseam.setFocusable(false);
            comment.setFocusable(false);

            button.setText("Done");
        }else {
            //in edit mode, we must remove old content.
            PersonList.remove(index);
        }

    }

    public void saveData(View view){
        if (button.getText() == "Done") {
            finish();
            return;
        }

        Context context = getApplicationContext();

        String PersonName = name.getText().toString();

        if (name.length() == 0) {
            Toast.makeText(context, "YOU MUST ENTRY NAME!", Toast.LENGTH_LONG).show();
            return;
        }

        //vaild date
        DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
        try {
            df.parse(date.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(context, "The date must in yyyy-mm-dd format!", Toast.LENGTH_LONG).show();
            return;
        }


        person = new Person(PersonName);

        person.setBust(bust.getText().toString());
        person.setComment(comment.getText().toString());
        person.setChest(chest.getText().toString());
        person.setDate(date.getText().toString());
        person.setHip(hip.getText().toString());
        person.setInseam(inseam.getText().toString());
        person.setWaist(waist.getText().toString());
        person.setNeck(neck.getText().toString());

        UpdatePersonDB();
        Toast.makeText(context, "Person data saved!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void readDB() {
        Context context = getApplicationContext();
        //read old person list
        try {
            FileInputStream fp = openFileInput(FILENAME);
            ObjectInputStream in = new ObjectInputStream(fp);
            PersonList = (ArrayList<Person>) in.readObject();
            in.close();
            fp.close();
        } catch (FileNotFoundException e) {
            Log.d("READDB", e.toString());
            PersonList = new ArrayList<Person>();
        } catch (IOException e) {
            Log.d("READDB", e.toString());
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d("READDB", e.toString());
            //e.printStackTrace();
        }

    }

    public void UpdatePersonDB() {
        Context context = getApplicationContext();
        //File file = new File(context.getFilesDir(), FILENAME);


       //add to list
        PersonList.add(person);

        //dump object
        try{
            FileOutputStream fileOut = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(PersonList);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            Log.d("UpdatePersonDB", e.toString());
        } catch (IOException e) {
            Log.d("UpdatePersonDB", e.toString());
        }

    }
}
