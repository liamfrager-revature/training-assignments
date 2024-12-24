import CommentComponent from "./CommentComponent";
import { Comment, NewComment } from "../../utils/Types"
import axiosUtil from "../../utils/AxiosUtil";
import { useEffect, useState } from "react";
import AddCommentComponent from "./AddCommentComponent";
import Load from "../ui/Load";
import { useUser } from "../../utils/Context";

const CommentsDisplayComponent = (props: {postID: number}) => {
    const [comments, setComments] = useState<Comment[]>();
    const {currentUser} = useUser();

    useEffect(() => {
      axiosUtil.get(`posts/${props.postID}/comments`).then(res => {
        setComments(res.data);
      });
    }, [props.postID])

    const addComment = (newCommentContent: string) => {
        const newComment: NewComment = {
            content: newCommentContent,
            post: {
                id: props.postID
            }
        }
        axiosUtil.post(`posts/${props.postID}/comments`, JSON.stringify(newComment)).then(res => {
            setComments([res.data, ...comments!]);
        })
    }

    const deleteComment = (commentID: number) => {
        setComments(comments?.filter(comment => comment.id !== commentID));
    }
    
    return (
        <>
        <h2>Comments</h2>
        <AddCommentComponent onCommentAdd={addComment}/>
        <Load loading={comments}>
            {comments?.length ? (
                <div className="scrollable space-above">
                {
                    comments.map((comment: Comment) => (
                        <CommentComponent key={comment.id} comment={comment} onCommentDelete={currentUser!.id === comment.user.id ? () => deleteComment(comment.id) : undefined}/>
                    ))
                }
                </div>
            ) : (
                <></>
            )}
        </Load>
        </>
    )
}

export default CommentsDisplayComponent;