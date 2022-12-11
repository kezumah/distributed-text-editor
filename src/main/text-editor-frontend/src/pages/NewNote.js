import { useState } from "react";
import Note from "../components/Note";

export default function NewNote() {
  const [code, setCode] = useState("");
  const [note, setNote] = useState("");

  return (
    <div className="create">
      <lable>Hash:</lable>
      <input type="text" required value={code} />
      <textarea
        required
        value={note}
        onChange={(e) => setNote(e.target.value)}
      ></textarea>
    </div>
  );
}
