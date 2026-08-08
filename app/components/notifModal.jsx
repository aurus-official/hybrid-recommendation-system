import { AntDesign, FontAwesome, Foundation } from '@expo/vector-icons'
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import ReactNativeModal from 'react-native-modal'
import { useStore } from '../store/useStore';
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useNavigation } from 'expo-router';
import { fetchRestData } from '../api/restManager';

const NotifModal = ({ isClicked, handleClick, currentTheme }) => {
    const theme = currentTheme;
    const [selectedDataId, setSelectedDataId] = useState(null);
    const [selectedReadingStatusId, setSelectedReadingStatusId] = useState(null);
    const setRestFarmDataSource = useStore((state) => state.setRestFarmDataSource);
    const setRestReadingStatusSource = useStore((state) => state.setRestReadingStatusSource);
    const notification = useStore((state) => state.notifications);
    const markAllSeen = useStore((state) => state.markAllSeen);
    const markOneSeen = useStore((state) => state.markOneSeen);
    const badgeCount = useStore((state) => state.badgeCount);
    const seenIds = useStore((state) => state.seenIds);
    const setBadgeCount = useStore((state) => state.setBadgeCount);
    const navigation = useNavigation();
    const card1Data = [];
    const liveData = useStore((state) => state.liveData);
    const resumeLiveFarmData = useStore((state) => state.resumeLiveFarmData);
    const resumeLiveReadingStatus = useStore((state) => state.resumeLiveReadingStatus);

    const restFarmDataQuery = useQuery({
        queryKey: ["restFarmData", selectedDataId],
        queryFn: () => fetchRestData(selectedDataId),
        enabled: !!selectedDataId,
        staleTime: Infinity,
    });

    const restReadingStatusQuery = useQuery({
        queryKey: ["restReadingStatus", selectedReadingStatusId],
        queryFn: () => fetchReadingStatusData(selectedReadingStatusId),
        enabled: !!selectedReadingStatusId,
        staleTime: Infinity,
    });

    const handleLoadPastDataClick = (id, dateTime, notificationType) => {
        if (notificationType === "SYSTEM_HEALTH_ISSUE") {
            if (liveData["readingStatusModel"].id === id) {
                resumeLiveReadingStatus();
                navigation.navigate("status");
                handleClick();
                markOneSeen(id);
                setBadgeCount();
            } else {
                setSelectedReadingStatusId(id);
                setRestReadingStatusSource(dateTime);
                navigation.navigate("status");
            }
        }

        if (notificationType === "RECOMMENDATION_SEVERITY_ISSUE") {
            if (liveData["llmRecommendationModel"].id === id) {
                resumeLiveFarmData();
                navigation.navigate("trends");
            } else {
                setSelectedDataId(id);
                setRestFarmDataSource(dateTime);
                navigation.navigate("trends");
            }
        }

        handleClick();
        markOneSeen(id);
        setBadgeCount();
    }

    if (notification != null) {
        notification.forEach(element => {
            const dateTime = new Date(element.createdAt).toLocaleString();
            const title = element.notificationType === "SYSTEM_HEALTH_ISSUE" ? "Sensor Readings Out of Bounds" : "High Priority Recommendation Detected";
            if (seenIds.includes(element.id)) {
                card1Data.push(
                    <TouchableOpacity key={element.id} style={{ paddingBottom: 20 }} name="" onPressIn={() => handleLoadPastDataClick(element.id, dateTime, element.notificationType)} activeOpacity={0.5}>
                        <View key={element.id} style={{
                            ...styles.buttonContainerNotification, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primayColor, borderWidth: 0.25,
                            boxShadow: [{
                                offsetX: 0,
                                offsetY: 0,
                                blurRadius: 3,
                                color: theme.paramBorderColor
                            }]
                        }}>
                            <Foundation key={element.id} style={styles.buttonIconStyleNotif} name='record' size={20} color={theme.textPrimaryColor} />
                            <View style={{ display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center" }} key={element.id + element.id}>
                                <Text style={{ ...styles.notifText1 }} key={element.id}>{title}</Text>
                                <Text key={element.id + "text"} style={{ ...styles.notifText2, color: theme.textPrimaryColor }} >{dateTime}</Text>
                            </View>
                        </View>
                    </TouchableOpacity>
                );
                return;
            }
            card1Data.push(
                <TouchableOpacity key={element.id} style={{ paddingBottom: 20 }} name="" onPressIn={() => handleLoadPastDataClick(element.id, dateTime, element.notificationType)} activeOpacity={0.5}>
                    <View key={element.id} style={{
                        ...styles.buttonContainerNotification, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primayColor, borderWidth: 1.75,
                        boxShadow: [{
                            offsetX: 0,
                            offsetY: 0,
                            blurRadius: 3,
                            color: theme.paramBorderColor
                        }]
                    }}>
                        <Foundation key={element.id} style={styles.buttonIconStyleNotif} name='record' size={20} color={theme.textPrimaryColor} />
                        <View style={{ display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center" }} key={element.id + element.id}>
                            <Text style={{ ...styles.notifText1 }} key={element.id}>{title}</Text>
                            <Text key={element.id + "text"} style={{ ...styles.notifText2, color: theme.textPrimaryColor }} >{dateTime}</Text>
                        </View>
                    </View>
                </TouchableOpacity>
            );
        })
    }

    return (
        <ReactNativeModal style={{ height: "100%", width: "100%", padding: 0, margin: 0 }} coverScreen={true} visible={isClicked} animationType="fade" transparent={true}>
            <View style={{ height: "100%", width: "100%", flex: 1, backgroundColor: 'rgba(0,0,0,0.5)', justifyContent: 'center' }}>
                <View style={{ ...styles.containerStyle, backgroundColor: theme.cardBackgroundColor }}>
                    <Text style={{ ...styles.title1, color: theme.textPrimaryColor }}>Notifications</Text>
                    <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >View most recent notifications</Text>
                    {card1Data}
                    <View style={styles.buttonsContainer} >
                        <TouchableOpacity disabled={badgeCount <= 0} style={{ flex: 1, opacity: (badgeCount <= 0) ? 0.25 : 1 }} name="" onPressIn={markAllSeen} activeOpacity={0.75}>
                            <View style={{ ...styles.buttonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.textPrimaryColor }}>
                                <FontAwesome style={styles.buttonIconStyle} name='check-circle' size={18} color={theme.textPrimaryColor} />
                                <Text style={{ ...styles.buttonStyle, color: theme.textPrimaryColor }} >Mark all seen</Text>
                            </View>
                        </TouchableOpacity>
                        <TouchableOpacity style={{ flex: 1 }} name="" onPressIn={handleClick} activeOpacity={0.75}>
                            <View style={{ ...styles.buttonContainer, backgroundColor: theme.primaryColor, borderColor: theme.primaryColor }}>
                                <AntDesign style={styles.buttonIconStyle} name='close' size={18} color={theme.whitePrimaryColor} />
                                <Text style={{ ...styles.buttonStyle, color: theme.whitePrimaryColor }} >Close</Text>
                            </View>
                        </TouchableOpacity>
                    </View>
                </View>
            </View>
        </ReactNativeModal>
    )
}

export default NotifModal

const styles = StyleSheet.create({
    containerStyle: {
        width: "100%",
        height: "auto",
        display: "flex",
        flexDirection: "column",
        paddingHorizontal: 28,
        paddingTop: 30,
        paddingBottom: 36,
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
    notifText1: {
        fontSize: 16,
        marginLeft: 24,
        paddingTop: 8,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        opacity: 0.8,
        marginBottom: 3
    },
    buttonContainer: {
        borderRadius: 12,
        borderWidth: 1,
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        paddingRight: 16,
    },
    buttonContainerNotification: {
        borderRadius: 12,
        borderWidth: 2,
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        paddingRight: 16,
    },
    buttonStyle: {
        fontSize: 16,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        marginLeft: 12,
        marginRight: 12,
        paddingBottom: 8,
        paddingTop: 8,
    },
    notifText2: {
        fontSize: 16,
        fontFamily: "Inter_500Regular",
        letterSpacing: -0.5,
        paddingBottom: 8,
        opacity: 0.75,
        alignSelf: "flex-start",
        marginLeft: 24,
    },
    buttonIconStyle: {
        marginLeft: 20,
    },
    buttonIconStyleNotif: {
        marginLeft: 8,
    },
    dataButtonContainer: {
        marginBottom: 24
    },
    buttonsContainer: {
        marginTop: 16,
        display: "flex",
        flexDirection: "row",
        columnGap: 16
    }
});

