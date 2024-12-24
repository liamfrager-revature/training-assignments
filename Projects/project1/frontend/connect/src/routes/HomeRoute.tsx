import { useEffect, useState } from "react";
import PostsDisplayComponent from "../components/post/PostsDisplayComponent";
import axiosUtil from "../utils/AxiosUtil";
import { Post } from "../utils/Types";
import { Link } from "react-router-dom";

const HomeRoute = () => {
    const [posts, setPosts] = useState<Post[]>();

    useEffect(() => {
        axiosUtil.get('/posts').then(res => setPosts(res.data))
    }, [])
    return (
        <>
            <h1>Home</h1>
            { posts?.length ? (
                <PostsDisplayComponent posts={posts}/>
            ) : (
                <p>There are no posts in your feed. <Link to="/search">Follow somebody</Link> and their posts will show up here!</p>
            )}
        </>
    );
}

export default HomeRoute;