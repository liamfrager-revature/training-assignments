import { faCancel, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axiosUtil from "../../utils/AxiosUtil";
import Modal from "../ui/Modal";
import { useState } from "react";

const DeleteCommentComponent = (props: {commentID: number, onCommentDelete: (id: number) => void}) => {
    const modalState = useState(false);
    const [showModal, hideModal] = [() => modalState[1](true), () => modalState[1](false)]

    const handleModal = (e: React.MouseEvent) => {
        e.stopPropagation();
        showModal();
    }
    const handleDelete = (e: React.MouseEvent) => {
        e.stopPropagation();
        hideModal();
        axiosUtil.delete(`/comments/${props.commentID}`).then(res => {
            props.onCommentDelete(props.commentID);
        });
    }

    return (
        <>
        <FontAwesomeIcon onClick={(e) => handleModal(e)} icon={faTrash} className="hover hover-error color-gray"/>
        <Modal modalState={modalState}>
            <>
            <h2>Careful!</h2>
            <p>Are you sure you want to delete this comment? This cannot be undone.</p>
            <button onClick={handleDelete} className="space-below"><FontAwesomeIcon icon={faTrash} className="space-right"/>Delete</button>
            <button onClick={hideModal}><FontAwesomeIcon icon={faCancel} className="hover color-error space-right"/>Cancel</button>
            </>
        </Modal>
        </>
    )
}

export default DeleteCommentComponent;