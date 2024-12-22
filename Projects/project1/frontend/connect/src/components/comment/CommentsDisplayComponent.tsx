import CommentComponent from "./CommentComponent";
import { Comment, NewComment } from "../../utils/Types"
import axiosUtil from "../../utils/AxiosUtil";
import { useEffect, useState } from "react";
import AddCommentComponent from "./AddCommentComponent";

const CommentsDisplayComponent = (props: {postID: number}) => {
    const [comments, setComments] = useState<Comment[]>();

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
    
    return (
        <>
        <h2>Comments</h2>
        <AddCommentComponent onCommentAdd={addComment}/>
        {
            comments !== undefined ? (
                <>
                {
                    comments.map((comment: Comment) => (
                        <CommentComponent key={comment.id} comment={comment} />
                    ))
                }
                </>
            ) : (
                <p>Loading comments...</p>
            )
        }
        </>
    )
}

export default CommentsDisplayComponent;