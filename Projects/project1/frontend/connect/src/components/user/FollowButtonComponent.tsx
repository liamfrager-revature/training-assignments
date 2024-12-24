import { useEffect, useState } from "react"
import axiosUtil from "../../utils/AxiosUtil"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserMinus, faUserPlus } from "@fortawesome/free-solid-svg-icons";

const FollowButtonComponent = (props: {userID: number}) => {
    const [followingUser, setFollowingUser] = useState<Boolean>();

    useEffect(() => {
        axiosUtil.get(`users/${props.userID}/follow`).then(res => setFollowingUser(res.data));
    }, [props.userID])
    

    const followUser = (userID: number) => {
        axiosUtil.post(`/users/${userID}/follow`).then(() => {
            setFollowingUser(true);
        }).catch(err => {
            console.error(err);
        })
    }

    const unfollowUser = (userID: number) => {
        axiosUtil.delete(`/users/${userID}/follow`).then(() => {
            setFollowingUser(false);
        }).catch(err => {
            console.error(err);
        })
    }
    
    return (
        <>
        { !followingUser ? (
            <button onClick={() => followUser(props.userID)}><FontAwesomeIcon icon={faUserPlus} className="space-right"/>Follow</button>
        ) : (
            <button onClick={() => unfollowUser(props.userID)}><FontAwesomeIcon icon={faUserMinus} className="space-right"/>Unfollow</button>
        )}
        </>
    )
}

export default FollowButtonComponent;