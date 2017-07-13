package com.ricindigus.ricardo.loginfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BienvenidaActivity extends AppCompatActivity {

    private static final String TAG = "BienvenidaActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView txtDetalles;
    private Button btnCerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        txtDetalles = (TextView) findViewById(R.id.txtDetalle);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        inicializar();

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

    }

    private void inicializar(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    txtDetalles.setText("Usuario: " + firebaseUser.getEmail());
                    Log.w(TAG, "onAuthStateChanged - Logueado" + firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - Logueado" + firebaseUser.getEmail());
                }else{
                    Log.w(TAG, "onAuthStateChanged - Cerro Session");
                }
            }
        };
    }


    private void cerrarSesion(){
        firebaseAuth.signOut();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

}
