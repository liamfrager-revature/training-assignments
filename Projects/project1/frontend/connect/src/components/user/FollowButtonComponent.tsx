import { useEffect, useState } from "react"
import axiosUtil from "../../utils/AxiosUtil"

const FollowButtonComponent = (props: {userID: number}) => {
    const [followingUser, setFollowingUser] = useState<Boolean>();

    useEffect(() => {
        axiosUtil.get(`users/${props.userID}/follow`).then(res => {console.log('res', res.data); setFollowingUser(res.data)});
    }, [])
    

    const followUser = (userID: number) => {
        const req: any = axiosUtil.post(`/users/${props.userID}/follow`).then(res => {
            setFollowingUser(true);
        }).catch(err => {
            console.error(err);
        })
    }

    const unfollowUser = (userID: number) => {
        const req: any = axiosUtil.delete(`/users/${props.userID}/follow`).then(res => {
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