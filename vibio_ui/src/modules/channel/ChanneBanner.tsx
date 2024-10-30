type Props = {
    backgroundUrl?: string;
};

const ChanneBanner = ({ backgroundUrl }: Props) => {
    return (
        <div className="w-full h-[160px] rounded-xl overflow-hidden">
            <img
                className="w-full h-full object-cover"
                src={
                    backgroundUrl ??
                    "https://yt3.googleusercontent.com/gJf0-5yiGCW0ojIG-g-e2xsdWUuan7URbyQBKyu84EDkTybY_7l_TsWLSDcCRWhmIARcFXoDHQ=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj"
                }
                alt="channel-banner"
            />
        </div>
    );
};

export default ChanneBanner;
