import { Link } from "react-router-dom";
import RegisterUserComponent from "../components/auth/RegisterUserComponent";

const RegisterRoute = () => {
    return (
        <>
        <RegisterUserComponent/>
        <span>Already have an account? Click here to <Link to="/login"><span>login.</span></Link></span>
        </>
    );
}

export default RegisterRoute;