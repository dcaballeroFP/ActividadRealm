package com.example.actividadrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.example.actividadrealm.Model.Persona;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    EditText nombre,apellido,edad,id,nombrecompleto;
    CheckBox masculino,femenino;
    Button añadir,mostrar,modificar,eliminar;
    TextView mostrarResultado;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        id=(EditText) findViewById(R.id.id);
        nombre=(EditText) findViewById(R.id.nombre);
        apellido=(EditText) findViewById(R.id.apellido);
        edad=(EditText) findViewById(R.id.edad);
        masculino=(CheckBox) findViewById(R.id.genereMasculi);
        femenino=(CheckBox) findViewById(R.id.genereFemeni);
        añadir=(Button) findViewById(R.id.añadir);
        mostrar=(Button) findViewById(R.id.mostrar);
        modificar=(Button) findViewById(R.id.modificar) ;
        eliminar=(Button) findViewById(R.id.eliminar);
        mostrarResultado=(TextView) findViewById(R.id.mostrarResultados);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_to_datanbase(
                        //----------------------------------------------------------------------------------------------
                        //VERSION 0
                        //nombre.getText().toString().trim(), apellido.getText().toString().trim(),
                        //VERSION 1
                        nombrecompleto.getText().toString().trim(), Integer.parseInt(edad.getText().toString().trim()));
                //----------------------------------------------------------------------------------------------
                calcularId();
            }


        });

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh_database();
            }
        });

        //----------------------------------------------------------------------------------------------
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------------------------------------------------------------------------------------------
                //VERSION 0
                //String name= nombre.getText().toString();
                //String surname= apellido.getText().toString();
                //----------------------------------------------------------------------------------------------
                //VERSION 1
                String nombrecompl= nombrecompleto.getText().toString();
                //----------------------------------------------------------------------------------------------
                String age = edad.getText().toString();
                updateFromDatabase(nombrecompl,age);/*name,surname*/
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------------------------------------------------------------------------------------------
                //VERSION 0
                //String name= nombre.getText().toString();
                //----------------------------------------------------------------------------------------------
                //VERSION 1
                String nombrecompl= nombrecompleto.getText().toString();
                //----------------------------------------------------------------------------------------------
                delete_from_database(nombrecompl/*name*/);
            }

        });
    }

    private void save_to_datanbase(
            //----------------------------------------------------------------------------------------------
            //VERSION 0
            //final String name, final String surname,
            //----------------------------------------------------------------------------------------------
            //VERSION 1
            final String nombrecompleto, final int age) {
        //----------------------------------------------------------------------------------------------
        final int index= calcularId();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Persona persona = realm.createObject(Persona.class,index);
                //----------------------------------------------------------------------------------------------
                //VERSION 0
                //persona.setNombre(name);
                //persona.setApellidos(surname);
                //----------------------------------------------------------------------------------------------
                //VERSION 1
                persona.setNombreCompleto(nombrecompleto);
                //----------------------------------------------------------------------------------------------
                persona.setEdad(age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("Succes", "--------->Ok<----------");

            }
        }, new Realm.Transaction.OnError(){
            @Override
            public void onError(Throwable error){
                Log.e("Failed", error.getMessage());
            }
        });
    }
    private void updateFromDatabase(
            //----------------------------------------------------------------------------------------------
            //VERSION 0
            //final String name, final String surname,
            //----------------------------------------------------------------------------------------------
            //VERSION 1
            final String nombrecompleto, final String age) {
        //----------------------------------------------------------------------------------------------
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                int identificador = Integer.parseInt(String.valueOf(Integer.parseInt(id.getText().toString())));
                Persona persona = realm.where(Persona.class)
                        .equalTo("identificador", identificador)
                        .findFirst();
                if (persona != null) {
                    //----------------------------------------------------------------------------------------------
                    //VERSION 0
                    //persona.setNombre(name);
                    //persona.setApellidos(surname);
                    //----------------------------------------------------------------------------------------------
                    //VERSION 1
                    persona.setNombreCompleto(nombrecompleto);
                    //----------------------------------------------------------------------------------------------
                    persona.setEdad(Integer.parseInt(age));

                    realm.insertOrUpdate(persona);
                    refresh_database();
                }
            }
        });
    }
    private void refresh_database() {
        RealmResults<Persona> results = realm.where(Persona.class)
                .findAllAsync();
        results.load();

        String personas="";
        for(Persona persona:results){
            personas+=
                    //----------------------------------------------------------------------------------------------
                    //VERSION 0
                    //"\t Nombre: "+persona.getNombre()+"\n\t Apellido: "+persona.getApellidos()+
                    //----------------------------------------------------------------------------------------------
                    //VERSION 1
                    "\t Nombre Completo: "+persona.getNombreCompleto()+"\n\t Edad:"+persona.getEdad()+"\n";
            //----------------------------------------------------------------------------------------------
        }
        mostrarResultado.setText(personas);
    }

    private void delete_from_database(String name) {
        final RealmResults<Persona> personas = realm.where(Persona.class).equalTo("nombre", name).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                personas.deleteFromRealm(0);
            }
        });
    }

    private final static int calcularId(){

        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(Persona.class).max("identificador");

        int nextId;
        if (currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}