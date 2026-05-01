import { StyleSheet, Text, useColorScheme, View } from 'react-native'

const NotificationItem = ({ text, icon, subText, currentTheme }) => {
    const theme = currentTheme;

    return (
        <View style={{
            ...styles.notifItemStyle,
            borderColor: theme.notifBorderColor,
            boxShadow: [{
                offsetX: 0,
                offsetY: 0,
                blurRadius: 4,
                color: theme.notifBorderColor
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

export default NotificationItem

const styles = StyleSheet.create({
    notifItemStyle: {
        width: "89%",
        minHeight: "50",
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
        marginLeft: 16,
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
        marginLeft: 24,
        marginRight: 24,
        fontWeight: "semibold",
        opacity: 0.75
    },
    subText: {
        marginLeft: 8,
        fontSize: 16
    }
})
