import { StyleSheet, View } from 'react-native'
import { Skeleton } from 'moti/skeleton';

const ParamCardLoading = ({ currentTheme }) => {
    const theme = currentTheme;

    return (
        <View style={{
            ...styles.paramCardStyle,
            borderColor: theme.paramBorderColor,
            boxShadow: [{
                offsetX: 0,
                offsetY: 0,
                blurRadius: 4,
                color: theme.paramBorderColor
            }]
        }}>
            <Skeleton colorMode={theme} width="100%" height="148">
            </Skeleton>
        </View>
    )
}

export default ParamCardLoading

const styles = StyleSheet.create({
    paramCardStyle: {
        width: "42%",
        minHeight: "148",
        borderStyle: "solid",
        borderWidth: 1.25,
        borderRadius: 12,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    },
    viewStyleSubText: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        alignSelf: "flex-start",
        marginLeft: 16
    },
    viewStyleImage: {
        padding: 8,
        borderRadius: 54,
        overflow: "hidden",
    },
    dataText: {
        fontSize: 24,
        marginTop: 32,
        marginBottom: 28,
        fontWeight: "bold"
    },
    subText: {
        marginLeft: 12
    }
})
