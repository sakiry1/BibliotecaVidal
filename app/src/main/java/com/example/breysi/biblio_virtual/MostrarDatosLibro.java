package com.example.breysi.biblio_virtual;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;


public class MostrarDatosLibro extends AppCompatActivity {

    Prestamo libropres;
    Calendar calendarDevolucion;
    private Usuario usuactual;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FloatingActionButton actionButton;
    public Libro libro = new Libro();
    List<Libro> listLibro;
    int posicion;
    TextView tv_titulo, tv_autor, tv_fecha_publicacion, tv_editorial, tv_estado,tv_isbn;
    ValueEventListener valuelistener = null;
    ImageView imagen_libro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_del_libro);
        tv_titulo = (TextView) findViewById(R.id.tv_titulo);
        tv_autor = (TextView) findViewById(R.id.tv_autor);
        tv_fecha_publicacion = (TextView) findViewById(R.id.tv_fecha_publicacion);
        tv_editorial = (TextView) findViewById(R.id.tv_editorial);
        imagen_libro = (ImageView) findViewById(R.id.imagen_libro);
        actionButton = findViewById(R.id.floatingActionButton);
        tv_estado = (TextView) findViewById(R.id.tv_estado);
        tv_isbn = (TextView) findViewById(R.id.tv_isbn);

        posicion = getIntent().getIntExtra("posicion", 0);
        listLibro = BuscarLibros.listaLi;
        libro = listLibro.get(posicion);

        tv_titulo.setText(libro.getTitulo());
        tv_autor.setText(libro.getAutor());
        tv_editorial.setText(libro.getEditorial());
        tv_isbn.setText(libro.getISBN());
        tv_fecha_publicacion.setText(libro.getAñoEdicion());

        Picasso.with(getBaseContext()).load(libro.getPortada()).placeholder(R.mipmap.ic_launcher).into(imagen_libro);

        usuactual = Login.usuarioactual;
        comprobarEstado(libro.getId());
        action();


    }


    public void action() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert2 = new AlertDialog.Builder(MostrarDatosLibro.this);
                alert2.setMessage("¿Desea reservar el libro?");
                alert2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reservaLibro(libro.getId());

                    }
                });
                alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog2 = alert2.create();
                alertDialog2.show();

            }
        });
    }
    /**
     * Comprueba el estado actual del libro
     * Si esta prestado o No
     * @param id_libro identificador de libro a comprobar
     */
    public void comprobarEstado(String id_libro) {

        myRef = FirebaseDatabase.getInstance().getReference();
        final Query estado = myRef.child("libros_prestados").orderByChild("id_libro").equalTo(id_libro);
        estado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    tv_estado.setText("Libro NO DISPONIBLE para Reservar");
                } else {
                    tv_estado.setText("Disponible para Reservar");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    /**
     * Reserva el libro seleccionado solo si esta disponible
     *
     * @param idlibro identificador de libro
     */

    public void reservaLibro(final String idlibro) {

        myRef = FirebaseDatabase.getInstance().getReference();
        //busca en librosprestados el id_libro
        final Query reserva = myRef.child("libros_prestados").orderByChild("id_libro").equalTo(idlibro);
        reserva.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {//si existe en libros prestados
                    final AlertDialog.Builder dialog1 = new AlertDialog.Builder(MostrarDatosLibro.this);
                    dialog1.setMessage("NO se puede RESERVAR, YA ESTA RESERVADO");
                    dialog1.show();

                } else {
                    // no existe puede reservar
                    myRef = FirebaseDatabase.getInstance().getReference().child("libros_prestados");
                    libropres = new Prestamo();
                    //recogemos la fecha actual String to Fecha  FECHA-STRING
                    libropres.setfecha_prestamo(Calendario.FechaaString(Calendar.getInstance()));
                    calendarDevolucion = Calendario.reservaMes(Calendar.getInstance());//agrega los 30 dias
                    libropres.setfecha_devolución(Calendario.FechaaString(calendarDevolucion)); //setDEVOLUCION
                    libropres.setid_libro(idlibro);
                    libropres.setid_usuario(usuactual.getDni());

                    myRef.push().setValue(libropres); //agrega el libro reservado al BD
                    final AlertDialog.Builder dialog1 = new AlertDialog.Builder(MostrarDatosLibro.this);
                    dialog1.setMessage("Reserva realizada");
                    dialog1.show();
                }
                reserva.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
