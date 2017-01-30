package com.example.junzhuo.junzhuo__sizebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by junzhuo on 1/29/17.
 */

public class PersonArrayAdapter extends ArrayAdapter<Person> {
    public PersonArrayAdapter (Context context, ArrayList<Person> persons) {
        super(context, 0, persons);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Person person = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_row, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.name);
        textView.setText(person.getName());

        Button delete = (Button) convertView.findViewById(R.id.del);
        Button edit = (Button) convertView.findViewById(R.id.edit);

        delete.setTag(position);
        edit.setTag(position);
        textView.setTag(position);

        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Person person = getItem(position);

                Log.d("VIEW: ", String.valueOf(position) + ", " + person.getName());
                Intent intent = new Intent(view.getContext(), addPerson.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, "view:" + person.getName());
                ((Activity) view.getContext()).startActivityForResult(intent, 1);
            }
        });

        //delete
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Person person = getItem(position);

                ((MainActivity) view.getContext()).showmyDialog(position);
            }
        });

        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Person person = getItem(position);

                Intent intent = new Intent(view.getContext(), addPerson.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE, "edit:" + person.getName());
                ((Activity) view.getContext()).startActivityForResult(intent, 1);
            }
        });

        return convertView;
    }
}