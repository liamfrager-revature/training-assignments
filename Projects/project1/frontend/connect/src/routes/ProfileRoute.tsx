import { useParams } from "react-router-dom";
import ProfileDisplayComponent from "../components/user/ProfileDisplayComponent";
import ErrorComponent from "../components/error/ErrorComponent";

const ProfileRoute = () => {
    const { userID } = useParams();

    return (
        <>
            { userID ? (
                <ProfileDisplayComponent userID={Number(userID)}/>
            ) : (
                <ErrorComponent statusCode={400} message={`User ID not valid.`}/>
            )}
        </>
    );
}

export default ProfileRoute;