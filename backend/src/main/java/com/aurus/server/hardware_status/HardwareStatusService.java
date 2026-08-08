package com.aurus.server.hardware_status;

import java.util.List;

import com.aurus.server.ingestion.hardware_status.HardwareStatusModel;
import com.aurus.server.ingestion.hardware_status.HardwareStatusRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HardwareStatusService {

    private final HardwareStatusRepository hardwareStatusRepository;
    private final int PAGE_SIZE = 8;

    public HardwareStatusService(HardwareStatusRepository hardwareStatusRepository) {
        this.hardwareStatusRepository = hardwareStatusRepository;
    }

    public HardwareStatusPageDTO getHardwareStatusPageDTO(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by(Sort.Order.desc("id")));
        Page<HardwareStatusModel> hardwareStatusModelsPage = hardwareStatusRepository.findAll(pageable);

        List<HardwareStatusSummaryDTO> hardwareStatusDTOs = hardwareStatusModelsPage.toList().stream().map(model -> {
            return new HardwareStatusSummaryDTO(model.getId(), model.getCreatedAt());
        }).toList();

        return new HardwareStatusPageDTO(hardwareStatusDTOs, hardwareStatusModelsPage.getTotalPages());
    }

    public HardwareStatusOutputDTO getHardwareStatusOutputDTO(long id) {
        HardwareStatusModel hardwareStatusModel = hardwareStatusRepository
                .findById(id)
                .orElseGet(() -> new HardwareStatusModel());

        return new HardwareStatusOutputDTO(
                hardwareStatusModel.getId(),
                hardwareStatusModel.isAds1(),
                hardwareStatusModel.isAds2(),
                hardwareStatusModel.isBme280(),
                hardwareStatusModel.isGuvas12sd(),
                hardwareStatusModel.isDs18b20(),
                hardwareStatusModel.getCreatedAt());
    }
}
