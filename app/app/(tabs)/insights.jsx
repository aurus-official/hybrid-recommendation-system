import { ScrollView, StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';
import ParamCard from '../../components/paramCard';
import { Ionicons } from '@expo/vector-icons';
import { useReducer, useState } from 'react';

const Insights = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;


    return (
        <ScrollView style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <View style={styles.viewContainerStyles} >
                <Text style={{ ...styles.title1, color: theme.textPrimaryColor }} >Insights and Recommendations</Text>
                <Text style={{ ...styles.subTitle1, color: theme.textSecondaryColor }} >Data Source : Live</Text>
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
                    <View style={{ ...styles.subTitle2Container, backgroundColor: theme.highSeverityColor }}>
                        <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }} >High Severity</Text>
                    </View>
                    <ParamCard />
                    <ParamCard />
                    <ParamCard />
                    <ParamCard />
                    <ParamCard />
                    <ParamCard />
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
