import { Tabs } from 'expo-router'
import { StyleSheet, useColorScheme } from 'react-native'
import { Ionicons } from '@expo/vector-icons'
import { Colors } from '../../constants/Colors'
import NotifBell from '../../components/notifBell'
import HeaderLeft from '../../components/headerLeft'


const GroupLayout = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;

    return (
        <Tabs screenOptions={{
            tabBarStyle: styles.tabBarStyle,
            tabBarActiveTintColor: theme.textPrimaryColor,
            headerStyle: { ...styles.headerStyle, backgroundColor: theme.primaryColor },
            headerRight: ((props) => (<NotifBell currentTheme={theme} {...props} />)),
        }} >
            <Tabs.Screen name="dashboard" options={{
                tabBarLabel: "Dashboard",
                headerTitle: ((props) => (<HeaderLeft {...props} titleName="Dashboard" />)),
                tabBarIcon: (({ focused }) => {
                    return <Ionicons
                        name={focused ? 'home' : 'home-outline'}
                        color={focused ? theme.primaryColor : theme.textPrimaryColor}
                        size={20}
                    />
                }),

            }}  ></Tabs.Screen>
            <Tabs.Screen name="monitoring" options={{
                tabBarLabel: "Monitoring",
                headerTitle: ((props) => (<HeaderLeft {...props} titleName="Monitoring" />)),
                tabBarIcon: (({ focused }) => {
                    return <Ionicons
                        name={focused ? 'leaf' : 'leaf-outline'}
                        color={focused ? theme.primaryColor : theme.textPrimaryColor}
                        size={20}
                    />
                })
            }}></Tabs.Screen>
            <Tabs.Screen name="insights" options={{
                tabBarLabel: "Insights",
                headerTitle: ((props) => (<HeaderLeft {...props} titleName="Insights" />)),
                tabBarIcon: (({ focused }) => {
                    return <Ionicons
                        name={focused ? 'bulb' : 'bulb-outline'}
                        color={focused ? theme.primaryColor : theme.textPrimaryColor}
                        size={20}
                    />
                })
            }}></Tabs.Screen>
            <Tabs.Screen name="trends" options={{
                tabBarLabel: "Trends",
                headerTitle: ((props) => (<HeaderLeft {...props} titleName="Trends" />)),
                tabBarIcon: (({ focused }) => {
                    return <Ionicons
                        name={focused ? 'receipt' : 'receipt-outline'}
                        color={focused ? theme.primaryColor : theme.textPrimaryColor}
                        size={20}
                    />
                })
            }}></Tabs.Screen>
            <Tabs.Screen name="status" options={{
                tabBarLabel: "Status",
                headerTitle: ((props) => (<HeaderLeft{...props} titleName="Status" />)),
                tabBarIcon: (({ focused }) => {
                    return <Ionicons
                        name={focused ? 'settings' : 'settings-outline'}
                        color={focused ? theme.primaryColor : theme.textPrimaryColor}
                        size={20}
                    />
                })
            }}></Tabs.Screen>
        </Tabs >
    )
}

export default GroupLayout

const styles = StyleSheet.create({
    tabBarStyle: {
        height: 56,
    },
    headerStyle: {
        height: 108,
    },
})
