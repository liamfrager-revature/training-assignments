import { useEffect, useState } from "react";
import { Post } from "../../utils/Types";
import PostDisplayComponent from "./PostDisplayComponent";
import Load from "../ui/Load";
import axiosUtil from "../../utils/AxiosUtil";
import { useUser } from "../../utils/Context";

const PostsDisplayComponent = (props: {posts?: Post[]}) => {
    const [posts, setPosts] = useState<Post[] | undefined>(props.posts);
    const {currentUser} = useUser();

    useEffect(() => {
        setPosts(props.posts)
    },[props.posts]);

    const onPostDelete = (postID: number) => {
        axiosUtil.delete(`posts/${postID}`).then(res => {
            setPosts(posts?.filter(post => post.id !== postID))
        });
    }

    return (
        <Load loading={posts}>
            <div className="scrollable flex-grow">
            { posts?.length ? (
                posts?.map(post => <PostDisplayComponent key={post.id} post={post} onPostDelete={post.user.id === currentUser!.id ? () => onPostDelete(post.id) : undefined}/>)
            ) : (
                <span>This user hasn't made any posts.</span>
            )}
            </div>
        </Load>
    )
}

export default PostsDisplayComponent;