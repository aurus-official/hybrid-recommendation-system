import { Button, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import ReactNativeModal from 'react-native-modal'
import { useRest } from './restProvider';
import { Entypo, MaterialIcons } from '@expo/vector-icons';
import { useEffect, useState } from 'react';

const HistoryModal = ({ handleSetDateTimeTitle, handleSetIsLatestDataToUse, isLoadPastPageDataClicked, handleLoadPastPageDataClick, currentTheme }) => {
    const { fetchRestPageData, restPageData, fetchRestData } = useRest();
    const [isLoadPastDataClicked, setLoadPastDataClicked] = useState(false);
    const [selectedId, setSelectedId] = useState(null);
    const theme = currentTheme;
    console.log(restPageData);

    useEffect(() => {
        if (isLoadPastPageDataClicked) {
            fetchRestPageData(1);
        }
    }, [isLoadPastPageDataClicked]);

    useEffect(() => {
        if (isLoadPastDataClicked) {
            fetchRestData(selectedId);
        }
    }, [isLoadPastDataClicked, selectedId]);

    const handleLoadPastDataClick = (id, dateTime) => {
        setSelectedId(id);
        setLoadPastDataClicked(true);
        handleSetIsLatestDataToUse(false);
        handleSetDateTimeTitle(dateTime);
        handleLoadPastPageDataClick();
    }

    const card1Data = []

    if (restPageData != null) {
        restPageData.forEach(element => {

            const dateTime = new Date(element.createdAt).toLocaleString();
            console.log(element.id)
            card1Data.push(
                <TouchableOpacity key={element.id} style={{ paddingBottom: 20 }} name="" onPressIn={() => handleLoadPastDataClick(element.id, dateTime)} activeOpacity={0.5}>
                    <View key={element.id} style={{ ...styles.moreButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primayColor }}>
                        <MaterialIcons key={element.id} style={styles.buttonIconStyle} name='reset-tv' size={20} color={theme.textPrimaryColor} />
                        <Text key={element.id + "text"} style={{ ...styles.subTitle3, color: theme.textPrimaryColor }} >{dateTime}</Text>
                    </View>
                </TouchableOpacity>
            )
        })
    }

    return (
        <ReactNativeModal style={{ height: "100%", width: "100%", padding: 0, margin: 0 }} coverScreen={true} visible={isLoadPastPageDataClicked} animationType="fade" transparent={true}>
            <View style={{ height: "100%", width: "100%", flex: 1, backgroundColor: 'rgba(0,0,0,0.7)', justifyContent: 'center' }}>
                <View style={{ backgroundColor: 'white', paddingTop: 32, paddingBottom: 40, paddingLeft: 20, paddingRight: 20, borderRadius: 16 }}>
                    <Text style={{ ...styles.title1, color: theme.textPrimaryColor }}>Select Old Record</Text>
                    <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Tap which record to load details</Text>
                    <View style={styles.dataButtonContainer}>
                        {card1Data.length > 0 && card1Data}
                    </View>
                    <View style={styles.leftRightButtonContainer} >
                        <TouchableOpacity style={{ flex: 1 }} name="" onPressIn={handleLoadPastPageDataClick} activeOpacity={0.5}>
                            <View style={{ ...styles.moreButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primayColor }}>
                                <MaterialIcons style={styles.buttonIconStyle} name='reset-tv' size={20} color={theme.textPrimaryColor} />
                                <Text style={{ ...styles.subTitle3, color: theme.textPrimaryColor }} >Previous Page</Text>
                            </View>
                        </TouchableOpacity>
                        <TouchableOpacity style={{ flex: 1 }} name="" onPressIn={handleLoadPastPageDataClick} activeOpacity={0.5}>
                            <View style={{ ...styles.moreButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primayColor }}>
                                <MaterialIcons style={styles.buttonIconStyle} name='reset-tv' size={20} color={theme.textPrimaryColor} />
                                <Text style={{ ...styles.subTitle3, color: theme.textPrimaryColor }} >Next Page</Text>
                            </View>
                        </TouchableOpacity>
                    </View>

                    <TouchableOpacity name="" onPressIn={handleLoadPastPageDataClick} activeOpacity={0.75}>
                        <View style={{ ...styles.moreButtonContainer, backgroundColor: theme.primaryColor, borderColor: theme.primaryColor }}>
                            <Entypo style={styles.buttonIconStyle} name='download' size={18} color={theme.whitePrimaryColor} />
                            <Text style={{ ...styles.subTitle3, color: theme.whitePrimaryColor }} >Close</Text>
                        </View>
                    </TouchableOpacity>
                </View>
            </View>
        </ReactNativeModal>
    )
}

export default HistoryModal

const styles = StyleSheet.create({
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
    moreButtonContainer: {
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
    leftRightButtonContainer: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        marginBottom: 16,
        columnGap: 8

    },
    subTitle3: {
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
