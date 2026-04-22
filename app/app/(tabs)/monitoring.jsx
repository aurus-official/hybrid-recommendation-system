import { ScrollView, StyleSheet, Text, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';
import ParamCard from '../../components/paramCard';
import IconTable from '../../components/iconTable';
import TitleTable from '../../components/titleTable';
import ParamCardLoading from '../../components/paramCardLoading';
import { useFarmData } from '../../components/farmDataProvider';

const Monitoring = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const { farmData, dataSource } = useFarmData();
    const iconTable = IconTable(theme);
    const titleTable = TitleTable.call();

    const card1Data = [];
    const card2Data = [];

    if (farmData != null) {
        const { aggregatedSensorDataModel, aggregatedWeatherDataModel } = farmData;

        const {
            startingWindow, endingWindow,
            ...aggregatedSensorDataModelRemovedTime } = {
            ...aggregatedSensorDataModel
        }

        Object.entries({ ...aggregatedSensorDataModelRemovedTime }).filter(([key, value]) => !(key === "id")).forEach(([key, value]) => {
            const text = titleTable[key];
            const icon = iconTable[key];
            card1Data.push(<ParamCard key={key} text={text} subText={`${value.value} ${value.unit}`} icon={icon} />);
        })


        const {
            processedWeatherDataId, id,
            ...aggregatedWeatherDataModelRemovedTime } = {
            ...aggregatedWeatherDataModel
        }

        Object.entries({ ...aggregatedWeatherDataModelRemovedTime }).filter(([key, value]) => !(key === "id")).forEach(([key, value]) => {
            const text = titleTable[key];
            const icon = iconTable[key];
            card2Data.push(<ParamCard key={key + value} text={text} subText={`${value.value} ${value.unit == "normalized" ? "" : value.unit}`} icon={icon} />);
        })

    }

    return (
        <ScrollView style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <View style={styles.viewContainerStyles} >
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Data Monitoring</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Environmental snapshots synchronized every 20 minutes.</Text>
                <Text style={{ ...styles.subSubTitle1, backgroundColor: theme.primaryColor, color: theme.whitePrimaryColor }} >Data Source : {dataSource}</Text>
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
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >Soil and Environmental Parameters</Text>
                    </View>
                    {(card1Data.length > 0) ?
                        card1Data :
                        <>
                            <ParamCardLoading />
                            <ParamCardLoading />
                            <ParamCardLoading />
                            <ParamCardLoading />
                            <ParamCardLoading />
                            <ParamCardLoading />
                        </>
                    }
                </View>
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
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >Weather Parameters</Text>
                    </View>
                    {(card2Data.length > 0) ?
                        card2Data :
                        <>
                            <ParamCardLoading />
                            <ParamCardLoading />
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

export default Monitoring

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
