import { Dimensions, ScrollView, StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';
import { useReducer, useState } from 'react';
import { useSSE } from '../../components/sseProvider';
import IconTable from '../../components/iconTable';
import TitleTable from '../../components/titleTable';
import SeverityTable from '../../components/severityTable';
import Trend from '../../components/trendCard';
import TrendCardLoading from '../../components/trendCardLoading';


const Trends = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const sseLatestData = useSSE();
    const titleTable = TitleTable.call();

    const trend1Temperature = [];
    const trend2PrecipitationProbability = [];
    const card1Data = [];

    const card2Data = [];

    const card3Data = [];

    if (sseLatestData != null) {
        const { processedWeatherDataModel } = sseLatestData;
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
                frontColor: 'transparent', // Makes it "outline only"
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
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }}>Data Trends</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Highlights data movement and direction</Text>
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
        marginLeft: 20,
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
})
