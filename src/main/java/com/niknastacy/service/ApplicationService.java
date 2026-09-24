package com.niknastacy.service;

import com.niknastacy.dto.ApplicationCardResponse;
import com.niknastacy.dto.SubmitApplicationRequest;
import com.niknastacy.dto.SubmitApplicationResponse;
import com.niknastacy.entity.Application;
import com.niknastacy.entity.ApplicationStatus;
import com.niknastacy.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CamundaWorkflowClient camundaWorkflowClient;
    private final ObjectMapper objectMapper;

    public List<ApplicationCardResponse> getUserApplications(String userId) {
        List<Application> applications = applicationRepository.findAllByUserId(userId);

        return applications.stream()
                .map(this::mapToCardResponse)
                .toList();
    }

    @Transactional
    public SubmitApplicationResponse submitApplication(String applicationId, String userId, SubmitApplicationRequest request) {
        if (request.getAddress() == null || request.getAddress().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Поле 'address' обязательно для заполнения");
        }

        Application application = applicationRepository.findByIdAndUserId(applicationId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не найдена"));

        if (application.getStatus() != ApplicationStatus.READY_FOR_DOCS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Оформить заявку можно только в статусе READY_FOR_DOCS. Текущий статус: " + application.getStatus());
        }

        try {
            String payloadJson = objectMapper.writeValueAsString(request);
            application.setPayload(payloadJson);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при конвертации payload в JSON", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка сериализации данных заявки");
        }

        application.setStatus(ApplicationStatus.SUBMITTED);
        applicationRepository.save(application);
        camundaWorkflowClient.sendSubmittedSignal(applicationId);

        return SubmitApplicationResponse.builder()
                .applicationId(application.getId())
                .status(application.getStatus().name())
                .success(true)
                .build();
    }

    private ApplicationCardResponse mapToCardResponse(Application app) {
        return ApplicationCardResponse.builder()
                .applicationId(app.getId())
                .status(app.getStatus().name())
                .statusTitle(app.getStatus().getTitle())
                .statusDescription(app.getStatus().getDescription())
                .progressPercent(app.getStatus().getProgressPercent())
                .canFillDocs(app.getStatus().getCanFillDocs())
                .createdAt(app.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }
}
