import { Colors } from "../constants/Colors";

const SeverityTable = (colorScheme) => {
    const theme = Colors[colorScheme] || Colors.light;

    const table = {
        0: {
            text: "Critical Severity",
            color: theme.highSeverityColor,
        },
        1: {
            text: "High Severity",
            color: theme.highSeverityColor,
        },
        2: {
            text: "Moderate Severity",
            color: theme.mediumSeverityColor,
        },
        3: {
            text: "Low Severity",
            color: theme.lowSeverityColor,
        },
    };
    return table;
};
export default SeverityTable;
