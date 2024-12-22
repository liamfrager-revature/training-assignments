import { useState } from "react";
import axiosUtil from "../../utils/AxiosUtil";
import { Comment } from "../../utils/Types";
import Metadata from "../ui/Metadata";
import UsernameComponent from "../user/UsernameComponent";
import PfpComponent from "../user/PfpComponent";

const CommentComponent = (props: {comment: Comment}) => {
    const [comment, setComment] = useState(props.comment);
    const [commentLikes, setCommentLikes] = useState(comment.likeCount);
    const user = comment.user

    const onCommentLike = () => {
        if (comment.currentUserLikeID) {
            axiosUtil.delete(`/likes/${comment.currentUserLikeID}`).then(res => {
                setCommentLikes(commentLikes - 1);
                setComment({...comment, currentUserLikeID: null})
            }).catch(err => {
                console.error(err);
            })
        } else {
            axiosUtil.post('/likes', {commentID: comment.id}).then(res => {
                setCommentLikes(commentLikes + 1);
                setComment({...comment, currentUserLikeID: res.data})
            }).catch(err => {
                console.error(err);
            })
        }
    }

    return (
        <div className="shadow-box comment">
            <PfpComponent pfp={user.pfp}/>
            <div className="comment-text space-left">
                <UsernameComponent user={user}/>
                <span>{comment.content}</span>
                <br />
                <Metadata
                    timestamp={comment.timestamp}
                    likeCount={commentLikes}
                    onLike={onCommentLike}
                    isLikedByCurrentUser={comment.currentUserLikeID != null}
                />
            </div>
        </div>
    )
}

export default CommentComponent;