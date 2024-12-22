import { useState } from "react";

const DoClick = (props: {children: JSX.Element, onClick: () => void, message?: string}) => {
    const [showMessage, setShowMessage] = useState(false);

    return (
        <span
            className="hover relative fit-content"
            onClick={props.onClick}
            onMouseEnter={() => setShowMessage(true)}
            onMouseLeave={() => setShowMessage(false)}
            >
            {props.children}
            {props.message && <span className={showMessage ? "do-click-hover align-center justify-center" : "hide"}>{props.message}</span>}
        </span>
    )
}

export default DoClick;