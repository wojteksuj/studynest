import {useEffect, useState} from "react";
import {useParams, useNavigate} from "react-router-dom";
import axios from "../api/axios";
import {CircleCheck, CircleDashed} from "lucide-react";
import TopBar from "../components/TopBar";

type Flashcard = {
    id: string;
    prompt: string;
    answer: string;
};

type FlashcardSetDetails = {
    id: string;
    title: string;
    description: string;
};

export default function SetDetailsPage() {

    const {setId} = useParams();
    const navigate = useNavigate();

    const [visibleAnswers, setVisibleAnswers] = useState<Set<string>>(new Set());

    const [flashcards, setFlashcards] = useState<Flashcard[]>([]);

    const [setDetails, setSetDetails] = useState<FlashcardSetDetails | null>(null);

    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [isDeleting, setIsDeleting] = useState(false);

    const fetchFlashcards = async () => {
        const response = await axios.get(
            `/flashcard-sets/${setId}/flashcards`
        );

        setFlashcards(response.data);
    };

    const fetchSetDetails = async () => {
        const response = await axios.get(`/flashcard-sets/${setId}`);
        setSetDetails(response.data);
    };

    const handleReturn = () => {
        navigate("/dashboard");
    };

    const handleDeleteSet = async () => {
        if (!setId) return;

        try {
            setIsDeleting(true);
            await axios.delete(`/flashcard-sets/${setId}`);
            navigate("/dashboard");
        } catch (error) {
            console.error("Delete failed", error);
        } finally {
            setIsDeleting(false);
            setShowDeleteModal(false);
        }
    };

    const toggleAnswer = (id: string) => {
        setVisibleAnswers(prev => {
            const newSet = new Set(prev);

            if (newSet.has(id)) {
                newSet.delete(id);
            } else {
                newSet.add(id);
            }

            return newSet;
        });
    };

    useEffect(() => {
        if (!setId) return;

        fetchSetDetails();
        fetchFlashcards();
    }, [setId]);

    return (
        <div className="min-h-screen relative overflow-hidden">

            {/* Background */}
            <div className="absolute inset-0 bg-gradient-to-br from-[#8FC3B1] via-[#E6BC9C] to-[#EE7F87]"/>
            <div className="absolute inset-0 bg-slate-900/80 backdrop-blur-2xl"/>

            <div className="relative z-10 min-h-screen flex flex-col">

                {/* Top bar */}
                <TopBar buttonLabel="Return" onButtonClick={handleReturn}/>

                {/* Content */}
                <div className="flex-1">
                    <div className="max-w-5xl mx-auto w-full px-12 py-16">

                        {setDetails && (
                            <div className="mb-16 flex items-start justify-between">

                                {/* Lewa strona */}
                                <div>
                                    <h1 className="text-4xl font-bold text-white tracking-tight mb-4">
                                        {setDetails.title}
                                    </h1>

                                    {setDetails.description && (
                                        <p className="text-slate-400 text-lg leading-relaxed max-w-2xl">
                                            {setDetails.description}
                                        </p>
                                    )}
                                </div>

                                {/* Prawa strona */}
                                <div className="flex flex-col items-end">
                                        <span
                                            className="bg-slate-800/70 text-slate-300 text-sm px-4 py-2 rounded-full border border-slate-700">
                                            {flashcards.length} cards
                                        </span>
                                </div>

                            </div>
                        )}
                        <h2 className="text-xl font-semibold text-white mb-10">
                            Flashcards
                        </h2>

                        {flashcards.length === 0 ? (
                            <div className="flex items-center justify-center py-24">
                                <div className="text-center">
                                    <h3 className="text-2xl font-semibold text-white mb-3">
                                        This set is empty
                                    </h3>
                                    <p className="text-slate-400">
                                        Add your first flashcard to get started.
                                    </p>
                                </div>
                            </div>
                        ) : (
                            <div className="space-y-6">
                                {flashcards.map((card) => {
                                    const isVisible = visibleAnswers.has(card.id);

                                    return (
                                        <div
                                            key={card.id}
                                            className="grid grid-cols-2 bg-slate-800/90 border border-slate-700 rounded-2xl overflow-hidden shadow-lg"
                                        >
                                            {/* PROMPT */}
                                            <div className="p-6 border-r border-slate-700 bg-slate-800">
                                                <div className="text-slate-100 font-medium leading-relaxed">
                                                    {card.prompt}
                                                </div>
                                            </div>

                                            {/* ANSWER */}
                                            <div className="p-6 bg-slate-900/60">
                                                <div className="flex items-start">
                                                    <div className="flex-1">
                                                        <div
                                                            className={`text-slate-300 leading-relaxed transition-all duration-300 ${
                                                                isVisible
                                                                    ? "opacity-100"
                                                                    : "opacity-40 blur-[2px]"
                                                            }`}
                                                        >
                                                            {isVisible ? card.answer : "Hidden"}
                                                        </div>
                                                    </div>

                                                    <button
                                                        onClick={() => toggleAnswer(card.id)}
                                                        className="ml-6 text-slate-400 hover:text-[#8FC3B1] transition"
                                                    >
                                                        {isVisible ? (
                                                            <CircleCheck size={22}/>
                                                        ) : (
                                                            <CircleDashed size={22}/>
                                                        )}
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    );
                                })}
                            </div>
                        )}
                        <div className="mt-16 flex justify-end gap-4">

                            <button
                                onClick={() => navigate(`/flashcard-sets/${setId}/edit`)}
                                className="bg-[#8FC3B1] hover:opacity-90 text-slate-900 px-6 py-3 rounded-xl transition font-semibold"
                            >
                                Edit
                            </button>

                            <button
                                onClick={() => setShowDeleteModal(true)}
                                className="bg-slate-700 hover:bg-slate-600 text-slate-200 px-6 py-3 rounded-xl transition border border-slate-600"
                            >
                                Delete set
                            </button>

                        </div>

                    </div>
                </div>

            </div>

            {showDeleteModal && (
                <div className="fixed inset-0 z-50 flex items-center justify-center">
                    {/* overlay */}
                    <div
                        className="absolute inset-0 bg-black/60 backdrop-blur-sm"
                        onClick={() => setShowDeleteModal(false)}
                    />

                    {/* modal */}
                    <div className="relative bg-slate-900 border border-slate-700 rounded-2xl p-8 w-full max-w-md shadow-2xl">
                        <h3 className="text-xl font-semibold text-white mb-4">
                            Delete this set?
                        </h3>

                        <p className="text-slate-400 mb-8">
                            This action cannot be undone. All flashcards in this set will be permanently removed.
                        </p>

                        <div className="flex justify-end space-x-4">
                            <button
                                onClick={() => setShowDeleteModal(false)}
                                className="px-4 py-2 rounded-lg bg-slate-700 hover:bg-slate-600 text-white transition"
                            >
                                Cancel
                            </button>

                            <button
                                onClick={handleDeleteSet}
                                disabled={isDeleting}
                                className="px-4 py-2 rounded-lg bg-red-600 hover:bg-red-700 text-white transition disabled:opacity-50"
                            >
                                {isDeleting ? "Deleting..." : "Delete"}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>


    );
}