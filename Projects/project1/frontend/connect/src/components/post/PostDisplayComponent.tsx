import { useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { Post } from "../../utils/Types";
import Metadata from "../ui/Metadata";
import { useNavigate } from "react-router-dom";
import UsernameComponent from "../user/UsernameComponent";
import ImageDisplay from "../ui/ImageDisplay";
import PfpComponent from "../user/PfpComponent";
import DeletePostComponent from "./DeletePostComponent";

const PostDisplayComponent = (props: {post: Post, onPostDelete?: (postID: number) => void}) => {
    const navigate = useNavigate();
    const [post, setPost] = useState(props.post);
    const [postLikes, setPostLikes] = useState(post.likeCount);
    const onPostComment = () => {
        navigate(`/post/${post.id}?comment=true`);
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
        <div className="shadow-box hover post-display padded" onClick={() => navigate(`/post/${post.id}`)}>
            {post.attachment && <ImageDisplay src={post.attachment}/>}
            <div className="align-top small-pfp">
                <PfpComponent pfp={post.user.pfp} />
                <div>
                    <UsernameComponent user={props.post.user} />
                    <span>{post.content}</span>
                    <br/><br/>
                    <Metadata
                        timestamp={post.timestamp}
                        commentCount={post.commentCount}
                        onComment={onPostComment}
                        likeCount={postLikes}
                        isLikedByCurrentUser={post.currentUserLikeID != null}
                        onLike={onPostLike}
                    />
                    {props.onPostDelete && <DeletePostComponent postID={props.post.id} onPostDelete={props.onPostDelete}/>}
                </div>
            </div>
        </div>
    )
}

export default PostDisplayComponent;