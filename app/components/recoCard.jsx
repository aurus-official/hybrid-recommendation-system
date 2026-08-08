import { StyleSheet, Text, View } from 'react-native'

const RecoCard = ({ text, icon, subText, currentTheme }) => {
    const theme = currentTheme;

    return (
        <View style={{
            ...styles.recoCardStyle,
            borderColor: theme.recoBorderColor,
            boxShadow: [{
                offsetX: 0,
                offsetY: 0,
                blurRadius: 4,
                color: theme.recoBorderColor
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

export default RecoCard

const styles = StyleSheet.create({
    recoCardStyle: {
        minHeight: 148,
        borderStyle: "solid",
        borderWidth: 1.25,
        borderRadius: 12,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        marginHorizontal: 24
    },
    viewStyleSubText: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        alignSelf: "flex-start",
        marginHorizontal: 16,
        marginTop: 16,
    },
    viewStyleImage: {
        padding: 8,
        borderRadius: 54,
        overflow: "hidden",
    },
    dataText: {
        fontSize: 16,
        marginTop: 20,
        marginBottom: 28,
        marginHorizontal: 24,
        fontWeight: "400",
        opacity: 0.75
    },
    subText: {
        marginLeft: 8,
        fontSize: 16
    }
});

