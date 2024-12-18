import { useNavigate } from "react-router-dom";
import { User } from "../../utils/Types";
import PfpComponent from "./PfpComponent";
import FollowButtonComponent from "./FollowButtonComponent";
import LogoutButtonComponent from "./../auth/LogoutButtonComponent";
import { useUser } from "../../utils/Context";

const UserDisplayComponent = (props: {user: User}) => {
    const navigate = useNavigate();
    const {currentUser} = useUser();
    return (
        <div className="user-display align-center hover" onClick={() => navigate(`/profile/${props.user.id}`)}>
            <span className="align-center">
                <PfpComponent pfp={props.user.pfp}/>
                <span className="space-left">{props.user.username}</span>
            </span>
            {currentUser!.id !== props.user.id ? <FollowButtonComponent userID={props.user.id!}/> : <LogoutButtonComponent />}
        </div>
    )
}

export default UserDisplayComponent;