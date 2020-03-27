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
import DaftarRencanaPemeriksaan from './pages/Manager/DaftarRencanaPemeriksaan';
import DetailRencana from "./pages/Manager/DetailRencanaPemeriksaan";
import FormTambahRencanaPemeriksaan from "./pages/Manager/FormTambahRencanaPemeriksaan";

import DaftarKantorCabang from './pages/Administrator/DaftarKantorCabang';
import DetailKantorCabang from "./pages/Administrator/DetailKantorCabang";
import FormTambahKantorCabang from "./pages/Administrator/FormTambahKantorCabang";

import DaftarRekomendasiBM from "./pages/BranchManager/DaftarRekomendasi";
import DetailBuktiBM from "./pages/BranchManager/DetailBuktiPelaksanaan";
import DaftarBuktiPelaksanaan from "./pages/DaftarBuktiPelaksanaan";
import NotFound from "./pages/error";
import Login from "./pages/login";
import Logout from "./pages/logout";
import FormDemo from "./pages/formPage";
import './App.css';

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
            <Route exact path="/manager/rencanaPemeriksaan" component={DaftarRencanaPemeriksaan} />
            <Route exact path="/manager/rencanaPemeriksaan/detail-rencana" component={DetailRencana} />
            <Route exact path="/manager/rencanaPemeriksaan/form-tambahRencanaPemeriksaan" component={FormTambahRencanaPemeriksaan} />
            
            <Route exact path="/administrator/kantorCabang/form-tambahKantorCabang" component={FormTambahKantorCabang} />
            <Route exact path="/administrator/kantorCabang" component={DaftarKantorCabang} />
            <Route exact path="/administrator/kantorCabang/detail-kantorCabang" component={DetailKantorCabang} />
            <Route exact path="/bm/rekomendasi" component={DaftarRekomendasiBM} />
            <Route exact path="/bm/rekomendasi/detail-bukti" component={DetailBuktiBM} />
            <Route exact path="/bukti-pelaksanaan" component={DaftarBuktiPelaksanaan} />
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
