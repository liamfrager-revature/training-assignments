import { useEffect, useState } from "react";
import { Post } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";

const PostComponent = (props: {postID: string}) => {
    const [post, setPost] = useState<Post>();

    useEffect(() => {
        axiosUtil.get(`/posts/${props.postID}`).then(res => setPost(res.data))
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