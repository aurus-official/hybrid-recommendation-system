import { StyleSheet, Text, useColorScheme, View } from 'react-native'
import { Colors } from '../constants/Colors';
import { Skeleton } from 'moti/skeleton';

const RecoCardLoading = ({ text, icon, subText }) => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;

    return (
        <View style={{
            ...styles.recoCardStyle,
            borderColor: theme.recoBorderColor,
            boxShadow: [{
                offsetX: 0,
                offsetY: 0,
                blurRadius: 4,
                color: theme.recoBorderColor
            }]
        }}>

            <Skeleton colorMode={theme} width="100%" height="148">
            </Skeleton>
        </View>
    )
}

export default RecoCardLoading

const styles = StyleSheet.create({
    recoCardStyle: {
        width: "89%",
        minHeight: "148",
        borderStyle: "solid",
        borderWidth: 1.25,
        borderRadius: 12,
        // boxShadow: "3 1 1 0 #5F6A6A",
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
        marginLeft: 16,
        marginTop: 16,
    },
    viewStyleImage: {
        padding: 8,
        borderRadius: 54,
        overflow: "hidden",
    },
    dataText: {
        fontSize: 16,
        marginTop: 20,
        marginBottom: 28,
        marginLeft: 24,
        marginRight: 24,
        fontWeight: "semibold",
        opacity: 0.75
    },
    subText: {
        marginLeft: 12,
        fontSize: 16
    }
})
