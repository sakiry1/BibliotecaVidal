package com.example.breysi.biblio_virtual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CuentaUsuario extends AppCompatActivity implements View.OnClickListener {
    TextView tv_nom_usuari, tv_poblacion, tv_provincia, tv_curso, tv_cognom, tv_email, tv_dni;
    String nom_user;
    Button btn_cerrar_sesion;
    String poblacion, provincia, curso, email, dni, cognom;
    Usuario user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuenta);

        tv_nom_usuari = (TextView) findViewById(R.id.tv_nom_apellido_usuario);
        tv_poblacion = (TextView) findViewById(R.id.tv_poblacion);
        tv_provincia = (TextView) findViewById(R.id.tv_provincia);
        tv_curso = (TextView) findViewById(R.id.tv_curso);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_dni = (TextView) findViewById(R.id.tv_dni);
        user = (Usuario) getIntent().getParcelableExtra("usuarioo");

        btn_cerrar_sesion = (Button) findViewById(R.id.btn_cerrar_sesion);

        nom_user = user.getNombre();
        poblacion = user.getPoblacion();
        provincia = user.getProvincia();
        curso = user.getCurso();
        email = user.getEmail();
        cognom = user.getApellido();
        dni = user.getDni();

        tv_nom_usuari.setText(nom_user + " " + cognom);
        tv_poblacion.setText(poblacion);
        tv_provincia.setText(provincia);
        tv_curso.setText(curso);
        tv_email.setText(email);
        tv_dni.setText(dni);

        btn_cerrar_sesion.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btn_cerrar_sesion:
                SharedPreferences.Editor editor = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE).edit();
                editor.putString("e_mail", "");
                editor.putString("password", "");
                editor.apply(); //guarda cambios
                i = new Intent(CuentaUsuario.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                Log.e("SharedPreferences HECHO", "Se ha vaciado la cuenta");
                break;
            default:
        }
    }
}