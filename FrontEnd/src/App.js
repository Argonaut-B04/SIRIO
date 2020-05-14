import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from "react-router-dom";
import ReactTooltip from 'react-tooltip';

import MainPage from "./pages";

import DaftarRencanaPemeriksaan from './pages/Manager/DaftarRencanaPemeriksaan';
import DetailRencanaPemeriksaan from "./pages/Manager/DetailRencanaPemeriksaan";
import FormTambahRencanaPemeriksaan from "./pages/Manager/FormTambahRencanaPemeriksaan";
import FormUbahRencanaPemeriksaan from "./pages/Manager/FormUbahRencanaPemeriksaan";

import DaftarKantorCabang from './pages/Administrator/DaftarKantorCabang';
import DetailKantorCabang from "./pages/Administrator/DetailKantorCabang";
import FormTambahKantorCabang from "./pages/Administrator/FormTambahKantorCabang";
import FormUbahKantorCabang from "./pages/Administrator/FormUbahKantorCabang.jsx";

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
import DashboadStaff from './pages/Supervisor/DashboadStaff';
import DemoFormPage from './pages/DemoFormPage';
import DemoFormPage2 from './pages/DemoFormPage2';
import DemoFormPage3 from './pages/DemoFormPage3';
import DemoFormPage4 from './pages/DemoFormPage4';

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
            <Route exact path="/demo4" component={DemoFormPage4} />
            <Route exact path="/demo3" component={DemoFormPage3} />
            <Route exact path="/demo2" component={DemoFormPage2} />
            <Route exact path="/demo" component={DemoFormPage} />
            <Route exact path="/" component={MainPage} />
            <Route exact path="/dashboard-kantor" component={DashboardKantorCabang} />

            <Route exact path="/manager/rencanaPemeriksaan" component={DaftarRencanaPemeriksaan} />
            <Route exact path="/manager/rencanaPemeriksaan/detail" component={DetailRencanaPemeriksaan} />
            <Route exact path="/manager/rencanaPemeriksaan/tambah" component={FormTambahRencanaPemeriksaan} />
            <Route exact path="/manager/rencanaPemeriksaan/ubah" component={FormUbahRencanaPemeriksaan} />

            <Route exact path="/administrator/kantorCabang/ubah" component={FormUbahKantorCabang} />
            <Route exact path="/administrator/kantorCabang/tambah" component={FormTambahKantorCabang} />
            <Route exact path="/administrator/kantorCabang" component={DaftarKantorCabang} />
            <Route exact path="/administrator/kantorCabang/detail" component={DetailKantorCabang} />

            <Route exact path="/hasil-pemeriksaan" component={DaftarHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/detail" component={DetailHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/tolak" component={FormTolakHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/tambah" component={FormTambahHasilPemeriksaan} />
            <Route exact path="/hasil-pemeriksaan/ubah" component={FormUbahHasilPemeriksaan} />

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

            <AuthorizedRoute exact path="/dashboard-staff" component={DashboadStaff} />

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
