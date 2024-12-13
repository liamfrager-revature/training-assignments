const EventDemo = () => {
    const handleClick = (message) => {
        console.log(message);
        if (message) {
            console.log("clicked!" + message);
        } else {
            console.log("clicked");
        }
    }

    return (
        <>
            <h5>Event Demo</h5>
            <button onClick={handleClick}>Click Me</button>
            <button onClick={() => handleClick(" now with more text!")}>Click Me Again</button>
        </>
    )
}

export default EventDemo;