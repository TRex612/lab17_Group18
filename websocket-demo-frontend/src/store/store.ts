import { configureStore } from '@reduxjs/toolkit'
import webSocketReducer from './Slices/webSocketSlice'
import usernameReducer from './Slices/usernameSlice'
import numUserReducer from './Slices/numUserSlice'
export const store = configureStore({
    reducer: {
        webSocket : webSocketReducer,
        username : usernameReducer,
        numUser : numUserReducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        serializableCheck: false,
    }),
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch