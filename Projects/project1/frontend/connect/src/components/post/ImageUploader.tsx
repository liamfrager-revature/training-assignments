import { useRef, useState } from "react";
import ErrorComponent from "../error/ErrorComponent";

const ImageUploader = (props: {setImage: (file: string) => void, maxSize?: number}) => {
    const fileInputRef = useRef<HTMLInputElement>(null);
    const [error, setError] = useState(false);

    const getFileBytes = (file?: File) => {
        if (file) {
            if (props.maxSize ? file.size <= props.maxSize : true) {
                setError(false);
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
            } else {
                setError(true);
                fileInputRef.current!.value = '';
            }
        }
    }

    return (
        <>
            <input ref={fileInputRef} type="file" name="attachment" accept=".jpg,.jpeg,.png,.gif" onChange={(e) => getFileBytes(e.target.files?.[0])}/>
            {error && <ErrorComponent message="File is too large. Please select a file smaller than 5MB."/>}
        </>
    )
}

export default ImageUploader;