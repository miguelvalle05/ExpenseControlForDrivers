package com.example.traz;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL="http://10.5.2.124/login/validar_usuario.php";
    private Map<String,String>params;
    public LoginRequest(String user, String pwd, Response.Listener<String> listener){
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        params= new HashMap<>();
        params.put("user",user);
        params.put("pwd",pwd);




    }

    @Override
    public Map<String,String> getParams(){return params;}


}
