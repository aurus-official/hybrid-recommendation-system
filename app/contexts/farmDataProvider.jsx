import React, { createContext, useContext, useEffect, useState } from "react";

import { createSSE } from "../utils/sseClient";
import { useNotificationData } from "./notificationProvider";

const BASE_URL = "http://192.168.18.3:8080"

const FarmDataContext = createContext(null);

export function FarmDataProvider({ children }) {
    const [restData, setRestData] = useState(null);
    const [restPageData, setRestPageData] = useState(null);
    const [sseData, setSSEData] = useState(null);
    const [notificationData, setNotificationData] = useState(null)
    const [dataSource, setDataSource] = useState("None");
    const { expoPushToken, deviceId } = useNotificationData();

    const farmData = (dataSource === "Latest") ? sseData : restData

    useEffect(() => {
        if (expoPushToken == "" || deviceId == "") return;

        const es = createSSE(setSSEData, setDataSource, setNotificationData, expoPushToken, deviceId);

        return () => {
            es.close();
        };
    }, [expoPushToken, deviceId]);

    const fetchRestPageData = async (pageNumber) => {
        try {
            const response = await fetch(`${BASE_URL}/history/page/${pageNumber}`);
            const data = await response.json();
            setRestPageData(data);
        } catch (e) { console.error(e); }
    };

    const fetchRestData = async (id) => {
        try {
            const response = await fetch(`${BASE_URL}/history/id/${id}`);
            const data = await response.json();
            setRestData(data);
        } catch (e) { console.error(e); }
    };

    return (
        <FarmDataContext.Provider value={{ farmData, restPageData, notificationData, dataSource, setDataSource, fetchRestPageData, fetchRestData }}>
            {children}
        </FarmDataContext.Provider>
    );
}

export function useFarmData() {
    return useContext(FarmDataContext);
}
