import { useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { Post } from "../../utils/Types";
import Metadata from "../ui/Metadata";
import { useNavigate } from "react-router-dom";
import UsernameComponent from "../user/UsernameComponent";
import ImageDisplay from "../ui/ImageDisplay";

const PostDisplayComponent = (props: {post: Post}) => {
    const navigate = useNavigate();
    const [post, setPost] = useState(props.post);
    const [postLikes, setPostLikes] = useState(post.likeCount);
    // TODO: fix like count!!
    const onPostComment = () => {
        return
    }
    const onPostLike = () => {
        if (post.currentUserLikeID) {
            axiosUtil.delete(`/likes/${post.currentUserLikeID}`).then(res => {
                setPostLikes(postLikes - 1);
                setPost({...post, currentUserLikeID: null})
            }).catch(err => {
                console.error(err);
            })
        } else {
            axiosUtil.post('/likes', {postID: post.id}).then(res => {
                setPostLikes(postLikes + 1);
                setPost({...post, currentUserLikeID: res.data})
            }).catch(err => {
                console.error(err);
            })
        }
    }

    return (
        <div className="postDisplay" onClick={() => navigate(`/post/${post.id}`)}>
            <div>
                {post.attachment && <ImageDisplay src={post.attachment}/>}
                <div>
                    <UsernameComponent user={props.post.user} />
                    <span>{post.content}</span>
                    <br/>
                    <Metadata
                        timestamp={post.timestamp}
                        commentCount={post.commentCount}
                        onComment={onPostComment}
                        likeCount={postLikes}
                        isLikedByCurrentUser={post.currentUserLikeID != null}
                        onLike={onPostLike}
                    />
                </div>
            </div>
        </div>
    )
}

export default PostDisplayComponent;