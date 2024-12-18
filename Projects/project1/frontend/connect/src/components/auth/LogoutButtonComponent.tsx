import { useNavigate } from "react-router-dom";

const LogoutButtonComponent = () => {
    const navigate = useNavigate();
    return (
        <button onClick={()=>navigate('/logout')}>Logout</button>
    )
}

export default LogoutButtonComponent;