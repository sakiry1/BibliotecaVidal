package com.example.breysi.biblio_virtual;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BuscarPorGeneroSeleccionado extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    EditText tv_buscar;
    ImageButton btn_buscar;
    String nomGenero;
    Button btn_busqueda_avanzada;
    ListView listview_libros;
    ImageView imageViewlibro;
    public static ArrayList<Libro> listaLi = new ArrayList<Libro>();

    ValueEventListener valueListener = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_anadir_libros);

        tv_buscar = (EditText) findViewById(R.id.tv_buscar);
        btn_busqueda_avanzada = (Button) findViewById(R.id.btn_busqueda_avanzada);
        listview_libros = (ListView) findViewById(R.id.listview_libros);
        imageViewlibro = (ImageView) findViewById(R.id.imageView_libro);
        btn_buscar = (ImageButton) findViewById(R.id.btn_buscar);

        btn_busqueda_avanzada.setOnClickListener(this);
        btn_buscar.setOnClickListener(this);

        nomGenero = getIntent().getStringExtra("nomGenero");

        final Query q_libro = myRef.child("libro").orderByChild("genero").equalTo(nomGenero);

        valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaLi.clear();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot item : dataSnapshot.getChildren()) {

                        Libro libro = item.getValue(Libro.class);

                        listaLi.add(libro);
                    }
                    Lista();
                    q_libro.removeEventListener(valueListener);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                listaLi.clear();
            }


        };

        q_libro.addValueEventListener(valueListener);


    }

    public class listenerLibro implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                listaLi.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    Libro libro = item.getValue(Libro.class);

                    listaLi.add(libro);
                }
                Lista();
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            listaLi.clear();

        }
    }

    private void buscarLibro(String textBuscar) {
        Query q = myRef.child("libro").orderByChild("titulo_mini").startAt(textBuscar.toLowerCase()).endAt(textBuscar.toLowerCase().concat("{"));
        q.addValueEventListener(new listenerLibro());

    }

    private void Lista() {

        listview_libros.setAdapter(null);

        for (final Libro libro : listaLi) {
            String titulo = libro.getTitulo();
            String autor = libro.getAutor();
            String portada = libro.getPortada();

            ListAdapter adapter = new AdaptadordeListas(BuscarPorGeneroSeleccionado.this, listaLi);
            listview_libros.setAdapter(adapter);

            listview_libros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    final int pos = (position);
                    Intent intent1;
                    //TODO
                    intent1 = new Intent(BuscarPorGeneroSeleccionado.this, MostrarDatosLibro.class);
                    intent1.putExtra("posicion", pos);
                    Intent intent2;
                    intent2 = new Intent(BuscarPorGeneroSeleccionado.this, MostrarDatosLibro.class);
                    intent2.putExtra("imagen", libro.getPortada());
                    startActivity(intent1);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_buscar:
                String textBuscar = tv_buscar.getText().toString();
                buscarLibro(textBuscar);
                break;
            case R.id.btn_busqueda_avanzada:
                intent = new Intent(BuscarPorGeneroSeleccionado.this, BusquedasAvanzadas.class);
                startActivity(intent);
                break;
        }
    }
}
