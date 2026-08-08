package com.aurus.server.hardware_status;

import java.time.LocalDateTime;

public record HardwareStatusOutputDTO(
        long id,
        boolean ads1,
        boolean ads2,
        boolean bme280,
        boolean guvas12sd,
        boolean ds18b20,
        LocalDateTime createdAt) {

}
