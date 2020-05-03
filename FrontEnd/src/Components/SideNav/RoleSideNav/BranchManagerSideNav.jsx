import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { halamanUtama, buktiRekomendasi, dashboard, profilKC } from '../../../Configuration/UrlConfig';

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
                        dashboard,
                        profilKC
                    ]
                }
            />
        );
    }
}
