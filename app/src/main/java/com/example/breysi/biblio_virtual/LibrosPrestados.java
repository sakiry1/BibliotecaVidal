package com.example.breysi.biblio_virtual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LibrosPrestados extends AppCompatActivity {

    ArrayList<Prestamo> listaLibrosPrestados = new ArrayList<Prestamo>(Configuracion.maxlibros);

    Prestamo itemprestadosClass = new Prestamo();
    private DatabaseReference Refprestados;
    ArrayList<HashMap<String, String>> valores = new ArrayList<HashMap<String, String>>();
    ListView ListaViewPrestados;
    Libro itemlibro = new Libro();
    private DatabaseReference myRef;
    private Usuario usuactual;
    TextView nprestados;
    int contador = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libros_prestados);
        ListaViewPrestados = (ListView) findViewById(R.id.listaprestados);
        nprestados = (TextView) findViewById(R.id.no_prestado);
        usuactual = Login.usuarioactual;

        myRef = FirebaseDatabase.getInstance().getReference();
        //consulta sobre los librosPrestados que tiene el usuario, usando su dni
        final Query q_librosPrestados = myRef.child("libros_prestados").orderByChild("id_usuario").equalTo(usuactual.getDni());
        q_librosPrestados.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaLibrosPrestados.clear();
                ListAdapter adapter = new AdaptadorPrestamos(LibrosPrestados.this, listaLibrosPrestados);
                ListaViewPrestados.setAdapter(adapter);
                contador = 0;
                if (contador <= listaLibrosPrestados.size()) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshotlibrosPrestados : dataSnapshot.getChildren()) {
                            itemprestadosClass = snapshotlibrosPrestados.getValue(Prestamo.class);//guarda los prestamos encontrados
                            listaLibrosPrestados.add(itemprestadosClass);//agrega los prestamos a la lista
                            contador++;
                        }
                    } else {//si el usuario no tiene NADA prestado  envia un mensaje
                        nprestados.setText(R.string.no_prestamos);
                    }

                } else {
                    System.out.println("cantidad maxima de prestamos");
                }
                for (final Prestamo prestadosClass : listaLibrosPrestados) {
                    final String libro = prestadosClass.getId_libro();
                    Refprestados = FirebaseDatabase.getInstance().getReference();
                    //consulta el Libro sobre el prestamo
                    final Query info_libro = Refprestados.child("libro").orderByChild("id").equalTo(libro);
                    info_libro.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot keysnap : dataSnapshot.getChildren()) {
                                itemlibro = keysnap.getValue(Libro.class);
                                prestadosClass.libro = itemlibro;
                            }
                            ListAdapter adapter = new AdaptadorPrestamos(LibrosPrestados.this, listaLibrosPrestados);
                            ListaViewPrestados.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


}
