import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { pengguna, kantorCabang } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Administrator
 */
export default class AdministratorSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                {...this.props}
                links={
                    [
                        pengguna,
                        kantorCabang
                    ]
                }
            />
        );
    }
}
