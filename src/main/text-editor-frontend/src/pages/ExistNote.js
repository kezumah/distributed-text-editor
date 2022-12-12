import { useEffect, useState } from "react";

export default function ExistNote() {
  const [code, setCode] = useState("");
  const [note, setNote] = useState(null);


/*
// mark your function as async
async function handleSubmit(e) {
  e.preventDefault();
  // always try using const rather than let
  const response = await fetch("http://localhost:9090/greeting", {
    method: "GET",
    header: { "Content-Type": "application/json" }})

  if (response.ok) {
      console.log("response is ok")
      //const json = response.json();
      console.log(response.text());
  } else {
      alert("HTTP response not ok");
  }
}
*/

const handleSubmit = async (e) => {
  e.preventDefault();

  await fetch("http://localhost:9090/greeting", {
    method: "GET",
    header: { "Content-Type": "application/json" },
    //body: JSON.stringify(note),
  }).then((res) => {
    let x = res.text()
    console.log(x);
    return x;
  });
};

/*

const handleSubmit = async (e) => {
    e.preventDefault();

    await fetch("http://localhost:9090/greeting", {
      method: "GET",
      header: { "Content-Type": "application/json" },
      //body: JSON.stringify(note),
    }).then((res) => {
      let x = res.text();
      console.log(res.text());
    });
  };

  */


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
