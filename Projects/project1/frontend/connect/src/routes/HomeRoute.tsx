import { useEffect, useState } from "react";
import PostsDisplayComponent from "../components/post/PostsDisplayComponent";
import axiosUtil from "../utils/AxiosUtil";
import { Post } from "../utils/Types";

const HomeRoute = () => {
    const [posts, setPosts] = useState<Array<Post>>();

    useEffect(() => {
        axiosUtil.get('/posts').then(res => {console.log(res.data); setPosts(res.data)})
    }, [])
    return (
        <>
            <h1>Home</h1>
            <PostsDisplayComponent posts={posts}/>
        </>
    );
}

export default HomeRoute;