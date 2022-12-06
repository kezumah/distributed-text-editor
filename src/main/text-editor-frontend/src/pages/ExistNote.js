import { useEffect, useState } from "react";
export default function ExistNote() {
  const [code, setCode] = useState("");
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");
  const [infos, setInfos] = useState([]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const noteCode = { code };

    const info = { name, address };

    fetch("http://localhost:9090/addUser", {
      method: "POST",
      headers: { "Content-type": "application/json" },
      body: JSON.stringify(info),
    }).then(() => {
      console.log("new info added");
    });
  };

  useEffect(() => {
    fetch("http://localhost:9090/users")
      .then((res) => res.json())
      .then((result) => {
        setInfos(result);
      });
  }, []);

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <lable>Name:</lable>
        <input
          type="text"
          required
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <lable> Address:</lable>
        <input
          type="text"
          required
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
        <button>Enter</button>
      </form>

      {console.log(infos)}
      {name}
      {address}
      <h2>INFO</h2>
      {infos.map((information) => (
        <div>
          Address: {information.address}
          ID: {information.id}
          Name: {information.name}
        </div>
      ))}
    </div>
  );
}
