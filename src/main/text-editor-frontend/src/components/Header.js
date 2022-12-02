import { Link } from "react-router-dom";

const Header = () => {
  return (
    <div>
      <ul className="nav">
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/newnote">New Note</Link>
        </li>
        <li>
          <Link to="/existnote">Exist Note</Link>
        </li>
      </ul>
    </div>
  );
};
export default Header;
