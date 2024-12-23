import { useEffect, useState } from "react";
import Modal from "../ui/Modal";
import UserDisplayComponent from "./UserDisplayComponent";
import { User } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import Load from "../ui/Load";

const FollowCountComponent = (props: {userID: number, followers: number, following: number}) => {
    const followersModalState = useState(false);
    const [followersModal, setFollowersModal] = followersModalState;
    const [followers, setFollowers] = useState<User[]>();
    const followingModalState = useState(false);
    const [followingModal, setFollowingModal] = followingModalState;
    const [following, setFollowing] = useState<User[]>();

    useEffect(() => {
        if (followersModal) {
            axiosUtil.get(`/users/${props.userID}/followers`).then(res => setFollowers(res.data));
        }
        if (followingModal) {
            axiosUtil.get(`/users/${props.userID}/following`).then(res => setFollowing(res.data));
        }
    }, [followersModal, followingModal, props.userID]);

    useEffect(() => { // Close modals when navigating to another user's page.
        setFollowersModal(false);
        setFollowingModal(false);
    }, [props.userID, setFollowersModal, setFollowingModal]);

    return (
        <>
        <span style={props.followers > 0 ? {cursor: "pointer"} : {}} onClick={props.followers > 0 ? () => setFollowersModal(true) : () => {}} className="space-right">Followers {props.followers}</span>
        <Modal modalState={followersModalState}>
            <>
                <h3>Followers</h3>
                <Load loading={followers}>
                    <div className="scrollable">
                        {followers?.map(follower => <UserDisplayComponent user={follower}/>)}
                    </div>
                </Load>
                <button className="space-above" type="button" onClick={() => setFollowersModal(false)}>Close</button>
            </>
        </Modal>
        <span style={props.following > 0 ? {cursor: "pointer"} : {}} onClick={props.following > 0 ? () => setFollowingModal(true) : () => {}} className="space-right">Following {props.following}</span>
        <Modal modalState={followingModalState}>
            <>
                <h3>Following</h3>
                <Load loading={following}>
                    <div className="scrollable">
                        {following?.map(user => <UserDisplayComponent user={user}/>)}
                    </div>
                </Load>
                <button className="space-above" type="button" onClick={() => setFollowingModal(false)}>Close</button>
            </>
        </Modal>
        </>
    )
}

export default FollowCountComponent;