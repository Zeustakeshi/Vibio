import {
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import TextArea from "@/components/ui/text-area";
import { updateVideoSchema } from "@/schema/video.schema";
import { useFormContext } from "react-hook-form";
import { z } from "zod";
import TagInput from "./TagInput";

type Props = {};

const FormMetadata = (props: Props) => {
    const { control, setValue } =
        useFormContext<z.infer<typeof updateVideoSchema>>();

    return (
        <div className="col-span-2 space-y-3 mb-10">
            {/* Title */}
            <FormField
                control={control}
                name="title"
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Tiêu đề</FormLabel>
                        <FormControl>
                            <Input
                                placeholder="Người xem sẽ nhớ đến video của bạn thông qua tiêu đề"
                                {...field}
                            />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />

            {/* Description */}
            <FormField
                control={control}
                name="description"
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Mô tả video</FormLabel>
                        <FormControl>
                            <TextArea
                                placeholder="Nhập mô tả để người xem có thêm thông tin về video của bạn"
                                className="min-h-[200px] max-h-[250px]"
                                {...field}
                            ></TextArea>
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />

            {/* Tag */}
            <FormField
                control={control}
                name="tags"
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Thẻ</FormLabel>
                        <FormDescription>
                            Việc thêm tag cho video sẽ giúp chúng tôi đánh giá
                            tốt hơn nội dung video của bạn đề đề xuất đến người
                            dùng một cách chính xác nhất
                        </FormDescription>
                        <FormControl>
                            <TagInput
                                tags={field.value}
                                onChange={(tags) => setValue("tags", tags)}
                            ></TagInput>
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />
        </div>
    );
};

export default FormMetadata;
