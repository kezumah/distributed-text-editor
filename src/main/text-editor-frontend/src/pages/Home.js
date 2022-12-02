import { useEffect } from "react";

export default function Home() {
  useEffect(() => {
    window.scroll(0, 0);
  }, []);

  return <div className="home">Welcome to the shared notepad</div>;
}
