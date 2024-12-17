import { useRef } from "react";

const AlertButton = () => {

    const timesClicked = useRef(0);

    const clickHandler = () => {
        timesClicked.current += 1;
        alert(`You clicked the button ${timesClicked.current} time` + (timesClicked.current === 1 ? '' : 's'))
    }

    return (
        <button onClick={clickHandler}>Click Me!</button>
    )
}

export default AlertButton;