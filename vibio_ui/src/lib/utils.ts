import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";
import { Visibility } from "../common/enum";

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs));
}

export function convertVisibilityToLabel(visibility: Visibility) {
    let label = "Công khai";
    switch (visibility) {
        case Visibility.PUBLIC: {
            label = "Công khai";
            break;
        }
        case Visibility.MEMBER: {
            label = "Thành viên";
            break;
        }
        case Visibility.PRIVATE: {
            label = "Riêng tư";
            break;
        }
    }
    return label;
}
