import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { useFonts, Inter_400Regular, Inter_500Medium } from '@expo-google-fonts/inter';
import { Poppins_400Regular, Poppins_500Medium } from '@expo-google-fonts/poppins';
import { FarmDataProvider } from '../contexts/farmDataProvider';
import { NotificationProvider } from '../contexts/notificationProvider';

export default function RootLayout() {
    let [fontsLoaded] = useFonts({
        Inter_400Regular,
        Inter_500Medium,
        Poppins_400Regular,
        Poppins_500Medium,

    });

    if (!fontsLoaded) {
        return null;
    }


    return (
        <NotificationProvider>
            <FarmDataProvider>
                <StatusBar style='inverted'></StatusBar>
                <Stack screenOptions={{
                    headerShown: false,
                }} >
                    <Stack.Screen name="(tabs)" />
                </Stack>
            </FarmDataProvider>
        </NotificationProvider>
    );
}

