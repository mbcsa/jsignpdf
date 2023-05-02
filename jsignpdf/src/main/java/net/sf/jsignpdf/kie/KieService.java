/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sf.jsignpdf.kie;

import java.util.List;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;

/**
 *
 * @author maro
 */
public class KieService {
    
    public static void main(String[] args){
        KieService kieService = new KieService();
    }
    
    public static List<ProcessInstance> getProcessInstances(String authToken, String containerId) {
        KieUtil util = new KieUtil(authToken);
        KieServicesClient kieServicesClient = util.getKieServicesClient();
        ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        return processClient.findProcessInstances(containerId, 0, 1000);
    }
    
    public static ProcessInstance getProcessInstance(String token, String containerId, Long processInstanceId) {
        KieUtil util = new KieUtil(token);
        KieServicesClient kieServicesClient = util.getKieServicesClient();
        ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        return processClient.getProcessInstance(containerId, processInstanceId, true);
    }
    
    
    
}
