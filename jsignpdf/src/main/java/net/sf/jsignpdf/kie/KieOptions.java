/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sf.jsignpdf.kie;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author maro
 */
public class KieOptions {
    private String jwt;
    private Long processInstanceId;
    private String containerId;
    private Long taskId;
    private String userId;

    public void loadOptions(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Cantidad de parámetros incorrectos");
        }
        
        if (args[0].length() == 0) {
            throw new IllegalArgumentException("Parámetro incorrecto (1)");
        }
        
        if (!isNumeric(args[1])) {
            throw new IllegalArgumentException("Parámetro incorrecto (2)");
        }
        
        if (!isNumeric(args[2])) {
            throw new IllegalArgumentException("Parámetro incorrecto (3)");
        }
        
        if (!isJWT(args[3])) {
            throw new IllegalArgumentException("Parámetro incorrecto (4)");
        }
        
        this.containerId = args[0];
        this.processInstanceId = Long.parseLong(args[1]);
        this.taskId = Long.parseLong(args[2]);
        this.jwt = args[3];
        
        //verify and use
        JWebToken incomingToken;
        try {
            System.out.println(this.jwt);
            incomingToken = new JWebToken(this.jwt);
            //TODO: Validar token cuando se resuelva la clave.
            // if (!incomingToken.isValid()) {
            // List<String> audience = incomingToken.getAudience();
            // Subject tiene el ID de usuario
            this.userId = incomingToken.getSubject();
            // }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
