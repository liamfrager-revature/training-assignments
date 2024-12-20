import { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import Navbar from "../components/ui/Navbar";
import { useUser } from "./Context";
import PageContent from "../components/ui/PageContent";

const ProtectedRoute = () =>{
    const navigate = useNavigate()
    const {currentUser} = useUser();
    // TODO: Check token validity on back end.

    useEffect(() => {
        if (!currentUser)
            navigate("/login");
    }, [currentUser, navigate]);

    if (currentUser)
        return (
            <>
                { currentUser &&
                <>
                <Navbar/>
                <PageContent>
                    <Outlet/>
                </PageContent>
                </>
                }
            </>
        )
    return (<></>);
}

export default ProtectedRoute;