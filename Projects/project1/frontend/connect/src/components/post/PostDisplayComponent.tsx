import { useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { Post } from "../../utils/Types";
import Metadata from "../Metadata";
import { useNavigate } from "react-router-dom";
import UsernameComponent from "../user/UsernameComponent";

const PostDisplayComponent = (props: {post: Post}) => {
    const navigate = useNavigate();
    const post = props.post;

    const [postLikes, setPostLikes] = useState(post.likeCount);
    // TODO: fix like count!!
    const onPostComment = () => {
        return
    }
    const onPostLike = () => {
        axiosUtil.post('/likes', {postID: post.id}).then(res => {
            setPostLikes(postLikes + 1);
        }).catch(err => {
            console.error(err);
        })
    }

    return (
        <div className="postDisplay" onClick={() => navigate(`/post/${post.id}`)}>
            <div>
                <img src={"null"} alt="" />
                <div>
                    <UsernameComponent user={props.post.user} />
                    <span>{post.content}</span>
                    <br/>
                    <Metadata
                        timestamp={post.timestamp}
                        commentCount={post.commentCount}
                        onComment={onPostComment}
                        likeCount={postLikes}
                        onLike={onPostLike}
                    />
                </div>
            </div>
        </div>
    )
}

export default PostDisplayComponent;