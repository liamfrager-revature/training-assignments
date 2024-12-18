import { faComment, faHeart } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const Metadata = (props: {
    timestamp?: EpochTimeStamp,
    commentCount?: number,
    onPostComment?: () => void,
    likeCount?: number,
    onPostLike?: () => void
}) => {

    const timeAgo = (epochTimeStamp: EpochTimeStamp) => {
        const date = new Date(epochTimeStamp);
        const now = new Date();

        const diffMs = now.getTime() - date.getTime(); // Difference in milliseconds
        const diffSeconds = Math.floor(diffMs / 1000);
        const diffMinutes = Math.floor(diffSeconds / 60);
        const diffHours = Math.floor(diffMinutes / 60);
        const diffDays = Math.floor(diffHours / 24);
        const diffWeeks = Math.floor(diffDays / 7);

        if (diffSeconds < 60) {
            return `${diffSeconds}s ago`;
        } else if (diffMinutes < 60) {
            return `${diffMinutes}m ago`;
        } else if (diffHours < 24) {
            return `${diffHours}h ago`;
        } else if (diffDays < 7) {
            return `${diffDays}d ago`;
        } else {
            return `${diffWeeks}w ago`;
        }
    }

    return (
        <span className="metadata">
            { props.timestamp && 
                <span className="space-right">{timeAgo(props.timestamp)}</span>
            }
            { props.commentCount !== undefined && props.onPostComment &&
                <span className="space-right">
                    <FontAwesomeIcon icon={faComment} onClick={props.onPostComment} className="space-right"/>
                    <span>{props.commentCount}</span>
                </span>
            }
            { props.likeCount !== undefined && props.onPostLike &&
                <span className="space-right">
                    <FontAwesomeIcon icon={faHeart} onClick={props.onPostLike} className="space-right"/>
                    <span>{props.likeCount}</span>
                </span>
            }
        </span>
    )
}

export default Metadata;