import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { halamanUtama, rencanaPemeriksaan, konfigurasi } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Manager Operational
 */
export default class ManagerOperationalSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        halamanUtama,
                        rencanaPemeriksaan,
                        konfigurasi
                    ]
                }
                {...this.props}
            />
        );
    }
}
