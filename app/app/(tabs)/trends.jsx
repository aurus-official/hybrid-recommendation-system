import { StyleSheet, Text, useColorScheme, View } from 'react-native'
import { Colors } from '../../constants/Colors';

const Trends = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;

    return (
        <View style={{ ...styles.viewStyles, backgroundColor: theme.screenBackgroundColor }}>
            <Text></Text>
        </View>
    )
}

export default Trends

const styles = StyleSheet.create({
    viewStyles: {
        flex: 1
    }
})
