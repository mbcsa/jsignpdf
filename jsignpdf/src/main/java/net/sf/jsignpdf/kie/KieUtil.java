/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sf.jsignpdf.kie;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.kie.server.client.UIServicesClient;
import org.kie.server.client.credentials.EnteredTokenCredentialsProvider;
import org.kie.server.client.DocumentServicesClient;

/**
 *
 * @author maro
 */
public class KieUtil {
    
    static final long serialVersionUID = 1L;
    
    private static final String jbpm_url = "http://jbpm.test.corsisa.com.ar/kie-server/services/rest/server";
 
    private String authToken = "";
    
    private static CredentialsProvider credentialsProvider;
    private static KieServicesConfiguration config;
    private static KieServicesClient kieServiceClient;
    private static UserTaskServicesClient userTaskService;
    
    public KieUtil(String authToken) {
        this.authToken = authToken;
        this.credentialsProvider = new EnteredTokenCredentialsProvider(this.authToken);
        // this.config = KieServicesFactory.newRestConfiguration(jbpm_url, credentialsProvider);
        this.config = KieServicesFactory.newRestConfiguration(jbpm_url,"marianocnh", "123");
        this.config.setMarshallingFormat(MarshallingFormat.JSON);
        this.kieServiceClient = KieServicesFactory.newKieServicesClient(this.config);
    }
    
    public UIServicesClient getKieUIServicesClient() {
        return this.kieServiceClient.getServicesClient(UIServicesClient.class);
    }
    
    public ProcessServicesClient getKieProcessServicesClient() {
        return this.kieServiceClient.getServicesClient(ProcessServicesClient.class);
    }
    
    public UserTaskServicesClient getKieUserTaskService() {
        return this.kieServiceClient.getServicesClient(UserTaskServicesClient.class);
    }
    
    public DocumentServicesClient getKieDocumentServicesClient() {
        return this.kieServiceClient.getServicesClient(DocumentServicesClient.class);
    }
}
