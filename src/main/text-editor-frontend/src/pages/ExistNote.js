import { useState } from "react";
export default function ExistNote() {
  const [code, setCode] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    const noteCode = { code };
    console.log(noteCode);

    fetch("http://localhost:8080/doc", {
      method: "POST",
      headers,
    });
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <lable>Note Code:</lable>
        <input
          type="text"
          required
          value={code}
          onChange={(e) => setCode(e.target.value)}
        />
        <button>Enter</button>
      </form>
    </div>
  );
}
