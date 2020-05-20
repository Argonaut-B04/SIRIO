import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';
import { dashboardStaff } from '../../../Configuration/UrlConfig';

/**
 * Komponen SideNav untuk Supervisor
 */
export default class SupervisorSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                {...this.props}
                links={
                    [
                        dashboardStaff
                    ]
                }
            />
        );
    }
}
