import { createContext, useContext, useState } from "react";
import { User } from "./Types";

// User Context

type UserContextType = {
    currentUser: User | null;
    setCurrentUser: (user: User | null) => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserContextProvider = (props: {children: any}) => {
    const storedUser = sessionStorage.getItem("currentUser");
    const [currentUser, setCurrentUser] = useState<User | null>(JSON.parse(storedUser!));
    return (
        <UserContext.Provider value={{currentUser, setCurrentUser}}>
            {props.children}
        </UserContext.Provider>
    )
}


export const useUser = (): UserContextType => {
    const context = useContext(UserContext);
    if (!context)
        throw new Error("useUser must be used within a UserContextProvider");
    return context;
};