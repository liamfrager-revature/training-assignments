const PageContent = (props: {children: any}) => {
    return (
        <div id="page-content">
            {props.children}
        </div>
    )
}

export default PageContent;