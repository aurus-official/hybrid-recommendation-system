import { ScrollView, StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Entypo, MaterialIcons } from '@expo/vector-icons';
import { Colors } from '../../constants/Colors';
import { useState } from 'react';
import { useFarmData } from '../../contexts/farmDataProvider';
import Trend from '../../components/trendCard';
import TrendCardLoading from '../../components/trendCardLoading';
import HistoryModal from '../../components/historyModal';
import RecoCardLoading from '../../components/recoCardLoading';
import TitleTable from '../../utils/titleTable';


const Trends = () => {
    const colorScheme = useColorScheme()
    const theme = Colors[colorScheme] || Colors.light;
    const { farmData, setDataSource, dataSource } = useFarmData();
    const [isLoadPastPageData, setLoadPastPageData] = useState(false);
    const titleTable = TitleTable();


    const handleLoadPastPageDataClick = () => {
        setLoadPastPageData(prev => !prev)
    }

    const trend1Temperature = [];
    const trend2PrecipitationProbability = [];
    const card1Data = [];

    if (farmData != null) {
        const { processedWeatherDataModel } = farmData;
        const { processedWeatherDataPointsHourly, processedWeatherDataPointsDaily } = processedWeatherDataModel;

        processedWeatherDataPointsHourly.slice(0, 6).forEach((element, index) => {
            const currentHour = new Date(element.time).toLocaleTimeString('en-US', {
                hour: 'numeric',
                hour12: true
            });
            if (index === 0) {
                trend1Temperature.push({
                    value: element.temperature,
                    label: currentHour,
                    frontColor: theme.primaryColor,
                    dashWidth: 5,
                    dashGap: 3
                })
                return;
            }
            trend1Temperature.push({
                value: element.temperature,
                label: currentHour,
                frontColor: 'transparent',
                barBorderColor: theme.primaryColor,
                barBorderWidth: 2,
                dashWidth: 5,
                dashGap: 3
            })
        });

        card1Data.push(
            <Trend key={1} currentTheme={theme} data={trend1Temperature} title={titleTable["temperature"]} suffix={"°C"} />
        )

        processedWeatherDataPointsHourly.slice(0, 6).forEach((element, index) => {
            const currentHour = new Date(element.time).toLocaleTimeString('en-US', {
                hour: 'numeric',
                hour12: true
            });
            if (index === 0) {
                trend2PrecipitationProbability.push({
                    value: element.precipitationProbability,
                    label: currentHour,
                    frontColor: theme.primaryColor,
                    dashWidth: 5,
                    dashGap: 3
                })
                return;
            }
            trend2PrecipitationProbability.push({
                value: element.precipitationProbability,
                label: currentHour,
                frontColor: 'transparent', // Makes it "outline only"
                barBorderColor: theme.primaryColor,
                barBorderWidth: 2,
                dashWidth: 5,
                dashGap: 3
            })
        });

        card1Data.push(
            <Trend key={2} currentTheme={theme} data={trend2PrecipitationProbability} title={titleTable["precipitationProbability"]} suffix={"%"} />
        )

    }

    return (
        <ScrollView style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <View style={styles.viewContainerStyles} >
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }}>Historical Records</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Tracking the success of previous directions.</Text>
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
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>View Historical Data</Text>
                    </View>
                    <HistoryModal currentTheme={theme} isLoadPastPageDataClicked={isLoadPastPageData} handleLoadPastPageDataClick={handleLoadPastPageDataClick} />
                    {(card1Data.length > 0) ?
                        <>
                            <Text style={styles.historicalStyleText}>Current Data Loaded</Text>
                            <Text style={styles.historicalStyleTextSecond}>{dataSource}</Text>
                            <View style={styles.buttonContainer} >
                                <TouchableOpacity name="" onPressIn={handleLoadPastPageDataClick} activeOpacity={0.75}>
                                    <View style={{ ...styles.moreButtonContainer, backgroundColor: theme.primaryColor, borderColor: theme.primaryColor }}>
                                        <Entypo style={styles.buttonIconStyle} name='download' size={18} color={theme.whitePrimaryColor} />
                                        <Text style={{ ...styles.subTitle3, color: theme.whitePrimaryColor }} >Load Past Data</Text>
                                    </View>
                                </TouchableOpacity>
                                <TouchableOpacity name="" onPressIn={() => setDataSource("Latest")} activeOpacity={0.5}>
                                    <View style={{ ...styles.moreButtonContainer, backgroundColor: theme.whitePrimaryColor, borderColor: theme.primayColor }}>
                                        <MaterialIcons style={styles.buttonIconStyle} name='reset-tv' size={20} color={theme.textPrimaryColor} />
                                        <Text style={{ ...styles.subTitle3, color: theme.textPrimaryColor }} >Reset to Latest</Text>
                                    </View>
                                </TouchableOpacity>
                            </View>
                        </>
                        :
                        <RecoCardLoading currentTheme={theme} />
                    }
                </View>
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }}>Data Trends and Patterns</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Highlights data movement and direction.</Text>
                {(card1Data.length > 0) ?
                    card1Data
                    : <TrendCardLoading currentTheme={theme} />
                }
            </View>

        </ScrollView>
    )
}

export default Trends

const styles = StyleSheet.create({
    viewStyles: {
        flex: 1,
    },
    viewContainerStyles: {
        marginLeft: 24,
        marginRight: 24,
        marginTop: 20,
        width: "100%",
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
        flexDirection: "column",
        flexWrap: "wrap",
        justifyContent: "center",
        alignItems: "center",
        rowGap: 24,
        height: "auto",
        flex: 1

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
        marginLeft: 12,
        marginRight: 12,
        paddingTop: 8,
        paddingBottom: 8,
    },
    graphContainer: {
        width: "90%",
    },
    legendWrapper: {
        flexDirection: 'row',
        justifyContent: 'center',
        marginBottom: 8,
    },
    legendItem: {
        flexDirection: 'row',
        alignItems: 'center',
        marginHorizontal: 10,
    },
    legendBox: {
        height: 12,
        width: 12,
        borderRadius: 2,
        marginRight: 6,
    },
    legendText: {
        fontSize: 12,
    },
    historicalStyleText: {
        fontSize: 16,
        marginTop: 12,
    },
    historicalStyleTextSecond: {
        fontSize: 20,
        marginTop: -12,
        marginBottom: 8

    },
    buttonContainer: {
        display: "flex",
        flexDirection: "row",
        columnGap: 8,
    },
    buttonIconStyle: {
        marginLeft: 20,
    }

})
