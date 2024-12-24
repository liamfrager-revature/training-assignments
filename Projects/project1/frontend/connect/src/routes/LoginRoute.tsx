import { Link } from "react-router-dom";
import AuthenticateUserComponent from "../components/auth/AuthenticateUserComponent";
import LogoComponent from "../components/ui/LogoComponent";

const LoginRoute = () => {
    return (
        <div className="align-center bg-white rounded shadow-box">
            <div className="padded flex-grow">
                <LogoComponent/>
            </div>
            <div className="padded flex-grow">
                <h2>Login</h2>
                <AuthenticateUserComponent/>
                <p>Need an account? Click here to <Link to="/register"><span>register</span></Link>.</p>
            </div>
        </div>
    );
}

export default LoginRoute;