import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import { useUser } from "../../utils/Context";

const Navbar = () => {
    const { currentUser, setCurrentUser } = useUser();

    type NavItem = {
        text: string,
        icon: IconProp,
        link: string
    }
    const navItems: NavItem[] = [{text: "Profile", icon: faUser, link: `/profile/${currentUser!.id}`}]

    return (
        <div id="navbar">
            <div className="logo">
                <img src={`${process.env.PUBLIC_URL}/logo.png`} alt="logo"/>
                <h1>CONNECT</h1>
            </div>
            <ul>
                {
                navItems.map((navItem, index) => (
                    <Link to={navItem.link}>
                        <li key={index} className="hover"><FontAwesomeIcon icon={navItem.icon} className="space-right" />{navItem.text}</li>
                    </Link>
                ))
                }
            </ul>
        </div>
    )
}

export default Navbar;