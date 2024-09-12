import { Link } from "@tanstack/react-router";
import React from "react";

type Props = {
    size?: "sm" | "md" | "lg";
};

const Logo = ({ size }: Props) => {
    return (
        <Link to="/">
            <h2 className="bg-gradient-to-r from-indigo-500 to-blue-500 bg-clip-text text-transparent font-bold text-3xl">
                Vibio
            </h2>
        </Link>
    );
};

export default Logo;
