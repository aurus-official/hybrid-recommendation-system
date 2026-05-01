import { Dimensions, StyleSheet, Text, View } from 'react-native'
import { BarChart } from 'react-native-gifted-charts';

const width = Dimensions.get("window").width

const TrendCard = ({ currentTheme, data, title, suffix }) => {
    const theme = currentTheme;
    return (
        <View style={{
            ...styles.card1Container,
            backgroundColor: theme.cardBackgroundColor,
            boxShadow: [{
                offsetX: 0,
                offsetY: 0,
                blurRadius: 4,
                color: theme.trendBorderColor
            }]
        }}>
            <View style={{ ...styles.subTitle2Container, backgroundColor: theme.primaryColor }}>
                <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>{title}</Text>

            </View>
            <View style{...styles.graphContainer}>
                <BarChart maxValue={50} stepValue={10} frontColor={theme.primaryColor} adjustToWidth={true} parentWidth={width * 0.90 * 0.83}
                    yAxisThickness={0} yAxisLabelSuffix={suffix} yAxisLabelWidth={width * 0.1} data={data} />
            </View>
            <View style={styles.legendWrapper}>
                <View style={styles.legendItem}>
                    <View style={[styles.legendBox, { backgroundColor: theme.primaryColor }]} />
                    <Text style={styles.legendText}>Current</Text>
                </View>
                <View style={styles.legendItem}>
                    <View style={[styles.legendBox, {
                        backgroundColor: 'transparent',
                        borderWidth: 2,
                        borderColor: theme.primaryColor
                    }]} />
                    <Text style={styles.legendText}>Future</Text>
                </View>
            </View>
        </View>
    )
}

export default TrendCard

const styles = StyleSheet.create({
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
