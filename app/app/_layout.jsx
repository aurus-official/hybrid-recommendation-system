import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { useFonts, Inter_400Regular, Inter_500Medium } from '@expo-google-fonts/inter';
import { Poppins_400Regular, Poppins_500Medium } from '@expo-google-fonts/poppins';

export default function RootLayout() {
    let [fontsLoaded] = useFonts({
        Inter_400Regular,
        Inter_500Medium,
        Poppins_400Regular,
        Poppins_500Medium,

    });

    if (!fontsLoaded) {
        return null; // Or a loading spinner
    }


    return (
        <>
            <StatusBar style='inverted'></StatusBar>
            <Stack screenOptions={{
                headerShown: false,
            }} >
                <Stack.Screen name="(tabs)" />
            </Stack>
        </>
    );
}

