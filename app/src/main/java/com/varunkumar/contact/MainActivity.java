package com.varunkumar.contact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name , email,newmail;
    Button butn,update,delete;
    Db_Helper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.emali);
        butn = (Button)findViewById(R.id.button);
        delete = (Button) findViewById(R.id.delet);
        update = (Button) findViewById(R.id.update);
        newmail =(EditText) findViewById(R.id.newMail);

        dbh=new Db_Helper(this);

        List<Contact> dataRecords=dbh.getAllContacts();

        for (Contact c:dataRecords
             ) {
            Log.i("Data",c.getName()+", "+c.getMail());
        }

         butn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                String nam= name.getText().toString();
                 String em=email.getText().toString();

                // Contact cnyc=new Contact();
                 dbh.addContact(new Contact(nam,em));
                 Toast.makeText(MainActivity.this, "name: "+nam+"email: "+em, Toast.LENGTH_SHORT).show();


             }
         });

         delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String em=email.getText().toString();
                 dbh.deleteContact(new Contact(em));
             }
         });

          update.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  String nam= name.getText().toString();
                  String em=email.getText().toString();
                  String newEmail=newmail.getText().toString();
                  dbh.updateContact(new Contact(nam,em),newEmail);

              }
          });


    }
}
