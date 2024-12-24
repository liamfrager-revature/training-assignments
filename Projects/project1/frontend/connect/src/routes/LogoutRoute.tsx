import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useUser } from "../utils/Context";

const LogoutRoute = () => {
    const navigate = useNavigate();
    const {currentUser, setCurrentUser} = useUser();

    useEffect(() => {
        if (currentUser) {
            sessionStorage.removeItem('currentUser');
            sessionStorage.removeItem('token');
            setCurrentUser(null);
        }
        navigate('/login');
    });
    return (
        <></>
    )
}

export default LogoutRoute;