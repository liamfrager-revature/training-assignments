const PageContent = (props: {children: any}) => {
    return (
        <div id="page-content">
            <div className="content">
                {props.children}
            </div>
        </div>
    )
}

export default PageContent;