import { useState } from "react";
import Note from "../components/Note";

export default function NewNote() {
  //const [code, setCode] = useState("");
  const [note, setNote] = useState("");


  const socket = new WebSocket('ws://localhost:1300');

  socket.onopen = function() {
    socket.send(JSON.stringify("hello"));
    console.log("Socket is opened!");
  }

  socket.onmessage = function (e) {
    let message = e.data;
    console.log(message);
  }

 // function sendMessage(data) {
  //  socket.send(data);
  //  console.log("success")
  //}

  function handleChange(e) {
    console.log(e.key);
   // console.log("Sending to socket server");
   // sendMessage(e.key);
  }


  return (
    <div className="create">
      <lable>Hash:</lable>
      <input type="text" required value={"abcd"} />
      <textarea
        required
        value={note}
        onKeyDown={(e) => handleChange(e)}
        onChange={(e) => setNote(e.target.value)}
      ></textarea>
    </div>
  );
}
