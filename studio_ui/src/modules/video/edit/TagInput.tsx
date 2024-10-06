import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { MAX_TAG_SIZE } from "@/schema/video.schema";
import { Plus, X } from "lucide-react";
import { useState } from "react";

type Props = {
    tags?: string[];
    onChange?: (tags: string[]) => void;
};

const TagInput = ({ tags = [], onChange }: Props) => {
    return (
        <div className="relative min-h-[300px] max-h-[310px] overflow-y-scroll custom-scroll w-full border-2 border-dashed rounded-xl border-primary p-3 pb-5 flex flex-wrap gap-3 items-start justify-start">
            {tags.map((tag, index) => (
                <VideoTagItem
                    key={index}
                    name={tag}
                    onRemove={(tag) => {
                        onChange?.(tags.filter((t) => t !== tag));
                    }}
                />
            ))}

            {tags.length < MAX_TAG_SIZE && (
                <ButtonAddTag
                    onSaveValue={(tag) => {
                        onChange?.([...tags, tag]);
                    }}
                ></ButtonAddTag>
            )}

            <p className="absolute bottom-3 right-3 text-muted-foreground">
                {tags.length}/30
            </p>
        </div>
    );
};

type VideoTagItemProps = {
    name: string;
    onRemove: (name: string) => void;
};

const VideoTagItem = ({ name, onRemove }: VideoTagItemProps) => {
    return (
        <div className="relative ">
            <Button
                variant="secondary"
                className="relative bg-opacity-90 backdrop-blur-md"
            >
                {name}
            </Button>
            <span
                onClick={() => onRemove(name)}
                className="absolute -top-1 -right-1 p-1 cursor-pointer rounded-full backdrop-blur-md bg-opacity-80"
            >
                <X size={15} />
            </span>
        </div>
    );
};

type ButtonAddTagProps = {
    onSaveValue: (value: string) => void;
};

const ButtonAddTag = ({ onSaveValue }: ButtonAddTagProps) => {
    const [tagName, setTagName] = useState<string>("");
    const [edit, setEdit] = useState<boolean>(false);

    const handleAddTag = () => {
        if (!tagName.trim()) return;
        onSaveValue(tagName);
        setTagName("");
        setEdit(false);
    };

    if (edit)
        return (
            <Input
                autoFocus
                onKeyDown={(e) => {
                    if (e.key !== "Enter") return;
                    handleAddTag();
                }}
                type="text"
                placeholder="tag"
                value={tagName}
                onChange={(e) => setTagName(e.target.value)}
                className="w-[150px] "
            />
        );

    return (
        <Button
            onClick={() => setEdit(true)}
            type="button"
            variant="secondary"
            size="icon"
        >
            <Plus size={20} />
        </Button>
    );
};

export default TagInput;
