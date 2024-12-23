import { useNavigate } from "react-router-dom";
import { User } from "../../utils/Types";

const UsernameComponent = (props: {user: User}) => {
    const navigate = useNavigate();

    const goToProfile = (e: React.MouseEvent) => {
        e.stopPropagation();
        navigate(`/profile/${props.user.id}`);
    }
    return (
        <span className="bold space-right hover hover-color" onClick={(e) => goToProfile(e)}>{props.user.username}</span>
    )
}

export default UsernameComponent;