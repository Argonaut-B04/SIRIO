import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";
import MainPage from "./pages";
import DaftarHasilPemeriksaan from "./pages/QAOfficer/DaftarHasilPemeriksaan";
import DaftarTugasPemeriksaan from "./pages/QAOfficer/DaftarTugasPemeriksaan";
import DaftarEmployee from "./pages/Administrator/DaftarEmployee";
import FormTambahEmployee from "./pages/Administrator/FormTambahEmployee";
import DetailEmployee from "./pages/Administrator/DetailEmployee";
import DaftarRekomendasi from "./pages/QAOfficer/DaftarRekomendasi";
import Reminder from "./pages/QAOfficer/Reminder";
import DaftarRekomendasiBM from "./pages/BranchManager/DaftarRekomendasi";
import FormTambahBukti from "./pages/BranchManager/TambahBuktiForm";
import FormUbahBukti from "./pages/BranchManager/UbahBuktiForm";
import DetailBuktiPelaksanaan from "./pages/BranchManager/BuktiPelaksanaanDetail";
import DaftarBuktiPelaksanaan from "./pages/QAOfficer/DaftarBuktiPelaksanaan";
import FormTolakBukti from "./pages/QAOfficer/TolakBuktiForm";
import NotFound from "./pages/error";
import Login from "./pages/login";
import Logout from "./pages/logout";
import FormDemo from "./pages/formPage";
import AuthorizedRoute from './Components/Route/AuthorizedRoute';
import './App.css';
import RegistrasiRisiko from './pages/ManagerOR/RegistrasiRisiko';
import DetailRisiko from './pages/ManagerOR/DetailRisiko';
import RiskLevel from './pages/ManagerOR/RiskLevel';
import RiskRating from './pages/ManagerOR/RiskRating';

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
            <Route exact path="/hasil-pemeriksaan" component={DaftarHasilPemeriksaan} />
            <Route exact path="/tugas-pemeriksaan" component={DaftarTugasPemeriksaan} />
            <Route exact path="/employee" component={DaftarEmployee} />
            <Route exact path="/employee/tambah" component={FormTambahEmployee} />
            <Route exact path="/employee/detail" component={DetailEmployee} />
            <AuthorizedRoute exact path="/rekomendasi" component={DaftarRekomendasi} />
            <AuthorizedRoute exact path="/rekomendasi/reminder" component={Reminder} />
            <AuthorizedRoute exact path="/risk-level" component={RiskLevel} />
            <AuthorizedRoute exact path="/risk-rating" component={RiskRating} />
            <Route exact path="/bm/rekomendasi" component={DaftarRekomendasiBM} />
            <Route exact path="/bukti-pelaksanaan/tambah" component={FormTambahBukti} />
            <Route exact path="/bukti-pelaksanaan/ubah" component={FormUbahBukti} />
            <Route exact path="/bukti-pelaksanaan/detail" component={DetailBuktiPelaksanaan} />
            <Route exact path="/bukti-pelaksanaan" component={DaftarBuktiPelaksanaan} />
            <Route exact path="/bukti-pelaksanaan/setujui" component={DetailBuktiPelaksanaan} />
            <Route exact path="/bukti-pelaksanaan/tolak" component={FormTolakBukti} />
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
