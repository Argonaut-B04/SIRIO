import React, { Component } from 'react';
import SideNavFramework from '../SideNavFramework';

/**
 * Komponen SideNav untuk Superuser
 */
export default class SuperuserSideNav extends Component {
    render() {
        return (
            <SideNavFramework
                links={
                    [
                        {
                            link: "#",
                            title: "Dashboard Employee",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Dashboard Kantor Cabang",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Pengguna",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Kantor Cabang",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Hasil Pemeriksaan",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Tugas Pemeriksaan",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Rekomendasi",
                            active: false
                        },
                        {
                            link: "#",
                            title: "Bukti Rekomendasi",
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
