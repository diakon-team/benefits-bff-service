package com.niknastacy.service;

import com.niknastacy.dto.ApplicationCardResponse;
import com.niknastacy.entity.Application;
import com.niknastacy.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<ApplicationCardResponse> getUserApplications(String userId) {
        List<Application> applications = applicationRepository.findAllByUserId(userId);

        return applications.stream()
                .map(this::mapToCardResponse)
                .toList();
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
