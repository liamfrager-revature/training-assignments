import { useEffect, useState } from "react";
import { Post, User } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import PfpComponent from "./PfpComponent";
import PostsDisplayComponent from "../post/PostsDisplayComponent";
import FollowButtonComponent from "./FollowButtonComponent";
import { useUser } from "../../utils/Context";
import { useNavigate } from "react-router-dom";

const ProfileDisplayComponent = (props: {userID: number}) => {
    const {currentUser, setCurrentUser} = useUser();
    const [user, setUser] = useState<User>();
    const [posts, setPosts] = useState<Array<Post>>();

    useEffect(() => {
        axiosUtil.get(`/users/${props.userID}`).then(res => setUser(res.data))
        axiosUtil.get(`/users/${props.userID}/posts`).then(res => setPosts(res.data))
    }, [props.userID])

    const logout = () => {
        if (currentUser!.id === props.userID) {
            sessionStorage.removeItem('currentUser');
            sessionStorage.removeItem('token');
            setCurrentUser(null);
        }
    }

    return (
        <>
        { user ? (
            <>
            <PfpComponent pfp={user.pfp}/>
            <div>
                <FollowButtonComponent userID={user.id!}/>
            </div>
            <span>{user.username}</span>
            { currentUser!.id === props.userID && <button onClick={logout}>Logout</button>}
            <PostsDisplayComponent posts={posts}/>
            </>
        ) : (
            <span>Loading user...</span>
        )
        }
        </>
    )
}

export default ProfileDisplayComponent;