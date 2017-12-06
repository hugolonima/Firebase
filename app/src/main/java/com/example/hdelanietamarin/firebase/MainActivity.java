package com.example.hdelanietamarin.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.*;
import com.google.firebase.*;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
 //firebase-pruebas-188208
    //AIzaSyDqTnvGmg45PDplivxTJ_-lYEzd2I-8mhs
    //Poner esto en el terminal : keytool -exportcert -list -v -alias androiddebugkey -keystore C:\Users\h.de.la.nieta.marin\.android\debug.keystore
//la contraseña por defecto es "android"
    //la clave resultante es:
    //MD5:  BE:5E:E2:6B:E4:FC:F1:F4:AA:20:60:52:9C:C2:30:58
    //SHA1: 8E:E4:40:45:AC:5E:D5:E0:72:98:E9:24:BC:C2:FF:C1:B6:1E:24:41
    //SHA256: 8E:3C:91:F8:2D:4A:E3:C8:0F:48:87:0B:3B:9C:B6:6F:8E:03:39:39:A4:DA:3C:FB:5D:F7:86:82:7F:3E:82:C6
    //Signature algorithm name: SHA1withRSA

    // private TextView lblCielo, lblTemperatura, lblHumedad;
    private Button btn;
    //private ListView list;
    private final String TAGLOG = "Hugo";
    //private ArrayList<String> arrayList = new ArrayList<>();
    DatabaseReference dref;
    private ListView listview;
    private ArrayList<String> list=new ArrayList<>();
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.list);

        //LAYOUT CON TRES TEXTVIEW Y UN BOTÓN
        /*lblCielo = (TextView) findViewById(R.id.lblCielo);
        lblHumedad = (TextView) findViewById(R.id.lblHumedad);
        lblTemperatura = (TextView) findViewById(R.id.lblTemperatura);
        btn = (Button) findViewById(R.id.button);
        final DatabaseReference dbCielo =
                FirebaseDatabase.getInstance().getReference()
                        .child("prediccion-hoy")
                        .child("cielo");*/
        /*dbCielo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                lblCielo.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
       /* DatabaseReference dbPrediccion =
                FirebaseDatabase.getInstance().getReference()
                        .child("prediccion-hoy");*/

        //Con este método al pulsar el botón se actuliza el valor
        //Así que si hacemos un set nos sube al firebase
       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbCielo.setValue("Vacío");

            }
        });


        dbPrediccion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lblCielo.setText(dataSnapshot.child("cielo").getValue().toString());
                lblTemperatura.setText(dataSnapshot.child("temperatura").getValue().toString());
                lblHumedad.setText(dataSnapshot.child("humedad").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        //PASAMOS A HACERLO CON UNA LISTA


       /* DatabaseReference dbDiasSemana =
                FirebaseDatabase.getInstance().getReference()
                        .child("dias-semana");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAGLOG, "onChildAdded: {" + dataSnapshot.getKey() + ": " + dataSnapshot.getValue() + "}");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAGLOG, "onChildChanged: {" + dataSnapshot.getKey() + ": " + dataSnapshot.getValue() + "}");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAGLOG, "onChildRemoved: {" + dataSnapshot.getKey() + ": " + dataSnapshot.getValue() + "}");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAGLOG, "onChildMoved: " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAGLOG, "Error!", databaseError.toException());
            }
        };

        dbDiasSemana.addChildEventListener(childEventListener);

    }*/

       //AHORA CON UNA LISTA EN LA QUE CADA NODO TIENE X DATOS

       /* DatabaseReference dref = FirebaseDatabase.getInstance().getReference();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                arrayList);
        list.setAdapter(arrayAdapter);*/
        btn = (Button) findViewById(R.id.button2);
        edit = (EditText) findViewById(R.id.editText2);

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,list);
        listview.setAdapter(adapter);
        dref=FirebaseDatabase.getInstance().getReference();

        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                list.add(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                list.remove(i);
                adapter.notifyDataSetChanged();
                dref.setValue(list);
                return true;

            }
        });

        //Voy a probar si con esto se añaden nuevos valores

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edit.getText().toString();
                if(!item.equals("")) {
                    addItem(item);
                }

            }
        });
    }

    private void addItem(String name) {
        dref.push().setValue(name);
    }
}
