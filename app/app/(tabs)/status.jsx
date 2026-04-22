import { ScrollView, StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';
import ParamCard from '../../components/paramCard';
import { Ionicons } from '@expo/vector-icons';
import { useReducer, useState } from 'react';
import RecoCard from '../../components/recoCard';
import IconTable from '../../components/iconTable';
import TitleTable from '../../components/titleTable';
import SeverityTable from '../../components/severityTable';
import ParamCardLoading from '../../components/paramCardLoading';
import RecoCardLoading from '../../components/recoCardLoading';
import { useFarmData } from '../../components/farmDataProvider';


const Status = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const { farmData } = useFarmData();
    const iconTable = IconTable(theme);
    const titleTable = TitleTable.call();

    const card1Data = [];

    if (farmData != null) {
        const { rawHealthCheckDataModel: { id, createdAt, ...parameters } } = farmData;

        Object.entries(parameters).forEach(([key, value]) => {
            const text = titleTable[key];
            const icon = iconTable[key];
            card1Data.push(<ParamCard key={key} text={text} subText={value ? "On" : "Off"} icon={icon}></ParamCard>)
        })
    }

    return (
        <ScrollView style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <View style={styles.viewContainerStyles} >
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >System Health Check</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Diagnostic overview ensuring hardware are working.</Text>
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
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >One-Wire Sensor and ADC</Text>
                    </View>
                    {(card1Data.length > 0) ?
                        card1Data :
                        <>
                            <ParamCardLoading />
                            <ParamCardLoading />
                            <ParamCardLoading />
                            <ParamCardLoading />
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
    },
    viewContainerStyles: {
        marginLeft: 24,
        marginRight: 24,
        marginTop: 20,
        width: "100%",
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
    card1Container: {
        marginTop: 16,
        marginBottom: 24,
        paddingBottom: 24,
        width: "90%",
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
        boxSizing: "border-box",
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
})

