import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";
import MainPage from "./pages";
import DaftarRekomendasi from "./pages/QAOfficer/DaftarRekomendasi";
import DaftarRekomendasiBM from "./pages/BranchManager/DaftarRekomendasi";
import DetailBuktiBM from "./pages/BranchManager/DetailBuktiPelaksanaan";
import DaftarBuktiPelaksanaan from "./pages/QAOfficer/DaftarBuktiPelaksanaan";
import NotFound from "./pages/error";
import Login from "./pages/login";
import Logout from "./pages/logout";
import FormDemo from "./pages/formPage";
import './App.css';
import RegistrasiRisiko from './pages/ManagerOR/RegistrasiRisiko';
import DetailRisiko from './pages/ManagerOR/DetailRisiko';

/**
 * Router utama yang memetakan url ke controller
 */
class App extends React.Component {
  render() {
    return (
      <>
        <Router>
          <Switch>
            <Route exact path="/" component={MainPage} />
            <Route exact path="/rekomendasi" component={DaftarRekomendasi} />
            <Route exact path="/bm/rekomendasi" component={DaftarRekomendasiBM} />
            <Route exact path="/bm/rekomendasi/detail-bukti" component={DetailBuktiBM} />
            <Route exact path="/bukti-pelaksanaan" component={DaftarBuktiPelaksanaan} />
            <Route exact path="/registrasi-risiko" component={RegistrasiRisiko} />
            <Route exact path="/registrasi-risiko/detail/1" component={DetailRisiko} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/logout" component={Logout} />
            <Route exact path="/Form" component={FormDemo} />
            <Route exact path="/404" component={NotFound} />
            <Redirect to="/404" />
          </Switch>
        </Router>
      </>
    )
  }
}

export default App;
