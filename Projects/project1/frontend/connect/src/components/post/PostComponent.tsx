import axios from "axios";
import { useEffect, useState } from "react";
import { Post } from "../../types/Types";

const PostComponent = (props: {postID: string}) => {
    const URL = process.env.DB_API_URL;
    const [post, setPost] = useState<Post>();

    useEffect(() => {
        axios.get(`${URL}/posts/${props.postID}`).then(res => setPost(res.data))
    }, [])
    return (
        <>
        { post ? (
            <>
            <span>{post.user.username}</span>
            <div>
                <img src="" alt="" />
            </div>
            </>
        ) : (
            <span>Loading post...</span>
        )
        }
        </>
    )
}

export default PostComponent;