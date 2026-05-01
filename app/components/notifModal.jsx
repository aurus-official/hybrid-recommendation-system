import { AntDesign } from '@expo/vector-icons'
import { Button, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import ReactNativeModal from 'react-native-modal'
import { useFarmData } from '../contexts/farmDataProvider';

const NotifModal = ({ isClicked, handleClick, currentTheme }) => {
    const theme = currentTheme;
    const { notificationData } = useFarmData();
    console.log("NOTIFICATIONS : " + notificationData)
    return (
        <ReactNativeModal style={{ height: "100%", width: "100%", padding: 0, margin: 0 }} coverScreen={true} visible={isClicked} animationType="fade" transparent={true}>
            <View style={{ height: "100%", width: "100%", flex: 1, backgroundColor: 'rgba(0,0,0,0.5)', justifyContent: 'center' }}>
                <View style={{ ...styles.containerStyle, backgroundColor: theme.cardBackgroundColor }}>
                    <Text style={{ ...styles.title1, color: theme.textPrimaryColor }}>Notifications</Text>
                    <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >View most recent notifications</Text>

                    <TouchableOpacity style={{ marginTop: "auto" }} name="" onPressIn={handleClick} activeOpacity={0.75}>
                        <View style={{ ...styles.buttonContainer, backgroundColor: theme.primaryColor, borderColor: theme.primaryColor }}>
                            <AntDesign style={styles.buttonIconStyle} name='close' size={18} color={theme.whitePrimaryColor} />
                            <Text style={{ ...styles.buttonStyle, color: theme.whitePrimaryColor }} >Close</Text>
                        </View>
                    </TouchableOpacity>
                </View>
            </View>
        </ReactNativeModal>
    )
}

export default NotifModal

const styles = StyleSheet.create({
    containerStyle: {
        height: "560",
        padding: 20,
        display: "flex",
        flexDirection: "column",
        paddingTop: 32,
        paddingBottom: 40,
        paddingLeft: 20,
        paddingRight: 20,
        borderRadius: 12
    },
    title1: {
        fontSize: 24,
        fontFamily: "Inter_500Medium",
        letterSpacing: -1
    },
    subTitle1: {
        fontSize: 16,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        marginLeft: 1,
        marginBottom: 32
    },
    buttonContainer: {
        borderRadius: 12,
        boxSizing: "border-box",
        borderWidth: 1,
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        paddingRight: 16,
        marginRight: 4,
    },
    buttonStyle: {
        fontSize: 16,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        marginLeft: 12,
        marginRight: 12,
        paddingTop: 8,
        paddingBottom: 8,
    },
    buttonIconStyle: {
        marginLeft: 20,
    },
    dataButtonContainer: {
        marginBottom: 24
    },
})
