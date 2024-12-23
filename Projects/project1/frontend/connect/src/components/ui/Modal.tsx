import { useEffect } from "react";

type ModalState = [boolean, React.Dispatch<React.SetStateAction<boolean>>]

const Modal = (props: {children: JSX.Element, modalState: ModalState}) => {
    const [show, setShow] = props.modalState;

    const handleEsc = (e: KeyboardEvent) => {
        if (e.key === 'Escape') {
            e.preventDefault();
            setShow(false);
        }
    }

    useEffect(() => {
        if (show) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = 'auto';
        }
        window.addEventListener('keydown', handleEsc);
    });

    return (
        <>
            {show &&
            <div onClick={(e) => e.stopPropagation()} className="modal-bg">
                <div className="modal rounded bg-white padded flex-col">
                    {props.children}
                </div>
            </div>
            }
        </>
    )
}

export default Modal;