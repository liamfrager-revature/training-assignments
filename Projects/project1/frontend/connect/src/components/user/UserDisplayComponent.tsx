import { useNavigate } from "react-router-dom";
import { User } from "../../utils/Types";
import PfpComponent from "./PfpComponent";
import FollowButtonComponent from "./FollowButtonComponent";
import LogoutButtonComponent from "./../auth/LogoutButtonComponent";
import { useUser } from "../../utils/Context";
import FollowCountComponent from "./FollowCountComponent";


const UserDisplayComponent = (props: {user: User}) => {
    const navigate = useNavigate();
    const {currentUser} = useUser();

    return (
        <div className="user-display shadow-box justify-between rounded align-center">
            <span className="align-center">
                <PfpComponent pfp={props.user.pfp}/>
                <span className="space-left hover" onClick={() => navigate(`/profile/${props.user.id}`)}>{props.user.username}</span>
            </span>
            <span className="align-center">
                <FollowCountComponent followers={props.user.followersCount} following={props.user.followingCount}/>
                {currentUser!.id !== props.user.id ? <FollowButtonComponent userID={props.user.id!}/> : <LogoutButtonComponent />}
            </span>
        </div>
    )
}

export default UserDisplayComponent;