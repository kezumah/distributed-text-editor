import { useEffect, useState } from "react";

export default function ExistNote() {
  const [code, setCode] = useState("");
  const [note, setNote] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch("http://localhost:9090/addUser", {
      method: "POST",
      header: { "Content-Type": "application/json" },
      body: JSON.stringify(note),
    }).then(() => {
      console.log("new blog add");
    });
  };

  useEffect(() => {
    fetch("http://localhost:9090/users")
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setNote(data);
      });
  }, []);

  return (
    <div className="create">
      <form onSubmit={handleSubmit}>
        <lable>Hash:</lable>
        <input
          type="text"
          required
          value={code}
          onChange={(e) => setCode(e.target.value)}
        />
        <button>Enter</button>
        {note && (
          <textarea
            required
            value={note[0].name}
            onChange={(e) => setNote(e.target.value)}
          ></textarea>
        )}
      </form>
    </div>
  );
}
