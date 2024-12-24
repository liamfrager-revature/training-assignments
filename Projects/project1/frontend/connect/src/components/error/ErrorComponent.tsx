import { faCircleExclamation } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ErrorComponent = (props: {statusCode?: string | number, message?: string}) => {
    return (
        <div className="error rounded justify-center">
            <FontAwesomeIcon icon={faCircleExclamation} className="space-right"/> 
            {props.statusCode && <span>Error: {props.statusCode}</span>}
            {props.message && <span>{props.message}</span>}
        </div>
    )
}

export default ErrorComponent;