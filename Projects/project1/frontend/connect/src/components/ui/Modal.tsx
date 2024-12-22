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
        window.addEventListener('keydown', handleEsc);
    });

    return (
        <>
            {show &&
            <div className="modal-bg">
                <div className="modal">
                    {props.children}
                </div>
            </div>
            }
        </>
    )
}

export default Modal;