import { useEffect, useState } from "react"
import axiosUtil from "../../utils/AxiosUtil"

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
        { !followingUser ? <button onClick={() => followUser(props.userID)}>Follow</button> : <button onClick={() => unfollowUser(props.userID)}>Following</button>}
        </>
    )
}

export default FollowButtonComponent;