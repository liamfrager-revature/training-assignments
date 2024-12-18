import { useEffect, useState } from "react";
import { Post } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import PfpComponent from "../user/PfpComponent";
import CommentsDisplayComponent from "../comment/CommentsDisplayComponent";
import PostDisplayComponent from "./PostDisplayComponent";

const PostComponent = (props: {postID: string}) => {
    const [post, setPost] = useState<Post>();

    useEffect(() => {
        axiosUtil.get(`/posts/${props.postID}`).then(res => setPost(res.data));
    }, [props.postID])
    return (
        <>
        { post ? (
            <>
            <PostDisplayComponent post={post}/>
            <CommentsDisplayComponent postID={post.id}/>
            </>
        ) : (
            <span>Loading post...</span>
        )
        }
        </>
    )
}

export default PostComponent;