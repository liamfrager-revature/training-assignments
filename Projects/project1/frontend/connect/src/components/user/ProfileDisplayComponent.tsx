import { useEffect, useState } from "react";
import { Post, User } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import PfpComponent from "./PfpComponent";
import PostsDisplayComponent from "../post/PostsDisplayComponent";
import FollowButtonComponent from "./FollowButtonComponent";

const ProfileDisplayComponent = (props: {userID: string}) => {

    const [user, setUser] = useState<User>();
    const [posts, setPosts] = useState<Array<Post>>();

    useEffect(() => {
        axiosUtil.get(`/users/${props.userID}`).then(res => setUser(res.data))
        axiosUtil.get(`/users/${props.userID}/posts`).then(res => setPosts(res.data))
    }, [props.userID])


    return (
        <>
        { user ? (
            <>
            <PfpComponent pfp={user.pfp}/>
            <div>
                <FollowButtonComponent userID={user.id!}/>
            </div>
            <span>{user.username}</span>
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