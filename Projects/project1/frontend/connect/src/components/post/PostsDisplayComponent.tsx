import { useEffect, useState } from "react";
import { Post } from "../../utils/Types";
import PostDisplayComponent from "./PostDisplayComponent";

const PostsDisplayComponent = (props: {posts?: Post[]}) => {
    const [posts, setPosts] = useState<Array<Post> | undefined>(props.posts);

    useEffect(() => {
        setPosts(props.posts)
    },[props.posts]);

    return (
        <>
        { posts ? (
            <>
            { posts.length > 0 ? (
                posts.map(post => <PostDisplayComponent key={post.id} post={post} />)
            ) : (
                <span>There are no posts.</span>
            )}
            </>
        ) : (
            <span>Loading posts...</span>
        )}
        </>
    )
}

export default PostsDisplayComponent;