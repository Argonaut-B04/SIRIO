import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";
import MainPage from "./pages";
import DaftarRekomendasi from "./pages/DaftarRekomendasi";
import NotFound from "./pages/error";

class App extends React.Component {
  render() {
    return (
      <>
        <Router>
          <Switch>
            <Route exact path="/" component={MainPage} />
            <Route exact path="/rekomendasi" component={DaftarRekomendasi} />
            <Route exact path="/404" component={NotFound} />
            <Redirect to="/404" />
          </Switch>
        </Router>
      </>
    )
  }
}

export default App;
