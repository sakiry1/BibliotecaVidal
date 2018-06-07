package com.example.breysi.biblio_virtual;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class BuscarPorEditorial extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ListView listview_editorial;
    public static ArrayList<Libro> listaLibro = new ArrayList<>();
    ImageView imageViewlibro;

    EditText tv_buscarEditorial;
    ImageButton btn_buscarEditorial;
    ArrayAdapter<String> adapter;

    final Query q_editorial = myRef.child("libro").orderByChild("editorial");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_editorial);

        tv_buscarEditorial = (EditText) findViewById(R.id.tv_buscarEditorial);
        btn_buscarEditorial = (ImageButton) findViewById(R.id.btn_buscarEditorial);
        imageViewlibro = (ImageView) findViewById(R.id.imageView_libro);
        listview_editorial = (ListView) findViewById(R.id.lista_libros_editorial);

        btn_buscarEditorial.setOnClickListener(this);

        q_editorial.addValueEventListener(new ValueEventListener() {
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
        Query q = myRef.child("libro").orderByChild("editorial_mini").startAt(textBuscar.toLowerCase()).endAt(textBuscar.toLowerCase().concat("{"));
        q.addValueEventListener(new BuscarPorEditorial.listenerEditorial());

    }


    public class listenerEditorial implements ValueEventListener {
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

        listview_editorial.setAdapter(null);

        for (final Libro libro : listaLibro) {
            String titulo = libro.getTitulo();
            String autor = libro.getAutor();
            String portada = libro.getPortada();


            //MI ADAPTADOR (CLASS)
            ListAdapter adapter = new AdaptadordeListas(BuscarPorEditorial.this, listaLibro);
            listview_editorial.setAdapter(adapter);

            listview_editorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    final int pos = (position);
                    Intent intent1;
                    intent1 = new Intent(BuscarPorEditorial.this, MostrarDatosLibro.class);
                    intent1.putExtra("posicion", pos);
                    Intent intent2;
                    intent2 = new Intent(BuscarPorEditorial.this, MostrarDatosLibro.class);
                    intent2.putExtra("imagen", libro.getPortada());
                    Toast.makeText(BuscarPorEditorial.this, "IMAGEN RECOGIDA || " + libro.getPortada(), Toast.LENGTH_LONG).show();
                    startActivity(intent1);
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buscarEditorial:
                String textBuscar = tv_buscarEditorial.getText().toString();
                buscarLibro(textBuscar);
                break;
        }
    }
}
