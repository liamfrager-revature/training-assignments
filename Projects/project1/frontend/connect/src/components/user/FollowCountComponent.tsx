const FollowCountComponent = (props: {followers: number, following: number}) => {
    return (
        <>
        <span className="space-right">Followers {props.followers}</span>
        <span className="space-right">Following {props.following}</span>
        </>
    )
}

export default FollowCountComponent;