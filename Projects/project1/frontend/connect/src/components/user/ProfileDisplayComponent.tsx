import { useEffect, useState } from "react";
import { NewPost, Post, User } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import PostsDisplayComponent from "../post/PostsDisplayComponent";
import UserDisplayComponent from "./UserDisplayComponent";
import { useUser } from "../../utils/Context";
import AddPostComponent from "../post/AddPostComponent";

const ProfileDisplayComponent = (props: {userID: number}) => {
    const [user, setUser] = useState<User>();
    const [posts, setPosts] = useState<Post[]>();
    const { currentUser } = useUser();

    useEffect(() => {
        axiosUtil.get(`/users/${props.userID}`).then(res => setUser(res.data))
        axiosUtil.get(`/users/${props.userID}/posts`).then(res => setPosts(res.data))
    }, [props.userID])

    const addPost = (newPostContent: string) => {
        const newPost: NewPost = {
            content: newPostContent
        }
        axiosUtil.post('/posts', JSON.stringify(newPost)).then(res => {
            setPosts([...posts!, res.data]);
        })
    }

    return (
        <>
        { user ? (
            <>
            <UserDisplayComponent user={user}/>
            { currentUser!.id === props.userID && <AddPostComponent onPostAdd={addPost}/>}
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