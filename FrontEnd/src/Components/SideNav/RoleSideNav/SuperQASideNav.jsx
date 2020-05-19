import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { hasilPemeriksaan, tugasPemeriksaan, rekomendasi, buktiRekomendasi, dashboard } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Super QA
 */
export default class SuperQASideNav extends Component {
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
                        dashboard
                    ]
                }
            />
        );
    }
}
