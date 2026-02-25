import {useEffect, useState} from "react";
import {useAuth} from "../context/AuthContext";
import {useNavigate} from "react-router-dom";

type FlashcardSet = {
    id: string;
    title: string;
};

type Topic = {
    id: string;
    topic: string;
};

type CreateSetRequest = {
    title: string;
    description: string;
    topicId: string;
};

export default function DashboardPage() {

    const {logout, username} = useAuth();
    const navigate = useNavigate();

    const [sets, setSets] = useState<FlashcardSet[]>([]);
    const [selectedSetId, setSelectedSetId] = useState<string | null>(null);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [topics, setTopics] = useState<Topic[]>([]);

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [selectedTopicId, setSelectedTopicId] = useState("");

    useEffect(() => {
        fetchSets();
    }, []);

    const fetchSets = async () => {
        const token = localStorage.getItem("accessToken");

        const response = await fetch("http://localhost:8080/api/flashcard-sets", {
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

    const openModal = async () => {
        setIsModalOpen(true);

        const token = localStorage.getItem("accessToken");

        const response = await fetch("http://localhost:8080/api/topics", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        const data = await response.json();
        setTopics(data);
    };

    const handleCreateSet = async () => {
        const token = localStorage.getItem("accessToken");

        const body: CreateSetRequest = {
            title,
            description,
            topicId: selectedTopicId
        };

        const response = await fetch("http://localhost:8080/api/flashcard-sets", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(body)
        });

        if (response.ok) {
            setIsModalOpen(false);
            setTitle("");
            setDescription("");
            setSelectedTopicId("");
            fetchSets();
        }
    };

    return (
        <div className="min-h-screen relative overflow-hidden">

            <div className="absolute inset-0 bg-gradient-to-br from-[#8FC3B1] via-[#E6BC9C] to-[#EE7F87]"/>
            <div className="absolute inset-0 bg-slate-900/80 backdrop-blur-2xl"/>

            <div className="relative z-10 min-h-screen flex flex-col">

                <div className="h-16 bg-slate-800/90 border-b border-slate-700 backdrop-blur-xl">
                    <div className="max-w-7xl mx-auto w-full px-12 h-full flex items-center justify-between">

                        <div className="flex items-center gap-4">
                            <div
                                className="h-10 w-10 rounded-full bg-[#8FC3B1] flex items-center justify-center text-slate-900 font-semibold text-base">
                                {username?.charAt(0).toUpperCase()}
                            </div>

                            <span className="text-slate-200 font-semibold text-base">
                {username}
            </span>
                        </div>

                        <button
                            onClick={handleLogout}
                            className="bg-[#EE7F87] hover:bg-[#E89A95] text-slate-900 font-semibold text-base px-6 py-2 rounded-xl transition shadow-md hover:shadow-lg"
                        >
                            Logout
                        </button>

                    </div>
                </div>


                <div className="flex-1">
                    <div className="max-w-7xl mx-auto w-full px-12 py-16 flex gap-20">

                        <div className="w-64 flex flex-col gap-6">

                            <button
                                onClick={openModal}
                                className="bg-[#8FC3B1] hover:bg-slate-600 text-slate-200 font-semibold px-6 py-3 rounded-xl transition shadow-md">
                                Add new set
                            </button>

                            <button
                                className="bg-slate-700 hover:bg-slate-600 text-slate-200 font-semibold px-6 py-3 rounded-xl transition shadow-md">
                                Add new topic
                            </button>

                        </div>

                        <div className="flex-1 flex flex-col items-center">

                            <h2 className="text-xl font-semibold text-white mb-8">
                                Your sets:
                            </h2>

                            <div
                                className="w-full max-w-2xl bg-slate-800/90 border border-slate-700 rounded-3xl shadow-2xl p-10">

                                <div className="space-y-6">
                                    {sets.map(set => (
                                        <button
                                            key={set.id}
                                            onClick={() => setSelectedSetId(set.id)}
                                            className={`w-full text-left text-lg px-4 py-3 rounded-xl transition
                                ${selectedSetId === set.id
                                                ? "bg-[#8FC3B1] text-slate-900"
                                                : "text-slate-200 hover:bg-slate-700"
                                            }`}
                                        >
                                            {set.title}
                                        </button>
                                    ))}
                                </div>

                            </div>

                        </div>

                    </div>
                </div>

            </div>
            {isModalOpen && (
                <div className="fixed inset-0 flex items-center justify-center bg-black/60 z-50">
                    <div className="bg-slate-800 p-8 rounded-2xl w-full max-w-md space-y-6">

                        <h3 className="text-white text-lg font-semibold">
                            Create new set
                        </h3>

                        <input
                            type="text"
                            placeholder="Title"
                            value={title}
                            onChange={e => setTitle(e.target.value)}
                            className="w-full px-4 py-2 rounded-lg bg-slate-700 text-white"
                        />

                        <textarea
                            placeholder="Description"
                            value={description}
                            onChange={e => setDescription(e.target.value)}
                            className="w-full px-4 py-2 rounded-lg bg-slate-700 text-white"
                        />

                        <select
                            value={selectedTopicId}
                            onChange={e => setSelectedTopicId(e.target.value)}
                            className="w-full px-4 py-2 rounded-lg bg-slate-700 text-white"
                        >
                            <option value="">Select topic</option>
                            {topics.map(topic => (
                                <option key={topic.id} value={topic.id}>
                                    {topic.topic}
                                </option>
                            ))}
                        </select>

                        <div className="flex justify-end gap-4">
                            <button
                                onClick={() => setIsModalOpen(false)}
                                className="px-4 py-2 bg-slate-600 text-white rounded-lg"
                            >
                                Cancel
                            </button>

                            <button
                                onClick={handleCreateSet}
                                className="px-4 py-2 bg-[#8FC3B1] text-slate-900 rounded-lg"
                            >
                                Create
                            </button>
                        </div>

                    </div>
                </div>
            )}
        </div>
    );
}
