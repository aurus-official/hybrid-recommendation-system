import { StyleSheet, Text, TouchableOpacity, useColorScheme, View } from 'react-native'
import { Ionicons } from '@expo/vector-icons'
import { useState } from 'react'
import { Colors } from '../constants/Colors';
import NotifModal from './notifModal';
import { useStore } from '../store/useStore';

const NotifBell = ({ currentTheme }) => {
    const theme = currentTheme;
    const badgeCount = useStore(state => state.badgeCount);
    const [isClicked, setIsClicked] = useState(false);

    const handleClick = () => {
        setIsClicked((prev) => !prev)
    }

    return (
        <TouchableOpacity style={styles.buttonStyle} onPress={handleClick}  >
            <Ionicons
                name={isClicked ? 'notifications' : 'notifications-outline'}
                color={theme.whitePrimaryColor}
                size={28}
            />
            {badgeCount > 0 &&
                <View style={styles.badgeContainer}>
                    <Text style={styles.badgeText}>
                        {badgeCount > 99 ? '99+' : badgeCount}
                    </Text>
                </View>
            }
            <NotifModal currentTheme={theme} isClicked={isClicked} handleClick={handleClick} />
        </TouchableOpacity>
    )
}

export default NotifBell

const styles = StyleSheet.create({
    buttonStyle: {
        display: "flex",
        flexDirection: "column",
        position: 'relative',
        width: 40,
        height: 40,
        justifyContent: 'center',
        alignItems: 'center',
        marginHorizontal: 24
    },
    badgeContainer: {
        position: 'absolute',
        right: -1,
        top: -1,
        backgroundColor: '#ff4d4f',
        borderRadius: 12,
        minWidth: 20,
        height: 20,
        justifyContent: 'center',
        alignItems: 'center',
        borderColor: '#ffffff',
        borderWidth: 2,
    },
    badgeText: {
        color: '#ffffff',
        fontSize: 12,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingHorizontal: 4,
    }
});

