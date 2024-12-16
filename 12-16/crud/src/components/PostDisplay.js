import { useState } from "react";
import axios from "axios";

const PostDisplay = (props) => {
    const [post, setPost] = useState(props.post);

    const updatePost = (postID) => {
        post.title = "This post has been updated";
        axios.patch(`http://localhost:8000/posts/${postID}`, post).then(res => { setPost({ ...post }) });
    }


    return (
        <div className="post">
            <h3>{post.title}</h3>
            <p>{post.body}</p>
            <span className="metadata">Posted by: {post.userId}</span>
            <button onClick={() => updatePost(post.id)}>Update Post</button>
            <button onClick={() => props.onDelete(post.id)}>Delete Post</button>
        </div>
    )
}

export default PostDisplay;