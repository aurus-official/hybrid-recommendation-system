import { StyleSheet, Text, useColorScheme, View } from 'react-native'
import { Colors } from '../constants/Colors';
import { Ionicons } from '@expo/vector-icons';

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

            <View style={{ ...styles.viewStyleSubText }}>
                <View style={{ ...styles.viewStyleImage, backgroundColor: theme.primaryColor }}>
                    <Ionicons name='water' size={20} color={theme.whitePrimaryColor} />
                </View>
                <Text style={styles.subText}>Hello world</Text>
            </View>
            <Text style={styles.dataText}>20%</Text>
        </View>
    )
}

export default ParamCard

const styles = StyleSheet.create({
    paramCardStyle: {
        width: "42%",
        minHeight: "148",
        borderStyle: "solid",
        borderWidth: 1.25,
        borderRadius: 12,
        // boxShadow: "3 1 1 0 #5F6A6A",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    },
    viewStyleSubText: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        alignSelf: "flex-start",
        marginLeft: 16
    },
    viewStyleImage: {
        padding: 4,
        borderRadius: 54,
        overflow: "hidden",
    },
    dataText: {
        fontSize: 24,
        marginTop: 32,
        marginBottom: 28,
        fontWeight: "bold"
    },
    subText: {
        marginLeft: 12
    }
})
