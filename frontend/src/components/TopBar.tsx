import { useAuth } from "../context/AuthContext";

type TopBarProps = {
    buttonLabel: string;
    onButtonClick: () => void;
};

export default function TopBar({ buttonLabel, onButtonClick }: TopBarProps) {
    const { username } = useAuth();

    return (
        <div className="h-16 bg-slate-800/90 border-b border-slate-700 backdrop-blur-xl">
            <div className="max-w-7xl mx-auto w-full px-12 h-full flex items-center justify-between">

                <div className="flex items-center gap-4">
                    <div className="h-10 w-10 rounded-full bg-[#8FC3B1] flex items-center justify-center text-slate-900 font-semibold text-base">
                        {username?.charAt(0).toUpperCase()}
                    </div>

                    <span className="text-slate-200 font-semibold text-base">
                        {username}
                    </span>
                </div>

                <button
                    onClick={onButtonClick}
                    className="bg-[#EE7F87] hover:bg-[#E89A95] text-slate-900 font-semibold text-base px-6 py-2 rounded-xl transition shadow-md hover:shadow-lg"
                >
                    {buttonLabel}
                </button>

            </div>
        </div>
    );
}