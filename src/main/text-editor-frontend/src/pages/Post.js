import { useLocation, useParams } from "react-router-dom";

export default function Post() {
  const { id } = useParams();
  return <h2>ID = {id}</h2>;
}
