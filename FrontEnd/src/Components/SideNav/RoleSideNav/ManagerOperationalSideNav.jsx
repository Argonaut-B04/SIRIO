import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { rencanaPemeriksaan, konfigurasi } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Manager Operational
 */
export default class ManagerOperationalSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        rencanaPemeriksaan,
                        konfigurasi
                    ]
                }
                {...this.props}
            />
        );
    }
}
