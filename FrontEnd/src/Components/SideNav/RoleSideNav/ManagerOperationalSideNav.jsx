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
                                    title: "Risk Rating",
                                    link: "#"
                                }, {
                                    title: "Registrasi Risiko",
                                    link: "#"
                                }, {
                                    title: "Risk Level",
                                    link: "#"
                                }, {
                                    title: "SOP",
                                    link: "#"
                                }
                            ]
                        },
                    ]
                }
            />
        );
    }
}
