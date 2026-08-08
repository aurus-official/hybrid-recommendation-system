import { ScrollView, StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';
import ParamCard from '../../components/paramCard';
import IconTable from '../../utils/iconTable';
import TitleTable from '../../utils/titleTable';
import ParamCardLoading from '../../components/paramCardLoading';
import { useStore } from '../../store/useStore';
import { Ionicons } from '@expo/vector-icons';
import { useState } from 'react';
import ReadingStatusHistoryModal from '../../components/readingStatusHistoryModal';
import HardwareStatusHistoryModal from '../../components/hardwareStatusHistoryModal';

const Status = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const displayReadingStatus = useStore((state) => state.displayReadingStatus);
    const readingStatusSource = useStore((state) => state.readingStatusSource)
    const displayHardwareStatus = useStore((state) => state.displayHardwareStatus);
    const hardwareStatusSource = useStore((state) => state.hardwareStatusSource)
    const iconTable = IconTable(theme);
    const titleTable = TitleTable()
    const [isLoadHardwarePastPageData, setLoadHardwarePastPageData] = useState(false);
    const [isLoadReadingPastPageData, setLoadReadingPastPageData] = useState(false);

    const handleLoadingHardwarePastPageDataClick = () => {
        setLoadHardwarePastPageData(prev => !prev)
    }

    const handleLoadingReadingPastPageDataClick = () => {
        setLoadReadingPastPageData(prev => !prev)
    }

    const card1Data = [];
    const card2Data = [];

    if (displayReadingStatus != null && displayHardwareStatus != null) {
        const { id: hsId, createdAt: hsCreatedAt, ...hsParameters } = displayHardwareStatus;

        Object.entries(hsParameters).forEach(([key, value]) => {
            const text = titleTable[key];
            const icon = iconTable[key];
            card1Data.push(<ParamCard key={key + value} currentTheme={theme} text={text} subText={value ? "Online" : "Offline"} icon={icon}></ParamCard>)
        })

        const { id: rsId, createdAt: rsCreatedAt, ...rsParameters } = displayReadingStatus;

        Object.entries(rsParameters).forEach(([key, value]) => {
            const keyArray = key.substring(7).split("");
            keyArray[0] = keyArray[0].toLowerCase();
            const finalizedKey = keyArray.join("");
            const text = titleTable[finalizedKey];
            const icon = iconTable[finalizedKey];
            if (rsCreatedAt === null) {
                card2Data.push(<ParamCard key={key + value} currentTheme={theme} text={text} subText={"Unavailable"} icon={icon}></ParamCard>)
                return;
            }

            card2Data.push(<ParamCard key={key + value} currentTheme={theme} text={text} subText={value ? "Valid" : "Invalid"} icon={icon}></ParamCard>)
        })

    }

    return (
        <ScrollView style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <View style={styles.viewContainerStyles} >
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Hardware Status Log</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >See if your devices are currently working.</Text>
                <Text style={{ ...styles.subSubTitle1, backgroundColor: theme.primaryColor, color: theme.whitePrimaryColor }} >Hardware Status Source : {hardwareStatusSource}</Text>
                <View style={{
                    ...styles.card1Container,
                    backgroundColor: theme.cardBackgroundColor,
                    boxShadow: [{
                        offsetX: 0,
                        offsetY: 0,
                        blurRadius: 4,
                        color: theme.paramBorderColor
                    }]
                }}>
                    <View style={{ ...styles.subTitle2Container, backgroundColor: theme.primaryColor }}>
                        <HardwareStatusHistoryModal currentTheme={theme} isLoadPastPageDataClicked={isLoadHardwarePastPageData}
                            handleLoadPastPageDataClick={handleLoadingHardwarePastPageDataClick} />
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >Hardware</Text>
                        {(card1Data.length > 0) ?
                            <TouchableOpacity name="" onPressIn={handleLoadingHardwarePastPageDataClick} activeOpacity={0.75}>
                                <View style={{ ...styles.moreButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primaryColor }}>
                                    <Text style={{ ...styles.subTitle3, color: theme.primaryColor }}>Hardware Logs</Text>
                                    <Ionicons style={styles.icons} name='arrow-forward' size={20} color={theme.primaryColor} />
                                </View>
                            </TouchableOpacity> : ""}
                    </View>
                    {(card1Data.length > 0) ?
                        card1Data :
                        <>
                            <ParamCardLoading currentTheme={theme} />
                            <ParamCardLoading currentTheme={theme} />
                            <ParamCardLoading currentTheme={theme} />
                            <ParamCardLoading currentTheme={theme} />
                        </>
                    }
                </View>
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Reading Status Out of Range Log</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Flags current metrics that are out of range and need attention.</Text>
                <Text style={{ ...styles.subSubTitle1, backgroundColor: theme.primaryColor, color: theme.whitePrimaryColor }} >Reading Status Source : {readingStatusSource}</Text>
                <View style={{
                    ...styles.card1Container,
                    backgroundColor: theme.cardBackgroundColor,
                    boxShadow: [{
                        offsetX: 0,
                        offsetY: 0,
                        blurRadius: 4,
                        color: theme.paramBorderColor
                    }]
                }}>
                    <View style={{ ...styles.subTitle2Container, backgroundColor: theme.primaryColor }}>
                        <ReadingStatusHistoryModal currentTheme={theme} isLoadPastPageDataClicked={isLoadReadingPastPageData}
                            handleLoadPastPageDataClick={handleLoadingReadingPastPageDataClick} />
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >Sensor Plausibility</Text>
                        {(card2Data.length > 0) ?
                            <TouchableOpacity name="" onPressIn={handleLoadingReadingPastPageDataClick} activeOpacity={0.75}>
                                <View style={{ ...styles.moreButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primaryColor }}>
                                    <Text style={{ ...styles.subTitle3, color: theme.primaryColor }}>Reading Logs</Text>
                                    <Ionicons style={styles.icons} name='arrow-forward' size={20} color={theme.primaryColor} />
                                </View>
                            </TouchableOpacity> : ""}
                    </View>
                    {(card2Data.length > 0) ?
                        card2Data :
                        <>
                            <ParamCardLoading currentTheme={theme} />
                            <ParamCardLoading currentTheme={theme} />
                            <ParamCardLoading currentTheme={theme} />
                            <ParamCardLoading currentTheme={theme} />
                        </>
                    }
                </View>
            </View>
        </ScrollView>
    )
}

export default Status

const styles = StyleSheet.create({
    viewStyles: {
        flex: 1,
        width: "100%",
    },
    viewContainerStyles: {
        width: "100%",
        paddingHorizontal: 24,
        marginTop: 20,
        height: "auto"
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
        marginLeft: 1
    },
    subSubTitle1: {
        fontSize: 13,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        marginLeft: 1,
        alignSelf: "flex-start",
        marginTop: 8,
        borderRadius: 8,
        paddingTop: 4,
        paddingBottom: 4,
        paddingLeft: 8,
        paddingRight: 8,
    },
    card1Container: {
        marginTop: 16,
        marginBottom: 24,
        paddingBottom: 24,
        width: "100%",
        borderRadius: 12,
        display: "flex",
        flexDirection: "row",
        flexWrap: "wrap",
        justifyContent: "space-evenly",
        rowGap: 24,
        height: "auto",
    },
    subTitle2Container: {
        borderRadius: 12,
        zIndex: 99,
        width: "100%",
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
    },
    subTitle2: {
        fontSize: 16,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        marginLeft: 24,
        paddingTop: 12,
        paddingBottom: 12,
    },
    moreButtonContainer: {
        borderRadius: 12,
        borderWidth: 1,
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        paddingRight: 16,
        marginRight: 4,
    },
    subTitle3: {
        fontSize: 16,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        marginLeft: 20,
        marginRight: 12,
        paddingTop: 8,
        paddingBottom: 8,
    },
    icons: {
        paddingTop: 4
    }
});

