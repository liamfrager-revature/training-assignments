import { Link } from "react-router-dom";
import RegisterUserComponent from "../components/auth/RegisterUserComponent";
import LogoComponent from "../components/ui/LogoComponent";

const RegisterRoute = () => {
    return (
        <div className="align-center bg-white rounded shadow-box">
            <div className="padded flex-grow">
                <LogoComponent/>
            </div>
            <div className="padded flex-grow">
                <h2>Register</h2>
                <RegisterUserComponent/>
                <p>Already have an account? Click here to <Link to="/login"><span>login</span></Link>.</p>
            </div>
        </div>
    );
}

export default RegisterRoute;