const Hello = (props) => {
    let message = "Hello, React!";
    return (
        <>
            <h1>{message}</h1>
            <p>Have fun learning to code!</p>
            <p>{props.messageProp}</p>
        </>
    )
}

export default Hello