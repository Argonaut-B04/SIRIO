import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { halamanUtama, dashboard } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Supervisor
 */
export default class SupervisorSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        halamanUtama,
                        dashboard
                    ]
                }
            />
        );
    }
}
