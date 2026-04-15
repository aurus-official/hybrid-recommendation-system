package com.aurus.server.engine;

import java.util.ArrayList;
import java.util.List;

import com.aurus.server.batch.derive.sensor.DerivedSensorDataModel;
import com.aurus.server.batch.derive.weather.DerivedWeatherDataModel;
import com.aurus.server.shared.CategoryType;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class EngineService {

    private KieContainer kieContainer;

    public EngineService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public void startEngine(DerivedSensorDataModel derivedSensorDataModel,
            DerivedWeatherDataModel derivedWeatherDataModel) {

        List<List<EngineCategoryOutputDTO>> categoryOutputLists = new ArrayList<>();

        CategoryType[] categoryTypes = CategoryType.values();

        for (CategoryType categoryType : categoryTypes) {
            KieSession kSession = kieContainer.newKieSession(categoryType.getValue().concat("-session"));
            List<EngineCategoryOutputDTO> intermediateOutputs = new ArrayList<>();

            try {
                kSession.setGlobal("intermediateOutputs", intermediateOutputs);

                kSession.insert(derivedSensorDataModel);
                kSession.insert(derivedWeatherDataModel);
                kSession.fireAllRules();

            } finally {
                categoryOutputLists.add(intermediateOutputs);
                kSession.dispose();
            }
        }

        EngineAggregatorOutputDTO engineAggregatorOutputDTO = new EngineAggregatorOutputDTO(
                categoryOutputLists.get(0),
                categoryOutputLists.get(1),
                categoryOutputLists.get(2),
                categoryOutputLists.get(3));

    }

}
