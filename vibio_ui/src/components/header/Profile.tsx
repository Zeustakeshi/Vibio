import { useAuth } from "../../context/AuthContext";
import { Avatar, AvatarImage } from "../ui/avatar";

type Props = {};

const Profile = ({}: Props) => {
    const { user } = useAuth();
    return (
        <div>
            <Avatar>
                <AvatarImage src={user?.avatar}></AvatarImage>
            </Avatar>
        </div>
    );
};

export default Profile;
