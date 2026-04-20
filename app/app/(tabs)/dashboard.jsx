import { ScrollView, StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';
import ParamCard from '../../components/paramCard';
import { Ionicons } from '@expo/vector-icons';
import { useReducer } from 'react';
import { useSSE } from '../../components/sseProvider';
import RecoCard from '../../components/recoCard';
import IconTable from '../../components/iconTable';
import TitleTable from '../../components/titleTable';
import SeverityTable from '../../components/severityTable';
import ParamCardLoading from '../../components/paramCardLoading';
import RecoCardLoading from '../../components/recoCardLoading';


const Dashboard = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const dataAcquired = useSSE();
    const iconTable = IconTable(theme);
    const titleTable = TitleTable.call();
    const severityTable = SeverityTable(theme);

    const [isPressed, dispatchPressed] = useReducer((prev, type) => {
        return {
            ...prev,
            [type]: !prev[type]
        }
    }, {
        derived: false,
        recommendation: false,
        quick_data: false
    });

    const handlePress = (event) => {
        dispatchPressed(event.target.name);
    }

    const card1Data = [];
    const severityData = {
        text: "",
        color: "",
    }

    const card2Data = [];


    const card3Data = [];

    if (dataAcquired != null) {

        const { irrigation, soilNutrient, microclimate, cropOperation, irrigationSeverityValue,
            soilNutrientSeverityValue, microclimateSeverityValue, cropOperationSeverityValue,
        } = {
            ...dataAcquired["llmRecommendationModel"]
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

        let severityValue = 4;
        const maxCategoryWithSeverity = Object.entries(categoryWithSeverity).reduce((maxSeverityObjects, [key, value]) => {
            if (severityValue > value.severityValue) {
                severityValue = value.severityValue
                return [{
                    [key]: {
                        text: value.text,
                        severityValue: value.severityValue
                    }
                }];
            }
            if (severityValue === value.severityValue) {
                severityValue = value.severityValue;
                maxSeverityObjects.push({
                    [key]: {
                        text: value.text,
                        severityValue: value.severityValue
                    }
                });
                return maxSeverityObjects;
            }

            return maxSeverityObjects;
        }, [])


        maxCategoryWithSeverity.forEach((value) => {
            Object.entries(value).forEach(([key, value]) => {
                const text = titleTable[key];
                const icon = iconTable[key];
                card1Data.push(<RecoCard key={key} text={text} subText={value.text} icon={icon} />);
            })
        });

        severityData.text = severityTable[severityValue].text;
        severityData.color = severityTable[severityValue].color;

        const { derivedSensorDataModel, derivedWeatherDataModel } = dataAcquired;

        const {
            aggregatedSensorDataId,
            ...derivedSensorDataModelRemovedIds } = {
            ...derivedSensorDataModel
        }

        Object.entries({ ...derivedSensorDataModelRemovedIds }).filter(([key, _]) => !key.endsWith("Unit")).slice(0, 3).forEach(([key, value]) => {
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

        Object.entries({ ...derivedWeatherDataModelRemovedIds }).filter(([key, _]) => !key.endsWith("Unit")).slice(0, 3).forEach(([key, value]) => {
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



        const { aggregatedSensorDataModel, aggregatedWeatherDataModel } = dataAcquired;

        const {
            startingWindow, endingWindow,
            ...aggregatedSensorDataModelRemovedTime } = {
            ...aggregatedSensorDataModel
        }


        Object.entries({ ...aggregatedSensorDataModelRemovedTime }).slice(0, 3).forEach(([key, value]) => {
            const text = titleTable[key];
            const icon = iconTable[key];
            card3Data.push(<ParamCard key={key} text={text} subText={`${value.value} ${value.unit == "normalized" ? "" : value.unit}`} icon={icon} />);
        })

        const {
            processedWeatherDataId,
            ...aggregatedWeatherDataModelRemovedTime } = {
            ...aggregatedWeatherDataModel
        }


        Object.entries({ ...aggregatedWeatherDataModelRemovedTime }).slice(0, 3).forEach(([key, value]) => {
            const text = titleTable[key];
            const icon = iconTable[key];
            card3Data.push(<ParamCard key={key + value} text={text} subText={`${value.value} ${value.unit == "normalized" ? "" : value.unit}`} icon={icon} />);
        })

    }


    return (
        <ScrollView style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <View style={styles.viewContainerStyles} >
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Recommended Actions</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Precise actions tailored to your field's current conditions.</Text>
                <Text style={{ ...styles.subSubTitle1, backgroundColor: theme.primaryColor, color: theme.whitePrimaryColor }} >Data Source : Latest</Text>
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
                    <View style={{ ...styles.subTitle2Container, backgroundColor: (severityData.color) ? severityData.color : theme.primaryColor }}>

                        {(severityData.text.length > 0 && card1Data.length > 0) ?
                            <>
                                <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>{severityData.text}</Text>
                                <TouchableOpacity name="" onPressIn={handlePress} onPressOut={handlePress} activeOpacity={0.75}>
                                    <View style={{ ...styles.moreButtonContainer, backgroundColor: isPressed.critical ? theme.highSeverityColor : theme.whitePrimaryColor, borderColor: theme.highSeverityColor }}>
                                        <Text style={{ ...styles.subTitle3, color: isPressed.critical ? theme.whitePrimaryColor : theme.textPrimaryColor }} >More Details</Text>
                                        <Ionicons name='arrow-forward' size={20} color={isPressed.critical ? theme.whitePrimaryColor : theme.textPrimaryColor} />
                                    </View>
                                </TouchableOpacity>
                            </>
                            :
                            <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>Suggested Steps</Text>
                        }
                    </View>
                    {(severityData.text.length > 0 && card1Data.length > 0) ?
                        card1Data :
                        <>
                            <RecoCardLoading />
                            <RecoCardLoading />
                        </>
                    }

                </View>

                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Smart Metrics</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Key indicators showing your crop and environmental health</Text>
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
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >Derived Indices</Text>

                        {(card2Data.length > 0) &&
                            <>
                                <TouchableOpacity name="" onPressIn={handlePress} onPressOut={handlePress} activeOpacity={0.75}>
                                    <View style={{ ...styles.moreButtonContainer, backgroundColor: isPressed.derived ? theme.textPrimaryColor : theme.whitePrimaryColor, borderColor: theme.primaryColor }}>
                                        <Text style={{ ...styles.subTitle3, color: isPressed.derived ? theme.whitePrimaryColor : theme.textPrimaryColor }} >More Details</Text>
                                        <Ionicons name='arrow-forward' size={20} color={isPressed.derived ? theme.whitePrimaryColor : theme.textPrimaryColor} />
                                    </View>
                                </TouchableOpacity>
                            </>
                        }
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

                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Data Monitoring View</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Sensor and environmental data feed</Text>
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
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >Data Readings</Text>
                        {(card3Data.length > 0) &&
                            <>
                                <TouchableOpacity name="" onPressIn={handlePress} onPressOut={handlePress} activeOpacity={0.75}>
                                    <View style={{ ...styles.moreButtonContainer, backgroundColor: isPressed.derived ? theme.textPrimaryColor : theme.whitePrimaryColor, borderColor: theme.primaryColor }}>
                                        <Text style={{ ...styles.subTitle3, color: isPressed.derived ? theme.whitePrimaryColor : theme.textPrimaryColor }} >More Details</Text>
                                        <Ionicons name='arrow-forward' size={20} color={isPressed.derived ? theme.whitePrimaryColor : theme.textPrimaryColor} />
                                    </View>
                                </TouchableOpacity>
                            </>
                        }
                    </View>
                    {(card3Data.length > 0) ?
                        card3Data :
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

export default Dashboard

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
