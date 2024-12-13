const Post = (props) => {
    console.log(props.post.comments);
    return (
        <>
            <h6>{props.post.content}</h6>
            <ul>
                {props.post.comments.map((comment, index) => (
                    <li>{comment}</li>
                ))}
            </ul>
        </>
    )
}

export default Post;