import { useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { Post } from "../../utils/Types";
import Metadata from "../ui/Metadata";
import { useNavigate } from "react-router-dom";
import UsernameComponent from "../user/UsernameComponent";
import ImageDisplay from "../ui/ImageDisplay";
import PfpComponent from "../user/PfpComponent";

const PostDisplayComponent = (props: {post: Post}) => {
    const navigate = useNavigate();
    const [post, setPost] = useState(props.post);
    const [postLikes, setPostLikes] = useState(post.likeCount);
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
        <div className="shadow-box hover" onClick={() => navigate(`/post/${post.id}`)}>
            <div className="post-display">
                {post.attachment && <ImageDisplay src={post.attachment}/>}
                <div className="align-top small-pfp">
                    <PfpComponent pfp={post.user.pfp} />
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
        </div>
    )
}

export default PostDisplayComponent;