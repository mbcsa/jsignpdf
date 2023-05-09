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
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import static net.sf.jsignpdf.Constants.LOGGER;
import net.sf.jsignpdf.SimpleSignPdfForm;
import net.sf.jsignpdf.utils.GuiUtils;
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
    private String jbpmHost;
    
    private static final String kie_url = "/kie-server/services/rest/server";

    public void loadOptions(String[] args) {
        
        try {
            if (args != null && args.length > 0) {
                receiveOptions(args);
            } else {
                SimpleWebServer.start(this);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Cannot load options", e);
            JOptionPane.showMessageDialog(null, "El tiempo especificado para la firma expirado. Vuelva a iniciar el proceso de firma desde el portal.");
            System.exit(0);
        }
    }
    
    public void loadOptionsFromWebServer(String[] args) {
        System.out.println("Options Received");
        // SimpleWebServer.stop();
        receiveOptions(args);
    }
    
    public void receiveOptions(String[] args) {
        boolean validation = true;
        System.out.println("Parametros: " + args.length);
        for (String arg : args){
            System.out.println(arg);
        }
        if (args.length != 5) {
            validation = false;
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, "Cantidad de parámetros incorrectos (" + args.length + ")");
        }
        
        if (args[0].length() == 0) {
            validation = false;
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, "Parámetro incorrecto (1)");
        }
        
        if (args[1].length() == 0) {
            validation = false;
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, "Parámetro incorrecto (2)");
        }
        
        if (!isNumeric(args[2])) {
            validation = false;
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, "Parámetro incorrecto (3)");
        }
        
        if (!isNumeric(args[3])) {
            validation = false;
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, "Parámetro incorrecto (4)");
        }
        
        if (!isJWT(args[4])) {
            validation = false;
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, "Parámetro incorrecto (5)");
        }
        
        if (validation == false) {
            JOptionPane.showMessageDialog(null, "Ocurrió un problema al intentar establecer la conexión con el Servidor.");            
            System.exit(0);
        }
        
        this.jbpmHost = args[0];
        this.containerId = args[1];
        this.processInstanceId = Long.parseLong(args[2]);
        this.taskId = Long.parseLong(args[3]);
        this.jwt = args[4];
        
        //verify and use
        JWebToken incomingToken;
        try {
            System.out.println(this.jwt);
            incomingToken = new JWebToken(this.jwt);
            //TODO: Validar token cuando se resuelva la clave.
            // if (!incomingToken.isValid()) {
            // Subject tiene el ID de usuario
            this.userId = incomingToken.getSubject();
            System.out.println(incomingToken.getSubject());
            System.out.println(incomingToken.getAudience());
            // }
            boolean showGui = true;

            if (showGui) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.err.println("Can't set Look&Feel.");
                }
                SimpleSignPdfForm tmpForm = new SimpleSignPdfForm(WindowConstants.EXIT_ON_CLOSE, this, null);
                tmpForm.pack();
                GuiUtils.center(tmpForm);
                tmpForm.setVisible(true);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KieOptions.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo autenticar con el Servidor.");            
            System.exit(0);
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
    
    public String getJbpmUrl() {
        return this.jbpmHost + kie_url;
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
