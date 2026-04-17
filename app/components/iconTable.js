import {
    Ionicons,
    MaterialIcons,
    MaterialCommunityIcons,
    FontAwesome6,
    Entypo,
    FontAwesome,
    FontAwesome5,
} from "@expo/vector-icons";
import { Colors } from "../constants/Colors";

const IconTable = (colorScheme) => {
    const theme = Colors[colorScheme] || Colors.light;

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
        light: (
            <Entypo color={theme.whitePrimaryColor} name="light-up" size={20} />
        ),
        balance: (
            <FontAwesome
                color={theme.whitePrimaryColor}
                name="balance-scale"
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
        evaporation: (
            <Entypo color={theme.whitePrimaryColor} name="air" size={20} />
        ),
        plant: (
            <FontAwesome6
                color={theme.whitePrimaryColor}
                name="plant-wilt"
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
        probe: (
            <MaterialCommunityIcons
                color={theme.whitePrimaryColor}
                name="thermometer-probe"
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
    };

    return table;
};

export default IconTable;
