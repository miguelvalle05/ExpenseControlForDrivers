package com.example.traz;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {



   TextInputEditText user;
     TextInputEditText pas;
    MaterialButton login;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      user = findViewById(R.id.login_inp_username);
      pas = findViewById(R.id.login_inp_password);
      login = findViewById(R.id.login_btn_login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username=user.getText().toString();
                final String password=pas.getText().toString();


                Response.Listener<String> responListener= new Response.Listener<String>()

                {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonResponse= new JSONObject(response);







                            boolean success= jsonResponse.getBoolean("success");

                            if(success){

                                Integer id_tipo_usuario = jsonResponse.getInt("id_tipo_usuario");

                                if (id_tipo_usuario==1){

                                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);

                                }
                                if(id_tipo_usuario==2){

                                    Intent intent= new Intent(getApplicationContext(),Home_Proceso.class);
                                    startActivity(intent);

                                }
                                if(id_tipo_usuario==3){

                                    Intent intent= new Intent(getApplicationContext(),Home_Consultor.class);
                                    startActivity(intent);

                                }










                            }else{
                                AlertDialog.Builder builder= new AlertDialog.Builder(Login.this);
                                builder.setMessage("Usuario o Contraseña Incorrecto")
                                        .setNegativeButton("Aceptar",null)
                                        .create().show();
                            }

                        } catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };


                LoginRequest loginRequest= new LoginRequest(username,password,responListener);
                RequestQueue queue=Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);

            }
        });



    }




}
