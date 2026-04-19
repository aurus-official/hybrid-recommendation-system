import { ScrollView, StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';
import ParamCard from '../../components/paramCard';
import IconTable from '../../components/iconTable';
import TitleTable from '../../components/titleTable';
import SeverityTable from '../../components/severityTable';
import { useSSE } from '../../components/sseProvider';
import RecoCard from '../../components/recoCard';
import { Fragment } from 'react/jsx-runtime';
import ParamCardLoading from '../../components/paramCardLoading';
import RecoCardLoading from '../../components/recoCardLoading';

const Insights = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const sseLatestData = useSSE();
    const iconTable = IconTable.call(colorScheme);
    const titleTable = TitleTable.call();
    const severityTable = SeverityTable.call(colorScheme);

    const card1Data = [];
    const card2Data = [];

    if (sseLatestData != null) {
        const { irrigation, soilNutrient, microclimate, cropOperation, irrigationSeverityValue,
            soilNutrientSeverityValue, microclimateSeverityValue, cropOperationSeverityValue,
        } = {
            ...sseLatestData["llmRecommendationModel"]
        }

        const categoryWithSeverity = {
            irrigation: {
                text: irrigation,
                severityValue: irrigationSeverityValue
            },
            soilNutrient: {
                text: soilNutrient,
                severityValue: soilNutrientSeverityValue
            },
            microclimate: {
                text: microclimate,
                severityValue: microclimateSeverityValue
            },
            cropOperation: {
                text: cropOperation,
                severityValue: cropOperationSeverityValue
            }
        }

        const categoryWithSeveritySorted = new Map();

        Object.entries({ ...categoryWithSeverity }).forEach(
            ([key, value]) => {
                if (categoryWithSeveritySorted.has(value.severityValue)) {
                    const existing = categoryWithSeveritySorted.get(value.severityValue)
                    existing.push({
                        ...value,
                        title: key
                    })
                    return;
                }
                categoryWithSeveritySorted.set(value.severityValue, [{
                    ...value,
                    title: key
                }])
            });


        categoryWithSeveritySorted.forEach((key, value) => {
            const { text, color } = severityTable[value];
            const data = [];
            key.forEach(value2 => {
                const recoTitle = titleTable[value2.title];
                const icon = iconTable[value2.title];
                data.push(<RecoCard key={value2.title} text={recoTitle} subText={value2.text} icon={icon} />);
            })
            card1Data.unshift(
                <View key={key} style={{
                    ...styles.card1Container,
                    backgroundColor: theme.cardBackgroundColor,
                    boxShadow: [{
                        offsetX: 0,
                        offsetY: 0,
                        blurRadius: 4,
                        color: theme.paramBorderColor
                    }]
                }}>
                    <View key={key} style={{ ...styles.subTitle2Container, backgroundColor: color }}>
                        <Text key={key} style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>{text}</Text>
                    </View>
                    {data}
                </View>
            )
        })


        const { derivedSensorDataModel, derivedWeatherDataModel } = sseLatestData;

        const {
            aggregatedSensorDataId,
            ...derivedSensorDataModelRemovedIds } = {
            ...derivedSensorDataModel
        }

        Object.entries({ ...derivedSensorDataModelRemovedIds }).filter(([key, value]) => !(key.endsWith("Unit") || key.endsWith("id"))).forEach(([key, value]) => {
            if (key === "plantStressIndex" || key === "heatStressIndex") {
                const text = titleTable[key.concat("Sensor")];
                const icon = iconTable[key];
                card2Data.push(<ParamCard key={key + value} text={text} subText={value} icon={icon} />);
                return;
            }
            const text = titleTable[key];
            const icon = iconTable[key];
            card2Data.push(<ParamCard key={key} text={text} subText={value} icon={icon} />);
        })

        const {
            aggregatedWeatherDataId,
            ...derivedWeatherDataModelRemovedIds } = {
            ...derivedWeatherDataModel
        }

        Object.entries({ ...derivedWeatherDataModelRemovedIds }).filter(([key, value]) => !(key.endsWith("Unit") || key.endsWith("id"))).forEach(([key, value]) => {
            if (key === "plantStressIndex" || key === "heatStressIndex") {
                const text = titleTable[key.concat("Weather")];
                const icon = iconTable[key];
                card2Data.push(<ParamCard key={key + value} text={text} subText={value} icon={icon} />);
                return;
            }

            const text = titleTable[key];
            const icon = iconTable[key];
            card2Data.push(<ParamCard key={key + value} text={text} subText={value} icon={icon} />);
        })

    }

    return (
        <ScrollView style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <View style={styles.viewContainerStyles} >
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Insights and Recommendations</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Data Source : Live</Text>
                {card1Data.length > 0 ?
                    card1Data
                    :
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
                            <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>Recommended Actions</Text>
                        </View>
                        <RecoCardLoading />
                        <RecoCardLoading />
                        <RecoCardLoading />
                    </View>
                }
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
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>Derived Agronomic Indices</Text>
                    </View>
                    {card2Data.length > 0 ?
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

export default Insights

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
})
