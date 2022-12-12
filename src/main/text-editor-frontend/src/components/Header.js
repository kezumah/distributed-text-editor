import { Link } from "react-router-dom";

const Header = () => {
  const handleSubmit = (e) => {
    //   e.preventDefault();
    //   fetch("http://localhost:9090/new", {
    //     method: "POST",
    //     headers: { "Content-type": "application/json" },
    //     body: JSON.stringify(),
    //   })
    //     .then(() => {
    //       (res) => {};
    //     })
    //     .then((data) => console.log(data))
    //     .then(() => {
    //       console.log("new info added");
    //     });
  };

  return (
    <div>
      <ul className="nav">
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link onSubmit={handleSubmit} to="/newnote">
            New Note
          </Link>
        </li>
        <li>
          <Link to="/existnote">Exist Note</Link>
        </li>
      </ul>
    </div>
  );
};
export default Header;
