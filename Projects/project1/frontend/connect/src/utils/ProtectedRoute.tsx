import { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import Navbar from "../components/ui/Navbar";
import { useUser } from "./Context";
import PageContent from "../components/ui/PageContent";
import axiosUtil from "./AxiosUtil";

const ProtectedRoute = () =>{
    const navigate = useNavigate()
    const {currentUser} = useUser();
    useEffect(() => {
        if (!currentUser)
            navigate("/logout");
        axiosUtil.get("/auth").then(res => {
            if (res.data === false) navigate("/logout");
        })
    }, [currentUser, navigate]);

    if (currentUser)
        return (
            <>
                { currentUser &&
                <div id="page-layout">
                <Navbar/>
                <PageContent>
                    <Outlet/>
                </PageContent>
                </div>
                }
            </>
        )
    return (<></>);
}

export default ProtectedRoute;