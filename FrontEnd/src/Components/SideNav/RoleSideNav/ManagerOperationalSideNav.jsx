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
                            title: "Konfigurasi",
                            active: false,
                            dropdown: [
                                {
                                    label: "Konfigurasi Risk Rating",
                                    value: "#"
                                }, {
                                    label: "Konfigurasi Registrasi Risiko",
                                    value: "#"
                                }, {
                                    label: "Konfigurasi Risk Level",
                                    value: "#"
                                }, {
                                    label: "Konfigurasi SOP",
                                    value: "#"
                                }
                            ]
                        },
                    ]
                }
            />
        );
    }
}
