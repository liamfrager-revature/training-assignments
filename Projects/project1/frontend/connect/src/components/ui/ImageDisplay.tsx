const ImageDisplay = (props: {src: string, alt?: string}) => {
    return (
        <img src={`data:image/png;base64,${props.src}`} alt={props.alt} />
    )
}

export default ImageDisplay;