import React, { createContext, useContext, useEffect, useState } from "react";

const RestContext = createContext(null);
const BASE_URL = "http://192.168.18.3:8080"

export function RestProvider({ children }) {
    const [restData, setRestData] = useState(null);
    const [restPageData, setRestPageData] = useState(null);


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
        <RestContext.Provider value={{ restData, restPageData, setRestData, setRestPageData, fetchRestData, fetchRestPageData }} >
            {children}
        </RestContext.Provider >
    );
}

export function useRest() {
    return useContext(RestContext);
}
