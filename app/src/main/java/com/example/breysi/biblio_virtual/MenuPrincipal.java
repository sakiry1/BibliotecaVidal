package com.example.breysi.biblio_virtual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference myRef;
    TextView tx;
    ImageButton btn_libros_prestados;
    ImageButton btn_anadir_libros;
    ImageButton btn_cuenta;
    Usuario user;

    @Override
    public void onBackPressed() {
        Log.e("Finish", "BACKPRESSED");
        //this.finish();
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        tx = (TextView) findViewById(R.id.textView);
        btn_libros_prestados = (ImageButton) findViewById(R.id.btn_libros_prestados);
        btn_anadir_libros = (ImageButton) findViewById(R.id.btn_buscar_anadir_libros);
        btn_cuenta = (ImageButton) findViewById(R.id.btn_cuenta);


        btn_libros_prestados.setOnClickListener(this);
        btn_anadir_libros.setOnClickListener(this);
        btn_cuenta.setOnClickListener(this);


        user = (Usuario) getIntent().getParcelableExtra("usuarioo");
        String nom_user = user.getNombre();
        String apellido_usuario = user.getApellido();
        tx.setText(nom_user + " " + apellido_usuario);

    }



    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_libros_prestados:
                intent = new Intent(MenuPrincipal.this, LibrosPrestados.class);
                startActivity(intent);
                break;
            case R.id.btn_buscar_anadir_libros:
                intent = new Intent(MenuPrincipal.this, BuscarLibros.class);
                startActivity(intent);
                break;
            case R.id.btn_cuenta:
                intent = new Intent(MenuPrincipal.this, CuentaUsuario.class);
                user = (Usuario) getIntent().getParcelableExtra("usuarioo");
                intent.putExtra("usuarioo", user);
                startActivity(intent);
                break;

            default:
                Toast.makeText(MenuPrincipal.this, "Error", Toast.LENGTH_LONG).show();
        }
    }
}
