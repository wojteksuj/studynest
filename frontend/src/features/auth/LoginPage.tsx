import { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { Eye, EyeOff } from "lucide-react";
import logo from "../../assets/sn-logo.png";


export default function LoginPage() {
    const { login } = useAuth();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const isFormValid =
        email.trim() !== "" &&
        password.trim() !== "";

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            await login({ email, password });
            navigate("/dashboard");
        } catch (err) {
            console.log("LOGIN ERROR", err);
            setError("Invalid credentials");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center relative overflow-hidden">

            {/* Background gradient */}
            <div className="absolute inset-0 bg-gradient-to-br from-[#8FC3B1] via-[#E6BC9C] to-[#EE7F87]" />

            {/* Dark overlay for contrast */}
            <div className="absolute inset-0 bg-slate-900/70 backdrop-blur-2xl" />

            {/* Card */}
            <div className="relative z-10 w-full max-w-md px-10 py-12 bg-slate-800/90 rounded-2xl shadow-2xl border border-slate-700">

                <div className="text-center mb-10">
                    <img
                        src={logo}
                        alt="StudyNest"
                        className="mx-auto mb-6 h-16 object-contain drop-shadow-[0_0_12px_rgba(255,255,255,0.3)]"
                    />

                </div>

                {error && (
                    <div className="mb-5 text-sm text-red-300 bg-red-900/40 border border-red-700 px-3 py-2 rounded-lg">
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit} noValidate className="space-y-6">

                    <div>
                        <label className="block text-sm text-slate-300 mb-2">
                            Email
                        </label>
                        <input
                            type="email"
                            required
                            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-4 py-3 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-[#8FC3B1] focus:border-transparent transition"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="you@example.com"
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-slate-300 mb-2">
                            Password
                        </label>

                        <div className="relative">
                            <input
                                type={showPassword ? "text" : "password"}
                                required
                                className="w-full bg-slate-900 border border-slate-700 rounded-lg px-4 py-3 pr-12 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-[#8FC3B1] transition"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder="••••••••"
                            />

                            <button
                                type="button"
                                onClick={() => setShowPassword(prev => !prev)}
                                className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-400 hover:text-white transition"
                            >
                                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                            </button>
                        </div>
                    </div>


                    {/* Primary Button */}
                    <button
                        type="submit"
                        disabled={loading || !isFormValid}
                        className={`w-full font-semibold py-3 rounded-lg transition
                        ${loading || !isFormValid
                            ? "bg-slate-600 text-slate-400 cursor-not-allowed"
                            : "bg-[#EE7F87] hover:bg-[#E89A95] text-slate-900"
                        }`}
                    >
                        {loading ? "Signing in..." : "Sign in"}
                    </button>

                    {/* Secondary Button */}
                    <button
                        type="button"
                        onClick={() => navigate("/register")}
                        className="w-full border border-slate-600 text-slate-300 hover:bg-slate-700 py-3 rounded-lg transition"
                    >
                        Register
                    </button>

                </form>

                <p className="text-center text-xs text-slate-500 mt-10">
                    © {new Date().getFullYear()} StudyNest
                </p>

            </div>
        </div>
    );
}
