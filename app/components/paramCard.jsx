import { StyleSheet, Text, useColorScheme, View } from 'react-native'
import { Colors } from '../constants/Colors';

const ParamCard = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;

    return (
        <View style={{
            ...styles.paramCardStyle,
            borderColor: theme.paramBorderColor,
            boxShadow: [{
                offsetX: 0,
                offsetY: 0,
                blurRadius: 4,
                color: theme.paramBorderColor
            }]
        }}>
            <Text>Hello world</Text>
        </View>
    )
}

export default ParamCard

const styles = StyleSheet.create({
    paramCardStyle: {
        width: "42%",
        minHeight: "140",
        borderStyle: "solid",
        borderWidth: 1.25,
        borderRadius: 12,
        // boxShadow: "3 1 1 0 #5F6A6A",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    }
})
