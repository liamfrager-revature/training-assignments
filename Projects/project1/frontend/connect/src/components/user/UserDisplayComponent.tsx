import { useNavigate } from "react-router-dom";
import { User } from "../../utils/Types";
import PfpComponent from "./PfpComponent";
import FollowButtonComponent from "./FollowButtonComponent";
import { useUser } from "../../utils/Context";
import FollowCountComponent from "./FollowCountComponent";


const UserDisplayComponent = (props: {user: User, showFollowCount?: boolean}) => {
    const navigate = useNavigate();
    const {currentUser} = useUser();

    return (
        <div className="user-display shadow-box justify-between rounded align-center">
            <span className="align-center">
                <PfpComponent pfp={props.user.pfp}/>
                <span className="space-left hover" onClick={() => navigate(`/profile/${props.user.id}`)}>{props.user.username}</span>
            </span>
            <span className="align-center">
                {props.showFollowCount === true && <FollowCountComponent userID={props.user.id!} followers={props.user.followersCount} following={props.user.followingCount}/>}
                {currentUser!.id !== props.user.id && <FollowButtonComponent userID={props.user.id!}/>}
            </span>
        </div>
    )
}

export default UserDisplayComponent;