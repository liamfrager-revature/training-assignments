const Load = (props: {children: JSX.Element, loading: any}) => {
    return (
        <>
        { props.loading ? (
            <>{props.children}</>
        ) : (
            <span>Loading...</span>
        )}
        </>
    )
}

export default Load;