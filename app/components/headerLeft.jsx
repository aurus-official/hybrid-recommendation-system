import { Image, StyleSheet, Text, View } from 'react-native'
import LogoImage from "../assets/logo.png"


const HeaderLeft = ({ titleName }) => {
    return (
        <View style={styles.viewStyle}>
            <Image style={styles.logoStyle} source={LogoImage}></Image>
            <Text style={styles.textStyle}>{titleName || "AgriSmart"}</Text>
        </View>
    )
}

export default HeaderLeft

const styles = StyleSheet.create({
    viewStyle: {
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
        marginLeft: 8,
        padding: 0,
        textAlign: "center",
    },
    logoStyle: {
        width: 38,
        height: 38,
        resizeMode: "contain",
    },
    textStyle: {
        color: "#ffffff",
        marginLeft: 12,
        fontSize: 20,
        textAlign: "center"
    }
})
