import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import ReactNativeModal from 'react-native-modal'
import { AntDesign, Foundation, MaterialIcons } from '@expo/vector-icons';
import { useEffect, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useStore } from '../store/useStore';
import { fetchHardwareStatus, fetchHardwareStatusPage } from '../api/restManager';

const HardwareStatusHistoryModal = ({ isLoadPastPageDataClicked, handleLoadPastPageDataClick, currentTheme }) => {
    const [selectedHardwareStatusId, setSelectedHardwareStatusId] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);
    const theme = currentTheme;
    const setRestHardwareStatusSource = useStore((state) => state.setRestHardwareStatusSource);
    const setRestHardwareStatus = useStore((state) => state.setRestHardwareStatus);
    const resumeLiveHardwareStatus = useStore((state) => state.resumeLiveHardwareStatus);
    const liveData = useStore((state) => state.liveData);
    const isLiveHardwareStatusMode = useStore((state) => state.isLiveHardwareStatusMode);
    let maxNumberOfPages = 1;

    const restHardwareStatusPageQuery = useQuery({
        queryKey: ["restHardwareStatusPage", pageNumber],
        queryFn: () => fetchHardwareStatusPage(pageNumber),
        enabled: isLoadPastPageDataClicked,
        staleTime: Infinity,
    });

    const restHardwareStatusQuery = useQuery({
        queryKey: ["restHardwareStatus", selectedHardwareStatusId],
        queryFn: () => fetchHardwareStatus(selectedHardwareStatusId),
        enabled: !!selectedHardwareStatusId,
        staleTime: Infinity,
    });

    useEffect(() => {
        if (restHardwareStatusQuery.isSuccess && selectedHardwareStatusId) {
            setRestHardwareStatus(selectedHardwareStatusId);
        }
    }, [restHardwareStatusQuery.isSuccess, selectedHardwareStatusId, setRestHardwareStatus]);

    const handleLoadPastDataClick = (id, dateTime) => {
        if (liveData["hardwareStatusModel"].id === id) {
            resumeLiveHardwareStatus();
            handleLoadPastPageDataClick();
            return;
        }

        setSelectedHardwareStatusId(id);
        setRestHardwareStatusSource(dateTime);
        handleLoadPastPageDataClick();
    }

    const handlePreviousPage = () => {
        if (pageNumber > 1) {
            setPageNumber(prev => prev - 1);
        }
    }

    const handleNextPage = () => {
        if (maxNumberOfPages >= pageNumber) {
            setPageNumber(prev => prev + 1);
        }
    }

    const card1Data = []

    if (restHardwareStatusPageQuery.data != null) {
        const { hardwareStatusSummaryDTOs, pageCount } = restHardwareStatusPageQuery.data;
        maxNumberOfPages = pageCount;


        hardwareStatusSummaryDTOs.forEach(element => {

            const dateTime = new Date(element.createdAt).toLocaleString();
            card1Data.push(
                <TouchableOpacity key={element.id} style={{ paddingBottom: 20 }} name="" onPressIn={() => handleLoadPastDataClick(element.id, dateTime)} activeOpacity={0.5}>
                    <View key={element.id} style={{
                        ...styles.moreButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primayColor,
                        boxShadow: [{
                            offsetX: 0,
                            offsetY: 0,
                            blurRadius: 3,
                            color: theme.paramBorderColor
                        }]
                    }}>
                        <Foundation key={element.id} style={styles.buttonIconStyle} name='record' size={20} color={theme.textPrimaryColor} />
                        <Text key={element.id + "text"} style={{ ...styles.buttonStyle, color: theme.textPrimaryColor }} >{dateTime}</Text>
                    </View>
                </TouchableOpacity>
            )
        })
    }

    return (
        <ReactNativeModal style={styles.reactNativeModelStyle} coverScreen={true} visible={isLoadPastPageDataClicked} animationType="fade" transparent={true}>
            <View style={styles.outerView}>
                <View style={{ ...styles.innerView, backgroundColor: theme.cardBackgroundColor }}>
                    <Text style={{ ...styles.title1, color: theme.textPrimaryColor }}>Select Past Hardware Logs - Page {pageNumber}</Text>
                    <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Tap which hardware to load details</Text>
                    <View style={styles.dataButtonContainer}>
                        {card1Data.length > 0 && card1Data}
                    </View>
                    <View style={styles.leftRightButtonContainer} >
                        <TouchableOpacity disabled={(pageNumber == 1)} style={(pageNumber == 1) ? { flex: 1, opacity: 0.25 } : { flex: 1 }} name="" onPressIn={handlePreviousPage} activeOpacity={0.5}>
                            <View style={{ ...styles.navButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.textPrimaryColor }}>
                                <Foundation style={styles.buttonIconStyle} name='previous' size={20} color={theme.textPrimaryColor} />
                                <Text style={{ ...styles.buttonStyle, color: theme.textPrimaryColor }} >Previous Page</Text>
                            </View>
                        </TouchableOpacity>
                        <TouchableOpacity disabled={(maxNumberOfPages == pageNumber)} style={(maxNumberOfPages == pageNumber) ? { flex: 1, opacity: 0.25 } : { flex: 1 }} name="" onPressIn={handleNextPage} activeOpacity={0.5}>
                            <View style={{ ...styles.navButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.textPrimaryColor }}>
                                <Foundation style={styles.buttonIconStyle} name='next' size={20} color={theme.textPrimaryColor} />
                                <Text style={{ ...styles.buttonStyle, color: theme.textPrimaryColor }} >Next Page</Text>
                            </View>
                        </TouchableOpacity>
                    </View>
                    <TouchableOpacity disabled={isLiveHardwareStatusMode} style={isLiveHardwareStatusMode ? { opacity: 0.25 } : { opacity: 1 }} name="" onPressIn={() => {
                        resumeLiveHardwareStatus();
                        handleLoadPastPageDataClick();
                    }} activeOpacity={0.25}>
                        <View style={{ ...styles.navButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.textPrimaryColor, marginBottom: 16 }}>
                            <MaterialIcons style={styles.buttonIconStyle} name='reset-tv' size={18} color={theme.textPrimaryColor} />
                            <Text style={{ ...styles.buttonStyle, color: theme.textPrimaryColor }} >Reset to Latest</Text>
                        </View>
                    </TouchableOpacity>

                    <TouchableOpacity name="" onPressIn={handleLoadPastPageDataClick} activeOpacity={0.75}>
                        <View style={{ ...styles.navButtonContainer, backgroundColor: theme.primaryColor, borderColor: theme.primaryColor }}>
                            <AntDesign style={styles.buttonIconStyle} name='close' size={18} color={theme.whitePrimaryColor} />
                            <Text style={{ ...styles.buttonStyle, color: theme.whitePrimaryColor }} >Close</Text>
                        </View>
                    </TouchableOpacity>
                </View>
            </View>
        </ReactNativeModal>
    )
}

export default HardwareStatusHistoryModal

const styles = StyleSheet.create({
    reactNativeModelStyle: {
        height: "100%",
        width: "100%",
        padding: 0,
        margin: 0
    },
    title1: {
        fontSize: 24,
        fontFamily: "Inter_500Medium",
        letterSpacing: -1
    },
    outerView: {
        height: "100%",
        width: "100%",
        flex: 1,
        backgroundColor: 'rgba(0,0,0,0.7)',
        justifyContent: 'center',
    },
    innerView: {
        paddingTop: 30,
        paddingBottom: 36,
        borderRadius: 12,
        paddingHorizontal: 28,
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
        borderWidth: 0.2,
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        paddingRight: 16,
    },
    navButtonContainer: {
        borderRadius: 12,
        borderWidth: 1,
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        paddingRight: 16,
    },
    leftRightButtonContainer: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        marginBottom: 16,
        columnGap: 16
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
        height: 460,
        marginBottom: 24
    },
});


