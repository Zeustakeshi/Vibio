import { Visibility } from "@/common/enum.ts";
import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs));
}

export function gigabytesToBytes(gigabytes: number) {
    return gigabytes * 1073741824; // 1 GB = 1,073,741,824 bytes
}

export function megabytesToBytes(megabytes: number) {
    return megabytes * 1048576; // 1 MB = 1,048,576 bytes
}

export function convertVisibilityToText(visibility: Visibility) {
    switch (visibility) {
        case Visibility.PUBLIC: {
            return "Công khai";
        }
        case Visibility.PRIVATE: {
            return "Riêng tư";
        }
        case Visibility.MEMBER: {
            return "Hội viên";
        }
    }
}
