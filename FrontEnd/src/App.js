import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from "react-router-dom";
import ReactTooltip from 'react-tooltip';

import DaftarRencanaPemeriksaan from './pages/Manager/DaftarRencanaPemeriksaan';
import DetailRencanaPemeriksaan from "./pages/Manager/DetailRencanaPemeriksaan";
import FormTambahRencanaPemeriksaan from "./pages/Manager/FormTambahRencanaPemeriksaan";
import FormUbahRencanaPemeriksaan from "./pages/Manager/FormUbahRencanaPemeriksaan";

import DaftarKantorCabang from './pages/Administrator/DaftarKantorCabang';
import DetailKantorCabang from "./pages/Administrator/DetailKantorCabang";
import FormTambahKantorCabang from "./pages/Administrator/FormTambahKantorCabang";
import FormUbahKantorCabang from "./pages/Administrator/FormUbahKantorCabang";
import ProfilKantorCabang from "./pages/BranchManager/ProfilKantorCabang";

import DaftarSOP from './pages/Manager/DaftarSOP';
import DetailSOP from "./pages/Manager/DetailSOP";
import FormTambahSOP from "./pages/Manager/FormTambahSOP";
import FormUbahSOP from "./pages/Manager/FormUbahSOP";

import DaftarHasilPemeriksaan from "./pages/QAOfficer/DaftarHasilPemeriksaan";
import DaftarTugasPemeriksaan from "./pages/QAOfficer/DaftarTugasPemeriksaan";

import DetailHasilPemeriksaan from "./pages/QAOfficer/DetailHasilPemeriksaan";
import FormTolakHasilPemeriksaan from "./pages/QAOfficer/FormTolakHasilPemeriksaan";
import FormTambahHasilPemeriksaan from "./pages/QAOfficer/FormTambahHasilPemeriksaan";
import FormUbahHasilPemeriksaan from "./pages/QAOfficer/FormUbahHasilPemeriksaan";
import DaftarEmployee from "./pages/Administrator/DaftarEmployee";
import FormTambahEmployee from "./pages/Administrator/FormTambahEmployee";
import FormUbahEmployee from "./pages/Administrator/FormUbahEmployee";
import DetailEmployee from "./pages/Administrator/DetailEmployee";
import DaftarRekomendasi from "./pages/QAOfficer/DaftarRekomendasi";
import Reminder from "./pages/QAOfficer/Reminder";

import FormTambahBukti from "./pages/BranchManager/TambahBuktiForm";
import FormUbahBukti from "./pages/BranchManager/UbahBuktiForm";
import FormTolakBukti from "./pages/QAOfficer/TolakBuktiForm";
import DetailBuktiPelaksanaan from "./pages/BranchManager/BuktiPelaksanaanDetail";
import DaftarBuktiPelaksanaan from "./pages/QAOfficer/DaftarBuktiPelaksanaan";

import DashboardKantorCabang from "./pages/QAOfficer/DashboardKantorCabang";

import Login from "./pages/login";
import Logout from "./pages/logout";
import ChangePassword from "./pages/changePassword";
import AuthorizedRoute from './Components/Route/AuthorizedRoute';
import './App.css';
import RegistrasiRisiko from './pages/ManagerOR/RegistrasiRisiko';
import DetailRisiko from './pages/ManagerOR/DetailRisiko';
import FormRisiko from './pages/ManagerOR/FormRisiko';
import HierarkiRisiko from './pages/ManagerOR/HierarkiRisiko';
import RiskLevel from './pages/ManagerOR/RiskLevel';
import RiskRating from './pages/ManagerOR/RiskRating';
import FormRisikoUbah from './pages/ManagerOR/FormRisikoUbah';
import Error401 from './pages/Errors/Error401';
import ErrorPage from './pages/Errors/Error';
import Error404 from './pages/Errors/Error404';
import TemplateReminder from './pages/QAOfficer/TemplateReminder';
import DashboardStaff from './pages/Supervisor/DashboardStaff';

/**
 * Router utama yang memetakan url ke controller
 */

class App extends React.Component {
  render() {
    return (
      <>
        <ReactTooltip place="top" type="dark" effect="solid" className="sirio_tooltip" />

        <Router>
          <Switch>
            <Route exact path="/" component={Login} />
            <Route exact path="/dashboard-kantor" component={DashboardKantorCabang} />

            <Route exact path="/rencana-pemeriksaan" component={DaftarRencanaPemeriksaan} />
            <Route exact path="/rencana-pemeriksaan/detail" component={DetailRencanaPemeriksaan} />
            <Route exact path="/rencana-pemeriksaan/tambah" component={FormTambahRencanaPemeriksaan} />
            <Route exact path="/rencana-pemeriksaan/ubah" component={FormUbahRencanaPemeriksaan} />

            <Route exact path="/sop" component={DaftarSOP} />
            <Route exact path="/sop/detail" component={DetailSOP} />
            <Route exact path="/sop/tambah" component={FormTambahSOP} />
            <Route exact path="/sop/ubah" component={FormUbahSOP} />

            <Route exact path="/kantor-cabang/ubah" component={FormUbahKantorCabang} />
            <Route exact path="/kantor-cabang/tambah" component={FormTambahKantorCabang} />
            <Route exact path="/kantor-cabang" component={DaftarKantorCabang} />
            <Route exact path="/kantor-cabang/detail" component={DetailKantorCabang} />

            <Route exact path="/hasil-pemeriksaan" component={DaftarHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/detail" component={DetailHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/tolak" component={FormTolakHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/tambah" component={FormTambahHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/ubah" component={FormUbahHasilPemeriksaan} />

            <Route exact path="/profil-kantor" component={ProfilKantorCabang} />

            <Route exact path="/tugas-pemeriksaan" component={DaftarTugasPemeriksaan} />

            <Route exact path="/employee" component={DaftarEmployee} />
            <Route exact path="/employee/tambah" component={FormTambahEmployee} />
            <Route exact path="/employee/ubah" component={FormUbahEmployee} />
            <Route exact path="/employee/detail" component={DetailEmployee} />

            <AuthorizedRoute exact path="/rekomendasi" component={DaftarRekomendasi} />
            <AuthorizedRoute exact path="/rekomendasi/reminder" component={Reminder} />
            <AuthorizedRoute exact path="/rekomendasi/reminder/pengaturan" component={TemplateReminder} />

            <AuthorizedRoute exact path="/risk-level" component={RiskLevel} />
            <AuthorizedRoute exact path="/risk-rating" component={RiskRating} />
            <AuthorizedRoute exact path="/pengaturan-reminder" component={TemplateReminder} />

            <AuthorizedRoute exact path="/bukti-pelaksanaan/tambah" component={FormTambahBukti} />
            <AuthorizedRoute exact path="/bukti-pelaksanaan/ubah" component={FormUbahBukti} />
            <AuthorizedRoute exact path="/bukti-pelaksanaan/detail" component={DetailBuktiPelaksanaan} />
            <AuthorizedRoute exact path="/bukti-pelaksanaan/tolak" component={FormTolakBukti} />
            <AuthorizedRoute exact path="/bukti-pelaksanaan" component={DaftarBuktiPelaksanaan} />

            <AuthorizedRoute exact path="/registrasi-risiko" component={RegistrasiRisiko} />
            <AuthorizedRoute exact path="/registrasi-risiko/detail" component={DetailRisiko} />
            <AuthorizedRoute exact path="/registrasi-risiko/tambah" component={FormRisiko} />
            <AuthorizedRoute exact path="/registrasi-risiko/ubah" component={FormRisikoUbah} />
            <AuthorizedRoute exact path="/registrasi-risiko/ubah-hierarki" component={HierarkiRisiko} />

            <AuthorizedRoute exact path="/dashboard-staff" component={DashboardStaff} />

            <AuthorizedRoute exact path="/change-password" component={ChangePassword} />

            <Route exact path="/login" component={Login} />
            <Route exact path="/logout" component={Logout} />

            <Route exact path="/401" component={Error401} />
            <Route exact path="/error" component={ErrorPage} />
            <Route path="" component={Error404} />
          </Switch>
        </Router>
      </>
    )
  }
}

export default App;
