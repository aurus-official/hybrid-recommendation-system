package com.aurus.server.reading_status;

import java.util.List;

public record ReadingStatusPageDTO(List<ReadingStatusSummaryDTO> readingStatusSummaryDTOs, int pageCount) {

}
