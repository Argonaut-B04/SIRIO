import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { hasilPemeriksaan, tugasPemeriksaan, rekomendasi, buktiRekomendasi, dashboardKantorCabang } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk QA Officer
 */
export default class QAOfficerSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                {...this.props}
                links={
                    [
                        hasilPemeriksaan,
                        tugasPemeriksaan,
                        rekomendasi,
                        buktiRekomendasi,
                        dashboardKantorCabang
                    ]
                }
            />
        );
    }
}
