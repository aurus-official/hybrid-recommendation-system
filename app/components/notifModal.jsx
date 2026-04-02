import { Button, StyleSheet, Text, View } from 'react-native'
import ReactNativeModal from 'react-native-modal'

const NotifModal = ({ isClicked, handleClick }) => {
    return (
        <ReactNativeModal style={{ height: "100%", width: "100%", padding: 0, margin: 0 }} coverScreen={true} visible={isClicked} animationType="fade" transparent={true}>
            <View style={{ height: "100%", width: "100%", flex: 1, backgroundColor: 'rgba(0,0,0,0.5)', justifyContent: 'center' }}>
                <View style={{ backgroundColor: 'white', padding: 20 }}>
                    <Text>Hello from the Modal!</Text>
                    <Button title="Close" onPress={handleClick} />
                </View>
            </View>
        </ReactNativeModal>
    )
}

export default NotifModal

const styles = StyleSheet.create({})
