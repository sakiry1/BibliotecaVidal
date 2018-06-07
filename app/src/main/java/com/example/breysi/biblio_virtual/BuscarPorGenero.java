package com.example.breysi.biblio_virtual;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;


public class BuscarPorGenero extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    public static ArrayList<Genero> listaGenero = new ArrayList<Genero>();

    ListView listview_genero;


    final Query q_genero = myRef.child("genero").orderByChild("nombre");

    ValueEventListener event = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_genero);

        listview_genero = (ListView) findViewById(R.id.listview_genero);

        event = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaGenero.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {

                        Genero genero = item.getValue(Genero.class);
                        listaGenero.add(genero);
                    }
                    Lista();
                    q_genero.removeEventListener(event);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listaGenero.clear();
                Lista();
            }
        };

        q_genero.addValueEventListener(event);

    }

    @Override
    public void onClick(View view) {

    }

    private void Lista() {

        listview_genero.setAdapter(null);

        final ArrayList<HashMap<String, String>> generos = new ArrayList<HashMap<String, String>>();

        for (final Genero genero : listaGenero) {
            final String txt_genero = genero.getNombre();

            final HashMap hashMap = new HashMap<String, String>();
            hashMap.put("genero", txt_genero);
            generos.add(hashMap);

            ListAdapter adapter = new SimpleAdapter(BuscarPorGenero.this, generos, R.layout.esquema_genero, new String[]{"genero"}, new int[]{R.id.tv_genero});
            listview_genero.setAdapter(adapter);

            listview_genero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                    final int pos = (position);
                    Intent intent1;
                    intent1 = new Intent(BuscarPorGenero.this, BuscarPorGeneroSeleccionado.class);

                    Genero g = listaGenero.get(pos);
                    intent1.putExtra("nomGenero", g.getNombre());

                    Intent intent2;
                    intent2 = new Intent(BuscarPorGenero.this, BuscarPorGeneroSeleccionado.class);
                    intent2.putExtra("imagen", listview_genero.getListPaddingBottom());
                    startActivity(intent1);
                }
            });
        }
    }
}