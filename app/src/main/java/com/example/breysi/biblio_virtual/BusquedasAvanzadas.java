package com.example.breysi.biblio_virtual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BusquedasAvanzadas extends AppCompatActivity implements View.OnClickListener{
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    ImageButton btn_autor;
    ImageButton btn_editorial;
    ImageButton btn_genero;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busquedas_avanzadas);

        btn_autor = (ImageButton) findViewById(R.id.btn_autor);
        btn_editorial = (ImageButton) findViewById(R.id.btn_editorial);
        btn_genero = (ImageButton) findViewById(R.id.btn_genero);

        btn_autor.setOnClickListener(this);
        btn_genero.setOnClickListener(this);
        btn_editorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_genero:
                intent = new Intent(BusquedasAvanzadas.this, BuscarPorGenero.class);
                startActivity(intent);
                break;
            case R.id.btn_autor:
                intent = new Intent(BusquedasAvanzadas.this, BuscarPorAutor.class);
                startActivity(intent);
                break;
            case R.id.btn_editorial:
                intent = new Intent(BusquedasAvanzadas.this, BuscarPorEditorial.class);
                startActivity(intent);
                break;

            default:
        }
    }
}
