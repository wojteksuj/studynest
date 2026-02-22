import {
    createContext,
    useContext,
    useState,
    useEffect,
    type ReactNode,
} from "react";
import axios from "../api/axios";

type LoginRequest = {
    email: string;
    password: string;
};

type AuthContextType = {
    token: string | null;
    username: string | null;
    isAuthenticated: boolean;
    login: (data: LoginRequest) => Promise<void>;
    logout: () => void;
};


const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(null);
    const [username, setUsername] = useState<string | null>(null);


    useEffect(() => {
        const storedToken = localStorage.getItem("token");
        if (storedToken) {
            setToken(storedToken);

            const payload = parseJwt(storedToken);
            setUsername(payload?.username ?? null);
        }
    }, []);


    const login = async ({ email, password }: LoginRequest) => {
        const response = await axios.post("/auth/login", {
            email,
            password,
        });

        const jwt: string = response.data.accessToken;

        if (!jwt) {
            throw new Error("Access token not found in response");
        }

        localStorage.setItem("accessToken", jwt);
        setToken(jwt);

        const payload = parseJwt(jwt);
        setUsername(payload?.username ?? null);
    };


    const logout = () => {
        localStorage.removeItem("accessToken");
        setToken(null);
    };

    const parseJwt = (token: string) => {
        try {
            const base64Payload = token.split(".")[1];
            const decodedPayload = atob(base64Payload);
            return JSON.parse(decodedPayload);
        } catch {
            return null;
        }
    };

    return (
        <AuthContext.Provider
            value={{
                token,
                username,
                isAuthenticated: !!token,
                login,
                logout,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within AuthProvider");
    }
    return context;
};
