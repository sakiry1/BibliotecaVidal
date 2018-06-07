package com.example.breysi.biblio_virtual;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPrestamos extends BaseAdapter {
    public ArrayList<Prestamo> listapre = new ArrayList<Prestamo>();
    private LayoutInflater l_Inflater;
    Context context;
    private DatabaseReference Refprestados;


    public AdaptadorPrestamos(Context context, ArrayList<Prestamo> results) {
        listapre = results;
        l_Inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listapre.size();
    }

    @Override
    public Prestamo getItem(int posicion) {
        return listapre.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.lista_libros_prestados, null);

        } else {

        }

        final Prestamo libropres = getItem(i); //prestamo
        ImageView iv_libro = (ImageView) convertView.findViewById(R.id.imageView2);
        TextView tv_titulo = (TextView) convertView.findViewById(R.id.tv_titulo);
        TextView tv_entrega = (TextView) convertView.findViewById(R.id.tv_fentrega);
        Button tv_devolver = (Button) convertView.findViewById(R.id.btn_devolver);

        tv_entrega.setText(libropres.getFecha_devolución());
        try {
            tv_titulo.setText(libropres.libro.getTitulo());
            Picasso.with(context).load(libropres.libro.getPortada()).placeholder(R.mipmap.ic_launcher).into(iv_libro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_devolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarPrestamos(libropres);

            }
        });
        return convertView;
    }


    public void borrarPrestamos(Prestamo prestamo) {

        Refprestados = FirebaseDatabase.getInstance().getReference();
        final Query borrar = Refprestados.child("libros_prestados").orderByChild("id_libro").equalTo(prestamo.getId_libro());
        borrar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshotlibrosPrestados : dataSnapshot.getChildren()) {
                        Prestamo prestamo1 = snapshotlibrosPrestados.getValue(Prestamo.class);
                        snapshotlibrosPrestados.getRef().removeValue();
                    }
                }
                borrar.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
