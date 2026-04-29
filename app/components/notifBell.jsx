import { StyleSheet, TouchableOpacity, useColorScheme } from 'react-native'
import { Ionicons } from '@expo/vector-icons'
import { useState } from 'react'
import { Colors } from '../constants/Colors';
import NotifModal from './notifModal';

const NotifBell = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const [isClicked, setIsClicked] = useState(false);

    const handleClick = () => {
        setIsClicked((prev) => !prev)
    }

    return (
        <TouchableOpacity style={styles.buttonStyle} onPress={handleClick}  >
            <Ionicons
                name={isClicked ? 'notifications' : 'notifications-outline'}
                color={theme.screenBackgroundColor}
                size={28}
            />
            <NotifModal isClicked={isClicked} handleClick={handleClick} />
        </TouchableOpacity>
    )
}

export default NotifBell

const styles = StyleSheet.create({
    buttonStyle: {
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        padding: 0,
        margin: 0,
        marginRight: 24
    }
})
