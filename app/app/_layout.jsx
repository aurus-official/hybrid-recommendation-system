import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { useFonts, Inter_400Regular, Inter_500Medium } from '@expo-google-fonts/inter';
import { Poppins_400Regular, Poppins_500Medium } from '@expo-google-fonts/poppins';
import { QueryClientProvider } from '@tanstack/react-query';
import { useNotificationManager } from '../hooks/useNotifManager';
import { createContext, useEffect, useState } from 'react';
import { useStore } from '../store/useStore';
import { startSSE } from '../api/sseManager';
import { useShallow } from 'zustand/react/shallow';
import { customQueryClient } from '../utils/queryClient';
import { initialWindowMetrics, SafeAreaProvider } from 'react-native-safe-area-context';
import { useNavigationMode } from 'react-native-navigation-mode';
import Animated, {
    FadeOut,
    useSharedValue,
    useAnimatedStyle,
    withTiming
} from 'react-native-reanimated';
import { StyleSheet } from 'react-native';
import Logo from "../assets/pechai-logo.png";

const queryClient = customQueryClient;
export const NavModeContext = createContext(null);

export default function RootLayout() {

    const [isAnimationOver, setIsAnimationOver] = useState(false);
    const logoScale = useSharedValue(3);

    useEffect(() => {
        logoScale.value = withTiming(1.5, { duration: 3600 });

        const timer = setTimeout(() => {
            setIsAnimationOver(true);
        }, 4000);

        return () => clearTimeout(timer);
    }, []);

    const logoStyle = useAnimatedStyle(() => ({
        transform: [{ scale: logoScale.value }],
    }));

    const { navigationMode, loading: navigationLoading } = useNavigationMode();
    const { expoPushToken, deviceId } = useStore(
        useShallow((state) => ({
            expoPushToken: state.expoPushToken,
            deviceId: state.deviceId,
        }))
    );
    const { initNotifications } = useNotificationManager(queryClient);

    let [fontsLoaded] = useFonts({
        Inter_400Regular,
        Inter_500Medium,
        Poppins_400Regular,
        Poppins_500Medium,
    });


    useEffect(() => {
        initNotifications();
    }, []);

    useEffect(() => {
        if (!expoPushToken || !deviceId) return;

        console.log("Tokens acquired, starting SSE stream...");
        const es = startSSE(expoPushToken, deviceId);

        return () => {
            console.log("Closing SSE stream...");
            es.close();
        };
    }, [expoPushToken, deviceId]);


    if (!fontsLoaded) {
        return null;
    }

    if (navigationLoading || !navigationMode) {
        return null;
    }

    return (
        <QueryClientProvider client={queryClient}>
            <NavModeContext.Provider value={navigationMode}>
                <SafeAreaProvider initialMetrics={initialWindowMetrics}>
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
                        </Animated.View>)}
                    <StatusBar style='inverted'></StatusBar>
                    <Stack screenOptions={{
                        headerShown: false,
                    }} >
                        <Stack.Screen name="(tabs)" />
                    </Stack>
                </SafeAreaProvider>
            </NavModeContext.Provider>
        </ QueryClientProvider>
    );
}
const styles = StyleSheet.create({
    rootContainer: {
        flex: 1,
    },
    splashContainer: {
        position: 'absolute',
        inset: 0,
        backgroundColor: "#fefbf8",
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 9999,
    },
    logo: {
        width: 150,
        height: 150,
    },
});

