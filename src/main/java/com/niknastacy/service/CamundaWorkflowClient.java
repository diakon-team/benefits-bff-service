package com.niknastacy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CamundaWorkflowClient {

    public void sendSubmittedSignal(String applicationId) {
        log.info("Camunda signal 'ApplicationSubmitted' successfully sent for application: {}", applicationId);
    }
}
