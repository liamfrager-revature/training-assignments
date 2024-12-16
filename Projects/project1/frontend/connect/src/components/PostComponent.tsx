import axios from "axios";
import { useEffect, useState } from "react";
import { Post } from "../types/Types";

const PostComponent = (props: {postID: string}) => {
    const [post, setPost] = useState<Post>();

    useEffect(() => {
        axios.get(`${props.postID}`).then(res => setPost(res.data))
    }, [])
    return (
        <>
        { post ? (
            <>
            <span>{post.user.name}</span>
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