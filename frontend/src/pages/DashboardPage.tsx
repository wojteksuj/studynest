import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

type FlashcardSet = {
    id: string;
    title: string;
};

export default function DashboardPage() {

    const { logout } = useAuth();
    const navigate = useNavigate();

    const [sets, setSets] = useState<FlashcardSet[]>([]);
    const [selectedSetId, setSelectedSetId] = useState<string | null>(null);

    useEffect(() => {
        fetchSets();
    }, []);

    const fetchSets = async () => {
        const token = localStorage.getItem("accessToken");

        const response = await fetch("http://localhost:8080/studynest/flashcard-sets", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        const data = await response.json();
        setSets(data);
    };

    const handleLogout = () => {
        logout();
        navigate("/");
    };

    return (
        <div className="min-h-screen relative overflow-hidden">

            <div className="absolute inset-0 bg-gradient-to-br from-[#8FC3B1] via-[#E6BC9C] to-[#EE7F87]" />

            <div className="absolute inset-0 bg-slate-900/80 backdrop-blur-2xl" />

            <div className="relative z-10 min-h-screen flex flex-col">


                <div className="h-16 bg-slate-800/90 border-b border-slate-700 flex items-center justify-end px-8">
                    <button
                        onClick={handleLogout}
                        className="bg-[#EE7F87] hover:bg-[#E89A95] text-slate-900 font-semibold px-5 py-2 rounded-xl transition"
                    >
                        Logout
                    </button>
                </div>

                <div className="flex flex-1 p-12">

                    <div className="w-80 bg-slate-800/90 border border-slate-700 rounded-2xl shadow-2xl p-6">

                        <h2 className="text-lg font-semibold text-white mb-6">
                            Your sets
                        </h2>

                        <div className="space-y-2">
                            {sets.map(set => (
                                <button
                                    key={set.id}
                                    onClick={() => setSelectedSetId(set.id)}
                                    className={`w-full text-left px-4 py-3 rounded-xl transition
                                        ${selectedSetId === set.id
                                        ? "bg-[#8FC3B1] text-slate-900"
                                        : "bg-slate-900/60 text-slate-300 hover:bg-slate-700"
                                    }`}
                                >
                                    {set.title}
                                </button>
                            ))}
                        </div>
                    </div>

                    <div className="flex-1 ml-16 flex items-center justify-center text-slate-400">
                        {selectedSetId
                            ? `Selected set ID: ${selectedSetId}`
                            : "Select a set to start learning"}
                    </div>

                </div>

            </div>
        </div>
    );
}
