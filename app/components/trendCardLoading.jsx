import { Skeleton } from 'moti/skeleton';
import { Dimensions, StyleSheet, Text, View } from 'react-native'
import { BarChart } from 'react-native-gifted-charts';

const width = Dimensions.get("window").width

const TrendCardLoading = ({ currentTheme }) => {
    const theme = currentTheme;
    return (
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
                <Text style={{ ...styles.subTitle2, color: theme.whitePrimaryColor }}>Pattern</Text>
            </View>
            <View style{...styles.graphContainer}>
                <Skeleton colorMode={theme} width="100%" height="190">
                </Skeleton>
            </View>
        </View>
    )
}

export default TrendCardLoading

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
