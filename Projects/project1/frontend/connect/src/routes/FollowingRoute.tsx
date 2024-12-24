import { useEffect, useState } from "react";
import { useUser } from "../utils/Context";
import axiosUtil from "../utils/AxiosUtil";
import UserDisplayComponent from "../components/user/UserDisplayComponent";
import { User } from "../utils/Types";
import Load from "../components/ui/Load";
import { Link } from "react-router-dom";

const FollowingRoute = () => {
    const { currentUser } = useUser();
    const [following, setFollowing] = useState<User[]>();

    useEffect(() => {
        axiosUtil.get(`users/${currentUser!.id}/following`).then(res => {
            setFollowing(res.data);
        });
    }, [currentUser])

    return (
        <>
            <h1>Following</h1>
            <Load loading={following}>
                <>
                {following?.length ? (
                    following?.map(user => (
                        <UserDisplayComponent key={user.id} user={user}/>
                    ))
                ) : (
                    <p>You are not following anybody. <Link to="/search">Click here</Link> to find users to follow!</p>
                )}
                </>
            </Load>
        </>
    );
}

export default FollowingRoute;