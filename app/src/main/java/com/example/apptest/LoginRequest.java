package com.example.apptest;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

<<<<<<< HEAD
    final static private String URL = "http://10.0.2.2:8000/user/signin";
=======
    final static private String URL = "http://www.univ237.com/Login.php";
>>>>>>> 03490e12f29232cbced10504fc11c19846542bbe
    private Map<String, String> map;

    public LoginRequest(String memberId, String password, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("MemberID", memberId);
        map.put("Password", password);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
