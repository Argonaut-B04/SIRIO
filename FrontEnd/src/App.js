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
import DetailHasilPemeriksaan from "./pages/QAOfficer/DetailHasilPemeriksaan";
import FormTolakHasilPemeriksaan from "./pages/QAOfficer/FormTolakHasilPemeriksaan";
import FormTambahHasilPemeriksaan from "./pages/QAOfficer/FormTambahHasilPemeriksaan";
import DaftarEmployee from "./pages/Administrator/DaftarEmployee";
import FormTambahEmployee from "./pages/Administrator/FormTambahEmployee";
import FormUbahEmployee from "./pages/Administrator/FormUbahEmployee";
import DetailEmployee from "./pages/Administrator/DetailEmployee";
import DaftarRekomendasi from "./pages/QAOfficer/DaftarRekomendasi";
import Reminder from "./pages/QAOfficer/Reminder";
import DaftarRekomendasiBM from "./pages/BranchManager/DaftarRekomendasi";
import FormBuktiBM from "./pages/BranchManager/FormBuktiPelaksanaan";
import DetailBuktiBM from "./pages/BranchManager/DetailBuktiPelaksanaan";
import DaftarBuktiPelaksanaan from "./pages/QAOfficer/DaftarBuktiPelaksanaan";
import DetailPersetujuanBukti from "./pages/QAOfficer/DetailPersetujuanBukti";
import FormFeedbackBukti from "./pages/QAOfficer/FormFeedbackBukti";
import Login from "./pages/login";
import Logout from "./pages/logout";
import FormDemo from "./pages/formPage";
import AuthorizedRoute from './Components/Route/AuthorizedRoute';
import './App.css';
import RegistrasiRisiko from './pages/ManagerOR/RegistrasiRisiko';
import DetailRisiko from './pages/ManagerOR/DetailRisiko';
import FormRisiko from './pages/ManagerOR/FormRisiko';
import HierarkiRisiko from './pages/ManagerOR/HierarkiRisiko';
import RiskLevel from './pages/ManagerOR/RiskLevel';
import RiskRating from './pages/ManagerOR/RiskRating';
import FormMultiPage from './pages/formMultiPage';
import Error401 from './pages/Errors/Error401';
import Error404 from './pages/Errors/Error404';

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
            <Route exact path="/hasil-pemeriksaan/detail" component={DetailHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/tolak" component={FormTolakHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/tambah" component={FormTambahHasilPemeriksaan} />
            <Route exact path="/tugas-pemeriksaan" component={DaftarTugasPemeriksaan} />
            <Route exact path="/employee" component={DaftarEmployee} />
            <Route exact path="/employee/tambah" component={FormTambahEmployee} />
            <Route exact path="/employee/ubah" component={FormUbahEmployee} />
            <Route exact path="/employee/detail" component={DetailEmployee} />
            <AuthorizedRoute exact path="/rekomendasi" component={DaftarRekomendasi} />
            <AuthorizedRoute exact path="/rekomendasi/reminder" component={Reminder} />
            <AuthorizedRoute exact path="/risk-level" component={RiskLevel} />
            <AuthorizedRoute exact path="/risk-rating" component={RiskRating} />
            <Route exact path="/bm/rekomendasi" component={DaftarRekomendasiBM} />
            <Route exact path="/bukti-pelaksanaan/tambah" component={FormBuktiBM} />
            <Route exact path="/bukti-pelaksanaan/detail" component={DetailBuktiBM} />
            <Route exact path="/bukti-pelaksanaan" component={DaftarBuktiPelaksanaan} />
            <Route exact path="/bukti-pelaksanaan/persetujuan" component={DetailPersetujuanBukti} />
            <Route exact path="/bukti-pelaksanaan/tolak" component={FormFeedbackBukti} />
            <Route exact path="/registrasi-risiko" component={RegistrasiRisiko} />
            <Route exact path="/registrasi-risiko/detail" component={DetailRisiko} />
            <Route exact path="/registrasi-risiko/tambah" component={FormRisiko} />
            <Route exact path="/registrasi-risiko/ubah-hierarki" component={HierarkiRisiko} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/logout" component={Logout} />
            <Route exact path="/Form" component={FormDemo} />
            <Route exact path="/Form-Multi" component={FormMultiPage} />
            <Route exact path="/404" component={Error404} />
            <Route exact path="/401" component={Error401} />
            <Redirect to="/404" />
          </Switch>
        </Router>
      </>
    )
  }
}

export default App;
