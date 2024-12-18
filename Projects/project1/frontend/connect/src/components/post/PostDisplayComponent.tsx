import { useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { Post } from "../../utils/Types";
import Metadata from "../Metadata";

const PostDisplayComponent = (props: {post: Post}) => {
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
        <div className="postDisplay">
            <div>
                <img src={"null"} alt="" />
                <div>
                    <span className="bold space-right">{post.user.username}</span>
                    <span>{post.content}</span>
                    <br/>
                    <Metadata
                        timestamp={post.timestamp}
                        commentCount={0}
                        onPostComment={onPostComment}
                        likeCount={postLikes}
                        onPostLike={onPostLike}
                    />
                </div>
            </div>
        </div>
    )
}

export default PostDisplayComponent;