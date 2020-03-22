import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

export default class ManagerOperationalSideNav extends Component {
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
                            title: "Rencana Pemeriksaan",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Konfigurasi Registrasi Risiko",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Konfigurasi Risk Level",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Konfigurasi Risk Rating",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Konfigurasi SOP",
                            active: false
                        }
                    ]
                }
            />
        );
    }
}
