/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sf.jsignpdf.kie;

import java.util.List;
import java.util.Map;
import org.kie.server.api.model.instance.DocumentInstance;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskAttachment;
import org.kie.server.api.model.instance.TaskInstance;

/**
 *
 * @author maro
 */
public class KieService {
    
    private static String userAuthToken;
    private static KieUtil util;
    
    //create an object of SingleObject
    private static KieService instance;

    //make the constructor private so that this class cannot be
    //instantiated
    private KieService(){
        util = new KieUtil(userAuthToken);
    }

    //Get the only object available
    public static KieService getInstance(String authToken){
        if (instance == null) {
            if (authToken.isBlank() && userAuthToken.isBlank()) {
                throw new NullPointerException("Auth token not assigned.");
            } else {
                if (!authToken.isBlank()) {
                    userAuthToken = authToken;
                }
            }
            instance = new KieService();
        } else {
            
        }
       return instance;
    }
    
    public static List<ProcessInstance> getProcessInstances(String containerId) {
        return util.getKieProcessServicesClient().findProcessInstances(containerId, 0, 1000);
    }
    
    public static ProcessInstance getProcessInstance(String containerId, Long processInstanceId) {
        return util.getKieProcessServicesClient().getProcessInstance(containerId, processInstanceId, true);
    }
    
    public static TaskInstance getTask(String containerId, Long taskId) {
        return util.getKieUserTaskService().getTaskInstance(containerId, taskId, true, true, true);
    }
    
    public static List<TaskAttachment> getTaskAttachments(String containerId, Long taskId) {
        return util.getKieUserTaskService().getTaskAttachmentsByTaskId(containerId, taskId);
    }
    
    public static void claimTask(String containerId, Long taskId, String userId) {
        util.getKieUserTaskService().claimTask(containerId, taskId, userId);
    }
    
    public static void startTask(String containerId, Long taskId, String userId) {
        util.getKieUserTaskService().startTask(containerId, taskId, userId);
    }
    
    public static void completeTask(String containerId, Long taskId, String userId, Map<String, Object> params) {
        util.getKieUserTaskService().completeTask(containerId, taskId, userId, params);
    }
    
    public static DocumentInstance getDocument(String documentId) {
        return util.getKieDocumentServicesClient().getDocument(documentId);
    }
    
    public static void updateDocument(DocumentInstance documentInstance) {
        util.getKieDocumentServicesClient().updateDocument(documentInstance);
    }
}
