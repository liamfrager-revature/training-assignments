import { Link } from "react-router-dom";
import AuthenticateUserComponent from "../components/auth/AuthenticateUserComponent";

const LoginRoute = () => {
    return (
        <>
        <AuthenticateUserComponent/>
        <span>Need an account? Click here to <Link to="/register"><span>register</span></Link></span>
        </>
    );
}

export default LoginRoute;