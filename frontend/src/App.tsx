import { useEffect, useState } from "react";
import { api } from "./api/axios";

function App() {
    const [data, setData] = useState("");

    useEffect(() => {
        api.get("/users")
            .then(res => setData(JSON.stringify(res.data)))
            .catch(err => console.error(err));
    }, []);

    return (
        <div>
            <h1>StudyNest</h1>
            <pre>{data}</pre>
        </div>
    );
}

export default App;
