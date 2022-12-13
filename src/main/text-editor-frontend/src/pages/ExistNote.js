import { useEffect, useState } from "react";

export default function ExistNote() {
  const [hash, setHash] = useState("");
  const [note, setNote] = useState(null);


/*
async function handleSubmit(e) {
  e.preventDefault();
  await fetch("http://localhost:9090/greeting", {
    method: "GET",
    header: { "Content-Type": "application/json" },
    //body: JSON.stringify(hash),
  }).then((res) => res.text()).then((res) => console.log(res))
};
*/

async function handleSubmit(e) {
  e.preventDefault();
  await fetch("http://localhost:9090/doc", {
    method: "POST",
    header: { "Content-Type": "application/json" },
    body: hash,
  }).then((res) => res.json()).then((res) => console.log(res))
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
          value={hash}
          onChange={(e) => setHash(e.target.value)}
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
