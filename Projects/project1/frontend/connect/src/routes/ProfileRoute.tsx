import { useParams } from "react-router-dom";
import ProfileDisplayComponent from "../components/user/ProfileDisplayComponent";
import { useEffect, useState } from "react";
import { useUser } from "../utils/Context";

const ProfileRoute = () => {
    const { userID } = useParams();
    const { currentUser } = useUser();
    const [userIDNumber, setUserIDNumber] = useState(() => {
        if (userID)
            return Number(userID)
        return currentUser!.id
    });

    useEffect(() => {
        if (userID)
            setUserIDNumber(Number(userID))
        else
            setUserIDNumber(currentUser!.id)
    }, [userID, currentUser, setUserIDNumber])


    return (
        <>
            { currentUser!.id === userID && <h1>Profile</h1>}
            <ProfileDisplayComponent userID={userIDNumber!}/>
        </>
    );
}

export default ProfileRoute;