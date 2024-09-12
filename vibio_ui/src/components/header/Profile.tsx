import React from "react";
import { Avatar, AvatarImage } from "../ui/avatar";

type Props = {};

const Profile = (props: Props) => {
    return (
        <div>
            <Avatar>
                <AvatarImage src="https://images.unsplash.com/photo-1463462927315-fb10af2c68d8?q=80&w=1770&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"></AvatarImage>
            </Avatar>
        </div>
    );
};

export default Profile;
