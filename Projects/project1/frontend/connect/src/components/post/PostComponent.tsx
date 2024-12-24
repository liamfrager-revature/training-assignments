import { useEffect, useState } from "react";
import { Post } from "../../utils/Types";
import axiosUtil from "../../utils/AxiosUtil";
import CommentsDisplayComponent from "../comment/CommentsDisplayComponent";
import PostDisplayComponent from "./PostDisplayComponent";
import ErrorComponent from "../error/ErrorComponent";
import { useSearchParams } from "react-router-dom";

const PostComponent = (props: {postID: string}) => {
    const [post, setPost] = useState<Post>();
    const [searchParams] = useSearchParams();

    useEffect(() => {
        if (searchParams.get("comment")) {
            document.getElementById("add-comment")?.focus();
        }
    });

    useEffect(() => {
        axiosUtil.get(`/posts/${props.postID}`).then(res => {
            setPost(res.data ? res.data : null);
        });
    }, [props.postID])
    return (
        <>
        { post === undefined ? (
            <span>Loading post...</span>
        ) : post === null ? (
            <ErrorComponent statusCode={404} message={`Could not find that post!`}/>
        ) : (
            <>
            <PostDisplayComponent post={post}/>
            <CommentsDisplayComponent postID={post.id}/>
            </>
        )}
        </>
    )
}

export default PostComponent;