import { StyleSheet, Text, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';

const Status = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;

    return (
        <View style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <Text></Text>
        </View>
    )
}

export default Status

const styles = StyleSheet.create({
    viewStyles: {
        flex: 1
    }
})
