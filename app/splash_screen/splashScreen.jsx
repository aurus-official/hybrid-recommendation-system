import React, { useState, useEffect } from 'react';
import { StyleSheet, View } from 'react-native';
import Animated, {
    FadeOut,
    useSharedValue,
    useAnimatedStyle,
    withTiming
} from 'react-native-reanimated';
import Logo from "../assets/pechai-logo.png"

export default function App() {
    const [isAnimationOver, setIsAnimationOver] = useState(false);
    const logoScale = useSharedValue(0.7);

    useEffect(() => {
        logoScale.value = withTiming(1, { duration: 600 });

        const timer = setTimeout(() => {
            setIsAnimationOver(true);
        }, 1000);

        return () => clearTimeout(timer);
    }, []);

    const logoStyle = useAnimatedStyle(() => ({
        transform: [{ scale: logoScale.value }],
    }));

    return (
        <View style={styles.rootContainer}>
            {!isAnimationOver && (
                <Animated.View
                    exiting={FadeOut.duration(400)}
                    style={styles.splashContainer}
                >
                    <Animated.Image
                        source={Logo}
                        style={[styles.logo, logoStyle]}
                        resizeMode="contain"
                    />
                </Animated.View>
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    rootContainer: {
        flex: 1,
    },
    mainAppView: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    text: {
        fontSize: 18,
        color: '#000000',
    },
    splashContainer: {
        position: 'absolute',
        inset: 0,
        backgroundColor: '#121212',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 999,
    },
    logo: {
        width: 150,
        height: 150,
    },
});

