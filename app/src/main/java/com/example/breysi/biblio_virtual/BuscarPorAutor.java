package com.example.breysi.biblio_virtual;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.HashMap;


public class BuscarPorAutor extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ListView listview_autor;
    public static ArrayList<Libro> listaLibro = new ArrayList<>();
    ImageView imageViewlibro;

    EditText tv_buscar;
    ImageButton btn_buscar;
    ArrayAdapter<String> adapter;
    final Query q_autor = myRef.child("libro").orderByChild("autor");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_autor);

        tv_buscar = (EditText) findViewById(R.id.tv_buscar2);
        imageViewlibro = (ImageView) findViewById(R.id.imageView_libro);
        btn_buscar = (ImageButton) findViewById(R.id.btn_buscar2);
        listview_autor = (ListView) findViewById(R.id.lista_libros_autor);
        btn_buscar.setOnClickListener(this);


        q_autor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    listaLibro.clear();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Libro libro = item.getValue(Libro.class);
                        listaLibro.add(libro);
                    }
                    Lista();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void buscarLibro(String textBuscar) {
        Query q = myRef.child("libro").orderByChild("autor_mini").startAt(textBuscar.toLowerCase()).endAt(textBuscar.toLowerCase().concat("{"));
        q.addValueEventListener(new listenerAutor());

    }

    public class listenerAutor implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                listaLibro.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Libro libro = item.getValue(Libro.class);
                    listaLibro.add(libro);
                }
                Lista();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


    private void Lista() {

        listview_autor.setAdapter(null);

        for (final Libro libro : listaLibro) {
            String titulo = libro.getTitulo();
            String autor = libro.getAutor();
            String portada = libro.getPortada();

            //MI ADAPTADOR (CLASS)
            ListAdapter adapter = new AdaptadordeListas(BuscarPorAutor.this, listaLibro);
            listview_autor.setAdapter(adapter);

            listview_autor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    final int pos = (position);
                    Intent intent1;
                    //TODO
                    intent1 = new Intent(BuscarPorAutor.this, MostrarDatosLibro.class);
                    intent1.putExtra("posicion", pos);
                    Intent intent2;
                    intent2 = new Intent(BuscarPorAutor.this, MostrarDatosLibro.class);
                    intent2.putExtra("imagen", libro.getPortada());
                    startActivity(intent1);
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buscar2:
                String textBuscar = tv_buscar.getText().toString();
                buscarLibro(textBuscar);
                break;
        }
    }
}
