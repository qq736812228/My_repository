package com.obai.platform.service;

import com.obai.platform.dto.BehaviorSubmitRequest;
import com.obai.platform.dto.SelfTestSubmitRequest;
import com.obai.platform.entity.BehaviorRecord;
import com.obai.platform.entity.HealthArchive;
import com.obai.platform.entity.SelfTestRecord;
import com.obai.platform.repository.BehaviorRecordRepository;
import com.obai.platform.repository.HealthArchiveRepository;
import com.obai.platform.repository.SelfTestRecordRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HealthService {
    private final HealthArchiveRepository archiveRepository;
    private final SelfTestRecordRepository selfTestRecordRepository;
    private final BehaviorRecordRepository behaviorRecordRepository;

    public HealthService(HealthArchiveRepository archiveRepository, SelfTestRecordRepository selfTestRecordRepository,
                         BehaviorRecordRepository behaviorRecordRepository) {
        this.archiveRepository = archiveRepository;
        this.selfTestRecordRepository = selfTestRecordRepository;
        this.behaviorRecordRepository = behaviorRecordRepository;
    }

    public HealthArchive archive(Long userId) {
        return archiveRepository.findByUserId(userId).orElseGet(() -> {
            HealthArchive archive = new HealthArchive();
            archive.userId = userId;
            archive.completenessScore = 20;
            return archiveRepository.save(archive);
        });
    }

    public HealthArchive saveArchive(HealthArchive archive, Long userId) {
        archive.userId = userId;
        return archiveRepository.save(archive);
    }

    @Transactional
    public SelfTestRecord submitSelfTest(Long userId, SelfTestSubmitRequest request) {
        SelfTestRecord record = new SelfTestRecord();
        record.userId = userId;
        record.version = request.version() == null ? "V1.1" : request.version();
        record.symptomTags = request.symptomTags();
        record.behaviorTags = request.behaviorTags();
        record.stoolStatus = request.stoolStatus();
        record.sleepStatus = request.sleepStatus();
        record.pressureLevel = request.pressureLevel();
        record.answerJson = request.answerJson();
        record.score = 76;
        record.riskLevel = "LOW";
        record.adviceSummary = "保持长期记录；如出现红旗信号，优先线下医疗评估。";
        return selfTestRecordRepository.save(record);
    }

    public List<SelfTestRecord> selfTests(Long userId) {
        return selfTestRecordRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public BehaviorRecord submitBehavior(Long userId, BehaviorSubmitRequest request) {
        BehaviorRecord record = new BehaviorRecord();
        record.userId = userId;
        record.recordDate = request.recordDate() == null ? LocalDate.now() : request.recordDate();
        record.dietTags = request.dietTags();
        record.exerciseTags = request.exerciseTags();
        record.sleepHours = request.sleepHours();
        record.stoolFrequency = request.stoolFrequency();
        record.pressureLevel = request.pressureLevel();
        record.note = request.note();
        return behaviorRecordRepository.save(record);
    }

    public List<BehaviorRecord> behaviors(Long userId) {
        return behaviorRecordRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
