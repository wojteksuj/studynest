import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "../api/axios";
import { CircleCheck, CircleDashed } from "lucide-react";
import TopBar from "../components/TopBar";

type Flashcard = {
    id: string;
    prompt: string;
    answer: string;
};

export default function SetDetailsPage() {

    const { setId } = useParams();
    const navigate = useNavigate();

    const [visibleAnswers, setVisibleAnswers] = useState<Set<string>>(new Set());

    const [flashcards, setFlashcards] = useState<Flashcard[]>([]);

    const fetchFlashcards = async () => {
        const response = await axios.get(
            `/flashcard-sets/${setId}/flashcards`
        );

        setFlashcards(response.data);
    };

    const handleReturn = () => {
        navigate("/dashboard");
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
        fetchFlashcards();
    }, [setId]);

    return (
        <div className="min-h-screen relative overflow-hidden">

            {/* Background */}
            <div className="absolute inset-0 bg-gradient-to-br from-[#8FC3B1] via-[#E6BC9C] to-[#EE7F87]" />
    <div className="absolute inset-0 bg-slate-900/80 backdrop-blur-2xl" />

    <div className="relative z-10 min-h-screen flex flex-col">

        {/* Top bar */}
        <TopBar buttonLabel="Return" onButtonClick={handleReturn} />

    {/* Content */}
    <div className="flex-1">
    <div className="max-w-5xl mx-auto w-full px-12 py-16">

    <h2 className="text-xl font-semibold text-white mb-10">
        Flashcards
        </h2>

        <div className="space-y-6">

            {flashcards.map(card => {
                const isVisible = visibleAnswers.has(card.id);

                return (
                    <div
                        key={card.id}
                        className="grid grid-cols-2 gap-6 bg-slate-800/90 border border-slate-700 rounded-2xl p-6 items-center"
                    >
                        <div className="text-slate-200 font-medium">
                            {card.prompt}
                        </div>

                        <div className="flex items-center justify-between ">
                            <div className="text-slate-400">
                                {isVisible ? card.answer : "Hidden"}
                            </div>

                            <button
                                onClick={() => toggleAnswer(card.id)}
                                className="ml-4 text-slate-400 hover:text-[#8FC3B1] transition"
                            >
                                {isVisible ? (
                                    <CircleCheck size={20} />
                                ) : (
                                    <CircleDashed size={20} />
                                )}
                            </button>
                        </div>
                    </div>
                );
            })}


    </div>

    </div>
    </div>

    </div>
    </div>
);
}