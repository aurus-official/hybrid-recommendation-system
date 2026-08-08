import { createMMKV } from "react-native-mmkv";
import { create } from "zustand";
import { persist, createJSONStorage } from "zustand/middleware";
import { customQueryClient } from "../utils/queryClient";

const storage = createMMKV();
const zustandStorage = {
    setItem: (name, value) => storage.set(name, value),
    getItem: (name) => storage.getString(name) ?? null,
    removeItem: (name) => storage.delete(name),
};
const queryClient = customQueryClient;

export const useStore = create(
    persist(
        (set, get) => ({
            notifications: [],
            seenIds: [],
            liveData: null,

            displayFarmData: null,
            displayReadingStatus: null,
            displayHardwareStatus: null,

            isLiveFarmDataMode: true,
            farmDataSource: "None",

            isLiveReadingStatusMode: true,
            readingStatusSource: "None",

            isLiveHardwareStatusMode: true,
            hardwareStatusSource: "None",

            badgeCount: 0,
            deviceId: null,
            expoPushToken: null,

            setSSEUpdate: (data) => {
                if (
                    data["aggregatedSensorDataModel"] == null ||
                    data["aggregatedWeatherDataModel"] == null ||
                    data["derivedSensorDataModel"] == null ||
                    data["derivedWeatherDataModel"] == null ||
                    data["llmRecommendationModel"] == null ||
                    data["processedWeatherDataModel"] == null
                ) {
                    set({ liveData: data });
                    if (get().isLiveFarmDataMode) {
                        set({
                            displayFarmData: data,
                        });
                    }
                    return;
                }
                const {
                    readingStatusModel,
                    hardwareStatusModel,
                    ...displayFarmData
                } = data;

                const displayFarmDataDateTime = new Date(
                    displayFarmData["llmRecommendationModel"].createdAt
                ).toLocaleString();

                const displayReadingStatusDateTime =
                    readingStatusModel.createdAt == null
                        ? "None"
                        : `${new Date(
                              readingStatusModel.createdAt
                          ).toLocaleString()} (Latest)`;

                const displayHardwareStatusDateTime =
                    hardwareStatusModel.createdAt == null
                        ? "None"
                        : `${new Date(
                              hardwareStatusModel.createdAt
                          ).toLocaleString()} (Latest)`;

                set({ liveData: data });
                if (get().isLiveFarmDataMode) {
                    set({
                        displayFarmData: displayFarmData,
                        displayReadingStatus: readingStatusModel,
                        displayHardwareStatus: hardwareStatusModel,
                        farmDataSource: `${displayFarmDataDateTime} (Latest)`,
                        readingStatusSource: `${displayReadingStatusDateTime}`,
                        hardwareStatusSource: `${displayHardwareStatusDateTime}`,
                    });
                }
            },
            setRestFarmData: (selectedDataId) => {
                const cachedRestFarmData = queryClient.getQueryData([
                    "restFarmData",
                    selectedDataId,
                ]);
                set({
                    isLiveFarmDataMode: false,
                    displayFarmData: cachedRestFarmData,
                });
            },
            setRestReadingStatus: (selectedReadingStatusId) => {
                const cachedRestReadingStatus = queryClient.getQueryData([
                    "restReadingStatus",
                    selectedReadingStatusId,
                ]);
                set({
                    isLiveFarmDataMode: false,
                    displayReadingStatus: cachedRestReadingStatus,
                });
            },
            setRestHardwareStatus: (selectedHardwareStatusId) => {
                const cachedRestHardwareStatus = queryClient.getQueryData([
                    "restHardwareStatus",
                    selectedHardwareStatusId,
                ]);
                set({
                    isLiveFarmDataMode: false,
                    displayHardwareStatus: cachedRestHardwareStatus,
                });
            },
            setRestFarmDataSource: (date) => {
                set({
                    isLiveFarmDataMode: false,
                    farmDataSource: date,
                });
            },
            setRestReadingStatusSource: (date) => {
                set({
                    isLiveReadingStatusMode: false,
                    readingStatusSource: date,
                });
            },
            setRestHardwareStatusSource: (date) => {
                set({
                    isLiveHardwareStatusMode: false,
                    hardwareStatusSource: date,
                });
            },
            resumeLiveFarmData: () => {
                const currentLive = get().liveData;
                const {
                    readingStatusModel,
                    hardwareStatusModel,
                    ...displayFarmData
                } = currentLive;
                const displayFarmDataDateTime = new Date(
                    displayFarmData["llmRecommendationModel"].createdAt
                ).toLocaleString();
                set({
                    displayFarmData: displayFarmData,
                    isLiveFarmDataMode: true,
                    farmDataSource: currentLive
                        ? `${displayFarmDataDateTime} (Latest)`
                        : "None",
                });
            },
            resumeLiveReadingStatus: () => {
                const currentLive = get().liveData;
                const { readingStatusModel } = currentLive;
                const displayReadingStatusDateTime = new Date(
                    readingStatusModel.createdAt
                ).toLocaleString();

                set({
                    displayReadingStatus: readingStatusModel,
                    isLiveReadingStatusMode: true,
                    readingStatusSource: currentLive
                        ? `${displayReadingStatusDateTime} (Latest)`
                        : "None",
                });
            },
            resumeLiveHardwareStatus: () => {
                const currentLive = get().liveData;
                const { hardwareStatusModel } = currentLive;
                const displayHardwareStatusDateTime = new Date(
                    hardwareStatusModel.createdAt
                ).toLocaleString();

                set({
                    displayHardwareStatus: hardwareStatusModel,
                    isLiveHardwareStatusMode: true,
                    HardwareStatusSource: currentLive
                        ? `${displayHardwareStatusDateTime} (Latest)`
                        : "None",
                });
            },

            setNotifications: (list) => {
                set({ notifications: list });
            },
            setBadgeCount: () => {
                const currentIds = get().notifications.map((n) => n.id);
                const seenIds = get().seenIds;
                let tempBadgeCount = 0;

                for (let currentId of currentIds) {
                    if (!seenIds.includes(currentId)) {
                        tempBadgeCount += 1;
                    }
                }

                set({ badgeCount: tempBadgeCount });
            },
            resetBadgeCount: () => {
                set({ badgeCount: 0 });
            },
            setExpoPushToken: (token) => set({ expoPushToken: token }),
            setDeviceId: (id) => set({ deviceId: id }),
            markAllSeen: () => {
                const currentIds = get().notifications.map((n) => n.id);
                set((state) => {
                    const merged = Array.from(
                        new Set([...currentIds, state.seenIds])
                    );
                    const limitedSeenIds = merged.slice(0, 5);

                    return {
                        seenIds: limitedSeenIds,
                        badgeCount: 0,
                    };
                });
            },
            markOneSeen: (id) => {
                const newSeenIds = get().seenIds;
                if (newSeenIds.includes(id)) {
                    return;
                }
                newSeenIds.pop();
                newSeenIds.unshift(id);

                newSeenIds.sort((a, b) => b - a);

                set({ seenIds: newSeenIds });
            },
        }),
        {
            name: "notification-state",
            storage: createJSONStorage(() => zustandStorage),
            partialize: (state) => ({
                seenIds: state.seenIds,
            }),
        }
    )
);
