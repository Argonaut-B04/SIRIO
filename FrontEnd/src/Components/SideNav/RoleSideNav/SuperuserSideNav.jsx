import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { dashboardEmployee, dashboardKantorCabang, pengguna, kantorCabang, hasilPemeriksaan, tugasPemeriksaan, rekomendasi, buktiRekomendasi, konfigurasi } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Superuser
 */
export default class SuperuserSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                {...this.props}
                links={
                    [
                        dashboardEmployee,
                        dashboardKantorCabang,
                        pengguna,
                        kantorCabang,
                        hasilPemeriksaan,
                        tugasPemeriksaan,
                        rekomendasi,
                        buktiRekomendasi,
                        konfigurasi,
                    ]
                }
            />
        );
    }
}
