const BASE_URL = "https://armanda-uncontracted-jasper.ngrok-free.dev";

export const fetchRestFarmData = async (selectedId) => {
    try {
        const response = await fetch(
            `${BASE_URL}/recommendations/id/${selectedId}`,
            {
                headers: {
                    "ngrok-skip-browser-warning": "true",
                },
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.error("Error fetching selected id.");
    }
};

export const fetchRestFarmPageData = async (pageNumber) => {
    try {
        const response = await fetch(
            `${BASE_URL}/recommendations/page/${pageNumber}`,
            {
                headers: {
                    "ngrok-skip-browser-warning": "true",
                },
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.error("Error fetching page.");
    }
};

export const fetchReadingStatus = async (selectedId) => {
    try {
        const response = await fetch(
            `${BASE_URL}/reading-status/id/${selectedId}`,
            {
                headers: {
                    "ngrok-skip-browser-warning": "true",
                },
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.error("Error fetching selected id.");
    }
};

export const fetchReadingStatusPage = async (pageNumber) => {
    try {
        const response = await fetch(
            `${BASE_URL}/reading-status/page/${pageNumber}`,
            {
                headers: {
                    "ngrok-skip-browser-warning": "true",
                },
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.error("Error fetching page.");
    }
};

export const fetchHardwareStatus = async (selectedId) => {
    try {
        const response = await fetch(
            `${BASE_URL}/hardware-status/id/${selectedId}`,
            {
                headers: {
                    "ngrok-skip-browser-warning": "true",
                },
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.error("Error fetching selected id.");
    }
};

export const fetchHardwareStatusPage = async (pageNumber) => {
    try {
        const response = await fetch(
            `${BASE_URL}/hardware-status/page/${pageNumber}`,
            {
                headers: {
                    "ngrok-skip-browser-warning": "true",
                },
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.error("Error fetching page.");
    }
};
