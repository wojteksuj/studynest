import {useEffect, useState} from "react";
import {useParams, useNavigate} from "react-router-dom";
import axios from "../api/axios";
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

export default function EditSetPage() {

    const {setId} = useParams();
    const navigate = useNavigate();

    const [flashcards, setFlashcards] = useState<Flashcard[]>([]);
    const [setDetails, setSetDetails] = useState<FlashcardSetDetails | null>(null);

    const [newPrompt, setNewPrompt] = useState("");
    const [newAnswer, setNewAnswer] = useState("");

    const [editing, setEditing] = useState<{
        id: string;
        field: "prompt" | "answer";
    } | null>(null);

    // ---------------- FETCH ----------------

    useEffect(() => {
        if (!setId) return;

        const fetchData = async () => {
            const setResponse = await axios.get(`/flashcard-sets/${setId}`);
            const cardsResponse = await axios.get(
                `/flashcard-sets/${setId}/flashcards`
            );

            setSetDetails(setResponse.data);
            setFlashcards(cardsResponse.data);
        };

        fetchData();
    }, [setId]);

    // ---------------- ADD ----------------

    const handleAddFlashcard = async () => {
        if (!newPrompt.trim() || !newAnswer.trim()) return;

        const response = await axios.post(
            `/flashcard-sets/${setId}/flashcards`,
            {prompt: newPrompt, answer: newAnswer}
        );

        setFlashcards(prev => [...prev, response.data]);

        setNewPrompt("");
        setNewAnswer("");
    };

    // ---------------- UPDATE ----------------

    const handleUpdate = async (
        id: string,
        field: "prompt" | "answer",
        value: string
    ) => {
        await axios.put(`/flashcards/${id}`, {
            [field]: value
        });

        setFlashcards(prev =>
            prev.map(card =>
                card.id === id ? {...card, [field]: value} : card
            )
        );

        setEditing(null);
    };

    return (
        <div className="min-h-screen relative overflow-hidden">

            {/* Background */}
            <div className="absolute inset-0 bg-gradient-to-br from-[#8FC3B1] via-[#E6BC9C] to-[#EE7F87]" />
            <div className="absolute inset-0 bg-slate-900/80 backdrop-blur-2xl" />

            <div className="relative z-10 min-h-screen flex flex-col">

                <TopBar
                    buttonLabel="Return"
                    onButtonClick={() => navigate("/dashboard")}
                />

                <div className="flex-1">
                    <div className="max-w-5xl mx-auto w-full px-12 py-16">

                        {/* HEADER */}
                        {setDetails && (
                            <div className="mb-16">
                                <h1 className="text-4xl font-bold text-white mb-4">
                                    {setDetails.title}
                                </h1>

                                {setDetails.description && (
                                    <p className="text-slate-400 text-lg">
                                        {setDetails.description}
                                    </p>
                                )}
                            </div>
                        )}

                        {/* FLASHCARDS */}
                        <div className="space-y-6">

                            {flashcards.map(card => (
                                <div
                                    key={card.id}
                                    className="grid grid-cols-2 bg-slate-800/90 border border-slate-700 rounded-2xl overflow-hidden"
                                >

                                    {/* PROMPT */}
                                    <div className="p-6 border-r border-slate-700">
                                        {editing?.id === card.id && editing.field === "prompt" ? (
                                            <textarea
                                                autoFocus
                                                defaultValue={card.prompt}
                                                onBlur={(e) =>
                                                    handleUpdate(
                                                        card.id,
                                                        "prompt",
                                                        e.target.value
                                                    )
                                                }
                                                className="w-full bg-slate-900 border border-slate-700 rounded-lg p-3 text-white"
                                            />
                                        ) : (
                                            <div
                                                onClick={() =>
                                                    setEditing({id: card.id, field: "prompt"})
                                                }
                                                className="text-white cursor-pointer hover:text-[#8FC3B1]"
                                            >
                                                {card.prompt}
                                            </div>
                                        )}
                                    </div>

                                    {/* ANSWER */}
                                    <div className="p-6 bg-slate-900/60">
                                        {editing?.id === card.id && editing.field === "answer" ? (
                                            <textarea
                                                autoFocus
                                                defaultValue={card.answer}
                                                onBlur={(e) =>
                                                    handleUpdate(
                                                        card.id,
                                                        "answer",
                                                        e.target.value
                                                    )
                                                }
                                                className="w-full bg-slate-900 border border-slate-700 rounded-lg p-3 text-white"
                                            />
                                        ) : (
                                            <div
                                                onClick={() =>
                                                    setEditing({id: card.id, field: "answer"})
                                                }
                                                className="text-slate-300 cursor-pointer hover:text-[#8FC3B1]"
                                            >
                                                {card.answer}
                                            </div>
                                        )}
                                    </div>

                                </div>
                            ))}

                            {/* NOWY WIERSZ (jak w diagramie) */}
                            <div className="grid grid-cols-2 bg-slate-800/50 border border-dashed border-slate-600 rounded-2xl overflow-hidden">

                                <div className="p-6 border-r border-slate-700">
                                    <textarea
                                        placeholder="Prompt..."
                                        value={newPrompt}
                                        onChange={(e) => setNewPrompt(e.target.value)}
                                        className="w-full bg-slate-900 border border-slate-700 rounded-lg p-3 text-white"
                                    />
                                </div>

                                <div className="p-6 bg-slate-900/60">
                                    <textarea
                                        placeholder="Answer..."
                                        value={newAnswer}
                                        onChange={(e) => setNewAnswer(e.target.value)}
                                        className="w-full bg-slate-900 border border-slate-700 rounded-lg p-3 text-white"
                                    />
                                </div>

                            </div>

                            {/* ACTION BUTTONS */}
                            <div className="flex justify-center gap-6 pt-10">

                                <button
                                    onClick={handleAddFlashcard}
                                    className="bg-[#8FC3B1] text-slate-900 px-8 py-3 rounded-xl font-semibold hover:opacity-90 transition"
                                >
                                    Add flashcard
                                </button>

                                <button
                                    onClick={() => navigate(`/flashcard-sets/${setId}`)}
                                    className="bg-slate-700 hover:bg-slate-600 text-white px-8 py-3 rounded-xl transition border border-slate-600"
                                >
                                    Save
                                </button>

                            </div>

                        </div>

                    </div>
                </div>

            </div>
        </div>
    );
}