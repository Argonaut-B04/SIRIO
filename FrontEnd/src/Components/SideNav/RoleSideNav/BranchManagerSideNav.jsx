import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

/**
 * Komponen SideNav untuk Branch Manager
 */
export default class BranchManagerSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        {
                            link: "#",
                            title: "Halaman Utama",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Rekomendasi",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Dashboard",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Profil Kantor Cabang",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}
