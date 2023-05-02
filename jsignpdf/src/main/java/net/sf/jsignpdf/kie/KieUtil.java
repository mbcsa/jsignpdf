/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sf.jsignpdf.kie;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.credentials.EnteredTokenCredentialsProvider;

/**
 *
 * @author maro
 */
public class KieUtil {
    
    static final long serialVersionUID = 1L;
    
    private static final String jbpm_url = "http://jbpm.test.corsisa.com.ar/kie-server/services/rest/server";
    private static final String jbpm_uname = "admin";
    private static final String jbpm_pass = "Corsisa01";
 
    private String authToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoRTZ2c2NfSnlUZW9YYkQ5NDdFWjRwVkZiYWRET3lQODYyVnBzUVY5QzRvIn0.eyJleHAiOjE2ODI5OTUyOTMsImlhdCI6MTY4Mjk5NDk5MywiYXV0aF90aW1lIjoxNjgyOTkyNDU3LCJqdGkiOiJmNjBlZWRhMy0yNjlhLTRiYmUtYTQyOC0zZTEyMWQ5ODM0ZTIiLCJpc3MiOiJodHRwOi8vYXV0aC50ZXN0LmNvcnNpc2EuY29tLmFyL3JlYWxtcy9DTkgiLCJhdWQiOlsia2llLWV4ZWN1dGlvbi1zZXJ2ZXIiLCJyZWFsbS1tYW5hZ2VtZW50Iiwia2llIiwiYWNjb3VudC1jb25zb2xlIiwiYnJva2VyIiwiYWNjb3VudCJdLCJzdWIiOiI2NzIwODZlNy0wYmQyLTRlMDktYTBjNC1hNTFkYjM0NjllYWIiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJqYnBtLWZyb250ZW5kIiwibm9uY2UiOiJhMjk1MDJiZC1hYWI0LTQ5YzItODcxNi03MWUxMGEzYzY3NWYiLCJzZXNzaW9uX3N0YXRlIjoiODQ5YzEyZDAtODRlYi00ZTljLWFiNzYtNTEwMTljNjY4NDc2IiwiYWNyIjoiMCIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJtYW5hZ2VyIiwibWFuYWdlLXVzZXJzIiwiQ29uY2VzaW9uYXJpbyIsImFkbWluIiwicHJvY2Vzcy1hZG1pbiIsIm9mZmxpbmVfYWNjZXNzIiwiYW5hbHlzdCIsInJlc3QtYWxsIiwiZGVmYXVsdC1yb2xlcy1jbmgiLCJkZXZlbG9wZXIiLCJ1bWFfYXV0aG9yaXphdGlvbiIsInJlc3QtcHJvamVjdCIsImtpZS1zZXJ2ZXIiLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsia2llLWV4ZWN1dGlvbi1zZXJ2ZXIiOnsicm9sZXMiOlsicHJvY2Vzcy1hZG1pbiIsInVtYV9wcm90ZWN0aW9uIiwiYWRtaW4iLCJyZXN0LWFsbCIsImFuYWx5c3QiLCJraWUtc2VydmVyIiwidXNlciJdfSwicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsInZpZXctcmVhbG0iLCJtYW5hZ2UtaWRlbnRpdHktcHJvdmlkZXJzIiwiaW1wZXJzb25hdGlvbiIsInJlYWxtLWFkbWluIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJqYnBtLWZyb250ZW5kIjp7InJvbGVzIjpbInVtYV9wcm90ZWN0aW9uIl19LCJraWUiOnsicm9sZXMiOlsicHJvY2Vzcy1hZG1pbiIsIm1hbmFnZXIiLCJ1bWFfcHJvdGVjdGlvbiIsIkNvbmNlc2lvbmFyaW8iLCJhZG1pbiIsInJlc3QtYWxsIiwiYW5hbHlzdCIsImRldmVsb3BlciIsInJlc3QtcHJvamVjdCIsImtpZS1zZXJ2ZXIiLCJncnVwb1BydWViYSIsInVzZXIiXX0sImFjY291bnQtY29uc29sZSI6eyJyb2xlcyI6WyJtYW5hZ2UtdXNlcnMiXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwiZGVsZXRlLWFjY291bnQiLCJtYW5hZ2UtY29uc2VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGFhIGF0cmlidXRvcyBwcm9maWxlIGVtYWlsIHJvbGVzIHVzZXJuYW1lIiwic2lkIjoiODQ5YzEyZDAtODRlYi00ZTljLWFiNzYtNTEwMTljNjY4NDc2IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImdyb3VwcyI6W10sInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.odH3N3aVOhnANfvCP1AFMSzxYx58nuGA01T2C2Av9ctCEPi-AdEc_2PIc2uJUTPkZh8QNGb17fro_-D0wyO8gs0ZheOGKIO5ffYAbXOrpbtB1_8qtjXsPRxdOBHq8Q63rYWsk1arawYdnfHhnqbXz8FvWis8Gxh4c8n6x8IhGLATNHdwm6riT8sbE4rP16A_CufsoUXKtezUieoIzYE3mhRHVMe3drgjyHH8QnzMZOltXmjE7VrXkq6xxhU2cyi5h7hojYPx-VW_mR2ZJYz_3VLiNZqqM95wGjLHGslNQjRSc3zu74ewAQtSv3bh9B8XG6iaJTanOiSyNiGck_KwAw";
    
    public KieUtil(String authToken) {
        // this.authToken = authToken;
    }
    
    public KieServicesClient getKieServicesClient(){
        CredentialsProvider credentialsProvider = new EnteredTokenCredentialsProvider(this.authToken);
        System.out.println(this.authToken);
        System.out.println(credentialsProvider.getHeaderName());
        System.out.println(credentialsProvider.getAuthorization());
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(jbpm_url, credentialsProvider);
        // KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(jbpm_url,jbpm_uname, jbpm_pass);
        config.setMarshallingFormat(MarshallingFormat.JSON);
        return KieServicesFactory.newKieServicesClient(config);
    }
}
