package com.aurus.server.hardware_status;

import java.util.List;

public record HardwareStatusPageDTO(List<HardwareStatusSummaryDTO> hardwareStatusSummaryDTOs, int pageCount) {

}
