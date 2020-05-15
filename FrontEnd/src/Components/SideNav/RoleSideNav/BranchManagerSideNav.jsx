import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { halamanUtama, buktiRekomendasi, dashboardKantorCabang, profilKC } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Branch Manager
 */
export default class BranchManagerSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                {...this.props}
                links={
                    [
                        halamanUtama,
                        buktiRekomendasi,
                        dashboardKantorCabang,
                        profilKC
                    ]
                }
            />
        );
    }
}
