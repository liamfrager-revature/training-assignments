import PostDisplay from "./PostDisplay";
import axios from "axios";
import { useEffect, useState } from "react";

const PostsDisplay = () => {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8000/posts").then(res => {
            setPosts(res.data);
        });
    }, [])

    const createPost = () => {
        const newPost = {
            "userId": 0,
            "id": `${parseInt(posts.length > 0 ? posts[posts.length - 1].id : '-1') + 1}`,
            "title": "This is a new post",
            "body": "It's got some interesting things written here."
        }
        axios.post("http://localhost:8000/posts", newPost).then(res => {
            setPosts([...posts, newPost]);
        })
    }

    const deletePost = (postID) => {
        axios.delete(`http://localhost:8000/posts/${postID}`).then(res => {
            setPosts(posts.filter(post => post.id != postID));
        });
    }

    return (
        <>
            <h1>Posts:</h1>
            <button onClick={createPost}>Create Post</button>
            <hr />
            {
                posts.map(post => (
                    <PostDisplay key={post.id}
                        post={post}
                        onDelete={(postID) => deletePost(postID)}
                    />
                ))
            }
        </>
    )
}

export default PostsDisplay;