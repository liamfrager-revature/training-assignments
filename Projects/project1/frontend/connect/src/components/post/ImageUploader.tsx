const ImageUploader = (props: {setImage: (file: string) => void}) => {
    const getFileBytes = (file?: File) => {
        if (file) {
            const reader = new FileReader();
            reader.readAsArrayBuffer(file);
            reader.onload = () => {
                const byteArray = new Uint8Array(reader.result as ArrayBuffer);
                let binary = '';
                const bytes = new Uint8Array(byteArray);
                for (let i = 0; i < bytes.byteLength; i++) {
                    binary += String.fromCharCode(bytes[i]);
                }
                props.setImage(btoa(binary));
            };
            reader.onerror = () => {
                console.error("File reading failed.");
            };
        }
    }

    return (
        <input type="file" name="attachment" accept=".jpg,.jpeg,.png,.gif" onChange={(e) => getFileBytes(e.target.files?.[0])}/>
    )
}

export default ImageUploader;