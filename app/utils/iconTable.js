import {
    Ionicons,
    MaterialIcons,
    MaterialCommunityIcons,
    FontAwesome6,
    Entypo,
    FontAwesome,
    FontAwesome5,
    AntDesign,
} from "@expo/vector-icons";

const IconTable = (currentTheme) => {
    const theme = currentTheme;

    const table = {
        water: (
            <Ionicons color={theme.whitePrimaryColor} name="water" size={20} />
        ),
        volts: (
            <MaterialIcons
                color={theme.whitePrimaryColor}
                name="electric-bolt"
                size={20}
            />
        ),
        "weather-clock": (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="weather-cloudy-clock"
                size={20}
            />
        ),
        weather: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="weather-cloudy"
                size={20}
            />
        ),
        temperature: (
            <FontAwesome6
                color={theme.whitePrimaryColor}
                name="temperature-half"
                size={20}
            />
        ),
        hardware: (
            <Ionicons
                color={theme.whitePrimaryColor}
                name="hardware"
                size={20}
            />
        ),
        "water-percent": (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="water-percent"
                size={20}
            />
        ),
        chip: (
            <FontAwesome
                color={theme.whitePrimaryColor}
                name="microchip"
                size={20}
            />
        ),
        cropOperation: (
            <FontAwesome5
                color={theme.whitePrimaryColor}
                name="seedling"
                size={20}
            />
        ),
        microclimate: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="weather-cloudy-clock"
                size={20}
            />
        ),
        soilNutrient: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="pot-steam"
                size={20}
            />
        ),
        irrigation: (
            <FontAwesome5
                color={theme.whitePrimaryColor}
                name="hand-holding-water"
                size={20}
            />
        ),
        combinedSoilMoisture: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="water-percent"
                size={28}
            />
        ),

        plantStressIndex: (
            <FontAwesome6
                color={theme.whitePrimaryColor}
                name="plant-wilt"
                size={20}
            />
        ),
        evaporationDryingRisk: (
            <Entypo color={theme.whitePrimaryColor} name="air" size={20} />
        ),
        soilFertilityIndex: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="flask-plus"
                size={20}
            />
        ),
        heatStressIndex: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="heat-wave"
                size={20}
            />
        ),
        uvStressIndex: (
            <MaterialIcons
                color={theme.whitePrimaryColor}
                name="wb-twilight"
                size={20}
            />
        ),

        lightGrowthIndex: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="alarm-light"
                size={20}
            />
        ),

        combinedAgronomicIndex: (
            <FontAwesome5
                color={theme.whitePrimaryColor}
                name="ruler-combined"
                size={20}
            />
        ),
        rainImpactIndex: (
            <FontAwesome5
                color={theme.whitePrimaryColor}
                name="cloud-sun-rain"
                size={20}
            />
        ),

        waterBalanceIndex: (
            <FontAwesome
                color={theme.whitePrimaryColor}
                name="balance-scale"
                size={20}
            />
        ),
        soilTemp: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="thermometer-probe"
                size={20}
            />
        ),
        airTemp: (
            <Entypo color={theme.whitePrimaryColor} name="gauge" size={20} />
        ),
        humidity: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="air-humidifier"
                size={20}
            />
        ),
        pressure: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="gauge"
                size={20}
            />
        ),
        lux: (
            <MaterialIcons
                color={theme.whitePrimaryColor}
                name="light"
                size={20}
            />
        ),
        uv: (
            <Entypo color={theme.whitePrimaryColor} name="light-up" size={20} />
        ),
        tds: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="flask"
                size={20}
            />
        ),
        prongMoisture: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="water-plus"
                size={20}
            />
        ),
        capacitiveMoisture: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="water-plus"
                size={20}
            />
        ),
        tempStress: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="heat-wave"
                size={20}
            />
        ),
        vapourPressureDeficit: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="air-filter"
                size={20}
            />
        ),
        precipitation: (
            <FontAwesome5
                color={theme.whitePrimaryColor}
                name="cloud-rain"
                size={20}
            />
        ),
        precipitationProbability: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="cloud-percent"
                size={20}
            />
        ),
        evapotranspiration: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="grain"
                size={20}
            />
        ),
        ads1: (
            <Ionicons
                color={theme.whitePrimaryColor}
                name="hardware-chip"
                size={20}
            />
        ),
        ads2: (
            <Ionicons
                color={theme.whitePrimaryColor}
                name="hardware-chip"
                size={20}
            />
        ),
        bme280: (
            <MaterialIcons
                color={theme.whitePrimaryColor}
                name="edgesensor-low"
                size={20}
            />
        ),
        guvas12sd: (
            <FontAwesome5
                color={theme.whitePrimaryColor}
                name="fantasy-flight-games"
                size={20}
            />
        ),
        ds18b20: (
            <AntDesign color={theme.whitePrimaryColor} name="line" size={20} />
        ),
    };

    return table;
};

export default IconTable;
