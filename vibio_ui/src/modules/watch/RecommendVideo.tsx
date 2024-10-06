type Props = {};

const RecommendVideo = (props: Props) => {
    return (
        <div>
            <h3 className="text-xl font-semibold mb-4">Đề xuất cho bạn</h3>
            <div className="flex flex-col gap-2 justify-start items-center">
                {new Array(10).fill(0).map((_, index) => (
                    <div
                        key={index}
                        className="flex justify-start items-start gap-3"
                    >
                        <div className="w-[60%] aspect-video overflow-hidden rounded-xl">
                            <img
                                className="w-full h-full object-cover"
                                src="https://i.ytimg.com/vi/IFdg9N7m9Mc/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBEELLrAZbNyLdZvOgpw_0v9tPfWQ"
                                alt=""
                            />
                        </div>
                        <div>
                            <h5 className="font-semibold text-sm line-clamp-2">
                                Lập Trình Hiệu Quả Với Visual Studio Code
                                #VSCode
                            </h5>
                            <div className="text-xs text-muted-foreground">
                                <p>CodeXplore</p>
                                <p>
                                    <span>12N lượt xem</span>
                                    <span>11 tháng trước</span>
                                </p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default RecommendVideo;
