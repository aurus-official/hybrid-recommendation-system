package com.aurus.server.engine;

import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;

public record EngineDerivedSensorDataReadyEvent(DerivedSensorDataModel derivedSensorDataModel) {

}
