package com.aurus.server.engine;

import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;

public record EngineDerivedWeatherDataReadyEvent(DerivedWeatherDataModel derivedWeatherDataModel) {

}
