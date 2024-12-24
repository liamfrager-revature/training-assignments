import { Outlet } from "react-router-dom";
import PageContent from "../components/ui/PageContent";

const UnprotectedRoute = () =>{
    return (
        <div id="page-layout" className="bg-dark">
            <PageContent>
                <Outlet/>
            </PageContent>
        </div>
    )
}

export default UnprotectedRoute;