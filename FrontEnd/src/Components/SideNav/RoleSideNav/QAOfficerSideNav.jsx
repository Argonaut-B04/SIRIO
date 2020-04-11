import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { halamanUtama, hasilPemeriksaan, tugasPemeriksaan, rekomendasi, buktiRekomendasi, dashboard } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk QA Officer
 */
export default class QAOfficerSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        halamanUtama,
                        hasilPemeriksaan,
                        tugasPemeriksaan,
                        rekomendasi,
                        buktiRekomendasi,
                        dashboard
                    ]
                }
            />
        );
    }
}
