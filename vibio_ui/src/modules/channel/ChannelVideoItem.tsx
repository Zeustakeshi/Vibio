type Props = {};

const ChannelVideoItem = (props: Props) => {
    return (
        <div className="cursor-pointer">
            <div className="w-full aspect-video rounded-xl overflow-hidden">
                <img
                    className="w-full h-full object-cover"
                    src="https://yt3.googleusercontent.com/gJf0-5yiGCW0ojIG-g-e2xsdWUuan7URbyQBKyu84EDkTybY_7l_TsWLSDcCRWhmIARcFXoDHQ=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj"
                    alt=""
                />
            </div>
            <h4 className="my-1 text-lg font-semibold">Hoạt hình con thỏ</h4>
            <p className="text-sm text-muted-foreground flex justify-start items-center gap-2">
                <span>100 lượt xem</span> - <span>2 tuần trước</span>
            </p>
        </div>
    );
};

export default ChannelVideoItem;
