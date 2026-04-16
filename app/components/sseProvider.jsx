import React, { createContext, useContext, useEffect, useState } from "react";
import { createSSE } from "./sseClient";

const SSEContext = createContext(null);

export function SSEProvider({ children }) {
    const [sseLatestData, setSSELatestData] = useState(null);

    useEffect(() => {
        const es = createSSE(setSSELatestData);

        return () => {
            es.close();
        };
    }, []);

    return (
        <SSEContext.Provider value={sseLatestData}>
            {children}
        </SSEContext.Provider>
    );
}

export function useSSE() {
    return useContext(SSEContext);
}
