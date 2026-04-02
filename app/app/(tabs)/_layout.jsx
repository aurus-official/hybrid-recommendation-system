import { Tabs } from 'expo-router'
import { StyleSheet, Text, View } from 'react-native'
import { Ionicons } from '@expo/vector-icons'

const GroupLayout = () => {
    return (
        <Tabs screenOptions={{
            tabBarStyle: styles.groupLayoutContainer,
            tabBarActiveTintColor: "#222222"

        }} >
            <Tabs.Screen name="dashboard" options={{ title: "Dashboard" }}></Tabs.Screen>
            <Tabs.Screen name="insights" options={{ title: "Insights" }}></Tabs.Screen>
            <Tabs.Screen name="monitoring" options={{ title: "Monitoring" }}></Tabs.Screen>
            <Tabs.Screen name="status" options={{ title: "Status" }}></Tabs.Screen>
            <Tabs.Screen name="trends" options={{ title: "Trends" }}></Tabs.Screen>
        </Tabs >
    )
}

export default GroupLayout

const styles = StyleSheet.create({
    groupLayoutContainer: {
        height: 55

    }
})
