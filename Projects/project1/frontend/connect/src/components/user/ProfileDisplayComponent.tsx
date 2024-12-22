import { useEffect, useState } from "react";
import { Post, UpdateUser, User } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import PostsDisplayComponent from "../post/PostsDisplayComponent";
import UserDisplayComponent from "./UserDisplayComponent";
import { useUser } from "../../utils/Context";
import AddPostComponent from "../post/AddPostComponent";
import EditProfileComponent from "./profile-edit/EditProfileComponent";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGear } from "@fortawesome/free-solid-svg-icons";

const ProfileDisplayComponent = (props: {userID: number}) => {
    const [user, setUser] = useState<User>();
    const [posts, setPosts] = useState<Post[]>();
    const [editingProfile, setEditingProfile] = useState(false);
    const { currentUser } = useUser();

    useEffect(() => {
        axiosUtil.get(`/users/${props.userID}`).then(res => setUser(res.data))
        axiosUtil.get(`/users/${props.userID}/posts`).then(res => setPosts(res.data))
    }, [props.userID])

    const profileEdited = (updatedUser: UpdateUser) => {
        setUser({...user!, ...updatedUser})
        let updatedCurrentUser = {...JSON.parse(sessionStorage.getItem("currentUser")!), ...updatedUser};
        updatedCurrentUser.password = null;
        sessionStorage.setItem("currentUser", JSON.stringify(updatedCurrentUser));

        setEditingProfile(false);
    }

    const postAdded = (addedPost: Post) => {
        setPosts([addedPost, ...posts!]);
    }

    return (
        <>
        { user ? (
            <>
            { editingProfile ? (
                <EditProfileComponent user={user} onClose={profileEdited}/>
            ) : (
                <>
                    { currentUser!.id === props.userID && <button onClick={() => setEditingProfile(true)}><FontAwesomeIcon icon={faGear} className="space-right"/>Edit Profile</button>}
                    <UserDisplayComponent user={user}/>
                    { currentUser!.id === props.userID && <AddPostComponent onPostAdd={postAdded}/>}
                    <h2>Posts</h2>
                    <PostsDisplayComponent posts={posts}/>
                </>
            )}
            </>
        ) : (
            <span>Loading user...</span>
        )
        }
        </>
    )
}

export default ProfileDisplayComponent;