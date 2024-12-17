const Error404Component = (props: {statusCode: string | number, message: string}) => {
    return (
        <div className="error">
            <h1>Error: {props.statusCode}</h1>
            <p>{props.message}</p>
        </div>
    )
}

export default Error404Component;