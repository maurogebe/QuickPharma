package com.example.demo.application.usecases;

import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.model.*;
import com.ibm.watson.assistant.v2.Assistant;
import org.springframework.stereotype.Service;

@Service
public class WatsonService {

    private final Assistant assistant;
    private final String session;

    public WatsonService() {
        IamAuthenticator authenticator = new IamAuthenticator("GTMvJd-3dnhX1YhGPrDP0FvXQ7ud9xO4UbCG7BzPSfYa");
        Assistant newAssistant = new Assistant("2021-06-14", authenticator);
        newAssistant.setServiceUrl("https://api.us-south.assistant.watson.cloud.ibm.com/instances/1bbef4d8-a350-43ab-9800-79176c3bc78c");

        this.assistant = newAssistant;

        CreateSessionOptions options = new CreateSessionOptions.Builder("7264cc12-76e6-4b5c-8e43-d8fe58684c48").build();
        SessionResponse session = assistant.createSession(options).execute().getResult();

        // Get the Session ID
        String sessionId = session.getSessionId();
        System.out.println("Session ID: " + sessionId);
        this.session = sessionId;
    }

    public StatefulMessageResponse sendMessage(String message) {

//        Response<WorkspaceCollection> res = assistant.listWorkspaces().execute();
//        System.out.println(res);


        MessageInput input = new MessageInput.Builder()
                .text("Hi")
                .build();
        MessageOptions messageOptions = new MessageOptions.Builder()
                .assistantId("7264cc12-76e6-4b5c-8e43-d8fe58684c48")
                .sessionId(session)
                .input(input)
                .build();
        StatefulMessageResponse messageResponse = assistant.message(messageOptions).execute().getResult();
        System.out.println(messageResponse);
        return messageResponse;
    }
}
