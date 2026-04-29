import React, { createContext, useContext, useEffect, useMemo, useState } from "react";

import { createSSE } from "../utils/sseClient";

const BASE_URL = "http://192.168.18.3:8080"

const FarmDataContext = createContext(null);

export function FarmDataProvider({ children }) {
    const [restData, setRestData] = useState(null);
    const [restPageData, setRestPageData] = useState(null);
    const [sseData, setSSEData] = useState(null);
    const [dataSource, setDataSource] = useState("None");

    const farmData = (dataSource === "Latest") ? sseData : restData

    useEffect(() => {
        const es = createSSE(setSSEData, setDataSource);

        return () => {
            es.close();
        };
    }, []);

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
        <FarmDataContext.Provider value={{ farmData, restPageData, dataSource, setDataSource, fetchRestPageData, fetchRestData }}>
            {children}
        </FarmDataContext.Provider>
    );
}

export function useFarmData() {
    return useContext(FarmDataContext);
}
