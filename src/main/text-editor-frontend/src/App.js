import "./App.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import NewNote from "./pages/NewNote";
import ExistNote from "./pages/ExistNote";
import NotFound from "./pages/NotFound";
import Post from "./pages/Post";
import SynchingEditor from "./SynchingEditor";

function App() {
  /*
  return (
    <div>
      <SynchingEditor />
      <br />

      <SynchingEditor />
      <br />

      <SynchingEditor />
    </div>
  );
  */
 return (
   <BrowserRouter basename="notepad">
     <div className="App">
       <h1>Note Pad</h1>
       <Header />
     </div>
     <div className="App">
       <Switch>
         <Route path="/" component={Home} exact />
         <Route path="/newnote" component={NewNote} exact />
         <Route path="/existnote" component={ExistNote} exact />
         <Route path="/existnote/post/:id" component={Post} exact />
         <Route component={NotFound} />
       </Switch>
     </div>
   </BrowserRouter>
 );
}

export default App;
