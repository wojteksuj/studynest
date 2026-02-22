type FlashcardSet = {
    id: string;
    title: string;
};

type SidebarProps = {
    sets: FlashcardSet[];
    selectedSetId: string | null;
    onSelect: (id: string) => void;
};

export default function Sidebar({ sets, selectedSetId, onSelect }: SidebarProps) {
    return (
        <div className="w-64 bg-slate-800 border-r border-slate-700 p-4 overflow-y-auto">

            <h2 className="text-lg font-semibold mb-4 text-white">
                Your Sets
            </h2>

            <div className="space-y-2">
                {sets.map(set => (
                    <button
                        key={set.id}
                        onClick={() => onSelect(set.id)}
                        className={`w-full text-left px-3 py-2 rounded-lg transition
                            ${selectedSetId === set.id
                            ? "bg-[#8FC3B1] text-slate-900"
                            : "text-white hover:bg-slate-700"
                        }`}
                    >
                        {set.title}
                    </button>
                ))}
            </div>
        </div>
    );
}
