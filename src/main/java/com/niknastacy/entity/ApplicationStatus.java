package com.niknastacy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationStatus {

    CHECKING_SMEV (
            "Проверка в ведомствах",
            "СМЭВ сверяет данные в реестрах льготников",
            25,
            false
    ),
    READY_FOR_DOCS(
            "Льготы подтверждены",
            "Нажмите, чтобы прикрепить документы и завершить оформление",
            50,
            true
    ),
    SUBMITTED(
            "Заявление на исполнении",
            "Документы приняты, ожидается выдача",
            75,
            false
    ),
    APPROVED(
            "Услуга оказана",
            "Льгота оформлена",
            100,
            false
    );

    private final String title;
    private final String description;
    private final Integer progressPercent;
    private final Boolean canFillDocs;

}
