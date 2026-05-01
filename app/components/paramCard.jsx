import { StyleSheet, Text, View } from 'react-native'

const ParamCard = ({ text, icon, subText, currentTheme }) => {
    const theme = currentTheme;

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
                    {icon}
                </View>
                <Text style={styles.subText}>{text}</Text>
            </View>
            <Text style={styles.dataText}>{subText}</Text>
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
        width: "81%",
        marginLeft: 16,
        marginTop: 16,
        marginRight: 16

    },
    viewStyleImage: {
        padding: 8,
        borderRadius: 54,
        overflow: "hidden",

    },
    dataText: {
        fontSize: 20,
        marginTop: 32,
        marginBottom: 28,
        fontWeight: "bold"
    },
    subText: {
        marginLeft: 8,
        flex: 1,
        flexShrink: 1

    }
})
