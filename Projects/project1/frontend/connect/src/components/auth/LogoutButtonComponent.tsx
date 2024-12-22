import { useNavigate } from "react-router-dom";

const LogoutButtonComponent = () => {
    const navigate = useNavigate();
    return (
        <button onClick={()=>navigate('/logout')}>Log Out</button>
    )
}

export default LogoutButtonComponent;