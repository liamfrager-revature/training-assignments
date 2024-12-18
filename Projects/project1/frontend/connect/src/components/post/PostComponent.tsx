import { useEffect, useState } from "react";
import { Post } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import PfpComponent from "../user/PfpComponent";

const PostComponent = (props: {postID: string}) => {
    const [post, setPost] = useState<Post>();

    useEffect(() => {
        axiosUtil.get(`/posts/${props.postID}`).then(res => setPost(res.data))
    }, [props.postID])
    return (
        <>
        { post ? (
            <>
            <span>{post.user.username}</span>
            <div>
                <PfpComponent pfp={post.user.pfp}/>
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