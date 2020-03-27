import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

/**
 * Komponen SideNav untuk Supervisor
 */
export default class SupervisorSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        {
                            link: "#",
                            title: "Halaman Utama",
                            active: false
                        }, {
                            link: "#",
                            title: "Dashboard",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}
