import { IoNotificationsOutline } from "react-icons/io5";
import { Button } from "../ui/button";

type Props = {};

const Notification = (props: Props) => {
    return (
        <Button size="sm" variant="ghost">
            <IoNotificationsOutline size={20} />
        </Button>
    );
};

export default Notification;
