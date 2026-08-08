import { Tabs } from 'expo-router'
import { StyleSheet, useColorScheme } from 'react-native'
import { Ionicons } from '@expo/vector-icons'
import { Colors } from '../../constants/Colors'
import NotifBell from '../../components/notifBell'
import HeaderLeft from '../../components/headerLeft'
import { NavModeContext } from '../_layout';
import { useContext } from 'react'

const GroupLayout = () => {
    const colorScheme = useColorScheme();
    const theme = Colors[colorScheme] || Colors.light;
    const navMode = useContext(NavModeContext);
    const { type, navigationBarHeight } = navMode;
    const defautHeight = 60;

    return (
        <Tabs screenOptions={{
            tabBarStyle: (type === "gesture") ? { height: defautHeight } : { height: defautHeight + navigationBarHeight },
            tabBarActiveTintColor: theme.textPrimaryColor,
            headerStyle: { ...styles.headerStyle, backgroundColor: theme.primaryColor },
            headerRight: ((props) => (<NotifBell currentTheme={theme} {...props} />)),
        }} >
            <Tabs.Screen name="dashboard" options={{
                tabBarLabel: "Dashboard",
                headerTitle: ((props) => (<HeaderLeft {...props} currentTheme={theme} titleName="Dashboard" />)),
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
                headerTitle: ((props) => (<HeaderLeft {...props} currentTheme={theme} titleName="Monitoring" />)),
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
                headerTitle: ((props) => (<HeaderLeft {...props} currentTheme={theme} titleName="Insights" />)),
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
                headerTitle: ((props) => (<HeaderLeft {...props} currentTheme={theme} titleName="Trends" />)),
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
                headerTitle: ((props) => (<HeaderLeft{...props} currentTheme={theme} titleName="Status" />)),
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
    headerStyle: {
        height: 104,
    },
})
