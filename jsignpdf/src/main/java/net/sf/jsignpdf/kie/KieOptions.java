/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sf.jsignpdf.kie;

import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author maro
 */
public class KieOptions {
    private String jwt;
    private Long processInstanceId;
    
    public void loadOptions(String[] args) {
        
        if (args.length != 2) {
            System.out.println("Cantidad de parámetros incorrectos");
            return;
        }
        
        if (!isJWT(args[0])) {
            System.out.println("Parámetro incorrecto (1)");
        }
        
        if (!isNumeric(args[1])) {
            System.out.println("Parámetro incorrecto (2)");
        }
        
        this.jwt = args[0];
        this.processInstanceId = Long.parseLong(args[1]);
        
    }
    
    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isJWT(String jwt) {
        String[] jwtSplitted = jwt.split("\\.");
        if (jwtSplitted.length != 3) // The JWT is composed of three parts
            return false;
        try {
            String jsonFirstPart = new String(Base64.getDecoder().decode(jwtSplitted[0]));
            JSONObject firstPart = new JSONObject(jsonFirstPart); // The first part of the JWT is a JSON
            if (!firstPart.has("alg")) // The first part has the attribute "alg"
                return false;
            String jsonSecondPart = new String(Base64.getDecoder().decode(jwtSplitted[1]));
            JSONObject secondPart = new JSONObject(jsonSecondPart); // The first part of the JWT is a JSON
            //Put the validations you think are necessary for the data the JWT should take to validate
        }catch (JSONException err){
            return false;
        }
        return true;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    
    
}
