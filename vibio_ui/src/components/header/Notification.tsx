import React from "react";
import { FaRegBell } from "react-icons/fa";
import { Button } from "../ui/button";
type Props = {};

const Notification = (props: Props) => {
    return (
        <Button variant="outline">
            <FaRegBell size={20} className=""></FaRegBell>
        </Button>
    );
};

export default Notification;
