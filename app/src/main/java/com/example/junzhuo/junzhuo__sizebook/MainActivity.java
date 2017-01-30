package com.example.junzhuo.junzhuo__sizebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "Person_db.sav";
    public final static String EXTRA_MESSAGE = "com.example.NAME";

    private TextView total_text;
    private ListView listView;

    //private ArrayAdapter<Person> PersonAdapter;
    PersonArrayAdapter PersonAdapter;
    private ArrayList<Person> PersonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        total_text = (TextView) findViewById(R.id.total);
        listView = (ListView) findViewById(R.id.listview_main);

        update_view();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        update_view();
    }

    public void update_view() {
        //readDB
        readDB();

        total_text.setText("Totoal Record: " + new Integer(PersonList.size()).toString());

        //set item list;
        listView.setAdapter(null);
        PersonAdapter = new PersonArrayAdapter(this, PersonList);
        listView.setAdapter(PersonAdapter);
    }

    public void addPerson(View view) {

        Intent intent = new Intent(this, addPerson.class);
        intent.putExtra(EXTRA_MESSAGE, "");
        startActivityForResult(intent, 1);
        //update_view();
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

    public void showmyDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do this action");
        builder.setMessage("do you want confirm this action?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do do my action here
                PersonList.remove(pos);
                UpdatePersonDB();
                dialog.dismiss();
                update_view();
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}

