import { useEffect, useState } from "react";
import { Post } from "../../utils/Types";
import PostDisplayComponent from "./PostDisplayComponent";
import axiosUtil from "../../utils/AxiosUtil";

const PostsDisplayComponent = () => {
    const [posts, setPosts] = useState<Array<Post>>();

    useEffect(() => {
        axiosUtil.get('/posts').then(res => {console.log(res.data); setPosts(res.data)})
    }, [])

    return (
        <>
        { posts ? (
            posts.length > 0 ? (
                posts.map(post => <PostDisplayComponent key={post.id} post={post} />)
            ) : (
                <span>There are no posts.</span>
            )
        ) : (
            <span>Loading posts...</span>
        )}
        </>
    )
}

export default PostsDisplayComponent;