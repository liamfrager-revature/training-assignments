import { useState } from "react";
import { UpdateUser, User } from "../../../utils/Types";
import ImageUploader from "../../post/ImageUploader";
import Modal from "../../ui/Modal";
import axiosUtil from "../../../utils/AxiosUtil";
import DoClick from "../../ui/DoClick";
import PfpComponent from "../PfpComponent";
import ErrorComponent from "../../error/ErrorComponent";


const EditProfileComponent = (props: {user: User, onClose: (updatedProfile: UpdateUser) => void}) => {
    const pfpModalState = useState(false);
    const [showPfpModal, hidePfpModal] = [() => pfpModalState[1](true), () => pfpModalState[1](false)]
    const passwordModalState = useState(false);
    const [showPasswordModal, hidePasswordModal] = [() => passwordModalState[1](true), () => passwordModalState[1](false)]
    const [updatedPfp, setUpdatedPfp] = useState('');
    const [pfp, setPfp] = useState<string | null>(null);
    const [username, setUsername] = useState<string | null>(null);
    const [email, setEmail] = useState<string | null>(null);
    const [newPassword, setNewPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [password, setPassword] = useState<string | null>(null);
    const [passwordUpdated, setPasswordUpdated] = useState<boolean>(false);
    const [conflictError, setConflictError] = useState<boolean>(false);

    const handleUsername = (value: string) => {
        setUsername(value === props.user.username ? null : value);
    }

    const handleEmail = (value: string) => {
        setEmail(value === props.user.email ? null : value);
    }

    const handlePfp = () => {
        setPfp(updatedPfp);
        hidePfpModal();
    }


    const handlePassword = () => {
        if (newPassword === confirmPassword) {
            setPassword(newPassword);
            setPasswordUpdated(true);
            hidePasswordModal();
        }
    }

    
    const updateProfile = (e: React.FormEvent<HTMLFormElement>) => {
        if (username || email || password || pfp) {
            setConflictError(false);
            e.preventDefault();
            let updatedUserDetails: UpdateUser = {}
            if (username) updatedUserDetails.username = username;
            if (email) updatedUserDetails.email = email;
            if (password) updatedUserDetails.password = password;
            if (pfp) updatedUserDetails.pfp = pfp;

            axiosUtil.put('/users', updatedUserDetails).then(res => {
                props.onClose(updatedUserDetails);
            }).catch(err => {
                if (err.status === 409)
                    setConflictError(true);
            })
        } else {
            props.onClose({});
        }
    }
    
    return (
        <form onSubmit={(e) => updateProfile(e)} className="wide-col-form user-display shadow-box rounded">
            <h1>Edit Profile</h1>
            <DoClick onClick={() => showPfpModal()} message="edit">
                <PfpComponent pfp={pfp ? pfp : props.user.pfp}/>
            </DoClick>
            {conflictError && <ErrorComponent message="That username or email is already in use."/>}
            <label htmlFor="username">Username</label>
            <input type="text" name="username" value={username !== null ? username : props.user.username} onChange={(e) => handleUsername(e.target.value)}/>
            <label htmlFor="email">Email</label>
            <input type="email" name="email" value={email !== null ? email : props.user.email} onChange={(e) => handleEmail(e.target.value)}/>
            <button type="button" onClick={showPasswordModal}>Change Password</button>
            {passwordUpdated && <span className="error rounded">Press save to change your password</span>}
            <button type="submit">Save</button>
            <Modal modalState={pfpModalState}>
                <>
                    <h1>Upload a new profile picture:</h1>
                    <ImageUploader setImage={setUpdatedPfp} maxSize={5 * 1024 * 1024}/>
                    <div className="align-center">
                        <button type="button" onClick={handlePfp}>Update picture</button>
                        <button type="button" onClick={hidePfpModal}>Cancel</button>
                    </div>
                </>
            </Modal>
            <Modal modalState={passwordModalState}>
                <>
                    <h1>Change password</h1>
                    <input type="password" name="password" placeholder="New Password" minLength={8} value={newPassword} onChange={(e) => setNewPassword(e.target.value)}/><br/>
                    <input type="password" name="confirmPassword" placeholder="Confirm Password" minLength={8} value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)}/><br/>
                    {newPassword !== confirmPassword && <div className="space-below"><ErrorComponent message="Passwords do not match"/></div>}
                    <div className="align-center">
                        <button type="button" onClick={handlePassword}>Change password</button>
                        <button type="button" onClick={hidePasswordModal}>Cancel</button>
                    </div>
                </>
            </Modal>
        </form>
    )
}

export default EditProfileComponent;