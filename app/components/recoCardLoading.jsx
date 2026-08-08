import { StyleSheet, View } from 'react-native'
import { Skeleton } from 'moti/skeleton';

const RecoCardLoading = ({ currentTheme }) => {
    const theme = currentTheme;

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
        minHeight: 148,
        borderStyle: "solid",
        borderWidth: 1.25,
        borderRadius: 12,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        marginHorizontal: 24
    },
});
